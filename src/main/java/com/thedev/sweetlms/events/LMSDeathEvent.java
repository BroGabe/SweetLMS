package com.thedev.sweetlms.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class LMSDeathEvent extends Event {

    private static final HandlerList handlerList = new HandlerList();

    private final Player attacker;
    private final Player defender;

    private double healAmount;
    private int potsAmount;

    public LMSDeathEvent(Player attacker, Player defender, double healAmount, int potsAmount) {
        this.attacker = attacker;
        this.defender = defender;

        this.healAmount = healAmount;
        this.potsAmount = potsAmount;
    }

    public Player getAttacker() {
        return attacker;
    }

    public Player getDefender() {
        return defender;
    }

    public void setHealAmount(double healAmount) {
        this.healAmount = healAmount;
    }

    public void setPotsAmount(int potsAmount) {
        this.potsAmount = potsAmount;
    }

    public int getPotsAmount() {
        return potsAmount;
    }

    public double getHealAmount() {
        return healAmount;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
