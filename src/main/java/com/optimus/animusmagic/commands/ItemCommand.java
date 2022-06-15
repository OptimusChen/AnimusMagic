package com.optimus.animusmagic.commands;

import com.optimus.animusmagic.AnimusMagic;
import com.optimus.animusmagic.talisman.Talisman;
import com.optimus.animusmagic.talisman.TalismanType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ItemCommand extends CustomCommand {

    public ItemCommand() {
        super("customitems");
    }

    @Override
    public void execute(Player sender, String[] args) {
        if (args.length == 3){
            String item = args[0];

            if (item.toLowerCase().equals("talisman")){
                sender.getInventory().addItem(new Talisman(TalismanType.valueOf(args[1].toUpperCase()), Integer.parseInt(args[2])).getItem());
            }
        }
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        switch (args.length){
            case 1:
                return Arrays.asList("talisman");
            case 2:
                return Arrays.asList("GENERIC", "RUNNING", "SHIELD", "STRENGTH", "GLIDING", "GLOWING", "STEALTH");
            case 3:
                return Arrays.asList("1", "2", "3", "4", "5");
            default:
                return Collections.emptyList();
        }
    }
}
