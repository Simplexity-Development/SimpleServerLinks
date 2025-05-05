package simplexity.simpleserverlinks;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.plugin.java.JavaPlugin;
import simplexity.simpleserverlinks.commands.LinksCommand;
import simplexity.simpleserverlinks.commands.ReloadCommand;
import simplexity.simpleserverlinks.configs.ConfigHandler;

@SuppressWarnings("DataFlowIssue")
public final class SimpleServerLinks extends JavaPlugin {

    private static SimpleServerLinks instance;
    private static final MiniMessage miniMessage = MiniMessage.miniMessage();

    @Override
    public void onEnable() {
        instance = this;
        setupConfig();
        ConfigHandler.getInstance().reloadConfigValues();
        getCommand("linkreload").setExecutor(new ReloadCommand());
        getCommand("links").setExecutor(new LinksCommand());
    }

    public static SimpleServerLinks getInstance() {
        return instance;
    }

    public static MiniMessage getMiniMessage() {
        return miniMessage;
    }

    private void setupConfig() {
        saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
