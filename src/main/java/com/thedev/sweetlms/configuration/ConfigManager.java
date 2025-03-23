package com.thedev.sweetlms.configuration;

import com.thedev.sweetlms.SweetLMS;
import com.thedev.sweetlms.utils.ColorUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class ConfigManager {

    private final List<String> gameStartingMessages;
    private final List<String> gameStartedMessages;
    private final List<String> notEnoughPlayersMessages;

    private final double healPerKill;

    public ConfigManager(SweetLMS plugin) {
        FileConfiguration config = plugin.getConfig();

        gameStartingMessages = config.getStringList("messages.game-starting");
        gameStartedMessages = config.getStringList("messages.game-started");
        notEnoughPlayersMessages = config.getStringList("messages.not-enough-players");
        healPerKill = config.getDouble("lms-settings.heal-per-kill");
    }

    public void broadcastGameStarting(int seconds) {
        gameStartingMessages.forEach(string -> {
            Bukkit.broadcastMessage(ColorUtil.color(string
                    .replace("%seconds%", String.valueOf(seconds))));
        });
    }

    public void broadcastGameStarted() {
        gameStartedMessages.forEach(s -> Bukkit.broadcastMessage(ColorUtil.color(s)));
    }

    public void broadcastNotEnoughPlayers() {
        notEnoughPlayersMessages.forEach(s -> Bukkit.broadcastMessage(ColorUtil.color(s)));
    }

    public double getHealPerKill() {
        return healPerKill;
    }
}
