package com.optimus.animusmagic.commands;

import com.optimus.animusmagic.AnimusMagic;
import com.optimus.animusmagic.player.PlayerRegistry;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class AccessoryBagCommand extends CustomCommand {

    public AccessoryBagCommand() {
        super("accessorybag");
    }

    @Override
    public void execute(Player sender, String[] args) {
        sender.openInventory(PlayerRegistry.get(sender).getAccessoryBag());
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return Collections.emptyList();
    }
}
