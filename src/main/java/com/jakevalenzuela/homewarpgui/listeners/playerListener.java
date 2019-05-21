package com.jakevalenzuela.homewarpgui.listeners;

import com.jakevalenzuela.homewarpgui.inventories.homeInventory;
import com.jakevalenzuela.homewarpgui.inventories.warpInventory;
import com.jakevalenzuela.homewarpgui.mainClass;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

public class playerListener implements Listener {

    homeInventory homes = new homeInventory();
    warpInventory warps = new warpInventory();

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
        if (mainClass.getInstance().homeConfig.contains(player.getUniqueId().toString())) {
            homeCount = mainClass.getInstance().homeConfig.getConfigurationSection(player.getUniqueId().toString()).getKeys(false).size();
        }

        int warpCount = 0;
        if (mainClass.getInstance().warpConfig.contains("warps.")) {
            warpCount = mainClass.getInstance().warpConfig.getConfigurationSection("warps.").getKeys(false).size();
        }

        if (event.getView().getTitle().equals(cutPlayerName + "'s Homes " + ChatColor.GREEN + "[Teleport]")) {
            event.setCancelled(true);

            if (slotNum < inventory.getSize()) {
                if (slotNum == inventory.getSize() - 2) {
                    player.sendMessage(ChatColor.GREEN + "Enter a name for your home.");
                    player.closeInventory();

                    new AnvilGUI(mainClass.getInstance(), player, "Enter a Home Name", (p, reply) -> {
                        player.openInventory(homes.setHomeIcon(reply));
                        return "Opened Set Icon Inventory";
                    });

                } else if (slotNum == inventory.getSize() - 1) {
                    player.openInventory(homes.deleteHomeInventory(player));

                } else if (slotNum >= 0 && slotNum <= homeCount && inventory.getItem(slotNum) != null) {
                    String homeName = inventory.getItem(slotNum).getItemMeta().getDisplayName();
                    double x = mainClass.getInstance().homeConfig.getInt(player.getUniqueId() + "." + homeName + "." + "x") + 0.5D;
                    double y = mainClass.getInstance().homeConfig.getInt(player.getUniqueId() + "." + homeName + "." + "y");
                    double z = mainClass.getInstance().homeConfig.getInt(player.getUniqueId() + "." + homeName + "." + "z") + 0.5D;
                    float pitch = mainClass.getInstance().homeConfig.getInt(player.getUniqueId() + "." + homeName + "." + "pitch");
                    float yaw = mainClass.getInstance().homeConfig.getInt(player.getUniqueId() + "." + homeName + "." + "yaw");
                    String worldName = mainClass.getInstance().homeConfig.getString(player.getUniqueId() + "." + homeName + "." + "world");
                    World world = Bukkit.getWorld(worldName);
                    final Location location = new Location(world, x, y, z, yaw, pitch);

                    player.teleport(location);
                    player.sendMessage(ChatColor.GREEN + "You have arrived at '" + homeName + "'!");
                }
            }

        } else if (event.getView().getTitle().equals(cutPlayerName + "'s Homes " + ChatColor.RED + "[Delete]")) {
            event.setCancelled(true);

            if (slotNum == inventory.getSize() - 1) {
                player.openInventory(homes.createHomeInventory(player));
            } else if ((slotNum >= 0) && (slotNum <= homeCount)) {
                if (inventory.getItem(slotNum) != null) {

                    String homeName = inventory.getItem(slotNum).getItemMeta().getDisplayName();
                    if (mainClass.getInstance().homeConfig.contains(player.getUniqueId() + "." + homeName)) {
                        mainClass.getInstance().homeConfig.set(player.getUniqueId() + "." + homeName, null);
                        player.sendMessage(ChatColor.GREEN + "Home '" + homeName + "' Deleted!");
                    }

                    if (mainClass.getInstance().homeConfig.contains(player.getUniqueId().toString())) {
                        Set<String> homelist = mainClass.getInstance().homeConfig.getConfigurationSection(player.getUniqueId().toString()).getKeys(false);
                        if (homelist.size() == 0) {
                            mainClass.getInstance().homeConfig.set(player.getUniqueId().toString(), null);
                        }
                    }
                }

                player.openInventory(homes.deleteHomeInventory(player));
            }

        } else if (event.getView().getTitle().equals("Select a Home Icon") && slotNum >= 0 && slotNum < 27) {
            event.setCancelled(true);
            player.closeInventory();
            player.updateInventory();

            String homeName = inventory.getItem(slotNum).getItemMeta().getDisplayName();
            if (mainClass.getInstance().homeConfig.contains(player.getUniqueId().toString())) {
                Set<String> homelist = mainClass.getInstance().homeConfig.getConfigurationSection(player.getUniqueId().toString()).getKeys(false);

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
                    mainClass.getInstance().homeConfig.set(player.getUniqueId() + "." + homeName + "." + "icon", inventory.getItem(slotNum).getType().name());
                    mainClass.getInstance().homeConfig.set(player.getUniqueId() + "." + homeName + "." + "world", player.getWorld().getName());
                    mainClass.getInstance().homeConfig.set(player.getUniqueId() + "." + homeName + "." + "x", player.getLocation().getBlockX());
                    mainClass.getInstance().homeConfig.set(player.getUniqueId() + "." + homeName + "." + "y", player.getLocation().getBlockY());
                    mainClass.getInstance().homeConfig.set(player.getUniqueId() + "." + homeName + "." + "z", player.getLocation().getBlockZ());
                    mainClass.getInstance().homeConfig.set(player.getUniqueId() + "." + homeName + "." + "pitch", player.getLocation().getPitch());
                    mainClass.getInstance().homeConfig.set(player.getUniqueId() + "." + homeName + "." + "yaw", player.getLocation().getYaw());
                    player.sendMessage(ChatColor.GREEN + "Home '" + homeName + "' Created!");
                } else {
                    player.sendMessage(ChatColor.RED + "You have reached your allowed maximum of houses!");
                }
            } else {
                mainClass.getInstance().homeConfig.set(player.getUniqueId() + "." + homeName + "." + "icon", inventory.getItem(slotNum).getType().name());
                mainClass.getInstance().homeConfig.set(player.getUniqueId() + "." + homeName + "." + "world", player.getWorld().getName());
                mainClass.getInstance().homeConfig.set(player.getUniqueId() + "." + homeName + "." + "x", player.getLocation().getBlockX());
                mainClass.getInstance().homeConfig.set(player.getUniqueId() + "." + homeName + "." + "y", player.getLocation().getBlockY());
                mainClass.getInstance().homeConfig.set(player.getUniqueId() + "." + homeName + "." + "z", player.getLocation().getBlockZ());
                mainClass.getInstance().homeConfig.set(player.getUniqueId() + "." + homeName + "." + "pitch", player.getLocation().getPitch());
                mainClass.getInstance().homeConfig.set(player.getUniqueId() + "." + homeName + "." + "yaw", player.getLocation().getYaw());
                player.sendMessage(ChatColor.GREEN + "Home '" + homeName + "' Created!");
            }
        } else if (event.getView().getTitle().equals("Warps " + ChatColor.GREEN + "[Teleport]")) { /* warp creation/teleport menu listener */
            event.setCancelled(true);

            if (slotNum < inventory.getSize()) {
                if (slotNum == inventory.getSize() - 2) {
                    player.sendMessage(ChatColor.GREEN + "Enter a name for the warp.");

                    new AnvilGUI(mainClass.getInstance(), player, "Enter a Warp Name", (p, reply) -> {
                        player.openInventory(warps.createSetIconInventory(reply));
                        return "Opened Set Icon Inventory";
                    });

                } else if (slotNum == inventory.getSize() - 1) {
                    player.openInventory(warps.deleteWarpInventory(player));

                } else if (slotNum >= 0 && slotNum <= warpCount && inventory.getItem(slotNum) != null) {
                    String warpName = inventory.getItem(slotNum).getItemMeta().getDisplayName();
                    double x = mainClass.getInstance().warpConfig.getInt("warps." + warpName + "." + "x") + 0.5D;
                    double y = mainClass.getInstance().warpConfig.getInt("warps." + warpName + "." + "y");
                    double z = mainClass.getInstance().warpConfig.getInt("warps." + warpName + "." + "z") + 0.5D;
                    float pitch = mainClass.getInstance().warpConfig.getInt("warps." + warpName + "." + "pitch");
                    float yaw = mainClass.getInstance().warpConfig.getInt("warps." + warpName + "." + "yaw");
                    String worldName = mainClass.getInstance().warpConfig.getString("warps." + warpName + "." + "world");
                    World world = Bukkit.getWorld(worldName);
                    final Location location = new Location(world, x, y, z, yaw, pitch);

                    player.teleport(location);
                    player.sendMessage(ChatColor.GREEN + "You have arrived at '" + warpName + "'!");
                }
            }
        } else if (event.getView().getTitle().equals("Warps " + ChatColor.RED + "[Delete]")) {
            event.setCancelled(true);

            if (slotNum == inventory.getSize() - 1) {
                player.openInventory(warps.createWarpInventory(player));
            } else if ((slotNum >= 0) && (slotNum <= warpCount)) {
                if (inventory.getItem(slotNum) != null) {

                    String warpName = inventory.getItem(slotNum).getItemMeta().getDisplayName();
                    if (mainClass.getInstance().warpConfig.contains("warps." + warpName)) {
                        mainClass.getInstance().warpConfig.set("warps." + warpName, null);
                        player.sendMessage(ChatColor.GREEN + "Warp '" + warpName + "' Deleted!");
                    }

                    if (mainClass.getInstance().warpConfig.contains("warps")) {
                        Set<String> warpList = mainClass.getInstance().warpConfig.getConfigurationSection("warps").getKeys(false);
                        if (warpList.size() == 0) {
                            mainClass.getInstance().warpConfig.set("warps", null);
                        }
                    }
                }

                player.openInventory(warps.deleteWarpInventory(player));
            }
        } else if (event.getView().getTitle().equals("Select a Warp Icon") && slotNum >= 0 && slotNum < 27) {
            event.setCancelled(true);
            player.closeInventory();
            player.updateInventory();

            String warpName = inventory.getItem(slotNum).getItemMeta().getDisplayName();
            mainClass.getInstance().warpConfig.set("warps." + warpName + "." + "icon", inventory.getItem(slotNum).getType().name());
            mainClass.getInstance().warpConfig.set("warps." + warpName + "." + "world", player.getWorld().getName());
            mainClass.getInstance().warpConfig.set("warps." + warpName + "." + "x", player.getLocation().getBlockX());
            mainClass.getInstance().warpConfig.set("warps." + warpName + "." + "y", player.getLocation().getBlockY());
            mainClass.getInstance().warpConfig.set("warps." + warpName + "." + "z", player.getLocation().getBlockZ());
            mainClass.getInstance().warpConfig.set("warps." + warpName + "." + "pitch", player.getLocation().getPitch());
            mainClass.getInstance().warpConfig.set("warps." + warpName + "." + "yaw", player.getLocation().getYaw());
            player.sendMessage(ChatColor.GREEN + "Warp '" + warpName + "' Created!");
        }

        try {
            mainClass.getInstance().homeConfig.save(mainClass.getInstance().homeDataFile);
            mainClass.getInstance().warpConfig.save(mainClass.getInstance().warpDataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}