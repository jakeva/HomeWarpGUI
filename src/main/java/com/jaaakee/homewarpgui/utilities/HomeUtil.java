package com.jaaakee.homewarpgui.utilities;

import com.jaaakee.homewarpgui.HomeWarpGUI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class HomeUtil {

    public void setHome(Player player, String homeName, String itemType) {
        HomeWarpGUI.getInstance().homeConfig.set(player.getUniqueId() + "." + homeName + "." + "icon", itemType);
        HomeWarpGUI.getInstance().homeConfig.set(player.getUniqueId() + "." + homeName + "." + "world", player.getWorld().getName());
        HomeWarpGUI.getInstance().homeConfig.set(player.getUniqueId() + "." + homeName + "." + "x", player.getLocation().getBlockX());
        HomeWarpGUI.getInstance().homeConfig.set(player.getUniqueId() + "." + homeName + "." + "y", player.getLocation().getBlockY());
        HomeWarpGUI.getInstance().homeConfig.set(player.getUniqueId() + "." + homeName + "." + "z", player.getLocation().getBlockZ());
        HomeWarpGUI.getInstance().homeConfig.set(player.getUniqueId() + "." + homeName + "." + "pitch", player.getLocation().getPitch());
        HomeWarpGUI.getInstance().homeConfig.set(player.getUniqueId() + "." + homeName + "." + "yaw", player.getLocation().getYaw());
    }

    public Location getHome(Player player, String homeName) {
        double x = HomeWarpGUI.getInstance().homeConfig.getInt(player.getUniqueId() + "." + homeName + "." + "x") + 0.5D;
        double y = HomeWarpGUI.getInstance().homeConfig.getInt(player.getUniqueId() + "." + homeName + "." + "y");
        double z = HomeWarpGUI.getInstance().homeConfig.getInt(player.getUniqueId() + "." + homeName + "." + "z") + 0.5D;
        float pitch = HomeWarpGUI.getInstance().homeConfig.getInt(player.getUniqueId() + "." + homeName + "." + "pitch");
        float yaw = HomeWarpGUI.getInstance().homeConfig.getInt(player.getUniqueId() + "." + homeName + "." + "yaw");
        String worldName = HomeWarpGUI.getInstance().homeConfig.getString(player.getUniqueId() + "." + homeName + "." + "world");
        World world = Bukkit.getWorld(worldName);
        return new Location(world, x, y, z, yaw, pitch);
    }
}