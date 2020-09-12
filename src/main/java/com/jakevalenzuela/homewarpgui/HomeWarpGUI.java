package com.jakevalenzuela.homewarpgui;

import com.jakevalenzuela.homewarpgui.inventories.HomeInventory;
import com.jakevalenzuela.homewarpgui.inventories.WarpInventory;
import com.jakevalenzuela.homewarpgui.listeners.PlayerListener;
import com.jakevalenzuela.homewarpgui.utilities.HomeUtil;
import com.jakevalenzuela.homewarpgui.utilities.WarpUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Set;

public final class HomeWarpGUI extends JavaPlugin {

    private static HomeWarpGUI instance;
    public File homeDataFile, warpDataFile;
    public YamlConfiguration homeConfig, warpConfig;
    private HomeInventory homes = new HomeInventory();
    private WarpInventory warps = new WarpInventory();

    private HomeUtil utilHome = new HomeUtil();
    private WarpUtil utilWarp = new WarpUtil();

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

            } else if (args.length >= 1) {
                if (homeConfig.contains(player.getUniqueId().toString())) {
                    Set<String> homeList = HomeWarpGUI.getInstance().homeConfig.getConfigurationSection(player.getUniqueId().toString()).getKeys(false);

                    String homeName = "";
                    for (int i = 0; i < args.length; i++) {
                        homeName += args[i] + " ";
                    }
                    homeName = homeName.trim();

                    if (homeConfig.getConfigurationSection(player.getUniqueId().toString()).contains(homeName)) {
                        player.teleport(utilHome.getHome(player, homeName));
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

            } else if (args.length >= 1) {
                if (warpConfig.contains("warps")) {
                    Set<String> warpList = HomeWarpGUI.getInstance().warpConfig.getConfigurationSection("warps").getKeys(false);

                    String warpName = "";
                    for (int i = 0; i < args.length; i++) {
                        warpName += args[i] + " ";
                    }
                    warpName = warpName.trim();

                    if (warpConfig.getConfigurationSection("warps").contains(warpName)) {
                        player.teleport(utilWarp.getWarp(player, warpName));
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