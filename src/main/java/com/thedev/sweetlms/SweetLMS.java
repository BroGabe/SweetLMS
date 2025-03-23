package com.thedev.sweetlms;

import com.thedev.sweetlms.configuration.ConfigManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class SweetLMS extends JavaPlugin {

    private ConfigManager configManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();

        configManager = new ConfigManager(this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }
}
