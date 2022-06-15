package com.optimus.animusmagic.commands;

import com.optimus.animusmagic.EnchantError;
import com.optimus.animusmagic.player.AnimusPlayer;
import com.optimus.animusmagic.player.PlayerRegistry;
import com.optimus.animusmagic.talisman.Talisman;
import com.optimus.animusmagic.talisman.TalismanType;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
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
                        switch (args[1].toLowerCase()){
                            case "running_talisman":
                                player.getEquipment().setItemInMainHand(new Talisman(TalismanType.RUNNING, Integer.parseInt(args[2])).getItem());
                                break;
                            case "shield_talisman":
                                player.getEquipment().setItemInMainHand(new Talisman(TalismanType.SHIELD, Integer.parseInt(args[2])).getItem());
                                break;
                            case "strength_talisman":
                                player.getEquipment().setItemInMainHand(new Talisman(TalismanType.STRENGTH, Integer.parseInt(args[2])).getItem());
                                break;
                            case "gliding_talisman":
                                player.getEquipment().setItemInMainHand(new Talisman(TalismanType.GLIDING, Integer.parseInt(args[2])).getItem());
                                break;
                            case "stealth_talisman":
                                player.getEquipment().setItemInMainHand(new Talisman(TalismanType.STEALTH, Integer.parseInt(args[2])).getItem());
                                break;
                            case "glowing_talisman":
                                player.getEquipment().setItemInMainHand(new Talisman(TalismanType.GLOWING, Integer.parseInt(args[2])).getItem());
                                break;
                            case "sharpness_6":
                                player.getEquipment().getItemInMainHand().addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 6);
                                break;
                            case "protection_5":
                                player.getEquipment().getItemInMainHand().addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5);
                                break;
                            case "axe_of_flames":
                                player.getEquipment().getItemInMainHand().addUnsafeEnchantment(Enchantment.FIRE_ASPECT, Integer.parseInt(args[2]));
                                break;
                            case "heart_scales":
                                break;
                        }
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 1);
                        player.sendMessage(ChatColor.GREEN + "Success!");
                    } else {
                        player.sendMessage(ChatColor.RED + animusPlayer.canEnchant(args[1].toLowerCase(), Integer.parseInt(args[2])).getMessage());
                        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                    }
                } else if (args.length == 4){

                }
            }
        }
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return Collections.emptyList();
    }
}
