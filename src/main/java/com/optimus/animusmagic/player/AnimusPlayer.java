package com.optimus.animusmagic.player;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import com.optimus.animusmagic.AnimusMagic;
import com.optimus.animusmagic.EnchantError;
import com.optimus.animusmagic.config.PlayerConfig;
import com.optimus.animusmagic.inventory.AccessoryBag;
import com.optimus.animusmagic.talisman.Talisman;
import com.optimus.animusmagic.talisman.TalismanType;
import lombok.Data;
import net.minecraft.network.protocol.game.PacketPlayOutPlayerInfo;
import net.minecraft.server.dedicated.DedicatedPlayerList;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.network.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

@Data
public class AnimusPlayer {

    private Player bukkitPlayer;
    private PlayerConfig config;
    private AccessoryBag accessoryBag;
    private double damageMult;
    private int extraArmor;
    private BossBar animusManaBar;

    public AnimusPlayer(Player player){
        this.bukkitPlayer = player;
        this.damageMult = 1.0;
        this.extraArmor = 0;

        config = PlayerConfig.create(player);

        createAccessoryBag();
        loadConfig();

        if (((boolean) getConfig().getItem("animus"))){
            animusManaBar = Bukkit.createBossBar(ChatColor.BLUE + "Animus Magic: " + getMana(), BarColor.BLUE, BarStyle.SOLID);
            animusManaBar.addPlayer(player);
        }else{
            animusManaBar = null;
        }

        showMana();
    }

    public double getAttributeAmount(Attribute attribute) {
        return getBukkitPlayer().getAttribute(attribute).getBaseValue();
    }

    private void createAccessoryBag() {
        accessoryBag = new AccessoryBag(this);
    }

    private void loadConfig(){  }

    public ItemStack getItemInHand() { return bukkitPlayer.getEquipment().getItemInMainHand(); }

    public int getLevel() { return (int) getConfig().getItem("animus-study-level"); }

    public int getMana() { return (int) getConfig().getItem("mana"); }

    public void disconnect() {
        HandlerList.unregisterAll(accessoryBag);
    }

    private void showMana(){
        Random rand = new Random();
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!getBukkitPlayer().isOnline()) {
                    cancel();
                }else if (getBukkitPlayer().isOnline()){
                    if ((boolean) getConfig().getItem("animus")){
                        int i = rand.nextInt(15);
                        getAnimusManaBar().setTitle(ChatColor.BLUE + "Animus Mana: " + getMana());
                        if (i == 1 && getMana() <= 95){
                            getConfig().setItem("mana", getMana() + 5);
                        }

                    }
                }
            }
        }.runTaskTimer(AnimusMagic.getInstance(), 5L, 5L);
    }

    public EnchantError canEnchant(String enchant, int level) {
        if (enchant.toLowerCase().contains("_talisman")) {
            if (Talisman.isTalisman(getItemInHand())){
                if (Talisman.parse(getItemInHand()).getType().equals(TalismanType.GENERIC)) {
                    TalismanType type = TalismanType.valueOf(enchant.replace("_talisman", "").toUpperCase());
                    if (level <= type.getMaxLevel()){
                        if (type.getMana() * level <= getMana()){
                            switch (enchant.toLowerCase()) {
                                case "running_talisman":
                                case "shield_talisman":
                                    if (level > getLevel()) {
                                        return EnchantError.LEVEL;
                                    } else {
                                        return EnchantError.NONE;
                                    }
                                case "strength_talisman":
                                    if (level + 1 > getLevel()) {
                                        return EnchantError.LEVEL;
                                    } else {
                                        return EnchantError.NONE;
                                    }
                                case "gliding_talisman":
                                    if (getLevel() > 1) {
                                        return EnchantError.NONE;
                                    } else {
                                        return EnchantError.LEVEL;
                                    }
                                case "glowing_talisman":
                                case "stealth_talisman":
                                    if (getLevel() > 2) {
                                        return EnchantError.NONE;
                                    } else {
                                        return EnchantError.LEVEL;
                                    }
                            }
                        } else {
                            return EnchantError.MANA;
                        }
                    } else {
                        return EnchantError.LEVEL_TOO_HIGH;
                    }
                } else {
                    return EnchantError.WRONG_ITEM;
                }
            }
        }
        return EnchantError.NONE;
    }
}
