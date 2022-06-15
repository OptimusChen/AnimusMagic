package com.optimus.animusmagic.config;

import com.optimus.animusmagic.AnimusMagic;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

@Data
public class PlayerConfig {

    private FileConfiguration config;
    private File file;

    public PlayerConfig(File file){
        this.file = file;
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    public Object getItem(String path) {
        return config.get(path);
    }

    public void setItem(String path, Object item){
        try {
            config.set(path, item);
            config.save(file);
            update();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void update(){
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    public static PlayerConfig create(Player player) {
        if (exists(player)){
            File file = new File(AnimusMagic.getInstance().getDataFolder()+File.separator+"Players"+File.separator+player.getUniqueId()+".yml");
            FileConfiguration config = YamlConfiguration.loadConfiguration(file);

            return new PlayerConfig(file);
        } else {
            File folder = new File(AnimusMagic.getInstance().getDataFolder() + File.separator + "Players");
            if (!folder.exists()) {
                folder.mkdirs();
            }
            File file = new File(AnimusMagic.getInstance().getDataFolder()+File.separator+"Players"+File.separator+player.getUniqueId()+".yml");
            if (!file.exists()) {
                try {
                    file.createNewFile();
                    Random rand = new Random();
                    int animus = rand.nextInt(1);
                    FileConfiguration config = YamlConfiguration.loadConfiguration(file);

                    ArrayList<ItemStack> storedAccessories = new ArrayList<>();
                    ArrayList<ItemStack> accessories = new ArrayList<>();
                    ArrayList<ItemStack> activeTalismans = new ArrayList<>();

                    config.set("stored-accessories", storedAccessories);
                    config.set("stored-active", accessories);
                    config.set("stored-talismans", activeTalismans);
                    config.set("gentle-talons", false);
                    config.set("undead", false);
                    config.set("animus-study-level", 1);
                    config.set("mana", 100);
                    config.set("shapeshift", "Default");

                    if (animus == 0){
                        config.set("animus", true);
                        player.sendTitle(ChatColor.AQUA + "" + ChatColor.BOLD + "You're an Animus! ", ChatColor.YELLOW + "Use /animus to learn to use your powers!", 20, 60, 20);
                        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10, 10);
                    }else{
                        config.set("animus", false);
                    }
                    config.save(file);
                }catch (IOException e){
                    e.printStackTrace();
                }
                return new PlayerConfig(file);
            }
            return null;
        }
    }

    public static boolean exists(Player player){
        File file = new File(AnimusMagic.getInstance().getDataFolder()+File.separator+"Players"+File.separator+player.getUniqueId()+".yml");
        return file.exists();
    }
}
