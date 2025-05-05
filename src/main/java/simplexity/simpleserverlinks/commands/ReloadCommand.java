package simplexity.simpleserverlinks.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import simplexity.simpleserverlinks.configs.ConfigHandler;
import simplexity.simpleserverlinks.configs.Message;

public class ReloadCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        ConfigHandler.getInstance().reloadConfigValues();
        sender.sendRichMessage(Message.CONFIG_RELOADED.getMessage());
        return false;
    }
}
