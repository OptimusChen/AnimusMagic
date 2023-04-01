package com.optimus.animusmagic.item;

import org.bukkit.ChatColor;

public enum ItemRarity {

    COMMON(ChatColor.WHITE),
    UNCOMMON(ChatColor.GREEN),
    RARE(ChatColor.BLUE),
    EPIC(ChatColor.DARK_PURPLE),
    LEGENDARY(ChatColor.GOLD),
    MYTHIC(ChatColor.LIGHT_PURPLE),
    SPECIAL(ChatColor.RED);

    final ChatColor color;
    ItemRarity(ChatColor color) {
        this.color = color;
    }

    public ChatColor getColor() { return color; }

}
