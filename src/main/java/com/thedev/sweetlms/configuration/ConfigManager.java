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
    private final List<String> graceExpiredMessages;
    private final List<String> whitelistedCommands;

    private final String base64Inventory;
    private final String base64Armor;
    private final String graceTitle;
    private final String graceSubtitle;
    private final String cannotDoCommand;

    private final double healPerKill;

    private final boolean denyTeleportation;

    private final int graceSeconds;
    private final int countdownSeconds;

    public ConfigManager(SweetLMS plugin) {
        FileConfiguration config = plugin.getConfig();

        gameStartingMessages = config.getStringList("messages.game-starting");
        gameStartedMessages = config.getStringList("messages.game-started");
        notEnoughPlayersMessages = config.getStringList("messages.not-enough-players");
        graceExpiredMessages = config.getStringList("messages.grace-expired");
        whitelistedCommands = config.getStringList("lms-settings.whitelisted-commands");

        base64Inventory = config.getString("kit-data.contents");
        base64Armor = config.getString("kit-data.armor");
        graceTitle = config.getString("messages.grace-title");
        graceSubtitle = config.getString("messages.grace-subtitle");
        cannotDoCommand = config.getString("messages.cannot-do-command");

        healPerKill = config.getDouble("lms-settings.heal-per-kill");
        graceSeconds = config.getInt("lms-settings.grace-timer");
        countdownSeconds = config.getInt("lms-settings.countdown-time");

        denyTeleportation = config.getBoolean("lms-settings.deny-teleportation");
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

    public List<String> getGraceExpiredMessages() {
        return graceExpiredMessages;
    }

    public List<String> getWhitelistedCommands() {
        return whitelistedCommands;
    }

    public int getCountdownSeconds() {
        return countdownSeconds;
    }

    public String getBase64Inventory() {
        return base64Inventory;
    }

    public String getCannotDoCommand() {
        return cannotDoCommand;
    }

    public String getBase64Armor() {
        return base64Armor;
    }

    public String getGraceTitle() {
        return graceTitle;
    }

    public String getGraceSubtitle() {
        return graceSubtitle;
    }

    public double getHealPerKill() {
        return healPerKill;
    }

    public int getGraceSeconds() {
        return graceSeconds;
    }

    public boolean getDenyTeleportation() {
        return denyTeleportation;
    }
}
