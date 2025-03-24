package com.thedev.sweetlms.modules.types;

import com.thedev.sweetlms.SweetLMS;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

public class LocationManager {

    private final String spawnWorldName;
    private final String lmsWorldName;

    private final double spawnX;
    private final double spawnY;
    private final double spawnZ;

    private final double lmsX;
    private final double lmsY;
    private final double lmsZ;

    public LocationManager(SweetLMS plugin) {
        FileConfiguration config = plugin.getConfig();

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
}
