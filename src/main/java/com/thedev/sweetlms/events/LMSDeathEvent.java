package com.thedev.sweetlms.events;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
public class LMSDeathEvent extends Event {

    private static final HandlerList handlerList = new HandlerList();

    private final Player attacker;
    private final Player defender;

    @Setter
    private double healAmount;
    @Setter
    private int potsAmount;

    public LMSDeathEvent(Player attacker, Player defender, double healAmount, int potsAmount) {
        this.attacker = attacker;
        this.defender = defender;

        this.healAmount = healAmount;
        this.potsAmount = potsAmount;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
