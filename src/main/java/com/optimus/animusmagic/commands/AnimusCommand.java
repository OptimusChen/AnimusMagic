package com.optimus.animusmagic.commands;

import com.optimus.animusmagic.EnchantError;
import com.optimus.animusmagic.player.AnimusPlayer;
import com.optimus.animusmagic.player.PlayerRegistry;
import com.optimus.animusmagic.talisman.Talisman;
import com.optimus.animusmagic.talisman.TalismanType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class AnimusCommand extends CustomCommand {

    public AnimusCommand() {
        super("animus");
    }

    @Override
    public void execute(Player player, String[] args) {
        AnimusPlayer animusPlayer = PlayerRegistry.get(player);
        if (args.length >= 1){
            if (args[0].equals("enchant")){
                if (args.length == 3){
                    if (animusPlayer.canEnchant(args[1].toLowerCase(), Integer.parseInt(args[2])).equals(EnchantError.NONE)){
                        int level = Integer.parseInt(args[2]);
                        int manaCost = 0;
                        switch (args[1].toLowerCase()){
                            case "running_talisman":
                                player.getEquipment().setItemInMainHand(new Talisman(TalismanType.RUNNING, level).getItem());
                                break;
                            case "shield_talisman":
                                player.getEquipment().setItemInMainHand(new Talisman(TalismanType.SHIELD, level).getItem());
                                break;
                            case "strength_talisman":
                                player.getEquipment().setItemInMainHand(new Talisman(TalismanType.STRENGTH, level).getItem());
                                break;
                            case "gliding_talisman":
                                player.getEquipment().setItemInMainHand(new Talisman(TalismanType.GLIDING, level).getItem());
                                break;
                            case "stealth_talisman":
                                player.getEquipment().setItemInMainHand(new Talisman(TalismanType.STEALTH, level).getItem());
                                break;
                            case "glowing_talisman":
                                player.getEquipment().setItemInMainHand(new Talisman(TalismanType.GLOWING, level).getItem());
                                break;
                            case "sharpness_6":
                                player.getEquipment().getItemInMainHand().addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 6);
                                manaCost = 15;
                                break;
                            case "protection_5":
                                player.getEquipment().getItemInMainHand().addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5);
                                manaCost = 15;
                                break;
                            case "axe_of_flames":
                                player.getEquipment().getItemInMainHand().addUnsafeEnchantment(Enchantment.FIRE_ASPECT, level);
                                manaCost = level * 10;
                                break;
                        }

                        if (args[1].toLowerCase().contains("_talisman")){
                            TalismanType type = TalismanType.valueOf(args[1].toLowerCase().replace("_talisman", "").toUpperCase());
                            manaCost = type.getMana() * level;
                        }

                        animusPlayer.getConfig().setItem("mana", animusPlayer.getMana() - manaCost);
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 1);
                        player.sendMessage(ChatColor.GREEN + "Success!");
                    } else {
                        player.sendMessage(ChatColor.RED + animusPlayer.canEnchant(args[1].toLowerCase(), Integer.parseInt(args[2])).getMessage());
                        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                    }
                } else if (args.length == 4){
                    if (animusPlayer.canEnchant(args[1].toLowerCase(), Integer.parseInt(args[3])).equals(EnchantError.NONE)) {
                        Player otherPlayer = Bukkit.getPlayer(args[2]);

                        if (otherPlayer == null) return;

                        AnimusPlayer otherAnimusPlayer = PlayerRegistry.get(otherPlayer);
                        int level = Integer.parseInt(args[3]);

                        switch (args[1].toLowerCase()) {
                            case "heart_scales":
                                otherPlayer.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, Integer.MAX_VALUE, level - 1, true));
                                break;
                            case "swift_talons":
                                if (level >= 4) {
                                    otherPlayer.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, level - 4));
                                }

                                otherPlayer.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, level - 1));
                                break;
                            case "stone_scales":
                                if (level == 3) {
                                    otherPlayer.removePotionEffect(PotionEffectType.SLOW);
                                    otherPlayer.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, Integer.MAX_VALUE, 0));
                                    otherPlayer.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, level - 1));
                                } else if (level >= 4) {
                                    otherPlayer.removePotionEffect(PotionEffectType.SLOW);
                                    otherPlayer.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, 0));
                                    otherPlayer.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, Integer.MAX_VALUE, 1));
                                    otherPlayer.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, level - 1));
                                } else {
                                    otherPlayer.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 3));
                                    otherPlayer.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, level - 1));
                                }
                                break;
                            case "leaping_talons":
                                otherPlayer.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 320, level - 1));
                                break;
                            case "magma_scales":
                                otherPlayer.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 3));
                                otherPlayer.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 0));
                                break;
                            case "rejuvenation":
                                otherPlayer.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 1, level));
                                break;
                            case "cure_disease":
                                otherPlayer.removePotionEffect(PotionEffectType.WITHER);
                                otherPlayer.removePotionEffect(PotionEffectType.HUNGER);
                                otherPlayer.removePotionEffect(PotionEffectType.POISON);
                                break;
                            case "gentle_talons":
                                otherAnimusPlayer.getConfig().setItem("gentle-talons", true);
                                break;
                            case "species_shift":
                                break;
                        }
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 1);
                        player.sendMessage(ChatColor.GREEN + "Success!");
                    } else {
                        player.sendMessage(ChatColor.RED + animusPlayer.canEnchant(args[1].toLowerCase(), Integer.parseInt(args[3])).getMessage());
                        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                    }
                }
            }
        }
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return Collections.emptyList();
    }
}
