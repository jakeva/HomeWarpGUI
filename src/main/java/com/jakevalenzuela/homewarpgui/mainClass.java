package com.jakevalenzuela.homewarpgui;

import com.jakevalenzuela.homewarpgui.inventories.homeInventory;
import com.jakevalenzuela.homewarpgui.inventories.warpInventory;
import com.jakevalenzuela.homewarpgui.listeners.playerListener;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;

public final class mainClass extends JavaPlugin {

    public File homeDataFile, warpDataFile;
    public YamlConfiguration homeConfig, warpConfig;
    private static mainClass instance;

    homeInventory homes = new homeInventory();
    warpInventory warps = new warpInventory();

    public mainClass() {
        instance = this;
    }

    public static mainClass getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {

        getServer().getPluginManager().registerEvents(new playerListener(), this);

        if (!instance.getDataFolder().exists()) {
            instance.getDataFolder().mkdir();
        }

        homeDataFile = new File(getDataFolder(), "homeData.yml");
        warpDataFile = new File(getDataFolder(), "warpData.yml");

        if (!homeDataFile.exists()) {
            try {
                homeDataFile.createNewFile();
            } catch (IOException e) {
                Bukkit.getLogger().severe("[HomeWarpGUI]: Error while creating homeData.yml");
            }
        }

        if (!warpDataFile.exists()) {
            try {
                warpDataFile.createNewFile();
            } catch (IOException e) {
                Bukkit.getLogger().severe("[HomeWarpGUI]: Error while creating warpData.yml");
            }
        }

        homeConfig = YamlConfiguration.loadConfiguration(homeDataFile);
        warpConfig = YamlConfiguration.loadConfiguration(warpDataFile);
    }

    @Override
    public void onDisable() {
        try {
            homeConfig.save(homeDataFile);
            warpConfig.save(warpDataFile);
        } catch (IOException e) {
            Bukkit.getLogger().severe("[HomeWarpGUI]: Error while saving files.");
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] arguments) {
        if (!(sender instanceof Player)) {
            if (command.getName().equalsIgnoreCase("home") || command.getName().equalsIgnoreCase("warp")) {
                sender.sendMessage("[HomeWarpGUI]: You must be a player.");
                return true;
            }
        }

        Player player = (Player)sender;

        if (command.getName().equalsIgnoreCase("home")) {
            player.openInventory(homes.createHomeInventory(player));
            return true;
        } else if (command.getName().equals("warp")) {
            player.openInventory(warps.createWarpInventory(player));
            return true;
        }
        return false;
    }
}