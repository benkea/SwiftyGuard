package me.wingedmlgpro.swiftyguard;

import me.wingedmlgpro.swiftyguard.command.RegionCommand;
import me.wingedmlgpro.swiftyguard.listener.UtilListener;
import me.wingedmlgpro.swiftyguard.listener.WandListener;
import me.wingedmlgpro.swiftyguard.util.Region;
import me.wingedmlgpro.swiftyguard.util.SettingsManager;
import me.wingedmlgpro.swiftyguard.util.WandPoint;
import org.bukkit.plugin.java.*;

import java.util.HashMap;
import java.util.Map;

/**
 * © This Document and Code is STRICTLY copyrighted(©) to Ben.
 * © If anyone takes any part of this code and uses it for
 * © Something public, Share with someone, uses it for API's,
 * © implementing it to their code and taking the rights for
 * © it is NOT allowed unless you get permission from me!
 * © This project SwiftyGuard was created by 35047
 * © at 6/06/15 at 12:53 PM
 */
public class Regions extends JavaPlugin {

	private static Regions instance = null;

	public static String Error = "§c§l/§4§l/ §6";
	public static String CantFindPlayer = Error + "Sorry, but we could not find the player ";
	public static String NoCONSOLE = Error + "Sorry, But the Console can't run this command!";
	public static String TAG = "§6§lSwi§e§lfty§6§l>§e§l>§r ";
	public static String TAG2 = "§6§l/§e§l/ §6";
	public static String Arrows = "§e§l>§6§l> ";
	public static String SwiftyAnnouncement = "§6§lSwi§e§lfty§6§l>§e§l>§r ";
	public static String PermCONSOLE = "§c§l/§4§l/ §cYou are not as cool as a Console, so stop trying to use this command!";
	public static String PermOWNER = "§c§l/§4§l/ §cYou are not as cool as a Owner, so stop trying to use this command!";
	public static String PermHEADADMIN = "§c§l/§4§l/ §cYou are not as cool as a HeadAdmin, so stop trying to use this command!";
	public static String PermAdmin = "§c§l/§4§l/ §cYou are not as cool as a Admin, so stop trying to use this command!";
	public static String PermMOD = "§c§l/§4§l/ §cYou are not as cool as a Mod, so stop trying to use this command!";
	public static String PermVIP = "§c§l/§4§l/ §cYou are not as cool as a VIP, so stop trying to use this command!";
	public static String PermHELPER = "§c§l/§4§l/ §cYou are not as cool as a Helper, so stop trying to use this command!";
	public static String PermIMMORTAL = "§c§l/§4§l/ §cYou are not as cool as a Immortal, so stop trying to use this command!";
	public static String PermLEGEND = "§c§l/§4§l/ §cYou are not as cool as a Legend, so stop trying to use this command!";
	public static String PermELITE = "§c§l/§4§l/ §cYou are not as cool as a Elite, so stop trying to use this command!";
	public static String PermHERO = "§c§l/§4§l/ §cYou are not as cool as a Hero, so stop trying to use this command!";
	public static String PermSergeant = "§c§l/§4§l/ §cYou are not as cool as a Sergeant, so stop trying to use this command!";

	private static Map<String, WandPoint> wandStorage = new HashMap<String, WandPoint>();

	@Override
	public void onEnable() {
		instance = this;

		this.getServer().getPluginManager().registerEvents(new WandListener(), this);
		this.getServer().getPluginManager().registerEvents(new UtilListener(), this);

		this.getCommand("region").setExecutor(new RegionCommand());
		SettingsManager.getInstance().setup(this);
		Region.registerRegions(SettingsManager.getInstance().getConfig());
	}

	@Override
	public void onDisable() {
		instance = null;
	}

	public static Regions getInstance() {
		return instance;
	}

	public static Map<String, WandPoint> getWandStorage() {
		return wandStorage;
	}

}
