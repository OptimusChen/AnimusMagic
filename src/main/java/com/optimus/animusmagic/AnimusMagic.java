package com.optimus.animusmagic;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.optimus.animusmagic.commands.AccessoryBagCommand;
import com.optimus.animusmagic.commands.AdminCommand;
import com.optimus.animusmagic.commands.AnimusCommand;
import com.optimus.animusmagic.commands.ItemCommand;
import com.optimus.animusmagic.listeners.EventListener;
import com.optimus.animusmagic.player.PlayerRegistry;
import org.bukkit.Bukkit;
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
    }

    @Override
    public void onDisable() {

    }

    public static AnimusMagic getInstance() { return main; }

}
