package com.jaaakee.homewarpgui;

import com.jaaakee.homewarpgui.inventories.HomeInventory;
import com.jaaakee.homewarpgui.inventories.WarpInventory;
import com.jaaakee.homewarpgui.listeners.PlayerListener;
import com.jaaakee.homewarpgui.utilities.HomeUtil;
import com.jaaakee.homewarpgui.utilities.WarpUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public final class HomeWarpGUI extends JavaPlugin {

    private static HomeWarpGUI instance;
    public File homeDataFile, warpDataFile;
    public YamlConfiguration homeConfig, warpConfig;
    private final HomeInventory homes = new HomeInventory();
    private final WarpInventory warps = new WarpInventory();

    private final HomeUtil utilHome = new HomeUtil();
    private final WarpUtil utilWarp = new WarpUtil();

    public HomeWarpGUI() {
        instance = this;
    }

    public static HomeWarpGUI getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);

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
            Bukkit.getLogger().severe("[HomeWarpGUI]: Error while saving homeData.yml or warpData.yml files.");
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) { /* Check if Command Sender is Console */
            if (command.getName().equalsIgnoreCase("home") || command.getName().equalsIgnoreCase("warp")) {
                sender.sendMessage("[HomeWarpGUI]: You must be a player.");
                return true;
            }
        }

        Player player = (Player) sender;
        if (command.getName().equalsIgnoreCase("home")) { /* Home Command */

            if (args.length == 0) {
                player.openInventory(homes.createHomeInventory(player));

            } else {
                if (homeConfig.contains(player.getUniqueId().toString())) {

                    StringBuilder homeName = new StringBuilder();
                    for (String arg : args) {
                        homeName.append(arg).append(" ");
                    }
                    homeName = new StringBuilder(homeName.toString().trim());

                    if (Objects.requireNonNull(homeConfig.getConfigurationSection(player.getUniqueId().toString())).contains(homeName.toString())) {
                        player.teleport(utilHome.getHome(player, homeName.toString()));
                        player.sendMessage(ChatColor.GREEN + "You have arrived at '" + homeName + "'!");
                    } else {
                        player.sendMessage(ChatColor.RED + "Invalid home name. /home <name>");
                    }
                }
            }
            return true;

        } else if (command.getName().equals("warp")) { /* Warp Command */

            if (args.length == 0) {
                player.openInventory(warps.createWarpInventory(player));

            } else {
                if (warpConfig.contains("warps")) {
                    StringBuilder warpName = new StringBuilder();
                    for (String arg : args) {
                        warpName.append(arg).append(" ");
                    }
                    warpName = new StringBuilder(warpName.toString().trim());

                    if (Objects.requireNonNull(warpConfig.getConfigurationSection("warps")).contains(warpName.toString())) {
                        player.teleport(utilWarp.getWarp(player, warpName.toString()));
                        player.sendMessage(ChatColor.GREEN + "You have arrived at '" + warpName + "'!");
                    } else {
                        player.sendMessage(ChatColor.RED + "Invalid warp name. /warp <name>");
                    }
                }
            }
            return true;
        }
        return false;
    }
}