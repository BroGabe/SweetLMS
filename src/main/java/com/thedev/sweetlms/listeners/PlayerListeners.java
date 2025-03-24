package com.thedev.sweetlms.listeners;

import com.thedev.sweetlms.SweetLMS;
import com.thedev.sweetlms.configuration.ConfigManager;
import com.thedev.sweetlms.modules.LMSManager;
import com.thedev.sweetlms.utils.ColorUtil;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.ArrayList;
import java.util.List;

public class PlayerListeners implements Listener {

    private final SweetLMS plugin;

    private final ConfigManager configManager;

    private final LMSManager lmsManager;

    public PlayerListeners(SweetLMS plugin) {
        this.plugin = plugin;

        configManager = plugin.getConfigManager();
        lmsManager = plugin.getLmsManager();
    }

    /**
     * Disallows non-lms players from harming lms players.
     * @param event
     */
    @EventHandler
    public void onPlayerAttack(EntityDamageByEntityEvent event) {
        if(!(event.getEntity() instanceof Player) || !(event.getDamager() instanceof Player)) return;

        Player defender = (Player) event.getEntity();
        Player attacker = (Player) event.getDamager();

        if(!lmsManager.isInLMS(defender)) return;

        if(lmsManager.isInLMS(attacker)) return;

        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        if(!lmsManager.isInLMS(event.getPlayer())) return;
        if(event.getPlayer().hasPermission("lms.admin")) return;

        String command = event.getMessage().toLowerCase();

        List<String> allowedCommands = new ArrayList<>();

        configManager.getWhitelistedCommands().forEach(cmd -> allowedCommands.add(cmd.toLowerCase()));

        for(String allowedCommand : allowedCommands) {
            String[] splitAllowed = allowedCommand.split(" ");
            String[] splitCommand = command.split(" ");
            if(!splitAllowed[0].equalsIgnoreCase(splitCommand[0])) continue;
            if(command.startsWith(allowedCommand)) return;
        }

        Player player = event.getPlayer();

        player.sendMessage(ColorUtil.color(configManager.getCannotDoCommand()));
        player.playSound(player.getLocation(), Sound.NOTE_BASS, 10, 6);
        event.setCancelled(true);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if(!lmsManager.isInLMS(event.getEntity())) return;

        lmsManager.getGameManager().playerDeath(event.getEntity().getUniqueId());

        if(event.getEntity().getKiller() == null) return;

        Player killer = event.getEntity().getKiller();

        if(!lmsManager.isInLMS(killer)) return;

        // Award player pots and health for killing another LMS player.
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if(!lmsManager.isInLMS(event.getPlayer())) return;

        Player player = event.getPlayer();

        player.getInventory().clear();

        lmsManager.getGameManager().playerDeath(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onKick(PlayerKickEvent event) {
        if(!lmsManager.isInLMS(event.getPlayer())) return;

        Player player = event.getPlayer();

        player.getInventory().clear();

        lmsManager.getGameManager().playerDeath(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        if(!lmsManager.isInLMS(event.getPlayer())) return;
        if(!configManager.getDenyTeleportation()) return;

        event.setCancelled(true);
    }
}
