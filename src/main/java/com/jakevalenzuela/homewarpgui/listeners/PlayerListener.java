package com.jakevalenzuela.homewarpgui.listeners;

import com.jakevalenzuela.homewarpgui.HomeWarpGUI;
import com.jakevalenzuela.homewarpgui.inventories.HomeInventory;
import com.jakevalenzuela.homewarpgui.inventories.WarpInventory;
import com.jakevalenzuela.homewarpgui.utilities.HomeUtil;
import com.jakevalenzuela.homewarpgui.utilities.WarpUtil;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.io.IOException;
import java.util.Set;

public class PlayerListener implements Listener {

    private HomeInventory homes = new HomeInventory();
    private WarpInventory warps = new WarpInventory();

    private HomeUtil UtilHome = new HomeUtil();
    private WarpUtil UtilWarp = new WarpUtil();

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        Inventory inventory = event.getInventory();
        int slotNum = event.getRawSlot();

        String cutPlayerName = player.getName();
        if (cutPlayerName.length() >= 11) {
            cutPlayerName = cutPlayerName.substring(0, 11);
        }

        int homeCount = 0;
        if (HomeWarpGUI.getInstance().homeConfig.contains(player.getUniqueId().toString())) {
            homeCount = HomeWarpGUI.getInstance().homeConfig.getConfigurationSection(player.getUniqueId().toString()).getKeys(false).size();
        }

        int warpCount = 0;
        if (HomeWarpGUI.getInstance().warpConfig.contains("warps")) {
            warpCount = HomeWarpGUI.getInstance().warpConfig.getConfigurationSection("warps").getKeys(false).size();
        }

        if (event.getView().getTitle().equals(cutPlayerName + "'s Homes " + ChatColor.GREEN + "[Teleport]")) {
            event.setCancelled(true);

            if (slotNum < inventory.getSize()) {
                if (slotNum == inventory.getSize() - 2) {
                    player.sendMessage(ChatColor.GREEN + "Enter a name for your home.");
                    player.closeInventory();

                    new AnvilGUI(HomeWarpGUI.getInstance(), player, "Enter a Home Name", (p, reply) -> {
                        player.openInventory(homes.setHomeIcon(reply));
                        return "Opened Set Icon Inventory";
                    });

                } else if (slotNum == inventory.getSize() - 1) {
                    player.openInventory(homes.deleteHomeInventory(player));

                } else if (slotNum >= 0 && slotNum <= homeCount && inventory.getItem(slotNum) != null) {
                    player.teleport(UtilHome.getHome(player, inventory.getItem(slotNum).getItemMeta().getDisplayName()));
                    player.sendMessage(ChatColor.GREEN + "You have arrived at '" + inventory.getItem(slotNum).getItemMeta().getDisplayName() + "'!");
                }
            }

        } else if (event.getView().getTitle().equals(cutPlayerName + "'s Homes " + ChatColor.RED + "[Delete]")) {
            event.setCancelled(true);

            if (slotNum == inventory.getSize() - 1) {
                player.openInventory(homes.createHomeInventory(player));
            } else if ((slotNum >= 0) && (slotNum <= homeCount)) {
                if (inventory.getItem(slotNum) != null) {

                    String homeName = inventory.getItem(slotNum).getItemMeta().getDisplayName();
                    if (HomeWarpGUI.getInstance().homeConfig.contains(player.getUniqueId() + "." + homeName)) {
                        HomeWarpGUI.getInstance().homeConfig.set(player.getUniqueId() + "." + homeName, null);
                        player.sendMessage(ChatColor.GREEN + "Home '" + homeName + "' Deleted!");
                    }

                    if (HomeWarpGUI.getInstance().homeConfig.contains(player.getUniqueId().toString())) {
                        Set<String> homeList = HomeWarpGUI.getInstance().homeConfig.getConfigurationSection(player.getUniqueId().toString()).getKeys(false);
                        if (homeList.size() == 0) {
                            HomeWarpGUI.getInstance().homeConfig.set(player.getUniqueId().toString(), null);
                        }
                    }
                }

                player.openInventory(homes.deleteHomeInventory(player));
            }

        } else if (event.getView().getTitle().equals("Select a Home Icon") && slotNum >= 0 && slotNum < 27) { /* Home Set Icon Inventory Listener */
            event.setCancelled(true);
            player.closeInventory();
            player.updateInventory();

            String homeName = inventory.getItem(slotNum).getItemMeta().getDisplayName();
            if (HomeWarpGUI.getInstance().homeConfig.contains(player.getUniqueId().toString())) {
                Set<String> homelist = HomeWarpGUI.getInstance().homeConfig.getConfigurationSection(player.getUniqueId().toString()).getKeys(false);

                int homeLimit = 0;
                for (int i = 1; i <= 52; i++) {
                    if (player.hasPermission("Homes.limit." + i)) {
                        homeLimit = i;
                    }
                }

                if (homeLimit == 0) {
                    homeLimit = 1;
                }

                if (homelist.size() < homeLimit) {
                    UtilHome.setHome(player, homeName, inventory.getItem(slotNum).getType().name()); /* Create Home */
                    player.sendMessage(ChatColor.GREEN + "Home '" + homeName + "' Created!");
                } else {
                    player.sendMessage(ChatColor.RED + "You have reached your allowed maximum of houses!"); /* Player has reached max amount of houses (set through permissions) */
                }
            } else {
                UtilHome.setHome(player, homeName, inventory.getItem(slotNum).getType().name()); /* Create Home */
                player.sendMessage(ChatColor.GREEN + "Home '" + homeName + "' Created!");
            }

        } else if (event.getView().getTitle().equals("Warps " + ChatColor.GREEN + "[Teleport]")) { /* Warp Teleport/Create Inventory Listener */
            event.setCancelled(true);

            if (slotNum < inventory.getSize()) {
                if (slotNum == inventory.getSize() - 2) {
                    player.sendMessage(ChatColor.GREEN + "Enter a name for the warp.");

                    new AnvilGUI(HomeWarpGUI.getInstance(), player, "Enter a Warp Name", (p, reply) -> {
                        player.openInventory(warps.createSetIconInventory(reply));
                        return "Opened Set Icon Inventory";
                    });

                } else if (slotNum == inventory.getSize() - 1) {
                    player.openInventory(warps.deleteWarpInventory(player));

                } else if (slotNum >= 0 && slotNum <= warpCount && inventory.getItem(slotNum) != null) {
                    player.teleport(UtilWarp.getWarp(player, inventory.getItem(slotNum).getItemMeta().getDisplayName()));
                    player.sendMessage(ChatColor.GREEN + "You have arrived at '" + inventory.getItem(slotNum).getItemMeta().getDisplayName() + "'!");
                }
            }

        } else if (event.getView().getTitle().equals("Warps " + ChatColor.RED + "[Delete]")) {
            event.setCancelled(true);

            if (slotNum == inventory.getSize() - 1) {
                player.openInventory(warps.createWarpInventory(player));
            } else if (slotNum >= 0 && slotNum <= warpCount) {
                if (inventory.getItem(slotNum) != null) {

                    String warpName = inventory.getItem(slotNum).getItemMeta().getDisplayName();
                    if (HomeWarpGUI.getInstance().warpConfig.contains("warps." + warpName)) {
                        HomeWarpGUI.getInstance().warpConfig.set("warps." + warpName, null);
                        player.sendMessage(ChatColor.GREEN + "Warp '" + warpName + "' Deleted!");
                    }

                    if (HomeWarpGUI.getInstance().warpConfig.contains("warps")) {
                        Set<String> warpList = HomeWarpGUI.getInstance().warpConfig.getConfigurationSection("warps").getKeys(false);
                        if (warpList.size() == 0) {
                            HomeWarpGUI.getInstance().warpConfig.set("warps", null);
                        }
                    }
                }

                player.openInventory(warps.deleteWarpInventory(player));
            }
        } else if (event.getView().getTitle().equals("Select a Warp Icon") && slotNum >= 0 && slotNum < 27) {
            event.setCancelled(true);
            player.closeInventory();
            player.updateInventory();

            UtilWarp.setWarp(player, inventory.getItem(slotNum).getItemMeta().getDisplayName(), inventory.getItem(slotNum).getType().toString());
            player.sendMessage(ChatColor.GREEN + "Warp '" + inventory.getItem(slotNum).getItemMeta().getDisplayName() + "' Created!");
        }

        try {
            HomeWarpGUI.getInstance().homeConfig.save(HomeWarpGUI.getInstance().homeDataFile);
            HomeWarpGUI.getInstance().warpConfig.save(HomeWarpGUI.getInstance().warpDataFile);
        } catch (IOException e) {
            Bukkit.getLogger().severe("[HomeWarpGUI]: Error while saving homeData.yml or warpData.yml files.");
        }
    }
}