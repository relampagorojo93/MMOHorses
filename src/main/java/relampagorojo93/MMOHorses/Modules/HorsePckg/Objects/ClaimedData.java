package relampagorojo93.MMOHorses.Modules.HorsePckg.Objects;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import relampagorojo93.MMOHorses.API.MMOHorsesAPI;
import relampagorojo93.MMOHorses.Enums.DropReason;
import relampagorojo93.MMOHorses.Enums.ItemType;
import relampagorojo93.MMOHorses.Modules.EntityPckg.Interfaces.Objects.MMOHorse;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingBoolean;
import relampagorojo93.MMOHorses.Modules.HorsePckg.Objects.Modules.DataObject;
import relampagorojo93.MMOHorses.Modules.HorsePckg.Objects.Modules.InventoryObject;
import relampagorojo93.MMOHorses.Modules.HorsePckg.Objects.Modules.LastDataObject;
import relampagorojo93.MMOHorses.Modules.HorsePckg.Objects.Modules.SettingsObject;
import relampagorojo93.MMOHorses.Modules.HorsePckg.Objects.Modules.StatsObject;
import relampagorojo93.MMOHorses.Modules.HorsePckg.Objects.Modules.UpgradesObject;
import relampagorojo93.MMOHorses.Modules.HorsePckg.Objects.Modules.WardrobeObject;

public class ClaimedData {
	private MMOHorse entity;
	private DataObject data;
	private SettingsObject settings;
	private UpgradesObject upgrades;
	private InventoryObject inventory;
	private WardrobeObject cosmetics;
	private StatsObject stats;
	private LastDataObject lastdata;

	public MMOHorse getMMOHorse() {
		return entity;
	}

	public DataObject getData() {
		return data;
	}

	public SettingsObject getSettings() {
		return settings;
	}

	public UpgradesObject getUpgrades() {
		return upgrades;
	}

	public InventoryObject getInventory() {
		return inventory;
	}

	public WardrobeObject getCosmetics() {
		return cosmetics;
	}

	public StatsObject getStats() {
		return stats;
	}

	public LastDataObject getLastData() {
		return lastdata;
	}

	public void setData(DataObject data) {
		this.data = data;
	}

	public void setSettings(SettingsObject settings) {
		this.settings = settings;
	}

	public void setUpgrades(UpgradesObject upgrades) {
		this.upgrades = upgrades;
	}

	public void setInventory(InventoryObject inventory) {
		this.inventory = inventory;
	}

	public void setCosmetics(WardrobeObject cosmetics) {
		this.cosmetics = cosmetics;
	}

	public void setStats(StatsObject stats) {
		this.stats = stats;
	}

	public void setLastData(LastDataObject lastdata) {
		this.lastdata = lastdata;
	}

	public void setMMOHorse(MMOHorse entity) {
		if (entity == null) {
			if (this.entity != null) {
				lastdata.setLastFood(this.entity.getMMOHorseData().getFood());
				lastdata.setLastHealth((double) this.entity.getHealth() > 0D ? (double) this.entity.getHealth() : 0D);
				lastdata.updateDatabase();
				this.entity = entity;
			}
		} else {
			(this.entity = entity).getMMOHorseData().setClaimedData(this);
			if (lastdata.getLastHealth() > 0D) {
				this.entity.setHealth(lastdata.getLastHealth());
				this.entity.getMMOHorseData().setFood(lastdata.getLastFood());
			} else
				this.entity.setHealth(this.entity.getMMOHorseData().getTotalMaxHealth());
		}
	}

	public long cooldown = 0L;

	public long getCooldown() {
		return cooldown;
	}

	public void setCooldown() {
		this.cooldown = System.currentTimeMillis();
	}

	public static void teleport(ClaimedData h, Entity ent) {
		if (h.getMMOHorse() != null) {
			if (!h.getMMOHorse().getLocation().getChunk().isLoaded())
				h.getMMOHorse().getLocation().getChunk().load();
			ent.teleport(h.getMMOHorse().getLocation());
			h.setCooldown();
		}
	}

	public static void teleportToYou(ClaimedData h, Entity ent) {
		teleportToYou(h, ent.getLocation());
	}

	public static void teleportToYou(ClaimedData h, Location l) {
		if (h.getMMOHorse() != null) {
			Location hl = h.getMMOHorse().getLocation();
			if (!hl.getChunk().isLoaded())
				hl.getChunk().load();
			if (!hl.getWorld().equals(l.getWorld())) {
				h.getMMOHorse().superDie();
				h.setMMOHorse(MMOHorsesAPI.getEntityModule().getRegistry().spawnEntity(h.getData().getType(), l));
			} else
				h.getMMOHorse().teleport(l);
			h.setCooldown();
		}
	}

	public void drop(DropReason dropreason, Location l) {
		if (dropreason == DropReason.GIFT) {
			if (SettingBoolean.DROPINVENTORYONGIFT.toBoolean())
				dropInventory(l);
			if (SettingBoolean.DROPUPGRADESONGIFT.toBoolean())
				dropUpgrades(l);
			if (SettingBoolean.DROPWARDROBEONGIFT.toBoolean())
				dropWardrobe(l);
		} else {
			dropInventory(SettingBoolean.valueOf("DROPINVENTORYON" + dropreason.name()).toBoolean() ? l : null);
			dropUpgrades(SettingBoolean.valueOf("DROPUPGRADESON" + dropreason.name()).toBoolean() ? l : null);
			dropWardrobe(SettingBoolean.valueOf("DROPWARDROBEON" + dropreason.name()).toBoolean() ? l : null);
		}
	}

	public void drop(Location l) {
		dropInventory(l);
		dropUpgrades(l);
		dropWardrobe(l);
	}

	public void dropInventory(Location l) {
		if (l != null) {
			if (getInventory().getContents() != null) {
				for (ItemStack i : getInventory().getContents())
					if (i != null && i.getType() != Material.AIR)
						l.getWorld().dropItem(l, i);
			}
		}
		getInventory().setContents(new ItemStack[28]);
	}

	public void dropUpgrades(Location l) {
		if (l != null) {
			if (getUpgrades().getSaddleItem() != null)
				l.getWorld().dropItem(l, getUpgrades().getSaddleItem().getResult());
			if (getUpgrades().getArmorItem() != null)
				l.getWorld().dropItem(l, getUpgrades().getArmorItem().getResult());
			if (getUpgrades().getChestItem() != null)
				l.getWorld().dropItem(l, getUpgrades().getChestItem().getResult());
			if (getUpgrades().getSpeedItem() != null)
				l.getWorld().dropItem(l, getUpgrades().getSpeedItem().getResult());
			if (getUpgrades().getJumpItem() != null)
				l.getWorld().dropItem(l, getUpgrades().getJumpItem().getResult());
			if (getUpgrades().getHealthItem() != null)
				l.getWorld().dropItem(l, getUpgrades().getHealthItem().getResult());
			if (getUpgrades().getHealthItem() != null)
				l.getWorld().dropItem(l, getUpgrades().getHealthItem().getResult());
		}
		getUpgrades().removeItem(ItemType.SADDLE);
		getUpgrades().removeItem(ItemType.ARMOR);
		getUpgrades().removeItem(ItemType.CHEST_UPGRADE);
		getUpgrades().removeItem(ItemType.SPEED_UPGRADE);
		getUpgrades().removeItem(ItemType.JUMP_UPGRADE);
		getUpgrades().removeItem(ItemType.HEALTH_UPGRADE);
	}

	public void dropWardrobe(Location l) {
		if (l != null) {
			if (getCosmetics().getBody() != null)
				l.getWorld().dropItem(l, getCosmetics().getBody());
			if (getCosmetics().getSaddle() != null)
				l.getWorld().dropItem(l, getCosmetics().getSaddle());
			if (getCosmetics().getAccessory() != null)
				l.getWorld().dropItem(l, getCosmetics().getAccessory());
		}
		getCosmetics().setBody(null);
		getCosmetics().setSaddle(null);
		getCosmetics().setAccessory(null);
	}
}