package com.optimus.animusmagic.item.weapon;

import com.optimus.animusmagic.AnimusMagic;
import com.optimus.animusmagic.item.AnimusItem;
import com.optimus.animusmagic.item.ItemRarity;
import com.optimus.animusmagic.item.ListeningItem;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class PlayerGun extends ListeningItem {

    @EventHandler
    public void onInteract(PlayerInteractEntityEvent e) {
        if (e.getRightClicked().getType().equals(EntityType.PLAYER)) {
            Player player = e.getPlayer();
            if (player.isSneaking()) {
                ItemStack item = e.getPlayer().getItemInHand();
                ItemMeta meta = item.getItemMeta();
                if (meta == null) return;
                meta.setDisplayName(ChatColor.GOLD + e.getRightClicked().getName() + " Gun");
                item.setItemMeta(meta);
            }
        }
    }

    @Override
    public void onRightClick(PlayerInteractEvent e) {
        Player player = e.getPlayer();

        String name = ChatColor.stripColor(player.getItemInHand().getItemMeta().getDisplayName()).split(" ")[0];

        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwner(name);
        item.setItemMeta(meta);

        Location loc = player.getLocation();
        Vector dir = loc.getDirection();
        dir.normalize();
        dir.multiply(2);

        ArmorStand armorStand = loc.getWorld().spawn(loc, ArmorStand.class);
        armorStand.setVelocity(dir);
        armorStand.setVisible(false);
        armorStand.setHelmet(item);

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
                                    armorStand.getWorld().createExplosion(armorStand.getLocation(), 5.0f);

                                    armorStand.remove();
                                    cancel();
                                }
                            }
                        }
                    }

                    if (armorStand.isOnGround()) {
                        armorStand.getWorld().createExplosion(armorStand.getLocation(), 5.0f);

                        armorStand.remove();
                        cancel();
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
        return "Player Gun";
    }

    @Override
    public String getLore() {
        return "Shoots hurb skulls that create explosions";
    }

    @Override
    public ItemRarity getRarity() {
        return ItemRarity.LEGENDARY;
    }

    @Override
    public Material getMaterial() {
        return Material.GOLDEN_HORSE_ARMOR;
    }

    @Override
    public boolean isEnchanted() {
        return false;
    }
}
