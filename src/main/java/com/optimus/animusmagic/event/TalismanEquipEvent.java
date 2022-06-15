package com.optimus.animusmagic.event;

import com.optimus.animusmagic.player.AnimusPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class TalismanEquipEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();
    private boolean isCancelled;
    private ItemStack item;
    private AnimusPlayer player;

    public TalismanEquipEvent(AnimusPlayer player, ItemStack talisman) {
        super();
        this.item = talisman;
        this.player = player;
    }

    public Player getPlayer() { return player.getBukkitPlayer(); }

    public AnimusPlayer getAnimusPlayer() { return player; }

    public ItemStack getItem() { return this.item; }

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
