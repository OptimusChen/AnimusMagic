package com.optimus.animusmagic.item;

import com.optimus.animusmagic.AnimusMagic;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public abstract class ListeningItem extends AnimusItem implements Listener {

    public ListeningItem() {
        Bukkit.getPluginManager().registerEvents(this, AnimusMagic.getInstance());
    }

}
