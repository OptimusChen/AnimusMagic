package com.optimus.animusmagic.inventory;

import com.optimus.animusmagic.AnimusMagic;
import com.optimus.animusmagic.event.TalismanEquipEvent;
import com.optimus.animusmagic.event.TalismanUnEquipEvent;
import com.optimus.animusmagic.player.AnimusPlayer;
import com.optimus.animusmagic.player.PlayerRegistry;
import com.optimus.animusmagic.talisman.Talisman;
import com.optimus.animusmagic.util.ItemUtils;
import net.minecraft.world.item.ItemArmor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftItem;
import org.bukkit.craftbukkit.v1_19_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class AccessoryBag extends CustomInventory implements Listener {

    private final Player player;
    private final AnimusPlayer animusPlayer;
    private final static ArrayList<Integer> talisman = new ArrayList<>();
    private static final ArrayList<Integer> accessory = new ArrayList<>();
    private static final ArrayList<Integer> storage = new ArrayList<>();

    public AccessoryBag(AnimusPlayer player) {
        super(6, "Accessories");
        this.player = player.getBukkitPlayer();
        this.animusPlayer = player;
        init();
        Bukkit.getPluginManager().registerEvents(this, AnimusMagic.getInstance());
    }

    @Override
    public void init() {
        fill();

        ArrayList<ItemStack> accessories = (ArrayList<ItemStack>) animusPlayer.getConfig().getItem("stored-active");
        ArrayList<ItemStack> storedAccessories = (ArrayList<ItemStack>) animusPlayer.getConfig().getItem("stored-accessories");
        ArrayList<ItemStack> talismans = (ArrayList<ItemStack>) animusPlayer.getConfig().getItem("stored-talismans");

        for (int i = 0; i < 9; i++){
            setItem(i, ItemUtils.createUIItem(Material.WHITE_STAINED_GLASS_PANE, 1,
                    "&aActive Accessory Slot " + (i + 1)));
            accessory.add(i);

            if (accessories.size() > 0){
                setItem(i, accessories.get(i));
            }
        }

        for (int i = 18; i < 36; i++){
            setItem(i, new ItemStack(Material.AIR));

            if (storedAccessories.size() > 0){
                setItem(i, storedAccessories.get(i-18));
            }

            storage.add(i);
        }

        for (int i = 45; i < 49; i++){
            setItem(i, ItemUtils.createUIItem(Material.WHITE_STAINED_GLASS_PANE, 1,
                    "&aActive Talisman Slot " + (i - 44)));
            talisman.add(i);

            if (talismans.size() > 0){
                setItem(i, talismans.get(i-45));
                if (Talisman.isTalisman(talismans.get(i-45))) {
                    Bukkit.getPluginManager().callEvent(new TalismanEquipEvent(animusPlayer, talismans.get(i-45)));
                }
            }
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        if (Objects.equals(e.getClickedInventory(), this)) {
            Player player = (Player) e.getWhoClicked();
            AnimusPlayer animusPlayer = PlayerRegistry.get(player);
            ItemStack item = e.getCurrentItem();

            if (talisman.contains(e.getSlot())){
                if (Talisman.isTalisman(e.getCursor()) && item != null){
                    Bukkit.getPluginManager().callEvent(new TalismanEquipEvent(animusPlayer, e.getCursor()));
                    setItem(e.getSlot(), e.getCursor());

                    player.setItemOnCursor(null);
                    player.sendMessage(ChatColor.GREEN + "Added!");
                    player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_LEATHER, 10, 1);
                } else if (e.getCursor().getType().equals(Material.AIR) && item.getType().equals(Material.PLAYER_HEAD)) {
                    player.setItemOnCursor(item);

                    Bukkit.getPluginManager().callEvent(new TalismanUnEquipEvent(animusPlayer, e.getCurrentItem()));

                    setItem(e.getSlot(), ItemUtils.createUIItem(Material.WHITE_STAINED_GLASS_PANE, 1,
                            "&aActive Talisman Slot " + (e.getSlot() - 44)));

                    player.sendMessage(ChatColor.GREEN + "Removed!");
                    player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_LEATHER, 10, 1);
                } else if (!Talisman.isTalisman(e.getCursor())) {
                    player.sendMessage(ChatColor.RED + "Item is not a talisman!");
                } else {
                    player.sendMessage(ChatColor.RED + "Please remove the item on your cursor!");
                }
            } else if (accessory.contains(e.getSlot()) && item != null) {
                if (isArmor(e.getCursor())){
                    //TODO: Add the stat bonus
                    setItem(e.getSlot(), e.getCursor());

                    player.setItemOnCursor(null);
                    player.sendMessage(ChatColor.GREEN + "Added!");
                    player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_LEATHER, 10, 1);
                } else if (e.getCursor().getType().equals(Material.AIR)) {
                    //TODO: Remove the stat bonus
                    player.setItemOnCursor(item);

                    setItem(e.getSlot(), ItemUtils.createUIItem(Material.WHITE_STAINED_GLASS_PANE, 1,
                            "&aActive Accessory Slot " + (e.getSlot() + 1)));

                    player.sendMessage(ChatColor.GREEN + "Removed!");
                    player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_LEATHER, 10, 1);
                } else if (!isArmor(e.getCursor())) {
                    player.sendMessage(ChatColor.RED + "Item is not an armor piece!");
                } else {
                    player.sendMessage(ChatColor.RED + "Please remove the item on your cursor!");
                }
            } else if (storage.contains(e.getSlot())){
                if (placeable(e.getCursor())){
                    setItem(e.getSlot(), e.getCursor());
                    player.setItemOnCursor(item);
                    player.sendMessage(ChatColor.GREEN + "Updated Storage!");
                    player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_LEATHER, 10, 1);
                } else if (e.getCursor().getType().equals(Material.AIR)) {
                    setItem(e.getSlot(), e.getCursor());
                    player.setItemOnCursor(item);
                    player.sendMessage(ChatColor.GREEN + "Updated Storage!");
                    player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_LEATHER, 10, 1);
                } else if (!placeable(e.getCursor()) && !e.getCursor().getType().equals(Material.AIR)){
                    player.sendMessage(ChatColor.RED + "Item is not an armor piece or a talisman!");
                }
            }
            e.setCancelled(true);
            save();
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e){
        if (Talisman.isTalisman(e.getItemInHand())){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e){
        if (e.getInventory().equals(this)){
            save();
        }
    }

    private void save() {
        AnimusPlayer player = PlayerRegistry.get(this.player);
        ArrayList<ItemStack> storedAccessories = new ArrayList<>();
        ArrayList<ItemStack> accessories = new ArrayList<>();
        ArrayList<ItemStack> talismans = new ArrayList<>();

        for (int i = 0; i < 9; i++){
            accessories.add(getItem(i));
        }

        for (int i = 18; i < 36; i++){
            storedAccessories.add(getItem(i));
        }

        for (int i = 45; i < 49; i++){
            talismans.add(getItem(i));
        }

        player.getConfig().setItem("stored-active", accessories);
        player.getConfig().setItem("stored-talismans", talismans);
        player.getConfig().setItem("stored-accessories", storedAccessories);
    }

    private boolean isArmor(ItemStack item){
        if (CraftItemStack.asNMSCopy(item).c() instanceof ItemArmor){
            return true;
        }
        return false;
    }

    private boolean placeable(ItemStack item){
        return isArmor(item) || Talisman.isTalisman(item);
    }
}
