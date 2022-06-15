package com.optimus.animusmagic.event;

import com.optimus.animusmagic.enchants.PlayerEnchantment;
import com.optimus.animusmagic.player.AnimusPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class PlayerEnchantEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();
    private boolean isCancelled;
    private PlayerEnchantment enchant;
    private AnimusPlayer enchanter;
    private Player victim;

    public PlayerEnchantEvent(AnimusPlayer enchanter, Player victim, PlayerEnchantment enchant) {
        super();
        this.enchant = enchant;
        this.victim = victim;
        this.enchanter = enchanter;
    }

    public Player getVictim() { return victim; }

    public AnimusPlayer getEnchanter() { return enchanter; }

    public PlayerEnchantment getEnchant() { return this.enchant; }

    public static HandlerList getHandlersList(){
        return HANDLERS;
    }

    public static HandlerList getHandlerList(){
        return HANDLERS;
    }

    public static HandlerList getHandler(){
        return HANDLERS;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        isCancelled = b;
    }
}
