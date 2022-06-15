package com.optimus.animusmagic.inventory;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_18_R2.inventory.CraftInventoryCustom;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class CustomInventory extends CraftInventoryCustom {

    public CustomInventory(int rows, String title) {
        super(null, rows*9, title);

    }

    public abstract void init();

    public void fill(){
        ItemStack item = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(" ");
        item.setItemMeta(meta);
        fill(item);
    }

    public void fill(ItemStack item){
        for (int i = 0; i < getSize(); i++){
            setItem(i, item);
        }
    }

    public void outline(ItemStack item){
        int rowSize = 9;
        int rows = getSize()/ rowSize;

        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < rows; j++) {
                if ((i == 0) ||
                        (j == 0) ||
                        (i == (rowSize -1)) ||
                        (j == (rows-1))){
                    setItem(j* rowSize +i, item);
                }
            }
        }
    }

    public void outline(){
        ItemStack item = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(" ");
        item.setItemMeta(meta);
        outline(item);
    }

    public void placeBackArrow(int slot){
        //setItem(slot, ItemCreator.goBack());
    }
}
