package com.thedev.sweetlms.configuration;

import com.thedev.sweetlms.SweetLMS;
import com.thedev.sweetlms.utils.ColorUtil;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class ConfigManager {

    private final SweetLMS plugin;

    private final FileConfiguration config;

    private final List<String> gameStartingMessages;
    private final List<String> gameStartedMessages;
    private final List<String> notEnoughPlayersMessages;

    @Getter
    private final List<String> graceExpiredMessages;

    @Getter
    private final List<String> whitelistedCommands;

    @Getter
    private final List<String> rewardsList;

    @Getter
    private String base64Inventory;

    @Getter
    private String base64Armor;

    @Getter
    private final String graceTitle;

    @Getter
    private final String graceSubtitle;

    @Getter
    private final String cannotDoCommand;

    @Getter
    private final double healPerKill;

    @Getter
    private final boolean denyTeleportation;

    @Getter
    private final int graceSeconds;

    @Getter
    private final int countdownSeconds;

    @Getter
    private final int potsAmount;

    public ConfigManager(SweetLMS plugin) {
        this.plugin = plugin;

        config = plugin.getConfig();

        gameStartingMessages = config.getStringList("messages.game-starting");
        gameStartedMessages = config.getStringList("messages.game-started");
        notEnoughPlayersMessages = config.getStringList("messages.not-enough-players");
        graceExpiredMessages = config.getStringList("messages.grace-expired");
        whitelistedCommands = config.getStringList("lms-settings.whitelisted-commands");
        rewardsList = config.getStringList("lms-settings.rewards");

        base64Inventory = config.getString("kit-data.contents");
        base64Armor = config.getString("kit-data.armor");
        graceTitle = config.getString("messages.grace-title");
        graceSubtitle = config.getString("messages.grace-subtitle");
        cannotDoCommand = config.getString("messages.cannot-do-command");

        healPerKill = config.getDouble("lms-settings.heal-per-kill");
        graceSeconds = config.getInt("lms-settings.grace-timer");
        countdownSeconds = config.getInt("lms-settings.countdown-time");
        potsAmount = config.getInt("lms-settings.pots-per-kill");

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

    public void updateInventoryString(String string) {
        config.set("kit-data.contents", string);
        plugin.saveConfig();

        base64Inventory = string;
    }

    public void updateArmorString(String string) {
        config.set("kit-data.armor", string);
        plugin.saveConfig();

        base64Armor = string;
    }
}
