package com.thedev.sweetlms.modules.types;

import com.thedev.sweetlms.SweetLMS;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class LocationManager {

    private final SweetLMS plugin;

    private final FileConfiguration config;

    private String spawnWorldName;
    private final String lmsWorldName;

    private double spawnX;
    private double spawnY;
    private double spawnZ;

    private final double lmsX;
    private final double lmsY;
    private final double lmsZ;

    public LocationManager(SweetLMS plugin) {
        this.plugin = plugin;

        config = plugin.getConfig();

        spawnWorldName = config.getString("spawn-location.world");
        lmsWorldName = config.getString("lms-location.world");

        spawnX = config.getDouble("spawn-location.x");
        spawnY = config.getDouble("spawn-location.y");
        spawnZ = config.getDouble("spawn-location.z");

        lmsX = config.getDouble("lms-location.x");
        lmsY = config.getDouble("lms-location.y");
        lmsZ = config.getDouble("lms-location.z");
    }

    public Location getSpawnLocation() {
        return new Location(Bukkit.getWorld(spawnWorldName), spawnX, spawnY, spawnZ);
    }

    public Location getLMSLocation() {
        return new Location(Bukkit.getWorld(lmsWorldName), lmsX, lmsY, lmsZ);
    }

    public void setSpawnLocation(Player player) {
        Location location = player.getLocation();

        spawnWorldName = location.getWorld().getName();
        spawnX = location.getX();
        spawnY = location.getY();
        spawnZ = location.getZ();

        config.set("spawn-location.world", spawnWorldName);
        config.set("spawn-location.x", spawnX);
        config.set("spawn-location.y", spawnY);
        config.set("spawn-location.z", spawnZ);

        plugin.saveConfig();
    }
}
