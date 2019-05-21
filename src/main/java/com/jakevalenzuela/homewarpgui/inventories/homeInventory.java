package com.jakevalenzuela.homewarpgui.inventories;

import com.jakevalenzuela.homewarpgui.mainClass;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Set;

public class homeInventory {

    public Inventory createHomeInventory(Player player) {
        String newPlayerName = player.getName();

        if (player.getName().length() >= 11) {
            newPlayerName = player.getName().substring(0, 11);
        }

        Inventory inventory = Bukkit.getServer().createInventory(null, 9, newPlayerName + "'s Homes " + ChatColor.GREEN + "[Teleport]");
        int count = 0;
        if (mainClass.getInstance().homeConfig.contains(player.getUniqueId().toString())) {
            Set<String> homeList = mainClass.getInstance().homeConfig.getConfigurationSection(player.getUniqueId().toString()).getKeys(false);

            int rows = 1;
            if (homeList.size() > 7) {
                for (int i = 0; i < homeList.size(); i++) {
                    if ((i + 2) % 9 == 0) {
                        rows++;
                    }
                }
            }

            int size = rows * 9;
            if (size > 54) {
                size = 54;
            }

            inventory = Bukkit.getServer().createInventory(null, size, newPlayerName + "'s Homes " + ChatColor.GREEN + "[Teleport]");
            for (String homes : homeList) {
                String iconName = mainClass.getInstance().homeConfig.getString(player.getUniqueId().toString() + "." + homes + "." + "icon");
                ItemStack homeItem = new ItemStack(Material.getMaterial(iconName));
                ItemMeta homeItemMeta = homeItem.getItemMeta();
                homeItemMeta.setDisplayName(String.valueOf(homes));
                homeItem.setItemMeta(homeItemMeta);
                inventory.setItem(count, homeItem);
                count++;
            }

            ItemStack setHomeItem = new ItemStack(Material.GREEN_WOOL);
            ItemMeta setHomeItemMeta = setHomeItem.getItemMeta();
            setHomeItemMeta.setDisplayName("Set Home");
            setHomeItem.setItemMeta(setHomeItemMeta);
            inventory.setItem(size - 2, setHomeItem);

            ItemStack delHomeItem = new ItemStack(Material.RED_WOOL);
            ItemMeta delHomeItemMeta = delHomeItem.getItemMeta();
            delHomeItemMeta.setDisplayName("Delete Home");
            delHomeItem.setItemMeta(delHomeItemMeta);
            inventory.setItem(size - 1, delHomeItem);
        } else {
            ItemStack setHomeItem = new ItemStack(Material.GREEN_WOOL);
            ItemMeta setHomeItemMeta = setHomeItem.getItemMeta();
            setHomeItemMeta.setDisplayName("Set Home");
            setHomeItem.setItemMeta(setHomeItemMeta);
            inventory.setItem(7, setHomeItem);

            ItemStack delHomeItem = new ItemStack(Material.RED_WOOL);
            ItemMeta delHomeItemMeta = delHomeItem.getItemMeta();
            delHomeItemMeta.setDisplayName("Delete Home");
            delHomeItem.setItemMeta(delHomeItemMeta);
            inventory.setItem(8, delHomeItem);
        }
        return inventory;
    }

    public Inventory deleteHomeInventory(Player player) {
        String newPlayerName = player.getName();

        if (player.getName().length() >= 11) {
            newPlayerName = player.getName().substring(0, 11);
        }

        Inventory inventory = Bukkit.getServer().createInventory(null, 9, newPlayerName + "'s Homes " + ChatColor.RED + "[Delete]");
        int count = 0;

        if (mainClass.getInstance().homeConfig.contains(player.getUniqueId().toString())) {
            Set<String> homeList = mainClass.getInstance().homeConfig.getConfigurationSection(player.getUniqueId().toString()).getKeys(false);
            int rows = 1;
            if (homeList.size() > 7) {
                for (int i = 0; i < homeList.size(); i++) {
                    if ((i + 1) % 9 == 0) {
                        rows++;
                    }
                }
            }

            int size = rows * 9;
            if (size > 54) {
                size = 54;
            }

            inventory = Bukkit.getServer().createInventory(null, size, newPlayerName + "'s Homes " + ChatColor.RED + "[Delete]");
            for (String homeName : homeList) {
                String iconName = mainClass.getInstance().homeConfig.getString(player.getUniqueId().toString() + "." + homeName + "." + "icon");
                ItemStack homeItem = new ItemStack(Material.getMaterial(iconName));
                ItemMeta homeItemMeta = homeItem.getItemMeta();
                homeItemMeta.setDisplayName(String.valueOf(homeName));
                homeItem.setItemMeta(homeItemMeta);
                inventory.setItem(count, homeItem);
                count++;
            }

            ItemStack delHomeItem = new ItemStack(Material.RED_WOOL);
            ItemMeta delHomeItemMeta = delHomeItem.getItemMeta();
            delHomeItemMeta.setDisplayName("Back");
            delHomeItem.setItemMeta(delHomeItemMeta);
            inventory.setItem(size - 1, delHomeItem);
        } else {
            ItemStack delHomeItem = new ItemStack(Material.RED_WOOL);
            ItemMeta delHomeItemMeta = delHomeItem.getItemMeta();
            delHomeItemMeta.setDisplayName("Back");
            delHomeItem.setItemMeta(delHomeItemMeta);
            inventory.setItem(8, delHomeItem);
        }
        return inventory;
    }

    public Inventory setHomeIcon(String homeName) {
        Inventory inventory = Bukkit.getServer().createInventory(null, 27, "Select a Home Icon");
        for (int i = 0; i < blocks.length; i++) {
            ItemStack iconItem = new ItemStack(blocks[i]);
            ItemMeta iconItemMeta = iconItem.getItemMeta();
            iconItemMeta.setDisplayName(homeName);
            iconItem.setItemMeta(iconItemMeta);
            inventory.setItem(i, iconItem);
        }
        return inventory;
    }

    private Material[] blocks = {
            Material.RED_BED,
            Material.CHEST,
            Material.CRAFTING_TABLE,
            Material.FURNACE,
            Material.JUKEBOX,
            Material.BOOKSHELF,
            Material.ENCHANTING_TABLE,
            Material.BRICKS,
            Material.SPRUCE_SIGN,
            Material.GRASS_BLOCK,
            Material.SAND,
            Material.STONE,
            Material.OAK_LOG,
            Material.GRANITE,
            Material.DIORITE,
            Material.BEDROCK,
            Material.SPONGE,
            Material.ICE,
            Material.COAL_ORE,
            Material.LAPIS_ORE,
            Material.IRON_ORE,
            Material.GOLD_ORE,
            Material.DIAMOND_ORE,
            Material.OAK_LEAVES,
            Material.RED_MUSHROOM_BLOCK,
            Material.CACTUS,
            Material.SLIME_BLOCK
    };
}