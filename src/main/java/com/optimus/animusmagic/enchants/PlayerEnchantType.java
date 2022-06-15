package com.optimus.animusmagic.enchants;

import lombok.Getter;
import org.bukkit.Material;

@Getter
public enum PlayerEnchantType {

    HEALTH_BOOST(Material.GOLDEN_APPLE, "&cHealth Boost", "Grants the named player +2 absorption hearts per level!", 2, 4),
    SWIFT_TALONS(Material.FEATHER, "&fSwift Talons", "Grants the named player speed. Also gives haste at lvl 4+!", 10, 5),
    STONE_SCALES(Material.COBBLESTONE, "&8Stone Scales", "Grants the named player resistance and slowness!", 10, 4),
    LEAPING_TALONS(Material.RABBIT_FOOT, "&aLeaping Talons", "Grants the named player jump boost!", 2, 3),
    MAGMA_SCALES(Material.MAGMA_BLOCK, "&6Magma Scales", "Grants the named player fire resistance and slowness. Also gives permanent fire aspect to named players items at lvl 2+!", 10, 2),
    REJUVENATION(Material.GOLDEN_CARROT, "&cRejuvenation", "Grants the named player instant health!", 5, 2),
    CURE_DISEASE(Material.MILK_BUCKET, "&eCure Disease", "Removes Wither, Poison and Hunger from the named player!", 10, 1),
    GENTLE_TALONS(Material.DIAMOND_ORE, "&bGentle Talons", "Gives the named player infinite Silk Touch.", 20, 1),
    SPECIES_SHIFT(Material.SLIME_SPAWN_EGG, "&aSpecies Shift", "Change your skin to another tribe!", 15, 1);

    Material material;
    String name;
    String lore;
    int mana;
    int maxLevel;

    PlayerEnchantType(Material material, String name, String lore, int mana, int maxLevel){
        this.material = material;
        this.name = name;
        this.lore = lore;
        this.mana = mana;
        this.maxLevel = maxLevel;
    }
}
