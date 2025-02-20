package io.github.swagree.repokefj;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private static Main plugin;

    public static Main getPlugin() {
        return plugin;
    }

    public static void setPlugin(Main plugin) {
        Main.plugin = plugin;
    }

    @Override
    public void onEnable() {
        setPlugin(this);
        Bukkit.getConsoleSender().sendMessage("§7[RePokeFJ] §b作者§fSwagRee §cQQ:§f352208610");
        getServer().getPluginManager().registerEvents(new PokemonFJListener(), this);
        getCommand("rfj").setExecutor(new CommandFJ());

        loadConfig();
    }

    private void loadConfig() {
        saveDefaultConfig();
        reloadConfig();
    }
}
 