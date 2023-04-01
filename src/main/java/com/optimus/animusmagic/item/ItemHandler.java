package com.optimus.animusmagic.item;

import com.optimus.animusmagic.item.misc.AnimusBook;
import com.optimus.animusmagic.item.weapon.*;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ItemHandler {

    private static ItemHandler instance;
    private final HashMap<String, AnimusItem> items;
    private int amountRegistered;
    public ItemHandler(){
        items = new HashMap<>();

        instance = this;

        registerItem(new GoodSword());
        registerItem(new GoodBow());
        registerItem(new TeleportationSword());
        registerItem(new AnimusBook());
        registerItem(new EnchantedThrowingAxe());
        registerItem(new ExplosiveBow());
        registerItem(new PlayerGun());
    }

    public ArrayList<ItemStack> getItems() {
        ArrayList<ItemStack> list = new ArrayList<>();
        for (Map.Entry<String, AnimusItem> entry : items.entrySet()) {
            list.add(entry.getValue().getItem());
        }
        return list;
    }

    public int getAmountRegistered() { return amountRegistered; }

    private void registerItem(AnimusItem item){
        items.put(item.getInternalName(), item);
        amountRegistered++;
    }

    public boolean isRegistered(ItemStack item){
        if (getRegistered(item) != null) {
            return true;
        }

        return false;
    }

    public AnimusItem getRegistered(ItemStack item){
        if (item == null)  return null;
        if (item.getItemMeta() == null) return null;

        if (item.getItemMeta().hasDisplayName()) {
            return items.get(ChatColor.stripColor(item.getItemMeta().getDisplayName().toLowerCase().replaceAll(" ", "_")));
        }

        return null;
    }

    public AnimusItem getRegistered(String s){
        return items.get(s);
    }

    public static ItemHandler getInstance() {
        if (instance == null) {
            return new ItemHandler();
        }

        return instance;
    }
}
