package com.thedev.sweetlms.modules.types;

import com.thedev.sweetlms.SweetLMS;
import com.thedev.sweetlms.configuration.ConfigManager;
import com.thedev.sweetlms.modules.enums.GameState;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

public class GameManager {

    /**
     * startGame(); method which will start
     * the countdown until the game starts and set gameState to Countdown.
     *
     * When GameState is at countdown, doing /lms join teleports you to the arena.
     *
     * startGame(); method will start the countdown task. When countdown task is over
     * it will start the grace task. When grace task is over, pvp will be enabled.
     * Possibly an endGrace(); method.
     *
     * Method to handle teleporting player to a game and giving them LMS kit.
     *
     * GameManager needs countdownManager for startGame();
     * countdownManager needs GraceManager for when countdown is over.
     *
     */

    private final SafetyCheckManager safetyCheckManager;

    private final ConfigManager configManager;

    private final KitManager kitManager;

    private final CountdownManager countdownManager;

    private final LocationManager locationManager;

    private final RewardManager rewardManager;

    private final Set<UUID> playersSet = new HashSet<>();

    private GameState gameState = GameState.NOT_ACTIVE;

    public GameManager(SweetLMS plugin) {
        configManager = plugin.getConfigManager();

        kitManager = new KitManager(plugin);
        locationManager = new LocationManager(plugin);
        countdownManager = new CountdownManager(plugin, this);

        safetyCheckManager = new SafetyCheckManager(locationManager, kitManager);

        rewardManager= new RewardManager();
    }

    public void startGame() {
        if(isGameRunning()) return;
        if(!safetyCheckManager.validGameSetup()) return;

        countdownManager.startCountdown(configManager.getCountdownSeconds());
    }

    public boolean isGameRunning() {
        if(gameState != GameState.NOT_ACTIVE) return true;
        return countdownManager.isCountdownActive();
    }

    public Set<UUID> getPlayersSet() {
        return playersSet;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public GameState getGameState() {
        return gameState;
    }

    public boolean isRunning() {
        return (gameState != GameState.NOT_ACTIVE);
    }

    protected void playerJoinGame(UUID playerUUID) {
        if(!isGameRunning()) return;

        if(Bukkit.getPlayer(playerUUID) == null) return;

        Player player = Bukkit.getPlayer(playerUUID);

        player.closeInventory();
        player.getInventory().clear();

        player.teleport(locationManager.getLMSLocation());
        kitManager.assignKitToPlayer(playerUUID);

        playersSet.add(playerUUID);
    }

    public void playerDeath(UUID playerUUID) {
        playersSet.remove(playerUUID);
    }

    protected int getCurrentPlayers() {
        return playersSet.size();
    }

    protected void forceEnd() {
        setGameState(GameState.NOT_ACTIVE);
        Iterator<UUID> playersIterator = playersSet.iterator();

        while(playersIterator.hasNext()) {
            UUID playerUUID = playersIterator.next();

            if(Bukkit.getPlayer(playerUUID) == null) {
                playersIterator.remove();
                continue;
            }

            Player player = Bukkit.getPlayer(playerUUID);

            player.getInventory().clear();

            player.teleport(locationManager.getSpawnLocation());
            playersIterator.remove();
        }
    }

    protected void endGame() {
        // Loop through playerSet and teleport each player back to spawn.
        Iterator<UUID> playersIterator = playersSet.iterator();
        while(playersIterator.hasNext()) {
            UUID playerUUID = playersIterator.next();

            // Check if player is online or still exists.

            // Teleports player back to spawn

            // Removes player from the list
            playersIterator.remove();
        }

        // Do other endGame(); code here.
    }

    public void playerWinEvent() {
        // Reward player for winning and teleport him to spawn with no inventory.
    }

    // When a player leaves, gets banned, or mysteriously dies,
    // check if he's the last player, or check if there's a last player left
    // to award the last player and shit
    public boolean isLastPlayer(UUID playerUUID) {
        return getPlayersSet().contains(playerUUID) && getPlayersSet().size() == 1;
    }

    public RewardManager getRewardManager() {
        return rewardManager;
    }
}
