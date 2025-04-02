package com.thedev.sweetlms;

import com.thedev.sweetlms.configuration.ConfigManager;
import com.thedev.sweetlms.modules.LMSManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class SweetLMS extends JavaPlugin {

    private LMSManager lmsManager;

    private ConfigManager configManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();

        configManager = new ConfigManager(this);
        lmsManager = new LMSManager(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
