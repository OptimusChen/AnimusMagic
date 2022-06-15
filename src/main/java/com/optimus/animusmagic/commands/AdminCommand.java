package com.optimus.animusmagic.commands;

import com.optimus.animusmagic.player.AnimusPlayer;
import com.optimus.animusmagic.player.PlayerRegistry;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AdminCommand extends CustomCommand {

    public AdminCommand() {
        super("animusadmin");
    }

    @Override
    public void execute(Player player, String[] args) {
        if (player.isOp()){
            if (args.length == 3){
                Player target = Bukkit.getPlayerExact(args[1]);
                if (target == null){
                    player.sendMessage(ChatColor.RED + "An error occurred! This player isn't online!");
                    return;
                }

                AnimusPlayer animus = PlayerRegistry.get(target);

                int i;

                switch (args[0].toLowerCase()){
                    case "animus":
                        if (args[2].equalsIgnoreCase("true") || args[2].equalsIgnoreCase("false")) {
                            animus.getConfig().setItem("animus", Boolean.parseBoolean(args[2].toLowerCase()));
                        }else{
                            player.sendMessage(ChatColor.RED + "Value must be true/false!");
                        }
                        player.sendMessage(ChatColor.GREEN + "Successfully set " + target.getName() + "'s Animus boolean to: " + Boolean.valueOf(args[2]));
                        break;
                    case "lvl":
                        i = 0;
                        try {
                            i = Integer.parseInt(args[2]);
                        }catch (NumberFormatException e){
                            player.sendMessage(ChatColor.RED + "This isn't a valid number!");
                            return;
                        }
                        animus.getConfig().setItem("animus-study-level", i-1);
                        player.sendMessage(ChatColor.GREEN + "Successfully set " + target.getName() + "'s Animus Level to: " + i);
                        break;
                    case "mana":
                        i = 0;
                        try {
                            i = Integer.parseInt(args[2]);
                        }catch (NumberFormatException e){
                            player.sendMessage(ChatColor.RED + "This isn't a valid number!");
                            return;
                        }
                        animus.getConfig().setItem("mana", i);
                        player.sendMessage(ChatColor.GREEN + "Successfully set " + target.getName() + "'s mana to: " + i);
                        break;
                }
            }else{
                player.sendMessage(ChatColor.RED + "Invalid Arguments!");
            }
        }
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1){
            return Arrays.asList("animus", "lvl", "mana");
        }else if (args.length == 2){
            List<String> list;
            list = new ArrayList<>();
            for (Player player : Bukkit.getOnlinePlayers()){
                list.add(player.getName());
            }
            return list;
        }else if (args.length == 3){
            switch (args[0].toLowerCase()){
                case "animus":
                    return Arrays.asList("true", "false");
            }
            return Collections.emptyList();
        }

        return Collections.emptyList();
    }
}
