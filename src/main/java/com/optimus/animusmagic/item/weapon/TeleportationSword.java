package com.optimus.animusmagic.item.weapon;

import com.optimus.animusmagic.item.AnimusItem;
import com.optimus.animusmagic.item.ItemRarity;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

public class TeleportationSword extends AnimusItem {

    @Override
    public void onRightClick(PlayerInteractEvent e) {
        ItemStack item = e.getItem();
        Player player = e.getPlayer();
        ItemMeta meta = item.getItemMeta();

        if (meta.hasDisplayName()) {
            if (ChatColor.stripColor(meta.getDisplayName()).
                    equalsIgnoreCase("Teleportation Sword")) {
                player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);

                for (int i = 0; i < 64; i++){
                    Location loc = player.getLocation();
                    Vector dir = loc.getDirection();
                    dir.normalize();
                    dir.multiply(0.125);
                    loc.add(dir);
                    loc.setY(loc.getY() + 0.01);
                    if (loc.getBlock().getType().equals(Material.AIR)
                            || loc.getBlock().getType().equals(Material.GRASS)
                            || loc.getBlock().getType().equals(Material.TALL_GRASS)) {
                        player.teleport(loc);
                    } else {
                        Vector vec = loc.getDirection();
                        vec.normalize();
                        vec.multiply(-0.75);
                        loc.add(vec);
                        player.teleport(loc);
                        player.sendMessage(ChatColor.RED + "There are blocks in the way!");
                        break;
                    }
                }
            }
        }
    }

    @Override
    public String getDisplayName() {
        return "Teleportation Sword";
    }

    @Override
    public String getLore() {
        return "Totally not a aspect of the end knock-off";
    }

    @Override
    public ItemRarity getRarity() {
        return ItemRarity.RARE;
    }

    @Override
    public Material getMaterial() {
        return Material.DIAMOND_SWORD;
    }

    @Override
    public boolean isEnchanted() {
        return false;
    }
}
