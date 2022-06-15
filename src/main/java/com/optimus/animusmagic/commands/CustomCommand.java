package com.optimus.animusmagic.commands;

import com.optimus.animusmagic.AnimusMagic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class CustomCommand implements CommandExecutor, TabCompleter {


    public CustomCommand(String name){
        AnimusMagic.getInstance().getCommand(name).setTabCompleter(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args){
        if (sender instanceof Player){
            execute((Player) sender, args);
        }else{
            execute(sender, args);
        }

        return true;
    }

    public void execute(CommandSender sender, String[] args){}

    public void execute(Player sender, String[] args){}

}
