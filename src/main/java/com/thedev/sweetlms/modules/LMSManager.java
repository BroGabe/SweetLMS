package com.thedev.sweetlms.modules;

import com.thedev.sweetlms.SweetLMS;
import com.thedev.sweetlms.modules.types.GameManager;
import org.bukkit.entity.Player;

public class LMSManager {

    /**
     * Player must have empty inventory before joining game.
     *
     * Upon typing /lms join, get teleported to the lms arena with a full kit and armor applied.
     * PvP is disabled until the event officially starts. After event starts grace will happen for x seconds
     * then pvp will enable. Upon each kill you will get configurable x amount of potions
     * and be healed configurable x amount of health.
     *
     *
     */

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
     *
     * NOTE: Code so if a player disconnects or dies mysteriously it
     * removes them from the game and clears inventory!
     */

    private final SweetLMS plugin;

    private final GameManager gameManager;

    public LMSManager(SweetLMS plugin) {
        this.plugin = plugin;

        gameManager = new GameManager(plugin);
    }

    public void startGame() {
        gameManager.startGame();
    }

    public boolean isInLMS(Player player) {
        return gameManager.getPlayersSet().contains(player.getUniqueId());
    }

    public GameManager getGameManager() {
        return gameManager;
    }
}
