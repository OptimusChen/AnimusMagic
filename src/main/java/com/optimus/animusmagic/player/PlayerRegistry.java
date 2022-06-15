package com.optimus.animusmagic.player;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class PlayerRegistry {

    private static HashMap<UUID, AnimusPlayer> map;

    public PlayerRegistry(){
        map = new HashMap<>();
    }

    public static void register(Player player){
        AnimusPlayer animusPlayer = new AnimusPlayer(player);
        map.put(player.getUniqueId(), animusPlayer);
    }

    public static void unRegister(Player player){
        get(player).disconnect();
        map.remove(player.getUniqueId());
    }

    public static void unRegister(UUID uuid){
        map.remove(uuid);
    }

    public static AnimusPlayer get(Player player){
        return map.get(player.getUniqueId());
    }

}
