package me.wingedmlgpro.swiftyguard.util;

import org.bukkit.configuration.file.*;
import org.bukkit.plugin.java.*;

import java.io.File;
import java.io.IOException;

/**
 * © This Document and Code is STRICTLY copyrighted(©) to Ben.
 * © If anyone takes any part of this code and uses it for
 * © Something public, Share with someone, uses it for API's,
 * © implementing it to their code and taking the rights for
 * © it is NOT allowed unless you get permission from me!
 * © This project SwiftyGuard was created by 35047
 * © at 28/06/15 at 11:05 AM
 */
public class SettingsManager {

	private static SettingsManager instance = null;

	private FileConfiguration config;
	private File configFile;

	public void setup(JavaPlugin plugin) {
		if (!plugin.getDataFolder().exists()) plugin.getDataFolder().mkdir();

		this.configFile = new File(plugin.getDataFolder(), "SwiftyGuard.yml");
		if (!this.configFile.exists()) {
			try {
				this.configFile.createNewFile();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		this.config = YamlConfiguration.loadConfiguration(this.configFile);
	}

	public FileConfiguration getConfig() {
		return this.config;
	}

	public void reloadConfig(JavaPlugin plugin) {
		if (this.configFile == null) return;
		this.config = YamlConfiguration.loadConfiguration(this.configFile);
	}

	public void saveConfig() {
		if (this.config == null || this.configFile == null) return;
		try {
			this.config.save(this.configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static SettingsManager getInstance() {
		if (instance == null) instance = new SettingsManager();
		return instance;
	}

}
