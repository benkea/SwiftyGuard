package me.wingedmlgpro.swiftyguard.listener;

import me.wingedmlgpro.swiftyguard.Regions;
import me.wingedmlgpro.swiftyguard.util.WandPoint;
import org.bukkit.event.*;
import org.bukkit.event.block.*;
import org.bukkit.event.player.*;

/**
 * © This Document and Code is STRICTLY copyrighted(©) to Ben.
 * © If anyone takes any part of this code and uses it for
 * © Something public, Share with someone, uses it for API's,
 * © implementing it to their code and taking the rights for
 * © it is NOT allowed unless you get permission from me!
 * © This project SwiftyGuard was created by 35047
 * © at 6/06/15 at 11:47 PM
 */
public class WandListener implements Listener {

	@EventHandler
	public void onWandInteract(PlayerInteractEvent e) {
		if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
			if (e.getItem() != null && e.getItem().getItemMeta() != null && e.getItem().getItemMeta().getDisplayName() != null && e.getItem().getItemMeta().getDisplayName().contains("§e§lSwi§6§lfty §e§lWa§6§lnd")) {
				if (!Regions.getWandStorage().containsKey(e.getPlayer().getName()))
					Regions.getWandStorage().put(e.getPlayer().getName(), new WandPoint(0, 0, 0, 0, 0, 0));
				Regions.getWandStorage().get(e.getPlayer().getName()).setP1X(e.getClickedBlock().getX());
				Regions.getWandStorage().get(e.getPlayer().getName()).setP1Y(e.getClickedBlock().getY());
				Regions.getWandStorage().get(e.getPlayer().getName()).setP1Z(e.getClickedBlock().getZ());
				e.getPlayer().sendMessage(Regions.TAG2 + "Position 1 set for Region!");

				e.setCancelled(true);
			}
		} else if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (e.getItem() != null && e.getItem().getItemMeta() != null && e.getItem().getItemMeta().getDisplayName() != null && e.getItem().getItemMeta().getDisplayName().contains("§e§lSwi§6§lfty §e§lWa§6§lnd")) {
				if (!Regions.getWandStorage().containsKey(e.getPlayer().getName()))
					Regions.getWandStorage().put(e.getPlayer().getName(), new WandPoint(0, 0, 0, 0, 0, 0));
				Regions.getWandStorage().get(e.getPlayer().getName()).setP2X(e.getClickedBlock().getX());
				Regions.getWandStorage().get(e.getPlayer().getName()).setP2Y(e.getClickedBlock().getY());
				Regions.getWandStorage().get(e.getPlayer().getName()).setP2Z(e.getClickedBlock().getZ());
				e.getPlayer().sendMessage(Regions.TAG2 + "Position 2 set for Region!");

				e.setCancelled(true);
			}
		}
	}
}
