package com.optimus.animusmagic.mob;

import com.optimus.animusmagic.AnimusMagic;
import com.optimus.animusmagic.util.Utils;
import lombok.Getter;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.event.NPCDeathEvent;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

@Getter
public abstract class NPCEntity implements Listener {

    protected NPC npc;
    protected final String name;
    protected int health;
    protected final int maxHealth;
    protected CraftPlayer entity;

    public NPCEntity(String name, int health) {
        this.name = name;
        this.health = health;
        this.maxHealth = health;
        this.entity = null;

        Bukkit.getPluginManager().registerEvents(this, AnimusMagic.getInstance());
    }

    public abstract void loadData();

    public abstract void AI(ArmorStand healthDisplay);

    public void spawn(Location location) {
        npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "Darkstalker Boss");
        npc.spawn(location);
        entity = (CraftPlayer) npc.getEntity();
        loadData();
        npc.setProtected(false);
        npc.data().set(NPC.NAMEPLATE_VISIBLE_METADATA, false);
        ArmorStand healthDisplay = npc.getEntity().getWorld().spawn(new Location(npc.getEntity().getLocation().getWorld(), npc.getEntity().getLocation().getX(), npc.getEntity().getLocation().getY() + 0.75, npc.getEntity().getLocation().getZ()), ArmorStand.class);
        healthDisplay.setCustomNameVisible(true);
        healthDisplay.setInvulnerable(true);
        healthDisplay.setGravity(false);
        healthDisplay.setVisible(false);
        healthDisplay.setSmall(true);
        healthDisplay.setCustomName(ChatColor.RED + name + " " + ChatColor.GREEN + Utils.format(health) + ChatColor.RED + "❤");
        AI(healthDisplay);
    }

    @EventHandler
    public void onDeath(NPCDeathEvent e) {
        if (e.getNPC().equals(npc)) {
            HandlerList.unregisterAll(this);
            npc.despawn();
        }
    }
}
