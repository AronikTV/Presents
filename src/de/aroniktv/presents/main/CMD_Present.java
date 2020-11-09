package de.aroniktv.presents.main;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class CMD_Present implements CommandExecutor {
	private main plugin;
	public CMD_Present(main plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			
			if(p.hasPermission("presents.setup")) {
				if(args.length == 1) {
					if(args[0].equalsIgnoreCase("give") || args[0].equalsIgnoreCase("giveskull") || args[0].equalsIgnoreCase("skull") || args[0].equalsIgnoreCase("g") || args[0].equalsIgnoreCase("gs") || args[0].equalsIgnoreCase("getskull") || args[0].equalsIgnoreCase("gets")) {
						String skullOwner = plugin.getConfig().getString("Skull.Owner");
						String skullDisplayName = plugin.getConfig().getString("Skull.DisplayName");
						
						ItemStack present = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
						SkullMeta presentMeta = (SkullMeta) present.getItemMeta();
						presentMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', skullDisplayName));
						presentMeta.setOwner(skullOwner);
						presentMeta.setLore(plugin.presentLore);
						present.setItemMeta(presentMeta);
						
						p.getInventory().addItem(present);
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.prefix + plugin.getConfig().getString("Messages.GetSkull")));
					} else {
						String didYouMean = plugin.getConfig().getString("Errors.Syntax.DidYouMean");
						didYouMean = didYouMean.replaceAll("%command%", "/present getskull");
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.prefix + didYouMean));
					}
				} else {
					String useCommand = plugin.getConfig().getString("Errors.Syntax.UseCommand");
					useCommand = useCommand.replaceAll("%command%", "/present getskull");
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.prefix + useCommand));
				}
			} else {
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.prefix + plugin.getConfig().getString("Errors.NoPermissions")));
			}
		} else {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.prefix + plugin.getConfig().getString("Errors.NoPlayer")));
		}
		return false;
	}
	
}
