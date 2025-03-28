package com.thedev.sweetlms.modules.types;

import com.thedev.sweetlms.SweetLMS;
import com.thedev.sweetlms.configuration.ConfigManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;
import java.util.List;

public class RewardManager {

    private final ConfigManager configManager;

    public RewardManager(SweetLMS plugin) {
        configManager = plugin.getConfigManager();
    }

    public void rewardPlayerKill(Player player, double health, int potsAmount) {
        rewardHealth(player, health);
        rewardPots(player, potsAmount);
    }

    private void rewardPots(Player player, int amount) {
        List<ItemStack> potsList = new ArrayList<>();

        for(int i = 0; i<amount; i++) {
            ItemStack potionItem = new ItemStack(Material.POTION, 10, (short) 5);

            Potion potion = new Potion(1);
            potion.setSplash(true);
            potion.setLevel(2);
            potion.setType(PotionType.INSTANT_HEAL);
            potion.apply(potionItem);

            potsList.add(potionItem);
        }

        player.getInventory().addItem(potsList.toArray(potsList.toArray(new ItemStack[0])));
    }

    private void rewardHealth(Player player, double amount) {
        double currentHealth = player.getHealth();

        double finalHealth = Math.min(currentHealth + amount, player.getMaxHealth());

        player.setHealth(finalHealth);
    }
}
