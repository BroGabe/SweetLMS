package com.thedev.sweetlms.modules;

import com.thedev.sweetlms.SweetLMS;
import com.thedev.sweetlms.modules.types.GameManager;
import lombok.Getter;
import org.bukkit.entity.Player;

public class LMSManager {

    private final SweetLMS plugin;

    @Getter
    private final GameManager gameManager;

    public LMSManager(SweetLMS plugin) {
        this.plugin = plugin;

        gameManager = new GameManager(plugin);
    }

    public void startGame() {
        gameManager.startGame();
    }

    public boolean canGameStart() {
        if(!gameManager.getSafetyCheckManager().validGameSetup()) return false;

        return !gameManager.isGameRunning();
    }

    public boolean isInLMS(Player player) {
        return gameManager.getPlayersSet().contains(player.getUniqueId());
    }

    public void playerKillReward(Player player, double health, int pots) {
        getGameManager().getRewardManager().rewardPlayerKill(player, health, pots);
    }

    public void setKitContents(Player player) {
        gameManager.getKitManager().
    }
}
