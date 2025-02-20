package io.github.swagree.repokefj;


import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class CommandFJ implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command1, String label, String[] args) {


        if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
            sendHelp(sender);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "reload":
                reloadAllConfig(sender);
                return true;
            case "open":
                Player player = (Player) sender;
                GuiMain.openGui(player);
                return true;
        }
        return false;
    }


    private static void reloadAllConfig(CommandSender sender) {
        Main.getPlugin().reloadConfig();
        sender.sendMessage(("§b重载成功"));
    }
    private static void sendHelp(CommandSender sender) {
        sender.sendMessage("§b<§m*-----=======§b热宝可梦分解§b §m=======-----§b>");

        sender.sendMessage("§e/rfj open §f- 打开分解界面");
        sender.sendMessage("§e/rfj reload §f 重载插件");
    }



}