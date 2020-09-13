package com.jaaakee.homewarpgui.utilities;

import com.jaaakee.homewarpgui.HomeWarpGUI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class WarpUtil {

    public void setWarp(Player player, String warpName, String itemType) {
        HomeWarpGUI.getInstance().warpConfig.set("warps." + warpName + "." + "icon", itemType);
        HomeWarpGUI.getInstance().warpConfig.set("warps." + warpName + "." + "world", player.getWorld().getName());
        HomeWarpGUI.getInstance().warpConfig.set("warps." + warpName + "." + "x", player.getLocation().getBlockX());
        HomeWarpGUI.getInstance().warpConfig.set("warps." + warpName + "." + "y", player.getLocation().getBlockY());
        HomeWarpGUI.getInstance().warpConfig.set("warps." + warpName + "." + "z", player.getLocation().getBlockZ());
        HomeWarpGUI.getInstance().warpConfig.set("warps." + warpName + "." + "pitch", player.getLocation().getPitch());
        HomeWarpGUI.getInstance().warpConfig.set("warps." + warpName + "." + "yaw", player.getLocation().getYaw());
    }

    public Location getWarp(Player player, String warpName) {
        double x = HomeWarpGUI.getInstance().warpConfig.getInt("warps." + warpName + "." + "x") + 0.5D;
        double y = HomeWarpGUI.getInstance().warpConfig.getInt("warps." + warpName + "." + "y");
        double z = HomeWarpGUI.getInstance().warpConfig.getInt("warps." + warpName + "." + "z") + 0.5D;
        float pitch = HomeWarpGUI.getInstance().warpConfig.getInt("warps." + warpName + "." + "pitch");
        float yaw = HomeWarpGUI.getInstance().warpConfig.getInt("warps." + warpName + "." + "yaw");
        String worldName = HomeWarpGUI.getInstance().warpConfig.getString("warps." + warpName + "." + "world");
        World world = Bukkit.getWorld(worldName);
        return new Location(world, x, y, z, yaw, pitch);
    }
}