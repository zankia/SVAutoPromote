package fr.zankia.svautopromote;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class SVAP extends JavaPlugin {
	public static final String NO_PERMISSION = "Erreur : Vous n'avez pas la permission pour cette commande.";
	private SVAPListener listener;
	
	public void onEnable() {
		saveDefaultConfig();
		this.listener = new SVAPListener(this);
		getServer().getPluginManager().registerEvents(this.listener, this);
		VaultLink.setupPerm(getServer().getServicesManager().getRegistration(Permission.class));
		getLogger().info("Enabled");
	}
	
	public void onDisable() {
		getLogger().info("Disabled");
	}
	
	public boolean onCommand(CommandSender sender, Command command, String name, String[] args) {
		if ((name.equalsIgnoreCase("svap")) || (name.equalsIgnoreCase("svautopromote"))) {
			if (sender.hasPermission("svautopromote.admin")) {
				if ((args.length == 1) && (args[0].equalsIgnoreCase("reload"))) {
					reloadConfig();
					this.listener.updateConf();
					sender.sendMessage(ChatColor.RED + "SVAutoPromote : " + ChatColor.GREEN + "Reload done.");
				} else
					return false;
			} else
				sender.sendMessage(ChatColor.RED + NO_PERMISSION);
			return true;
		}
		return false;
	}
	
	public void logtoFile(String message) {
		if (!getDataFolder().exists())
			getDataFolder().mkdir();
		
		File dest = new File(getDataFolder(), "log.txt");
		
		try {
			if (!dest.exists())
				dest.createNewFile();
			
			PrintWriter log = new PrintWriter(new FileWriter(dest, true));
			log.println(message);
			log.flush();
			log.close();
		} catch (IOException e) {
			getLogger().severe("Couldn't write to log");
		}
	}
}
