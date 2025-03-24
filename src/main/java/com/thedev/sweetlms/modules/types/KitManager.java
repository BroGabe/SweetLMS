package com.thedev.sweetlms.modules.types;

import com.thedev.sweetlms.SweetLMS;
import com.thedev.sweetlms.configuration.ConfigManager;
import com.thedev.sweetlms.utils.ItemSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;
import java.util.UUID;

public class KitManager {

    private final ConfigManager configManager;

    public KitManager(SweetLMS plugin) {
        configManager = plugin.getConfigManager();
    }

    protected void assignKitToPlayer(UUID playerUUID) {
        if(!isValidKit()) return;
        if(Bukkit.getPlayer(playerUUID) == null) return;

        Player player = Bukkit.getPlayer(playerUUID);

        player.getInventory().setContents(Objects.requireNonNull(getInventory()).getContents());
        player.getInventory().setArmorContents(getArmorContents());
    }

    public boolean isValidKit() {
        return (getArmorContents() != null && getInventory() != null);
    }

    private Inventory getInventory() {
        Inventory inventory;

        try {
            inventory = ItemSerializer.fromBase64(configManager.getBase64Inventory());
        } catch (Exception exception) {
            return null;
        }

        return inventory;
    }

    private ItemStack[] getArmorContents() {
        ItemStack[] contents;

        if(configManager.getBase64Armor().isEmpty()) {
            return new ItemStack[0];
        }

        try {
            contents = ItemSerializer.itemStackArrayFromBase64(configManager.getBase64Armor());
        } catch (Exception exception) {
            return null;
        }

        return contents;
    }
}
