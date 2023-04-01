package com.optimus.animusmagic.commands;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import com.optimus.animusmagic.mob.Darkstalker;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.trait.SkinTrait;
import net.minecraft.server.level.EntityPlayer;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.profile.PlayerTextures;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class SpawnNPCCommand extends CustomCommand {

    public SpawnNPCCommand() {
        super("spawnnpc");
    }

    @Override
    public void execute(Player player, String[] args) {
        if (player.isOp()) {
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("Starflight")) {
                    NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, ChatColor.YELLOW + "" + ChatColor.BOLD + "Click");
                    npc.spawn(player.getLocation());

                    EntityPlayer entityPlayer = ((CraftPlayer) npc.getEntity()).getHandle();;

                    GameProfile gameProfile = entityPlayer.fz();
                    gameProfile.getProperties().removeAll("textures");

                    PropertyMap map = gameProfile.getProperties();

                    map.put("textures", new Property("textures", "ewogICJ0aW1lc3RhbXAiIDogMTYxNzI5OTA4MDc0MCwKICAicHJvZmlsZUlkIiA6ICJiN2ZkYmU2N2NkMDA0NjgzYjlmYTllM2UxNzczODI1NCIsCiAgInByb2ZpbGVOYW1lIiA6ICJDVUNGTDE0IiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2EwNmU2NzNjMzk0MzA5N2RjYzg4ZTE3MDNhYmIxZWZlZTcyOTZiYzBjYzFhOTU0N2QyMDBkN2YzOGM5YzU5NDAiCiAgICB9CiAgfQp9", "eoH/M+M49Hew7JhBiEGJDXxmFJNqmU8yUIFgtJBcZZdyfVpjZnnR59YGPcyJZg/FBlvKBnV02red1O2OS7JGteKTow7cmmCDlyAyTyCxjqstyRKl9ZsPbVSbxboghTWAVrl4dLZTRrFe0/rHcMVXngkdU0z1talRVBg2ylr0faO7j8vx76MF+Lclq8W0ZCmLDagGZ5VADeXu3hmMkAXIFmzZKglJAH4i7ncxOP2Z1kwtYu+pD13XJBjDI1UDCSnm758fEFMNiUmg7JZ8RrVeeiyOFwdYE/U32g2scplC5eq7WbV6vVpXCArI4V4ZSUPELccO/aVgu8eD1SGnuLvYygq1TmDe+7nwDMTJXvihy2k9MDpbuS7SmpHWNFUfJPLeLX11uGdGM5/M+1QPxvvQ3wwPVVzJ2fCDUm8NnuQ4EpGB1Rn2uNkSsK1oQ5pMj4SIp9dHAs26QtlXr631QBLkru5QVVjfg7mGdPCFBRFWRWn3o9JOIdsWtHjr1Zi+K9QOwnjLZm3pQZLlVhAOW4vzsaMdnlqqTPFYycaJC9+HqU7ghlDlvU7VfiEmcYRP6MrKBzqEfeqILASiHadCLq6icCMFGUd63j2ohyCm6ZSqMYrvvcedhH0PhcV9Sn90+3x1F6IlSToCDz3aFlKPE9+APhW8HChfd+d7hSa3WT851/k="));

                    ArmorStand armorStand = player.getWorld().spawn(new Location(player.getLocation().getWorld(), player.getLocation().getX(), player.getLocation().getY() + 0.2, player.getLocation().getZ()), ArmorStand.class);
                    armorStand.setCustomName("Starflight");
                    armorStand.setVisible(false);
                    armorStand.setGravity(false);
                    armorStand.setInvulnerable(true);
                    armorStand.setCustomNameVisible(true);

                } else if (args[0].equalsIgnoreCase("Darkstalker")) {
                    new Darkstalker().spawn(player.getLocation());
                }
            }
        }
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return Collections.emptyList();
    }
}
