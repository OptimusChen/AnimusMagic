package com.optimus.animusmagic.item.weapon;

import com.optimus.animusmagic.AnimusMagic;
import com.optimus.animusmagic.item.AnimusItem;
import com.optimus.animusmagic.item.ItemRarity;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class EnchantedThrowingAxe extends AnimusItem {

    @Override
    public void onRightClick(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        ItemStack item = new ItemStack(Material.IRON_AXE);
        item.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);

        Location loc = player.getLocation();
        Vector dir = loc.getDirection();
        dir.normalize();
        dir.multiply(2);

        ArmorStand armorStand = loc.getWorld().spawn(loc, ArmorStand.class);
        armorStand.setItemInHand(item);
        armorStand.setVelocity(dir);
        armorStand.setVisible(false);

        final int[] lifespan = {0};

        new BukkitRunnable() {
            @Override
            public void run() {
                if (armorStand.isDead()){
                    cancel();
                }else{
                    lifespan[0] = lifespan[0] + 1;
                    Vector dir = armorStand.getLocation().getDirection();
                    dir.normalize();
                    dir.multiply(2);
                    armorStand.setVelocity(dir);

                    if (armorStand.getNearbyEntities(0.5, 0.5, 0.5).size() > 0){
                        for (Entity e : armorStand.getNearbyEntities(0.5, 0.5, 0.5)){
                            if (!e.getType().equals(EntityType.ARMOR_STAND)) {
                                if (e instanceof LivingEntity livingEntity && !e.equals(player)) {
                                    if (e instanceof Player p) {
                                        p.sendMessage(ChatColor.GRAY + player.getName() + "'s enchanted axe hit you for " + ChatColor.RED + "15❤" + ChatColor.GRAY + "!");
                                    }

                                    livingEntity.damage(15, player);

                                    armorStand.remove();
                                    cancel();
                                }
                            }
                        }
                    }

                    if (lifespan[0] > 4 * 20){
                        armorStand.remove();
                        cancel();
                    }

                }
            }
        }.runTaskTimer(AnimusMagic.getInstance(), 5L, 1);
    }

    @Override
    public String getDisplayName() {
        return "Enchanted Throwing Axe";
    }

    @Override
    public String getLore() {
        return "Throws axes that deal " + ChatColor.RED + "15❤" + ChatColor.GRAY + ". Totally not a axe of the shredded rip-off";
    }

    @Override
    public ItemRarity getRarity() {
        return ItemRarity.LEGENDARY;
    }

    @Override
    public Material getMaterial() {
        return Material.IRON_AXE;
    }

    @Override
    public boolean isEnchanted() {
        return true;
    }
}
