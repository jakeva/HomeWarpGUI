package com.jakevalenzuela.homewarpgui.utilities;

import com.jakevalenzuela.homewarpgui.mainClass;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class homeUtil {

    public void setHome(Player player, String homeName, String itemType) {
        mainClass.getInstance().homeConfig.set(player.getUniqueId() + "." + homeName + "." + "icon", itemType);
        mainClass.getInstance().homeConfig.set(player.getUniqueId() + "." + homeName + "." + "world", player.getWorld().getName());
        mainClass.getInstance().homeConfig.set(player.getUniqueId() + "." + homeName + "." + "x", player.getLocation().getBlockX());
        mainClass.getInstance().homeConfig.set(player.getUniqueId() + "." + homeName + "." + "y", player.getLocation().getBlockY());
        mainClass.getInstance().homeConfig.set(player.getUniqueId() + "." + homeName + "." + "z", player.getLocation().getBlockZ());
        mainClass.getInstance().homeConfig.set(player.getUniqueId() + "." + homeName + "." + "pitch", player.getLocation().getPitch());
        mainClass.getInstance().homeConfig.set(player.getUniqueId() + "." + homeName + "." + "yaw", player.getLocation().getYaw());
    }

    public Location getHome(Player player, String homeName) {
        double x = mainClass.getInstance().homeConfig.getInt(player.getUniqueId() + "." + homeName + "." + "x") + 0.5D;
        double y = mainClass.getInstance().homeConfig.getInt(player.getUniqueId() + "." + homeName + "." + "y");
        double z = mainClass.getInstance().homeConfig.getInt(player.getUniqueId() + "." + homeName + "." + "z") + 0.5D;
        float pitch = mainClass.getInstance().homeConfig.getInt(player.getUniqueId() + "." + homeName + "." + "pitch");
        float yaw = mainClass.getInstance().homeConfig.getInt(player.getUniqueId() + "." + homeName + "." + "yaw");
        String worldName = mainClass.getInstance().homeConfig.getString(player.getUniqueId() + "." + homeName + "." + "world");
        World world = Bukkit.getWorld(worldName);
        final Location location = new Location(world, x, y, z, yaw, pitch);
        return location;
    }
}