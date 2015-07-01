package me.wingedmlgpro.swiftyguard.listener;

import me.wingedmlgpro.swiftyguard.Regions;
import me.wingedmlgpro.swiftyguard.util.Region;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.*;

import java.util.List;

/**
 * © This Document and Code is STRICTLY copyrighted(©) to Ben.
 * © If anyone takes any part of this code and uses it for
 * © Something public, Share with someone, uses it for API's,
 * © implementing it to their code and taking the rights for
 * © it is NOT allowed unless you get permission from me!
 * © This project SwiftyGuard was created by 35047
 * © at 7/06/15 at 9:40 AM
 */
public class UtilListener implements Listener {

	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		if (!Region.getRegions(e.getBlock().getLocation()).isEmpty()) {
			e.setCancelled(true);
			e.getPlayer().sendMessage(Regions.Error + "You cannot break blocks in this area!");
		}
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		List<Region> playerRegions = Region.getRegions(e.getBlock().getLocation());
		for (Region region : playerRegions) {
			if (!region.canPlaceBlock()) {
				e.setCancelled(true);
				e.getPlayer().sendMessage(Regions.Error + "You cannot place blocks in this area!");
				break;
			}
		}
	}

	@EventHandler
	public void onEntityDamage(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if (e.getCause() == EntityDamageEvent.DamageCause.FALL) {
				if (!Region.getRegions(p.getLocation()).isEmpty()) {
					e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player) {
			if (e.getDamager() instanceof Player) {
				Player p = (Player) e.getEntity();
				Player d = (Player) e.getDamager();
				List<Region> playerRegions = Region.getRegions(d.getLocation());
				for (Region region : playerRegions) {
					if (!region.isPvPEnabled()) {
						e.setCancelled(true);
						d.sendMessage(Regions.Error + "You cannot PvP in this area!");
						break;
					}
				}
				List<Region> playerRegions2 = Region.getRegions(p.getLocation());
				for (Region region : playerRegions2) {
					if (!region.isPvPEnabled()) {
						e.setCancelled(true);
						d.sendMessage(Regions.Error + "That Person is in a Safe Zone!");
						break;
					}
				}
			}
			if (e.getDamager() instanceof Projectile){
				Projectile proj = (Projectile) e.getDamager();
				if (proj instanceof Arrow || proj instanceof FishHook){
					List<Region> playerRegions = Region.getRegions(proj.getLocation());
					for (Region region : playerRegions) {
						if (!region.isPvPEnabled()) {
							e.setCancelled(true);
							if (proj.getShooter() instanceof Player){
								Player d = (Player)proj.getShooter();
								d.sendMessage(Regions.Error+"You cannot PvP in this area!");
							}
						}
					}
				}
			}
		}
	}

}
