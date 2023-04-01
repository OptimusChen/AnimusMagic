package com.optimus.animusmagic.talisman;

import com.optimus.animusmagic.AnimusMagic;
import com.optimus.animusmagic.util.ItemUtils;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_19_R1.inventory.CraftItemStack;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Getter
public class Talisman {

    private TalismanType type;
    private int teir;

    public Talisman(TalismanType type, int teir){
        this.type = type;
        this.teir = teir;
    }

    public ItemStack getItem(){
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        List<String> lore = new ArrayList<>();

        ItemUtils.setMaxStackSize(CraftItemStack.asNMSCopy(item).c(), 1);
        ItemUtils.addLoreMessage(type.lore.replaceAll("%level", "" + teir).
                replaceAll("%halflevel", "" + teir/2), lore);

        if (!(type.equals(TalismanType.GENERIC) || type.equals(TalismanType.GLIDING) || type.equals(TalismanType.GLOWING) || type.equals(TalismanType.STEALTH))){
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', type.name + " " + teir));
        }else{
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', type.name));
        }

        meta.setLore(lore);
        item.setItemMeta(meta);

        ItemUtils.IDtoSkull(item, type.headId);

        return item;
    }

    public static boolean isTalisman(ItemStack item){
        ItemMeta meta = item.getItemMeta();

        if (meta == null) return false;

        if (meta.hasDisplayName()){
            String name = ChatColor.stripColor(meta.getDisplayName()).toLowerCase();
            if (name.contains("talisman") && item.getType().equals(Material.PLAYER_HEAD)){
                return true;
            }else{
                return false;
            }
        }
        return false;
    }

    public static Talisman parse(ItemStack item){
        ItemMeta meta = item.getItemMeta();
        String[] split = ChatColor.stripColor(meta.getDisplayName()).split(" ");

        try {
            TalismanType type = TalismanType.valueOf(split[0].toUpperCase());
            int teir;

            if (split.length == 2){
                teir = 1;
            } else {
                teir = Integer.parseInt(split[2]);
            }

            Talisman talisman = new Talisman(type, teir);

            return talisman;
        }catch (IllegalArgumentException e){

        }
        return null;
    }
}
