package com.optimus.animusmagic.item.weapon;

import com.optimus.animusmagic.item.AnimusItem;
import com.optimus.animusmagic.item.ItemRarity;
import org.bukkit.Material;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class GoodSword extends AnimusItem {

    @Override
    public void onEntityDamage(EntityDamageByEntityEvent e) {
        e.setDamage(1000000);
    }

    @Override
    public String getDisplayName() {
        return "Good Sword";
    }

    @Override
    public String getLore() {
        return "Legit just a good sword";
    }

    @Override
    public ItemRarity getRarity() {
        return ItemRarity.SPECIAL;
    }

    @Override
    public Material getMaterial() {
        return Material.IRON_SWORD;
    }

    @Override
    public boolean isEnchanted() {
        return false;
    }
}
