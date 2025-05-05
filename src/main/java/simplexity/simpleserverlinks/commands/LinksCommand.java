package simplexity.simpleserverlinks.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import simplexity.simpleserverlinks.Link;
import simplexity.simpleserverlinks.SimpleServerLinks;
import simplexity.simpleserverlinks.configs.ConfigHandler;
import simplexity.simpleserverlinks.configs.Message;

import java.util.List;

public class LinksCommand implements CommandExecutor {

    private final MiniMessage miniMessage = SimpleServerLinks.getMiniMessage();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        List<Link> serverLinks = ConfigHandler.getInstance().getLinks();
        if (serverLinks.isEmpty()) {
            sender.sendRichMessage(Message.NO_LINKS.getMessage());
            return false;
        }
        Component componentToSend = miniMessage.deserialize(Message.LINK_HEADER.getMessage());
        for (Link link : serverLinks) {
            Component componentToAdd = miniMessage.deserialize(Message.LINK_LAYOUT.getMessage(),
                    Placeholder.component("title", link.displayName()),
                    Placeholder.parsed("link", String.valueOf(link.uri())),
                    Placeholder.component("desc", link.description()));
            componentToSend = componentToSend.append(componentToAdd);
        }
        sender.sendMessage(componentToSend);
        return false;
    }
}
