package com.thedev.sweetlms.modules.types;

import com.thedev.sweetlms.SweetLMS;
import com.thedev.sweetlms.configuration.ConfigManager;
import com.thedev.sweetlms.modules.enums.GameState;
import com.thedev.sweetlms.utils.ColorUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

public class GraceManager {

    private final SweetLMS plugin;

    private final ConfigManager configManager;

    private final GameManager gameManager;

    private BukkitTask graceTask;

    private Instant countdown = null;

    public GraceManager(SweetLMS plugin, GameManager gameManager) {
        this.plugin = plugin;
        this.gameManager = gameManager;

        configManager = plugin.getConfigManager();
    }

    public void startGrace(int seconds) {
        if(isCountdownActive()) return;

        setCountdown(seconds);

        gameManager.setGameState(GameState.GRACE);

        graceTask = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            if(isCountdownOver()) {
                announceGraceExpires();
                gameManager.setGameState(GameState.ACTIVE);
                graceTask.cancel();
                return;
            }
            announceGrace(getCountdownSeconds());
        }, 1L, 20L);
    }

    private void announceGrace(int seconds) {
        for(UUID playerUUID : gameManager.getPlayersSet()) {
            if(Bukkit.getPlayer(playerUUID) == null) continue;

            Player player = Bukkit.getPlayer(playerUUID);

            player.sendTitle(ColorUtil.color(configManager.getGraceTitle()),
                    ColorUtil.color(configManager.getGraceSubtitle().replace("%seconds%", String.valueOf(seconds))));
        }
    }

    private void announceGraceExpires() {
        for(UUID playerUUID : gameManager.getPlayersSet()) {
            if(Bukkit.getPlayer(playerUUID) == null) continue;

            Player player = Bukkit.getPlayer(playerUUID);

            configManager.getGraceExpiredMessages().forEach(s ->
                    player.sendMessage(ColorUtil.color(s)));
        }
    }

    public int getCountdownSeconds() {
        if(!isCountdownActive()) return 0;

        return (int) Duration.between(Instant.now(), countdown).getSeconds();
    }

    /**
     * Sets the time in seconds for the countdown to last. After time is up, countdown will expire.
     * Checks if a countdown is current active, if so, will return to avoid interference.
     *
     * Sets the countdown to 30 seconds if seconds parameter is below 10. This is to avoid
     * odd functionality with a short countdown.
     * @param seconds how many seconds the countdown will last before the LMS game starts.
     */
    protected void setCountdown(int seconds) {
        if(isCountdownActive()) return;

        if(seconds < 10) {
            seconds = 30;
        }

        countdown = Instant.now().plusSeconds(seconds);
    }

    /**
     * @return True if countdown is ahead of cached time. False if not active, or if current time
     * is before cached time.
     */
    private boolean isCountdownOver() {
        if(!isCountdownActive()) return true;

        return Instant.now().isAfter(countdown);
    }

    /**
     * @return true if a countdown task is currently running. False if not.
     */
    public boolean isCountdownActive() {
        return graceTask != null
                && countdown != null
                && Bukkit.getScheduler().isCurrentlyRunning(graceTask.getTaskId());
    }
}
