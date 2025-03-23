package com.thedev.sweetlms.modules.types;

import com.thedev.sweetlms.SweetLMS;
import com.thedev.sweetlms.configuration.ConfigManager;
import com.thedev.sweetlms.modules.enums.GameState;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import java.time.Duration;
import java.time.Instant;

public class CountdownManager {

    private final SweetLMS plugin;

    private final GameManager gameManager;

    private final GraceManager graceManager;

    private final ConfigManager configManager;

    private BukkitTask countdownTask;

    private Instant countdown = null;

    public CountdownManager(SweetLMS plugin, GameManager gameManager, GraceManager graceManager) {
        this.plugin = plugin;
        this.gameManager = gameManager;
        this.graceManager = graceManager;

        configManager = plugin.getConfigManager();
    }

    public void startCountdown(int seconds) {
        if(isCountdownActive()) return;

        setCountdown(seconds);

        gameManager.setGameState(GameState.COUNTDOWN);

        countdownTask = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin,
                () -> {
            // If game started, change gamestate to grace, start grace, and end countdown.
            if(isCountdownOver()) {
                countdownTask.cancel();

                if(gameManager.getCurrentPlayers() < 2) {
                    gameManager.forceEnd();

                    configManager.broadcastNotEnoughPlayers();
                    return;
                }

                gameManager.setGameState(GameState.GRACE);

                graceManager.startGrace();

                configManager.broadcastGameStarted();
                return;
            }

            // If countdown is still active, broadcast countdown.
            configManager.broadcastGameStarting(getCountdownSeconds());

                }, 1L, 20L * 5);
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
        return countdownTask != null
                && countdown != null
                && Bukkit.getScheduler().isCurrentlyRunning(countdownTask.getTaskId());
    }
}
