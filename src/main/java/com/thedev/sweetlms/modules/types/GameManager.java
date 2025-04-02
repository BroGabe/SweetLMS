package com.thedev.sweetlms.modules.types;

import com.thedev.sweetlms.SweetLMS;
import com.thedev.sweetlms.configuration.ConfigManager;
import com.thedev.sweetlms.modules.enums.GameState;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

public class GameManager {

    private final ConfigManager configManager;

    private final CountdownManager countdownManager;

    private final LocationManager locationManager;

    @Getter
    private final KitManager kitManager;

    @Getter
    private final SafetyCheckManager safetyCheckManager;

    @Getter
    private final RewardManager rewardManager;

    @Getter
    private final Set<UUID> playersSet = new HashSet<>();

    @Setter
    @Getter
    private GameState gameState = GameState.NOT_ACTIVE;

    public GameManager(SweetLMS plugin) {
        configManager = plugin.getConfigManager();

        kitManager = new KitManager(plugin);
        locationManager = new LocationManager(plugin);
        countdownManager = new CountdownManager(plugin, this);

        safetyCheckManager = new SafetyCheckManager(locationManager, kitManager);

        rewardManager= new RewardManager(plugin);
    }

    public void startGame() {
        if(isGameRunning()) return;
        if(!safetyCheckManager.validGameSetup()) return;

        countdownManager.startCountdown(configManager.getCountdownSeconds());
    }

    public boolean isGameRunning() {
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

    public void gameWon(Player player) {
        Iterator<UUID> playersIterator = playersSet.iterator();
        while(playersIterator.hasNext()) {
            UUID playerUUID = playersIterator.next();

            if(Bukkit.getPlayer(playerUUID) == null) {
                playersIterator.remove();
                continue;
            }

            Player participator = Bukkit.getPlayer(playerUUID);
            participator.teleport(locationManager.getSpawnLocation());
            playersIterator.remove();
        }

        if(player == null) return;

        rewardManager.rewardPlayerWin(player);
    }

    public boolean hasWinner() {
        return (getPlayersSet().size() == 1);
    }

    public Player getLastPlayer() {
        if(getPlayersSet().size() != 1) return null;

        if(Bukkit.getPlayer(getPlayersSet().iterator().next()) == null) {
            forceEnd();
            return null;
        }

        return Bukkit.getPlayer(getPlayersSet().iterator().next());
    }
}
