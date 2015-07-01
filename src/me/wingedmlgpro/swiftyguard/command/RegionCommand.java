package me.wingedmlgpro.swiftyguard.command;

import me.dylzqn.PlayerAPI.SwiftyPlayer;
import me.dylzqn.Utils.RankManager;
import me.wingedmlgpro.swiftyguard.Regions;
import me.wingedmlgpro.swiftyguard.util.Cuboid;
import me.wingedmlgpro.swiftyguard.util.Region;
import me.wingedmlgpro.swiftyguard.util.SettingsManager;
import me.wingedmlgpro.swiftyguard.util.WandPoint;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;

/**
 * © This Document and Code is STRICTLY copyrighted(©) to Ben.
 * © If anyone takes any part of this code and uses it for
 * © Something public, Share with someone, uses it for API's,
 * © implementing it to their code and taking the rights for
 * © it is NOT allowed unless you get permission from me!
 * © This project SwiftyGuard was created by 35047
 * © at 6/06/15 at 1:02 PM
 */
public class RegionCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("region")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(Regions.NoCONSOLE);
				return true;
			}

			Player p = (Player) sender;
			SwiftyPlayer user = new SwiftyPlayer(p);
			if (!(user.getRank().equals(RankManager.HEADADMIN) || user.getRank().equals(RankManager.OWNER))) {
				user.sendMessage(Regions.PermHEADADMIN);
				return true;
			}
			if (args.length == 0) {
				p.sendMessage(Regions.Arrows + "§bRegion Command§7:");
				p.sendMessage(Regions.Arrows + "§b/region create §a<RegionName>");
				p.sendMessage(Regions.Arrows + "§b/region delete §a<RegionName>");
				p.sendMessage(Regions.Arrows + "§b/region info §a<RegionName>");
				p.sendMessage(Regions.Arrows + "§b/region list");
				p.sendMessage(Regions.Arrows + "§b/region tp §a<RegionName>");
				p.sendMessage(Regions.Arrows + "§b/region wand");
				p.sendMessage(Regions.Arrows + "§b/region setPvP §a<RegionName> <True|False>");
				p.sendMessage(Regions.Arrows + "§b/region setPlaceBlock §a<RegionName> <True|False>");
			} else {
				String subCmd = args[0];
				if (subCmd.equalsIgnoreCase("create")) {
					if (args.length > 1) {
						WandPoint wand = Regions.getWandStorage().get(p.getName());
						if (wand == null || !wand.isInitialised()) {
							p.sendMessage(Regions.Error + "You haven't selected an area of where the Region will go!\n" + Regions.Arrows + "§bDo /region wand§7: §ato be able to select the area");
							return true;
						} else {
							String rgName = args[1];
							if (SettingsManager.getInstance().getConfig().get("SwiftyGuard." + rgName) != null) {
								p.sendMessage(Regions.Error + "The Region with the name " + rgName + " already exists!");
								return true;
							} else if (SettingsManager.getInstance().getConfig().get("SwiftyGuard." + rgName) == null) {
								World world = Bukkit.getWorld(p.getWorld().getName());
								Region rg = new Region(rgName, new Cuboid(new Location(world, wand.getP1X(), wand.getP1Y(), wand.getP1Z()), new Location(world, wand.getP2X(), wand.getP2Y(), wand.getP2Z())));
								rg.setCreator(p.getUniqueId());
								rg.setPvP(false);
								rg.setPlaceBlock(false);
								SettingsManager.getInstance().getConfig().set("SwiftyGuard." + rg.getName(), rg.serialize());
								SettingsManager.getInstance().saveConfig();
								Region.registerRegion(rg);
								p.sendMessage(Regions.TAG2 + "You created a new region with the name " + rgName);
							}
							return true;
						}
					} else {
						p.sendMessage(Regions.Error + "Usage: §b/region create §a<RegionName>");
					}
				} else if (subCmd.equalsIgnoreCase("delete")) {
					if (args.length <= 1) {
						p.sendMessage(Regions.Error + "Usage: §b/region delete §a<RegionName>");
						return true;
					}
					String rgName = args[1];
					Region region = Region.getRegion(rgName);
					if (region == null) {
						p.sendMessage(Regions.Error + "There is no Region with the name " + rgName);
						return true;
					} else {
						SettingsManager.getInstance().getConfig().set("SwiftyGuard." + region.getName(), null);
						SettingsManager.getInstance().saveConfig();
						Region.unregisterRegion(region);
						p.sendMessage(Regions.TAG2 + "You deleted the Region " + rgName);
					}
				} else if (subCmd.equalsIgnoreCase("list")) {
					if (SettingsManager.getInstance().getConfig().getConfigurationSection("SwiftyGuard").getKeys(false) == null) {
						p.sendMessage(Regions.TAG + "§bRegions§7: §anull");
						return true;
					} else {
						String msg = Regions.TAG + "§bRegions§7: §a" + SettingsManager.getInstance().getConfig().getConfigurationSection("SwiftyGuard").getKeys(false);
						msg = msg.replace("[", "").replace("]", "");
						p.sendMessage(msg);
					}
				} else if (subCmd.equalsIgnoreCase("info")){
					if (args.length <= 1) {
						p.sendMessage(Regions.Error + "Usage: §b/region info §a<RegionName>");
						return true;
					}
					String rgName = args[1];
					Region rg = Region.getRegion(rgName);
					if (rg==null){
						p.sendMessage(Regions.Error + "There is no Region with the name " + rgName);
						return true;
					} else {
						p.sendMessage(Regions.Arrows+"§b"+rg.getName()+"§7:");
						p.sendMessage(Regions.Arrows+"§bCreators UUID§7: §a"+rg.getCreator());
						p.sendMessage(Regions.Arrows+"§bPvP §7: §a"+rg.isPvPEnabled());
						p.sendMessage(Regions.Arrows+"§bPlaceBlock§7: §a"+rg.canPlaceBlock());
					}
				} else if (subCmd.equalsIgnoreCase("tp")) {
					if (args.length <= 1) {
						p.sendMessage(Regions.Error + "Usage: §b/region tp §a<RegionName>");
						return true;
					}
					String rgName = args[1];
					if (SettingsManager.getInstance().getConfig().get("SwiftyGuard." + rgName) == null) {
						p.sendMessage(Regions.Error + "There is no Region with the name " + rgName);
						return true;
					}
					String w = SettingsManager.getInstance().getConfig().getString("SwiftyGuard." + rgName + ".Locations.worldName");
					double x = SettingsManager.getInstance().getConfig().getDouble("SwiftyGuard." + rgName + ".Locations.x1");
					double y = SettingsManager.getInstance().getConfig().getDouble("SwiftyGuard." + rgName + ".Locations.y1");
					double z = SettingsManager.getInstance().getConfig().getDouble("SwiftyGuard." + rgName + ".Locations.z1");
					p.teleport(new Location(Bukkit.getWorld(w), x, y, z));
					p.sendMessage(Regions.TAG2 + "You teleported to the region " + rgName);
				} else if (subCmd.equalsIgnoreCase("wand")) {
					PlayerInventory pi = p.getInventory();
					ItemStack wand = new ItemStack(Material.STONE_AXE);
					ItemMeta meta = wand.getItemMeta();
					meta.setDisplayName("§e§lSwi§6§lfty §e§lWa§6§lnd");
					wand.setItemMeta(meta);
					pi.addItem(wand);
					p.sendMessage(Regions.TAG2 + "You got the want item!");
					p.sendMessage(Regions.Arrows + "§bLeft Click Block§7: §aTo Select Location 1 of your Region!");
					p.sendMessage(Regions.Arrows + "§bRight Click Block§7: §aTo Select Location 2 of your Region!");
				} else if (subCmd.equalsIgnoreCase("setPvP")) {
					if (args.length <= 2) {
						p.sendMessage(Regions.Error + "Usage: §b/region setPvp §a<RegionName> <True|False>");
						return true;
					}
					String rgName = args[1];
					String PvP = args[2];
					Region rg = Region.getRegion(rgName);
					if (rg == null) {
						p.sendMessage(Regions.Error + "There is no Region with the name " + rgName);
						return true;
					}
					if (PvP.equalsIgnoreCase("true")) {
						rg.setPvP(true);
						SettingsManager.getInstance().getConfig().set("SwiftyGuard." + rgName, rg.serialize());
						SettingsManager.getInstance().saveConfig();
						p.sendMessage(Regions.TAG2 + "You set PvP in the region " + rgName + " to " + PvP);
						return true;
					}
					if (PvP.equalsIgnoreCase("false")) {
						rg.setPvP(false);
						SettingsManager.getInstance().getConfig().set("SwiftyGuard." + rgName, rg.serialize());
						SettingsManager.getInstance().saveConfig();
						p.sendMessage(Regions.TAG2 + "You set PvP in the region " + rgName + " to " + PvP);
						return true;
					} else {
						p.sendMessage(Regions.Error + "You can only set PvP to be False or True!");
						return true;
					}
				} else if (subCmd.equalsIgnoreCase("setPlaceBlock")) {
					if (args.length <= 2) {
						p.sendMessage(Regions.Error + "Usage: §b/region setPlaceBlock §a<RegionName> <True|False");
						return true;
					}
					String rgName = args[1];
					String PlaceBlock = args[2];
					Region rg = Region.getRegion(rgName);
					if (rg == null) {
						p.sendMessage(Regions.Error + "There is no Region with the name " + rgName);
						return true;
					}
					if (PlaceBlock.equalsIgnoreCase("true")) {
						rg.setPlaceBlock(true);
						SettingsManager.getInstance().getConfig().set("SwiftyGuard." + rgName, rg.serialize());
						SettingsManager.getInstance().saveConfig();
						p.sendMessage(Regions.TAG2 + "You set PlaceBlock in the region " + rgName + " to " + PlaceBlock);
						return true;
					}
					if (PlaceBlock.equalsIgnoreCase("false")) {
						rg.setPlaceBlock(false);
						SettingsManager.getInstance().getConfig().set("SwiftyGuard." + rgName, rg.serialize());
						SettingsManager.getInstance().saveConfig();
						p.sendMessage(Regions.TAG2 + "You set PlaceBlock in the region " + rgName + " to " + PlaceBlock);
						return true;
					} else {
						p.sendMessage(Regions.Error + "You can only set PlaceBlock to be False or True!");
						return true;
					}
				}
			}
			return true;
		}
		return false;
	}

}
