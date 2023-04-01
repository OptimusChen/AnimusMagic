package com.optimus.animusmagic;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import com.optimus.animusmagic.commands.*;
import com.optimus.animusmagic.listeners.EventListener;
import com.optimus.animusmagic.player.PlayerRegistry;
import net.minecraft.server.level.EntityPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.UUID;

public final class AnimusMagic extends JavaPlugin {

    private static AnimusMagic main;
    private PlayerRegistry registry;

    @Override
    public void onEnable() {
        main = this;
        registry = new PlayerRegistry();

        Bukkit.getPluginManager().registerEvents(new EventListener(), this);
        getCommand("accessorybag").setExecutor(new AccessoryBagCommand());
        getCommand("customitems").setExecutor(new ItemCommand());
        getCommand("animus").setExecutor(new AnimusCommand());
        getCommand("animusadmin").setExecutor(new AdminCommand());
        getCommand("spawnnpc").setExecutor(new SpawnNPCCommand());
        getCommand("itembrowser").setExecutor(new ItemBrowserCommand());

    }

    @Override
    public void onDisable() {

    }

    public static AnimusMagic getInstance() { return main; }

}
