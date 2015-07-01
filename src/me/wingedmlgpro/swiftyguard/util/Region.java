package me.wingedmlgpro.swiftyguard.util;

import org.bukkit.*;
import org.bukkit.configuration.*;
import org.bukkit.configuration.file.*;
import org.bukkit.configuration.serialization.*;

import java.util.*;

public class Region implements ConfigurationSerializable {

	private static Map<String, Region> regionMap = new HashMap<String, Region>(); // Caches all the regions.

	private String regionName = null;
	private UUID regionCreator = null;
	private Cuboid regionCuboid = null;

	private boolean pvp = false;
	private boolean placeBlock = false;

	public Region(String name, Cuboid cuboid) {
		this.regionName = name;
		this.regionCuboid = cuboid;
	}

	public boolean canPlaceBlock() {
		return this.placeBlock;
	}

	public UUID getCreator() {
		return this.regionCreator;
	}

	public Cuboid getCuboid() {
		return this.regionCuboid;
	}

	public String getName() {
		return this.regionName;
	}

	public boolean isPvPEnabled() {
		return this.pvp;
	}

	public void setCreator(UUID creatorUUID) {
		this.regionCreator = creatorUUID;
	}

	public void setPlaceBlock(boolean placeBlock) {
		this.placeBlock = placeBlock;
	}

	public void setPvP(boolean flag) {
		this.pvp = flag;
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> serializedRegion = new HashMap<String, Object>();
		serializedRegion.put("Name", this.regionName);
		if (this.regionCreator != null) serializedRegion.put("Creator", this.regionCreator.toString());
		serializedRegion.put("PvP", this.pvp);
		serializedRegion.put("PlaceBlock", this.placeBlock);
		if (this.regionCuboid != null) serializedRegion.put("Locations", this.regionCuboid.serialize());
		return serializedRegion;
	}

	public static Region deserialize(Map<String, Object> serializedRegion) {
		if (serializedRegion.get("Name") != null) {
			String name = (String) serializedRegion.get("Name");
			if (serializedRegion.containsKey("Locations")) {
				UUID creator = serializedRegion.containsKey("Creator") && isUUID(serializedRegion.get("Creator").toString()) ? UUID.fromString(serializedRegion.get("Creator").toString()) : null;
				Cuboid regionCuboid = Cuboid.deserialize(((ConfigurationSection) serializedRegion.get("Locations")).getValues(false));
				if (regionCuboid != null) {
					Region region = new Region(name, regionCuboid);
					region.setCreator(creator);
					if (serializedRegion.containsKey("PvP")) region.setPvP((Boolean) serializedRegion.get("PvP"));
					if (serializedRegion.containsKey("PlaceBlock"))
						region.setPlaceBlock((Boolean) serializedRegion.get("PlaceBlock"));
					return region;
				}
			}
		}
		return null;
	}

	public static Region getRegion(String regionName) {
		return regionMap.get(regionName.toLowerCase());
	}

	public static List<Region> getRegions(Location location) {
		List<Region> regionList = new ArrayList<Region>();
		if (location != null) {
			for (Region region : regionMap.values()) {
				if (region.getCuboid().containsLocation(location)) regionList.add(region);
			}
		}
		return regionList;
	}

	public static void registerRegion(Region region) {
		if (region != null) regionMap.put(region.getName().toLowerCase(), region);
	}

	public static void unregisterRegion(Region region) {
		if (region != null) regionMap.remove(region.getName().toLowerCase());
	}

	public static void registerRegions(FileConfiguration fileConfig) {
		regionMap.clear();
		if (fileConfig != null && fileConfig.isConfigurationSection("SwiftyGuard")) {
			for (Map.Entry<String, Object> regionEntry : fileConfig.getConfigurationSection("SwiftyGuard").getValues(false).entrySet()) {
				if (regionEntry.getValue() instanceof ConfigurationSection) {
					Region region = deserialize(((ConfigurationSection) regionEntry.getValue()).getValues(false));
					if (region != null) regionMap.put(region.getName().toLowerCase(), region);
					else System.err.println("Failed to load the region '" + regionEntry.getKey() + "'!");
				}
			}
		}
	}

	private static boolean isUUID(String aString) {
		try {
			UUID.fromString(aString);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

}
