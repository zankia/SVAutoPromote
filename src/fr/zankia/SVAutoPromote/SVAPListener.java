package fr.zankia.SVAutoPromote;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class SVAPListener implements Listener {
	private List<Map<?, ?>> groupsToCheck;
	private SVAP plugin;

	public SVAPListener(SVAP plugin) {
		this.plugin = plugin;
		updateConf();
	}

	public void updateConf() {
		this.groupsToCheck = this.plugin.getConfig().getMapList("groups");
	}
	
	@EventHandler(priority=EventPriority.NORMAL)
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		String current = VaultLink.perm.getPrimaryGroup(p);
		for (Map<?, ?> group : this.groupsToCheck) {
			String from = (String)group.get("from");
			if (current.equalsIgnoreCase(from)) {
				if ((System.currentTimeMillis() - p.getFirstPlayed()) / 86400000L
						> ((Integer)group.get("time")).intValue()) {
					VaultLink.perm.playerAddGroup(null, p, (String)group.get("to"));
					VaultLink.perm.playerRemoveGroup(null, p, from);
					
					this.plugin.logtoFile("[" + new SimpleDateFormat("dd/MM/yy HH:mm:ss").format(
							new Date()) + "] " + e.getPlayer().getName() + ": " + from + " -> " + 
							group.get("to"));
				}
			}
		}
	}
}
