package com.thedev.sweetlms.modules.types;

import com.thedev.sweetlms.SweetLMS;
import com.thedev.sweetlms.configuration.ConfigManager;
import com.thedev.sweetlms.utils.ItemSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import java.util.UUID;

public class KitManager {

    private final ConfigManager configManager;

    public KitManager(SweetLMS plugin) {
        configManager = plugin.getConfigManager();
    }

    public boolean isValidKit() {
        return (getArmorContents() != null && getInventory() != null);
    }

    public void saveInventoryToFile(Player player) {
        if(!isInventoryEmpty(player)) {
            String inventoryBase64 = ItemSerializer.toBase64(player.getInventory());
            configManager.updateInventoryString(inventoryBase64);
        }

        if(!isArmorEmpty(player)) {
            String armorBase64 = ItemSerializer.itemStackArrayToBase64(player.getInventory().getArmorContents());
            configManager.updateArmorString(armorBase64);
        }
    }

    protected void assignKitToPlayer(UUID playerUUID) {
        if(!isValidKit()) return;
        if(Bukkit.getPlayer(playerUUID) == null) return;

        Player player = Bukkit.getPlayer(playerUUID);

        player.getInventory().setContents(Objects.requireNonNull(getInventory()).getContents());
        player.getInventory().setArmorContents(getArmorContents());
    }

    private Inventory getInventory() {
        if(configManager.getBase64Inventory().isEmpty()) return null;

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
            return null;
        }

        try {
            contents = ItemSerializer.itemStackArrayFromBase64(configManager.getBase64Armor());
        } catch (Exception exception) {
            return null;
        }

        return contents;
    }

    private boolean isInventoryEmpty(Player player) {
        Iterator<ItemStack> inventoryContents = Arrays.stream(player.getInventory().getContents()).iterator();

        while(inventoryContents.hasNext()) {
            ItemStack itemStack = inventoryContents.next();

            if(itemStack == null || itemStack.getType() == Material.AIR) continue;

            return false;
        }

        return true;
    }

    private boolean isArmorEmpty(Player player) {
        Iterator<ItemStack> inventoryContents = Arrays.stream(player.getInventory().getArmorContents()).iterator();

        while(inventoryContents.hasNext()) {
            ItemStack itemStack = inventoryContents.next();

            if(itemStack == null || itemStack.getType() == Material.AIR) continue;

            return false;
        }

        return true;
    }
}
