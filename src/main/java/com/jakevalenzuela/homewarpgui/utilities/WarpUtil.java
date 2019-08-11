package com.jakevalenzuela.homewarpgui.utilities;

import com.jakevalenzuela.homewarpgui.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class WarpUtil {

    public void setWarp(Player player, String warpName, String itemType) {
        Main.getInstance().warpConfig.set("warps." + warpName + "." + "icon", itemType);
        Main.getInstance().warpConfig.set("warps." + warpName + "." + "world", player.getWorld().getName());
        Main.getInstance().warpConfig.set("warps." + warpName + "." + "x", player.getLocation().getBlockX());
        Main.getInstance().warpConfig.set("warps." + warpName + "." + "y", player.getLocation().getBlockY());
        Main.getInstance().warpConfig.set("warps." + warpName + "." + "z", player.getLocation().getBlockZ());
        Main.getInstance().warpConfig.set("warps." + warpName + "." + "pitch", player.getLocation().getPitch());
        Main.getInstance().warpConfig.set("warps." + warpName + "." + "yaw", player.getLocation().getYaw());
    }

    public Location getWarp(Player player, String warpName) {
        double x = Main.getInstance().warpConfig.getInt("warps." + warpName + "." + "x") + 0.5D;
        double y = Main.getInstance().warpConfig.getInt("warps." + warpName + "." + "y");
        double z = Main.getInstance().warpConfig.getInt("warps." + warpName + "." + "z") + 0.5D;
        float pitch = Main.getInstance().warpConfig.getInt("warps." + warpName + "." + "pitch");
        float yaw = Main.getInstance().warpConfig.getInt("warps." + warpName + "." + "yaw");
        String worldName = Main.getInstance().warpConfig.getString("warps." + warpName + "." + "world");
        World world = Bukkit.getWorld(worldName);
        final Location location = new Location(world, x, y, z, yaw, pitch);
        return location;
    }
}