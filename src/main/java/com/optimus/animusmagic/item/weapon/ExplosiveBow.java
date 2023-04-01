package com.optimus.animusmagic.item.weapon;

import com.optimus.animusmagic.AnimusMagic;
import com.optimus.animusmagic.item.AnimusItem;
import com.optimus.animusmagic.item.ItemRarity;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class ExplosiveBow extends AnimusItem {

    @Override
    public void onBowShoot(EntityShootBowEvent e) {
        if (e.getProjectile() instanceof Arrow arrow) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (arrow.isDead()){
                        cancel();
                    }else{
                        if (arrow.isOnGround()) {
                            arrow.getWorld().createExplosion(arrow.getLocation(), e.getForce()*5);
                            arrow.remove();
                            cancel();
                        }
                    }
                }
            }.runTaskTimer(AnimusMagic.getInstance(), 5L, 1);
        }
    }

    @Override
    public void onEntityDamage(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Arrow arrow) {
            arrow.getWorld().createExplosion(arrow.getLocation(), 3);
            arrow.remove();
        }
    }

    @Override
    public String getDisplayName() {
        return "Explosive Bow";
    }

    @Override
    public String getLore() {
        return "Shoots arrows that explode";
    }

    @Override
    public ItemRarity getRarity() {
        return ItemRarity.EPIC;
    }

    @Override
    public Material getMaterial() {
        return Material.BOW;
    }

    @Override
    public boolean isEnchanted() {
        return false;
    }
}
