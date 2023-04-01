package com.optimus.animusmagic.commands;

import com.optimus.animusmagic.inventory.ItemBrowser;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class ItemBrowserCommand extends CustomCommand {
    public ItemBrowserCommand() {
        super("itembrowser");
    }

    @Override
    public void execute(Player sender, String[] args) {
        if (sender.isOp()) {
            ItemBrowser browser = new ItemBrowser();
            sender.openInventory(browser);
        }
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return Collections.emptyList();
    }
}
