package com.optimus.animusmagic.listeners;

import com.optimus.animusmagic.AnimusMagic;
import com.optimus.animusmagic.event.TalismanEquipEvent;
import com.optimus.animusmagic.event.TalismanUnEquipEvent;
import com.optimus.animusmagic.item.ItemHandler;
import com.optimus.animusmagic.player.AnimusPlayer;
import com.optimus.animusmagic.player.PlayerRegistry;
import com.optimus.animusmagic.talisman.Talisman;
import com.optimus.animusmagic.talisman.TalismanType;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class EventListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        PlayerRegistry.register(e.getPlayer());
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e){
        PlayerRegistry.unRegister(e.getPlayer());
    }

    @EventHandler
    public void onEquip(TalismanEquipEvent e){
        Talisman talisman = Talisman.parse(e.getItem());
        Player player = e.getPlayer();
        AnimusPlayer animusPlayer = e.getAnimusPlayer();

        switch (talisman.getType()){
            case SHIELD:
                player.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(animusPlayer.getAttributeAmount(Attribute.GENERIC_ARMOR) + talisman.getTeir());
                break;
            case STRENGTH:
                animusPlayer.setDamageMult(animusPlayer.getDamageMult() + (talisman.getTeir()/20F));
                break;
            case RUNNING:
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(animusPlayer.getAttributeAmount(Attribute.GENERIC_MOVEMENT_SPEED) * (1 + (talisman.getTeir()/10F)));
                    }
                }.runTaskLater(AnimusMagic.getInstance(), 2);
                break;
            case GLOWING:
                player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, Integer.MAX_VALUE, 0, true));
                break;
            case GLIDING:
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, Integer.MAX_VALUE, 0, true));
                break;
            case STEALTH:
                player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0, true));
                break;
            case GENERIC:
                player.sendMessage(ChatColor.GREEN + "why");
                break;
        }
    }

    @EventHandler
    public void onUnEquip(TalismanUnEquipEvent e){
        Talisman talisman = Talisman.parse(e.getItem());
        Player player = e.getPlayer();
        AnimusPlayer animusPlayer = e.getAnimusPlayer();

        switch (talisman.getType()){
            case SHIELD:
                player.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(animusPlayer.getAttributeAmount(Attribute.GENERIC_ARMOR) - talisman.getTeir());
                break;
            case STRENGTH:
                animusPlayer.setDamageMult(animusPlayer.getDamageMult() - (talisman.getTeir()/20F));
                break;
            case RUNNING:
                player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.1f);

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        ArrayList<ItemStack> list = (ArrayList<ItemStack>) animusPlayer.getConfig().getItem("stored-talismans");

                        for (ItemStack item : list){
                            if (Talisman.isTalisman(item)){
                                if (Talisman.parse(item).getType().equals(TalismanType.RUNNING)){
                                    Bukkit.getPluginManager().callEvent(new TalismanEquipEvent(animusPlayer, item));
                                }
                            }
                        }
                    }
                }.runTaskLater(AnimusMagic.getInstance(), 1);
                break;
            case GLOWING:
                player.removePotionEffect(PotionEffectType.GLOWING);
                break;
            case GLIDING:
                player.removePotionEffect(PotionEffectType.SLOW_FALLING);
                break;
            case STEALTH:
                player.removePotionEffect(PotionEffectType.INVISIBILITY);
                break;
            case GENERIC:
                player.sendMessage(ChatColor.GREEN + "xd");
                break;
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e){
        new BukkitRunnable() {
            @Override
            public void run() {
                Player player = e.getPlayer();
                if (!player.hasMetadata("NPC")) {
                    AnimusPlayer animusPlayer = PlayerRegistry.get(player);
                    ArrayList<ItemStack> list = (ArrayList<ItemStack>) animusPlayer.getConfig().getItem("stored-talismans");

                    for (ItemStack item : list){
                        if (Talisman.isTalisman(item) && !Talisman.parse(item).getType().equals(TalismanType.RUNNING)){
                            Bukkit.getPluginManager().callEvent(new TalismanUnEquipEvent(animusPlayer, item));
                            Bukkit.getPluginManager().callEvent(new TalismanEquipEvent(animusPlayer, item));
                        }
                    }
                }
            }
        }.runTaskLater(AnimusMagic.getInstance(), 1);
    }

    @EventHandler
    public void onShoot(EntityShootBowEvent e) {
        if (e.getEntity() instanceof Player player) {
            ItemStack item = player.getEquipment().getItemInMainHand();
            if (ItemHandler.getInstance().isRegistered(item)) {
                ItemHandler.getInstance().getRegistered(item).onBowShoot(e);
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e){
        if (e.getDamager() instanceof Player player) {
            if (!player.hasMetadata("NPC")) {
                AnimusPlayer animusPlayer = PlayerRegistry.get(player);
                e.setDamage(e.getDamage() * animusPlayer.getDamageMult());
            }

            ItemStack item = player.getEquipment().getItemInMainHand();

            if (ItemHandler.getInstance().isRegistered(item)) {
                ItemHandler.getInstance().getRegistered(item).onEntityDamage(e);
            }
        } else if (e.getDamager() instanceof Arrow arrow) {
            if (arrow.getShooter() instanceof Player player) {
                ItemStack item = player.getEquipment().getItemInMainHand();

                if (ItemHandler.getInstance().isRegistered(item)) {
                    ItemHandler.getInstance().getRegistered(item).onEntityDamage(e);
                }
            }
        }
    }

    @EventHandler
    public void onClick(NPCRightClickEvent e) {
        if (ChatColor.stripColor(e.getNPC().getName()).equalsIgnoreCase("Click")) {

        }
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        ItemStack item = e.getItem();

        if (item != null) {
            if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if (ItemHandler.getInstance().isRegistered(item)) {
                    ItemHandler.getInstance().getRegistered(item).onRightClick(e);
                }
            } else if (e.getAction().equals(Action.LEFT_CLICK_AIR) || e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
                if (ItemHandler.getInstance().isRegistered(item)) {
                    ItemHandler.getInstance().getRegistered(item).onLeftClick(e);
                }
            }
        }
    }
}
