package fr.zankia.svautopromote;

import net.milkbowl.vault.permission.Permission;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultLink {
	public static Permission perm = null;

	protected static void setupPerm(RegisteredServiceProvider<Permission> permProvider) {
		perm = (Permission)permProvider.getProvider();
	}
}
