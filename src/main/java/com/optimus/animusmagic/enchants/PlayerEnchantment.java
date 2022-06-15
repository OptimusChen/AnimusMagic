package com.optimus.animusmagic.enchants;

import lombok.Getter;
import org.bukkit.Bukkit;

@Getter
public class PlayerEnchantment {

    private PlayerEnchantType type;

    public PlayerEnchantment(PlayerEnchantType type){
        this.type = type;
    }

}
