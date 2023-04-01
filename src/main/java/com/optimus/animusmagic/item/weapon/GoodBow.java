package com.optimus.animusmagic.item.weapon;

import com.optimus.animusmagic.item.AnimusItem;
import com.optimus.animusmagic.item.ItemRarity;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class GoodBow extends AnimusItem {

    @Override
    public void onEntityDamage(EntityDamageByEntityEvent e) {
        e.setDamage(1000000);
    }

    @Override
    public String getDisplayName() {
        return "Good Bow";
    }

    @Override
    public String getLore() {
        return "Legit just a good bow";
    }

    @Override
    public ItemRarity getRarity() {
        return ItemRarity.SPECIAL;
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
