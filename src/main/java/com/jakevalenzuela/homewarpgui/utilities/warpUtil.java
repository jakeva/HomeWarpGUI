package com.jakevalenzuela.homewarpgui.utilities;

import com.jakevalenzuela.homewarpgui.mainClass;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class warpUtil {

    public void setWarp(Player player, String warpName, String itemType) {
        mainClass.getInstance().warpConfig.set("warps." + warpName + "." + "icon", itemType);
        mainClass.getInstance().warpConfig.set("warps." + warpName + "." + "world", player.getWorld().getName());
        mainClass.getInstance().warpConfig.set("warps." + warpName + "." + "x", player.getLocation().getBlockX());
        mainClass.getInstance().warpConfig.set("warps." + warpName + "." + "y", player.getLocation().getBlockY());
        mainClass.getInstance().warpConfig.set("warps." + warpName + "." + "z", player.getLocation().getBlockZ());
        mainClass.getInstance().warpConfig.set("warps." + warpName + "." + "pitch", player.getLocation().getPitch());
        mainClass.getInstance().warpConfig.set("warps." + warpName + "." + "yaw", player.getLocation().getYaw());
    }

    public Location getWarp(Player player, String warpName) {
        double x = mainClass.getInstance().warpConfig.getInt("warps." + warpName + "." + "x") + 0.5D;
        double y = mainClass.getInstance().warpConfig.getInt("warps." + warpName + "." + "y");
        double z = mainClass.getInstance().warpConfig.getInt("warps." + warpName + "." + "z") + 0.5D;
        float pitch = mainClass.getInstance().warpConfig.getInt("warps." + warpName + "." + "pitch");
        float yaw = mainClass.getInstance().warpConfig.getInt("warps." + warpName + "." + "yaw");
        String worldName = mainClass.getInstance().warpConfig.getString("warps." + warpName + "." + "world");
        World world = Bukkit.getWorld(worldName);
        final Location location = new Location(world, x, y, z, yaw, pitch);
        return location;
    }
}