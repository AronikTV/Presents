package de.aroniktv.presents.main;

import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class EVT_BlockChangeEvent implements Listener {
	private main plugin;
	public EVT_BlockChangeEvent(main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		
		String skullOwner = plugin.getConfig().getString("Skull.Owner");
		String skullDisplayName = plugin.getConfig().getString("Skull.DisplayName");
		
		ItemStack present = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		SkullMeta presentMeta = (SkullMeta) present.getItemMeta();
		presentMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', skullDisplayName));
		presentMeta.setOwner(skullOwner);
		presentMeta.setLore(plugin.presentLore);
		present.setItemMeta(presentMeta);
		
		if(e.getBlockPlaced().getType() == Material.SKULL) {
			if(p.hasPermission("presents.setup")) {
				int id;
				Location loc = e.getBlock().getLocation();
				
				if(main.sk.contains("Presents")) {
					id = main.sk.getInt("Presents") + 1;
					
					if(loc.getWorld().getName().equals(main.sk.getString("World"))) {
						main.sk.set("Presents", id);
						main.sk.set(id + ".X", loc.getX());
						main.sk.set(id + ".Y", loc.getY());
						main.sk.set(id + ".Z", loc.getZ());
						
						try {
							main.sk.save(main.skulls);
							
							String placed = plugin.getConfig().getString("Messages.PlaceSkull");
							placed = placed.replaceAll("%id%", String.valueOf(id));
							p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.prefix + placed));
						} catch (IOException e1) {
							p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.prefix + plugin.getConfig().getString("Errors.CantSaveFile")));
							e.setBuild(true);
						}
					} else {
						e.setCancelled(true);
					}
				} else {
					id = 1;
					
					main.sk.set("Presents", id);
					main.sk.set(id + ".X", loc.getX());
					main.sk.set(id + ".Y", loc.getY());
					main.sk.set(id + ".Z", loc.getZ());
					
					main.sk.set("World", loc.getWorld().getName());
					
					try {
						main.sk.save(main.skulls);
						
						String placed = plugin.getConfig().getString("Messages.PlaceSkull");
						placed = placed.replaceAll("%id%", String.valueOf(id));
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.prefix + placed));
					} catch (IOException e1) {
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.prefix + plugin.getConfig().getString("Errors.CantSaveFile")));
						e.setBuild(true);
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		Player p = e.getPlayer();
		
		if(e.getBlock().getType() == Material.SKULL) {
			if(e.getBlock().getLocation().getWorld().getName().equals(main.sk.getString("World"))) {
				e.setCancelled(true);
			}
		}
	}
}
