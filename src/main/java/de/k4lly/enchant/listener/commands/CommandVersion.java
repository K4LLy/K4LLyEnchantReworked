package main.java.de.k4lly.enchant.listener.commands;

import main.java.de.k4lly.enchant.controller.PluginController;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandVersion implements CommandExecutor {

    private PluginController controller;

    public CommandVersion(PluginController controller) {
        this.controller = controller;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] args) {
        if (args.length == 0) {
            commandSender.sendMessage(ChatColor.AQUA + "This Server uses K4LLyEnchant v" + controller.getMain().getDescription().getVersion() + ".");
            return true;
        } else {
            return false;
        }
    }
}
