package com.optimus.animusmagic.item;

import com.optimus.animusmagic.util.ItemUtils;
import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public abstract class AnimusItem {

    public AnimusItem() {

    }
    public void onEntityDamage(EntityDamageByEntityEvent e) {}

    public void onRightClick(PlayerInteractEvent e) {}

    public void onLeftClick(PlayerInteractEvent e) {}

    public void onBowShoot(EntityShootBowEvent e) {}

    public abstract String getDisplayName();

    public abstract String getLore();

    public abstract ItemRarity getRarity();

    public abstract Material getMaterial();

    public abstract boolean isEnchanted();

    public String getInternalName() {
        return ChatColor.stripColor(getItem().getItemMeta().getDisplayName().toLowerCase().replaceAll(" ", "_"));
    }

    public ItemStack getItem() {
        ItemStack item = new ItemStack(getMaterial());
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();

        ItemUtils.addLoreMessage(getLore(), lore);
        lore.add(" ");

        ItemRarity rarity = getRarity();

        lore.add(rarity.getColor() + "" + ChatColor.BOLD + rarity.name());

        meta.setLore(lore);
        meta.setDisplayName(rarity.getColor() + getDisplayName());

        if (isEnchanted()) {
            meta.addEnchant(Enchantment.CHANNELING, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

        item.setItemMeta(meta);

        return item;
    }

    /*

    Currently unused

    public void onArmorEquip(PlayerArmorEquipEvent e) { }

    public void onArmorUnEquip(PlayerArmorUnequipEvent e) {}

     */
}
