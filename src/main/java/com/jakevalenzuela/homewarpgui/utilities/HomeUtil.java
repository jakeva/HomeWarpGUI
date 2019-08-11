package com.jakevalenzuela.homewarpgui.utilities;

import com.jakevalenzuela.homewarpgui.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class HomeUtil {

    public void setHome(Player player, String homeName, String itemType) {
        Main.getInstance().homeConfig.set(player.getUniqueId() + "." + homeName + "." + "icon", itemType);
        Main.getInstance().homeConfig.set(player.getUniqueId() + "." + homeName + "." + "world", player.getWorld().getName());
        Main.getInstance().homeConfig.set(player.getUniqueId() + "." + homeName + "." + "x", player.getLocation().getBlockX());
        Main.getInstance().homeConfig.set(player.getUniqueId() + "." + homeName + "." + "y", player.getLocation().getBlockY());
        Main.getInstance().homeConfig.set(player.getUniqueId() + "." + homeName + "." + "z", player.getLocation().getBlockZ());
        Main.getInstance().homeConfig.set(player.getUniqueId() + "." + homeName + "." + "pitch", player.getLocation().getPitch());
        Main.getInstance().homeConfig.set(player.getUniqueId() + "." + homeName + "." + "yaw", player.getLocation().getYaw());
    }

    public Location getHome(Player player, String homeName) {
        double x = Main.getInstance().homeConfig.getInt(player.getUniqueId() + "." + homeName + "." + "x") + 0.5D;
        double y = Main.getInstance().homeConfig.getInt(player.getUniqueId() + "." + homeName + "." + "y");
        double z = Main.getInstance().homeConfig.getInt(player.getUniqueId() + "." + homeName + "." + "z") + 0.5D;
        float pitch = Main.getInstance().homeConfig.getInt(player.getUniqueId() + "." + homeName + "." + "pitch");
        float yaw = Main.getInstance().homeConfig.getInt(player.getUniqueId() + "." + homeName + "." + "yaw");
        String worldName = Main.getInstance().homeConfig.getString(player.getUniqueId() + "." + homeName + "." + "world");
        World world = Bukkit.getWorld(worldName);
        final Location location = new Location(world, x, y, z, yaw, pitch);
        return location;
    }
}