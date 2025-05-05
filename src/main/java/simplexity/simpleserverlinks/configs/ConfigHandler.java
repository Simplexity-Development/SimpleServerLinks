package simplexity.simpleserverlinks.configs;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Server;
import org.bukkit.ServerLinks;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import simplexity.simpleserverlinks.Link;
import simplexity.simpleserverlinks.SimpleServerLinks;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;


@SuppressWarnings({"UnstableApiUsage", "RedundantIfStatement"})
public class ConfigHandler {

    private static ConfigHandler instance;

    public ConfigHandler() {
    }

    public static ConfigHandler getInstance() {
        if (instance == null) instance = new ConfigHandler();
        return instance;
    }

    private final Logger logger = SimpleServerLinks.getInstance().getLogger();
    private final MiniMessage miniMessage = SimpleServerLinks.getMiniMessage();

    private final ArrayList<Link> serverLinks = new ArrayList<>();

    public void reloadConfigValues() {
        SimpleServerLinks.getInstance().reloadConfig();
        FileConfiguration config = SimpleServerLinks.getInstance().getConfig();
        validateLinks(config);
        setLinks();
        LocaleHandler.getInstance().reloadLocale();
    }

    public void validateLinks(FileConfiguration config) {
        ConfigurationSection links = config.getConfigurationSection("links");
        if (links == null) {
            logger.severe("Links section was not found, please be sure that you did not use TAB instead of SPACE");
            return;
        }
        Set<String> linkKeys = links.getKeys(false);
        serverLinks.clear();
        for (String key : linkKeys) {
            ConfigurationSection configSection = config.getConfigurationSection("links." + key);
            if (configSection == null) {
                logger.warning("An issue occurred trying to validate the '" + key + "' section, please make sure all spacing is correct and that TAB was not used instead of SPACE");
                continue;
            }
            String url = configSection.getString("url");
            String displayNameString = configSection.getString("display-name", "HEY THIS IS CONFIGURED WRONG, PUT A NAME HERE");
            String descriptionString = configSection.getString("description", "This is the default description, you probably wanna change this if you're seeing it");
            String typeString = configSection.getString("type");
            if (url == null || url.isEmpty()) {
                logger.warning("The url for the link '" + key + "' was empty or null. Please check your config.");
                continue;
            }
            boolean usingType = true;
            if (typeString == null || typeString.isEmpty() || typeString.equalsIgnoreCase("none")) usingType = false;
            ServerLinks.Type linkType = null;
            if (usingType) {
                try{
                    linkType = ServerLinks.Type.valueOf(typeString);
                } catch (IllegalArgumentException e) {
                    logger.warning("Invalid link type provided on '" + key + "', please check that the type is one of the ones listed at https://jd.papermc.io/paper/1.21.5/org/bukkit/ServerLinks.Type.html");
                    continue;
                }
            }
            try {
                URI uri = new URI(url);
                Component displayNameComponent = miniMessage.deserialize(displayNameString);
                Component descriptionComponent = miniMessage.deserialize(descriptionString);
                Link link = new Link(uri, displayNameComponent, descriptionComponent, linkType);
                serverLinks.add(link);
            } catch (URISyntaxException e) {
                logger.warning("The URL provided is invalid. Please make sure that you have provided the full url such as 'https://www.minecraft.net/en-us' and not 'minecraft.net'");
            }
        }
    }

    private void setLinks(){
        Server server = SimpleServerLinks.getInstance().getServer();
        List<ServerLinks.ServerLink> currentLinks = server.getServerLinks().getLinks();
        for (ServerLinks.ServerLink serverLink : currentLinks) {
            server.getServerLinks().removeLink(serverLink);
        }
        for (Link link : serverLinks) {
            if (link.linkType() == null) {
                server.getServerLinks().addLink(link.displayName(), link.uri());
                continue;
            }
            server.getServerLinks().addLink(link.linkType(), link.uri());
        }
    }

    public List<Link> getLinks(){
        return serverLinks;
    }
}
