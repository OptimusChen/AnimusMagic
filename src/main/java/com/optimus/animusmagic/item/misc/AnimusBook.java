package com.optimus.animusmagic.item.misc;

import com.optimus.animusmagic.item.ItemRarity;
import com.optimus.animusmagic.item.ListeningItem;
import org.bukkit.Material;

public class AnimusBook extends ListeningItem {
    @Override
    public String getDisplayName() {
        return "Animus Book";
    }

    @Override
    public String getLore() {
        return "Drops from the Darkstalker Boss and doesn't do anything yet";
    }

    @Override
    public ItemRarity getRarity() {
        return ItemRarity.EPIC;
    }

    @Override
    public Material getMaterial() {
        return Material.ENCHANTED_BOOK;
    }

    @Override
    public boolean isEnchanted() {
        return true;
    }
}
