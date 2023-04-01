package com.optimus.animusmagic.inventory;

import com.optimus.animusmagic.AnimusMagic;
import com.optimus.animusmagic.item.ItemHandler;
import com.optimus.animusmagic.util.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Objects;

public class ItemBrowser extends CustomInventory implements Listener {

    private int pages;
    private final ArrayList<Integer> itemSlots;
    public ItemBrowser() {
        super(6, "Item Browser");

        pages = 0;
        itemSlots = new ArrayList<>();
        init();

        Bukkit.getPluginManager().registerEvents(this, AnimusMagic.getInstance());
    }

    @Override
    public void init() {
        outline(new ItemStack(Material.GRAY_STAINED_GLASS_PANE));

        setItem(49, ItemUtils.createUIItem(Material.OAK_SIGN, 1, "&aSearch"));

        pages = (int) Math.round(ItemHandler.getInstance().getAmountRegistered() / 28.0);

        if (pages > 1) {
            setItem(53, ItemUtils.createUIItem(Material.ARROW, 1, "&aNext Page"));
        }

        for (int i = 0; i < getSize(); i++) {
            if (getItem(i) == null) {
                itemSlots.add(i);
            }
        }

        ArrayList<ItemStack> items = ItemHandler.getInstance().getItems();
        for (int i = 0; i < items.size(); i++) {
            if (i < 29) {
                addItem(items.get(i));
            }
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (Objects.equals(e.getClickedInventory(), this)) {
            Player player = (Player) e.getWhoClicked();
            ItemStack item = e.getCurrentItem();

            if (item != null) {
                if (itemSlots.contains(e.getSlot())) {
                    player.getInventory().addItem(item);
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 1);
                }
            }
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        if (e.getInventory().equals(this)) {
            HandlerList.unregisterAll(this);
        }
    }
}
