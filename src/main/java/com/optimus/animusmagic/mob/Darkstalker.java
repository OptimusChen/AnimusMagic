package com.optimus.animusmagic.mob;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import com.optimus.animusmagic.AnimusMagic;
import com.optimus.animusmagic.item.ItemHandler;
import com.optimus.animusmagic.util.ItemUtils;
import com.optimus.animusmagic.util.Utils;
import jdk.jfr.Enabled;
import net.citizensnpcs.api.event.NPCDeathEvent;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.trait.Equipment;
import net.citizensnpcs.trait.SkinTrait;
import net.minecraft.server.level.EntityPlayer;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_19_R1.metadata.EntityMetadataStore;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.Random;

public class Darkstalker extends NPCEntity {

    private Player target;
    private Equipment equipment;
    private boolean canUseRanged;
    private boolean canUsePearl;
    public Darkstalker() {
        super("Darkstalker", 2000);
    }

    @Override
    public void loadData() {
        npc.getOrAddTrait(SkinTrait.class).setTexture("ewogICJ0aW1lc3RhbXAiIDogMTYxNjgwNDAxMDg2MCwKICAicHJvZmlsZUlkIiA6ICJjMGYzYjI3YTUwMDE0YzVhYjIxZDc5ZGRlMTAxZGZlMiIsCiAgInByb2ZpbGVOYW1lIiA6ICJDVUNGTDEzIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzg4MDkwZDA3NjExNDI2ZmZkYWE3NjRjNmYwNGE1YzJjNWQ0NmMzMWM3YmU1ODBiY2UxMzg3ZTQwZjcyNmI2NzUiCiAgICB9CiAgfQp9", "Tps7dCx/j6s4DObuqDcvaCIZJi+aXduWyY9Jl6CaqS0pZp7KEHW8b2YfNCctzshuEdRDe2j7W6XCr6kCYht6P5wmhjw371zKVwS0yuBnqoHFRr2T2LWNalg8eqZ5XbN9BQdEwF9ZVuQ3X7J5XO3L8bYd2eS1kaFKoiadr1N4l6bD+QkThFeEcoliT4wpGgFYbRV70qH/5+i2YpIwx9pMOcUP//NAjW+/lULMcW/ysAEC5B2ulJo5EcfMcxMUOBhZdATRPJWNw5q45uE0f5byLYt7sh++dPqn3tFrxlffiLPZ8aac/Km8mF2c4ra966V5wTcFZVZkqxOxqGE1h+eZ1PrhYAF838DPoqpSgS/Fh1ECFlp3ofJAp3u9jl7csUyan1HYLqQTswg5yHqyEn1IBnCMpKvGSLqzuINgzmTuEkWbLlmgrX0AOQH1mMoRLaNjbDRxKEPyDG29YkZrBRkQWdTmrjY+FXkBps6iIsmPe3PyUu7C+T0MTFtTy99t+SB5L4bU5beOav1PtRApn2x9lM4SpANUoJdtVrxQfVp1WjsF+f+gYRdqL2f0qFqk40D8w+osdZOylbDu5oipBrjkwCkoJFbkcK8mhWjfDX+Hoe9eYddv4YIGp+7DvpSxE9EpWTty2/QYAlk539ThSUGi7D+71unhW1vOFbp5rFVKQ8I=");
        npc.getOrAddTrait(SkinTrait.class).setShouldUpdateSkins(true);

        canUseRanged = true;
        canUsePearl = true;
        equipment = npc.getOrAddTrait(Equipment.class);

        entity = (CraftPlayer) npc.getEntity();

        EntityPlayer entityPlayer = entity.getHandle();;

        GameProfile gameProfile = entityPlayer.fz();
        gameProfile.getProperties().removeAll("textures");

        PropertyMap map = gameProfile.getProperties();

        map.put("textures", new Property("textures", "ewogICJ0aW1lc3RhbXAiIDogMTYxNjgwNDAxMDg2MCwKICAicHJvZmlsZUlkIiA6ICJjMGYzYjI3YTUwMDE0YzVhYjIxZDc5ZGRlMTAxZGZlMiIsCiAgInByb2ZpbGVOYW1lIiA6ICJDVUNGTDEzIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzg4MDkwZDA3NjExNDI2ZmZkYWE3NjRjNmYwNGE1YzJjNWQ0NmMzMWM3YmU1ODBiY2UxMzg3ZTQwZjcyNmI2NzUiCiAgICB9CiAgfQp9", "Tps7dCx/j6s4DObuqDcvaCIZJi+aXduWyY9Jl6CaqS0pZp7KEHW8b2YfNCctzshuEdRDe2j7W6XCr6kCYht6P5wmhjw371zKVwS0yuBnqoHFRr2T2LWNalg8eqZ5XbN9BQdEwF9ZVuQ3X7J5XO3L8bYd2eS1kaFKoiadr1N4l6bD+QkThFeEcoliT4wpGgFYbRV70qH/5+i2YpIwx9pMOcUP//NAjW+/lULMcW/ysAEC5B2ulJo5EcfMcxMUOBhZdATRPJWNw5q45uE0f5byLYt7sh++dPqn3tFrxlffiLPZ8aac/Km8mF2c4ra966V5wTcFZVZkqxOxqGE1h+eZ1PrhYAF838DPoqpSgS/Fh1ECFlp3ofJAp3u9jl7csUyan1HYLqQTswg5yHqyEn1IBnCMpKvGSLqzuINgzmTuEkWbLlmgrX0AOQH1mMoRLaNjbDRxKEPyDG29YkZrBRkQWdTmrjY+FXkBps6iIsmPe3PyUu7C+T0MTFtTy99t+SB5L4bU5beOav1PtRApn2x9lM4SpANUoJdtVrxQfVp1WjsF+f+gYRdqL2f0qFqk40D8w+osdZOylbDu5oipBrjkwCkoJFbkcK8mhWjfDX+Hoe9eYddv4YIGp+7DvpSxE9EpWTty2/QYAlk539ThSUGi7D+71unhW1vOFbp5rFVKQ8I="));

        entity.setMaxHealth(maxHealth);
        entity.setHealth(health);

        entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(150);

        entity.getEquipment().setItemInMainHand(new ItemStack(Material.BOW));
        entity.getEquipment().setChestplate(new ItemStack(Material.ELYTRA));
        entity.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 1, true));
        entity.setMetadata("NPC", new FixedMetadataValue(AnimusMagic.getInstance(), true));
    }

    @Override
    public void AI(ArmorStand armorStand) {
        final int[] i = {0};
        new BukkitRunnable() {
            @Override
            public void run() {
                i[0] = i[0] + 1;

                if (npc.isSpawned()) {
                    if (armorStand != null){
                        armorStand.setFireTicks(0);
                        armorStand.teleport(new Location(entity.getWorld(), entity.getLocation().getX(), entity.getLocation().getY() + 0.75, entity.getLocation().getZ()));
                        armorStand.setCustomName(ChatColor.RED + "Darkstalker " + ChatColor.GREEN + Utils.format(Math.round(entity.getHealth())) + ChatColor.RED + "❤");
                    }else{
                        npc.destroy();
                    }

                    List<Entity> entities1 = entity.getNearbyEntities(3, 3, 3);
                    List<Entity> entities2 = entity.getNearbyEntities(40, 20, 40);

                    boolean hasPlayers = false;

                    if (target == null) {
                        for (Entity e : entities1) {
                            if (e instanceof Player) {
                                if (!e.hasMetadata("NPC") &&
                                    e != entity) {
                                    hasPlayers = true;
                                    npc.getNavigator().setTarget(e, true);
                                    target = (Player) e;
                                }
                            }
                        }
                    }

                    if (!hasPlayers && target == null) {
                        for (Entity e : entities2) {
                            if (e instanceof Player) {
                                if (!e.hasMetadata("NPC") &&
                                        e != entity) {
                                    target = entity;
                                }
                            }
                        }
                    }

                    if (!entities2.contains(target) || i[0] % 10 == 0) {
                        target = null;
                    }

                    entity.setSprinting(true);

                    if (i[0] % 5 == 0) {
                        Material type = entity.getLocation().getBlock().getType();
                        Location loc = entity.getLocation();

                        if (i[0] % 20 == 0) {
                            if (canUsePearl) {
                                if (type.equals(Material.LAVA) || type.equals(Material.WATER)) {
                                    pearl();
                                }
                            }

                            if (entity.getFireTicks() > 0 && !type.equals(Material.LAVA)) {
                                extinguish();
                            }

                            Location head = new Location(loc.getWorld(), loc.getX(), loc.getY() + 1, loc.getZ());

                            if (!head.getBlock().getType().equals(Material.AIR)) {
                                breakOut();
                            }
                        }

                        if (i[0] % 30 == 0) {
                            if (entity.getHealth() < 200) {
                                heal();
                            }

                            if (!hasPlayers && canUseRanged) {
                                rangedAttack();
                            }
                        }

                        if (hasPlayers) {
                            meleeAttack();
                        }

                        if (target != null) {
                            entity.teleport(entity.getLocation().setDirection(target.getLocation().subtract(entity.getLocation()).toVector()));
                        }
                    }
                }else if (!npc.isSpawned()){
                    armorStand.remove();
                    npc.destroy();
                    cancel();
                }
            }
        }.runTaskTimer(AnimusMagic.getInstance(), 5L, 1);
    }

    private void meleeAttack() {
        Random rand = new Random();

        switch (rand.nextInt(2)) {
            case 0:

                break;
        }
    }

    private void rangedAttack() {
        Random rand = new Random();
        Location loc;
        npc.getNavigator().setTarget(target, true);

        switch (1) {
            case 0:
                equipment.set(Equipment.EquipmentSlot.HAND, new ItemStack(Material.BOW));


                break;
            case 1:
                ItemStack item = new ItemStack(Material.IRON_AXE);
                item.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);

                equipment.set(Equipment.EquipmentSlot.HAND, item);
                entity.swingMainHand();

                loc = entity.getLocation();
                Vector dir = loc.getDirection();
                dir.normalize();
                dir.multiply(2);

                ArmorStand armorStand = loc.getWorld().spawn(loc, ArmorStand.class);
                armorStand.setItemInHand(item);
                armorStand.setVelocity(dir);
                armorStand.setVisible(false);

                final int[] lifespan = {0};

                equipment.set(Equipment.EquipmentSlot.HAND, new ItemStack(Material.AIR));
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
                                        if (e instanceof LivingEntity livingEntity) {
                                            if (e instanceof Player player) {
                                                player.sendMessage(ChatColor.GRAY + "Darkstalker's enchanted axe hit you for " + ChatColor.RED + "15❤" + ChatColor.GRAY + "!");
                                            }

                                            livingEntity.damage(15, entity);

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
                break;
        }
    }

    private void pearl() {
        canUseRanged = false;
        canUsePearl = false;

        equipment.set(Equipment.EquipmentSlot.HAND, new ItemStack(Material.ENDER_PEARL));
        entity.swingMainHand();

        if (target != null) {
            entity.teleport(entity.getLocation().setDirection(target.getLocation().subtract(entity.getLocation()).toVector()));
        }

        Location loc = entity.getEyeLocation().toVector().add(entity.getLocation().getDirection().multiply(2)).toLocation(entity.getWorld(),
                entity.getLocation().getYaw(),
                entity.getLocation().getPitch());
        EnderPearl pearl = entity.getWorld().spawn(loc, EnderPearl.class);
        pearl.setShooter(entity);

        Vector dir = entity.getLocation().getDirection();
        dir.normalize();
        dir.multiply(1.5);

        pearl.setVelocity(dir);

        new BukkitRunnable() {
            @Override
            public void run() {
                canUseRanged = true;
            }
        }.runTaskLater(AnimusMagic.getInstance(), 30);


        new BukkitRunnable() {
            @Override
            public void run() {
                canUsePearl = true;
            }
        }.runTaskLater(AnimusMagic.getInstance(), 60);
    }

    private void extinguish() {
        canUseRanged = false;

        Location loc = entity.getLocation();
        Material type = entity.getWorld().getBlockAt(loc).getType();

        if (type.equals(Material.AIR) || type.equals(Material.CAVE_AIR)) {
            ItemStack pot = new ItemStack(Material.SPLASH_POTION);
            PotionMeta meta = (PotionMeta) pot.getItemMeta();
            meta.setColor(Color.BLUE);
            pot.setItemMeta(meta);

            equipment.set(Equipment.EquipmentSlot.HAND, pot);

            new BukkitRunnable() {
                @Override
                public void run() {
                    entity.teleport(new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), 90));
                    entity.swingMainHand();
                    equipment.set(Equipment.EquipmentSlot.HAND, new ItemStack(Material.AIR));

                    ThrownPotion thrownPotion = (ThrownPotion) entity.getWorld().spawnEntity(loc,EntityType.SPLASH_POTION);
                    thrownPotion.setItem(pot);
                    thrownPotion.setVelocity(entity.getLocation().getDirection().multiply(1.5));
                }
            }.runTaskLater(AnimusMagic.getInstance(), 5);

            new BukkitRunnable() {
                @Override
                public void run() {
                    entity.setFireTicks(0);

                    if (target != null) {
                        entity.teleport(entity.getLocation().setDirection(target.getLocation().subtract(entity.getLocation()).toVector()));
                    }

                    canUseRanged = true;
                }
            }.runTaskLater(AnimusMagic.getInstance(), 7);
        }
    }

    private void heal() {
        canUseRanged = false;

        equipment.set(Equipment.EquipmentSlot.HAND, new ItemStack(Material.GOLDEN_APPLE));
        playSound(Sound.ENTITY_GENERIC_EAT, 1);
        new BukkitRunnable() {
            @Override
            public void run() {
                canUseRanged = true;
            }
        }.runTaskLater(AnimusMagic.getInstance(), 32);
    }

    private void breakOut() {
        new BukkitRunnable() {
            @Override
            public void run() {
                Location loc = entity.getLocation();
                loc.setY(loc.getY() + 1);
            }
        }.runTaskLater(AnimusMagic.getInstance(), 200);
    }

    private void playSound(Sound sound, float pitch) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.playSound(entity.getLocation(), sound, 10, pitch);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if (e.getDamager().equals(entity) &&
            e.getDamager().getLocation().distance(e.getEntity().getLocation()) < 3) {
            Location loc = entity.getEyeLocation().toVector().add(entity.getLocation().getDirection().multiply(2)).toLocation(entity.getWorld(),
                    entity.getLocation().getYaw(),
                    entity.getLocation().getPitch());

            playSound(Sound.ENTITY_PLAYER_ATTACK_SWEEP, 1);
            entity.getWorld().spawnParticle(Particle.SWEEP_ATTACK, loc, 1);
            entity.getWorld().spawnParticle(Particle.SWEEP_ATTACK, new Location(loc.getWorld(), loc.getX(), loc.getY() + 1, loc.getZ()), 1);
            entity.getWorld().spawnParticle(Particle.SWEEP_ATTACK, new Location(loc.getWorld(), loc.getX(), loc.getY() - 1, loc.getZ()), 1);
        }
    }

    @EventHandler
    public void onTP(PlayerTeleportEvent e) {
        if (e.getPlayer().equals(entity)) {
            canUsePearl = true;
            equipment.set(Equipment.EquipmentSlot.HAND, new ItemStack(Material.AIR));
        }
    }

    @EventHandler
    public void onDeath(NPCDeathEvent e) {
        if (e.getNPC().equals(npc)) {
            ItemStack item = new ItemStack(Material.NETHERITE_SWORD);
            item.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 10);

            ItemStack aote = ItemUtils.createUIItem(Material.DIAMOND_SWORD, 1, ChatColor.BLUE + "Teleportation Sword");
            ItemMeta meta = aote.getItemMeta();
            aote.setItemMeta(meta);
            aote.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 10);

            entity.getWorld().dropItem(entity.getLocation(), item);
            entity.getWorld().dropItem(entity.getLocation(), ItemHandler.getInstance().getRegistered("animus_book").getItem());

            npc.despawn();
            npc.destroy();
        }
    }
}
