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

public class warpInventory {

    public Inventory createWarpInventory(Player player) {

        Inventory inventory = Bukkit.getServer().createInventory(null, 9, "Warps " + ChatColor.GREEN + "[Teleport]");
        int count = 0;
        if (mainClass.getInstance().warpConfig.contains("warps")) {
            Set<String> warpList = mainClass.getInstance().warpConfig.getConfigurationSection("warps").getKeys(false);

            int rows = 1;
            if (warpList.size() > 7) {
                for (int i = 0; i < warpList.size(); i++) {
                    if (i + 2 % 9 == 0) {
                        rows++;
                    }
                }
            }

            int size = rows * 9;
            if (size > 54) {
                size = 54;
            }

            inventory = Bukkit.getServer().createInventory(null, size, "Warps " + ChatColor.GREEN + "[Teleport]");
            for (String warps : warpList) {
                String iconName = mainClass.getInstance().warpConfig.getString("warps." + warps + "." + "icon");
                ItemStack warpItem = new ItemStack(Material.getMaterial(iconName));
                ItemMeta warpItemMeta = warpItem.getItemMeta();
                warpItemMeta.setDisplayName(String.valueOf(warps));
                warpItem.setItemMeta(warpItemMeta);
                inventory.setItem(count, warpItem);
                count++;
            }

            ItemStack setWarpItem = new ItemStack(Material.GREEN_WOOL);
            ItemMeta setWarpItemMeta = setWarpItem.getItemMeta();
            setWarpItemMeta.setDisplayName("Set Warp");
            setWarpItem.setItemMeta(setWarpItemMeta);
            inventory.setItem(size - 2, setWarpItem);

            ItemStack delWarpItem = new ItemStack(Material.RED_WOOL);
            ItemMeta delWarpItemMeta = delWarpItem.getItemMeta();
            delWarpItemMeta.setDisplayName("Delete Warp");
            delWarpItem.setItemMeta(delWarpItemMeta);
            inventory.setItem(size - 1, delWarpItem);
        } else {
            ItemStack setWarpItem = new ItemStack(Material.GREEN_WOOL);
            ItemMeta setWarpItemMeta = setWarpItem.getItemMeta();
            setWarpItemMeta.setDisplayName("Set Warp");
            setWarpItem.setItemMeta(setWarpItemMeta);
            inventory.setItem(7, setWarpItem);

            ItemStack delWarpItem = new ItemStack(Material.RED_WOOL);
            ItemMeta delWarpItemMeta = delWarpItem.getItemMeta();
            delWarpItemMeta.setDisplayName("Delete Warp");
            delWarpItem.setItemMeta(delWarpItemMeta);
            inventory.setItem(8, delWarpItem);
        }
        return inventory;
    }

    public Inventory deleteWarpInventory(Player player) {
        Inventory inventory = Bukkit.getServer().createInventory(null, 9, "Warps " + ChatColor.RED + "[Delete]");
        int count = 0;

        if (mainClass.getInstance().warpConfig.contains("warps")) {
            Set<String> warpList = mainClass.getInstance().warpConfig.getConfigurationSection("warps").getKeys(false);
            int rows = 1;
            if (warpList.size() > 7) {
                for (int i = 0; i < warpList.size(); i++) {
                    if ((i + 1) % 9 == 0) {
                        rows++;
                    }
                }
            }

            int size = rows * 9;
            if (size > 54) {
                size = 54;
            }

            inventory = Bukkit.getServer().createInventory(null, size, "Warps " + ChatColor.RED + "[Delete]");
            for (String warps : warpList) {
                String iconName = mainClass.getInstance().warpConfig.getString("warps." + warps + "." + "icon");
                ItemStack warpItem = new ItemStack(Material.getMaterial(iconName));
                ItemMeta warpItemMeta = warpItem.getItemMeta();
                warpItemMeta.setDisplayName(String.valueOf(warps));
                warpItem.setItemMeta(warpItemMeta);
                inventory.setItem(count, warpItem);
                count++;
            }

            ItemStack delWarpItem = new ItemStack(Material.RED_WOOL);
            ItemMeta delWarpItemMeta = delWarpItem.getItemMeta();
            delWarpItemMeta.setDisplayName("Back");
            delWarpItem.setItemMeta(delWarpItemMeta);
            inventory.setItem(size - 1, delWarpItem);
        } else {
            ItemStack delWarpItem = new ItemStack(Material.RED_WOOL);
            ItemMeta delWarpItemMeta = delWarpItem.getItemMeta();
            delWarpItemMeta.setDisplayName("Back");
            delWarpItem.setItemMeta(delWarpItemMeta);
            inventory.setItem(8, delWarpItem);
        }
        return inventory;
    }

    public Inventory createSetIconInventory(String warpName) {
        Inventory inventory = Bukkit.getServer().createInventory(null, 27, "Select a Warp Icon");
        for (int i = 0; i < blocks.length; i++) {
            ItemStack iconItem = new ItemStack(blocks[i]);
            ItemMeta iconItemMeta = iconItem.getItemMeta();
            iconItemMeta.setDisplayName(warpName);
            iconItem.setItemMeta(iconItemMeta);
            inventory.setItem(i, iconItem);
        }
        return inventory;
    }

    private Material[] blocks = {
            Material.GLOWSTONE,
            Material.MYCELIUM,
            Material.FISHING_ROD,
            Material.WATER_BUCKET,
            Material.LAVA_BUCKET,
            Material.SPRUCE_SIGN,
            Material.ANVIL,
            Material.MINECART,
            Material.SAND,
            Material.CHEST,
            Material.CRAFTING_TABLE,
            Material.ENCHANTING_TABLE,
            Material.END_PORTAL_FRAME,
            Material.JUKEBOX,
            Material.BOOKSHELF,
            Material.BEDROCK,
            Material.STONE,
            Material.ICE,
            Material.GRASS_BLOCK,
            Material.OAK_SAPLING,
            Material.CACTUS,
            Material.RED_MUSHROOM_BLOCK,
            Material.WHEAT,
            Material.OAK_LEAVES,
            Material.RED_BED,
            Material.POPPY,
            Material.LILY_PAD
    };
}