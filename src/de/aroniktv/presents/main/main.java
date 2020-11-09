package de.aroniktv.presents.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin {
	
	public static File skulls;
	public static FileConfiguration sk;
	
	public static File found;
	public static FileConfiguration f;
	
	public String prefix = getConfig().getString("Prefix") + "&r ";
	
	ArrayList<String> presentLore = new ArrayList<String>();
	
	public void onEnable() {
		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[&6Presents&7] &aLoading contents"));
		loadConfig();
		loadFiles();
		loadCommands();
		loadEvents();
		addLore();
		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[&6Presents&7] &aPlugin enabled - Version: &cv" + this.getDescription().getVersion()));
		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[&6Presents&7] &aDeveloped by &cAronikTV"));
	}
	
	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[&6Presents&7] &aPlugin disabled"));
		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[&6Presents&7] &aDeveloped by &cAronikTV"));
	}
	
	public void loadConfig() {
    	getConfig().options().copyDefaults(true);
    	saveDefaultConfig();
    }
	
	public void loadFiles() {
		main.skulls = new File("plugins/Presents", "presents.yml");
		main.sk = YamlConfiguration.loadConfiguration(main.skulls);
		
		main.found = new File("plugins/Presents", "found.yml");
		main.f = YamlConfiguration.loadConfiguration(main.found);
		
		try {
			main.sk.save(main.skulls);
			main.f.save(main.found);
		} catch(IOException e) {
			
		}
	}
	
	public void loadCommands() {
		this.getCommand("Present").setExecutor(new CMD_Present(this));
	}
	
	public void loadEvents() {
		Bukkit.getPluginManager().registerEvents(new EVT_BlockChangeEvent(this), this);
		Bukkit.getPluginManager().registerEvents(new EVT_BlockInteractEvent(this), this);
	}
	
	public void addLore() {
		for(String lore : getConfig().getStringList("Skull.Lore"))  {
			presentLore.add(ChatColor.translateAlternateColorCodes('&', lore));
		}
	}
	
	public int getFound(Player p) {
		if(f.contains(p.getUniqueId().toString())) {
			return f.getInt(p.getUniqueId().toString() + ".Found");
		} else {
			return 0;
		}
	}
}
