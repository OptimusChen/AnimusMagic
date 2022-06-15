package com.optimus.animusmagic.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.world.item.Item;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Skull;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

public class ItemUtils {

    public static ItemStack createUIItem(Material material, int amount, String name) {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        item.setItemMeta(meta);

        return item;
    }

    public static void addLoreMessage(String string, List<String> lore){
        String[] strings = string.split(" ");
        String tmp = "";
        for (String s : strings){
            if ((s.length() + 1 + tmp.length()) <= 32){
                tmp = tmp + s + " ";
            } else {
                lore.add(ChatColor.GRAY + tmp);
                tmp = s + " ";
            }
        }
        if (tmp.length() > 0) {
            lore.add(ChatColor.GRAY + tmp);
        }
    }

    public static ItemStack IDtoSkull(ItemStack head, String url) {

        SkullMeta skullMeta = (SkullMeta) head.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);

        profile.getProperties().put("textures", new Property("textures", url));

        try {
            Method mtd = skullMeta.getClass().getDeclaredMethod("setProfile", GameProfile.class);
            mtd.setAccessible(true);
            mtd.invoke(skullMeta, profile);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            ex.printStackTrace();
        }

        head.setItemMeta(skullMeta);
        return head;
    }

    public static void setMaxStackSize(Item item, int i){
        try {

            Field field = Item.class.getDeclaredField("maxStackSize");
            field.setAccessible(true);
            field.setInt(item, i);

        } catch (Exception e) {
        }
    }
}
