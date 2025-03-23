package com.thedev.sweetlms.modules.types;

import com.thedev.sweetlms.SweetLMS;
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

    private final LocationManager locationManager;

    private GameState gameState = GameState.NOT_ACTIVE;

    private final Set<UUID> playersSet = new HashSet<>();

    public GameManager(SweetLMS plugin) {
        locationManager = new LocationManager(plugin);
    }

    public void startGame() {

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
        // Code here is to manage teleporting player to the game.
        // Will use KitManager to set a player's inventory.
        playersSet.add(playerUUID);
    }

    protected void playerDeath(UUID playerUUID) {
        playersSet.remove(playerUUID);
    }

    protected int getCurrentPlayers() {
        return playersSet.size();
    }

    protected void forceEnd() {
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
}
