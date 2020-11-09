package de.aroniktv.presents.main;

import java.io.IOException;

import at.ibizagamer.api.coins.getcoins;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import net.md_5.bungee.api.ChatColor;

public class EVT_BlockInteractEvent implements Listener {
	private main plugin;
	public EVT_BlockInteractEvent(main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		
		if(p.getWorld().getName().equals(main.sk.getString("World"))) {
			if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				if(e.getClickedBlock().getType() == Material.SKULL) {
					
					Location loc = e.getClickedBlock().getLocation();
					int maxPresents = main.sk.getInt("Presents");
					
					for(int i = 1; i < maxPresents + 1; i++) {
						if(main.sk.getDouble(i + ".X") == loc.getX() && main.sk.getDouble(i + ".Y") == loc.getY() && main.sk.getDouble(i + ".Z") == loc.getZ()) {
							if(main.f.contains(p.getUniqueId().toString() + "." + String.valueOf(i))) {
								int found = 0;
								if(main.f.contains(p.getUniqueId().toString() + ".Found")) {
									found = main.f.getInt(p.getUniqueId().toString() + ".Found") + 1;
								}
								
								p.playSound(p.getLocation(), Sound.ENDERDRAGON_WINGS, 1, 1);
								
								String title = plugin.getConfig().getString("Title.AlreadyFound.Title");
								title = title.replaceAll("%found%", String.valueOf(found));
								title = title.replaceAll("%tofind%", String.valueOf(main.sk.getInt("Presents")));
								
								String subtitle = plugin.getConfig().getString("Title.AlreadyFound.Subtitle");
								subtitle = subtitle.replaceAll("%found%", String.valueOf(found));
								subtitle = subtitle.replaceAll("%tofind%", String.valueOf(main.sk.getInt("Presents")));
								p.sendTitle(ChatColor.translateAlternateColorCodes('&', title), ChatColor.translateAlternateColorCodes('&', subtitle));
							} else if(main.f.getBoolean(p.getUniqueId().toString() + "." + String.valueOf(i)) == true) {
								int found;
								if(main.f.contains(p.getUniqueId().toString() + ".Found")) {
									found = main.f.getInt(p.getUniqueId().toString() + ".Found") + 1;
								} else {
									found = 1;
								}
								
								p.playSound(p.getLocation(), Sound.ENDERDRAGON_WINGS, 1, 1);
								
								String title = plugin.getConfig().getString("Title.AlreadyFound.Title");
								title = title.replaceAll("%found%", String.valueOf(found));
								title = title.replaceAll("%tofind%", String.valueOf(main.sk.getInt("Presents")));
								
								String subtitle = plugin.getConfig().getString("Title.AlreadyFound.Subtitle");
								subtitle = subtitle.replaceAll("%found%", String.valueOf(found));
								subtitle = subtitle.replaceAll("%tofind%", String.valueOf(main.sk.getInt("Presents")));
								p.sendTitle(ChatColor.translateAlternateColorCodes('&', title), ChatColor.translateAlternateColorCodes('&', subtitle));
							} else {					
								main.f.set(p.getUniqueId().toString() + "." + String.valueOf(i), true);
								
								int found;
								if(main.f.contains(p.getUniqueId().toString() + ".Found")) {
									found = main.f.getInt(p.getUniqueId().toString() + ".Found") + 1;
								} else {
									found = 1;
								}
								
								main.f.set(p.getUniqueId().toString() + ".Found", found);
								
								try {
									main.f.save(main.found);									
									p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 1);
									
									String title = plugin.getConfig().getString("Title.FoundNew.Title");
									getcoins.addCoins(p.getUniqueId().toString().replace("-", ""), 50);
									title = title.replaceAll("%found%", String.valueOf(found));
									title = title.replaceAll("%tofind%", String.valueOf(main.sk.getInt("Presents")));
									
									String subtitle = plugin.getConfig().getString("Title.FoundNew.Subtitle");
									subtitle = subtitle.replaceAll("%found%", String.valueOf(found));
									subtitle = subtitle.replaceAll("%tofind%", String.valueOf(main.sk.getInt("Presents")));
									p.sendTitle(ChatColor.translateAlternateColorCodes('&', title), ChatColor.translateAlternateColorCodes('&', subtitle));
								} catch (IOException e1) {
								}
							}
						}
					}
					
				}
			}
		}
	}
}
