package relampagorojo93.MMOHorses.Modules.HorsePckg;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import relampagorojo93.LibsCollection.SpigotDebug.Data.DebugErrorData;
import relampagorojo93.LibsCollection.SpigotMessages.MessagesUtils;
import relampagorojo93.LibsCollection.SpigotPlugin.LoadOn;
import relampagorojo93.LibsCollection.SpigotPlugin.PluginModule;
import relampagorojo93.LibsCollection.Utils.Bukkit.TasksUtils;
import relampagorojo93.MMOHorses.API.MMOHorsesAPI;
import relampagorojo93.MMOHorses.Enums.Colour;
import relampagorojo93.MMOHorses.Enums.DropReason;
import relampagorojo93.MMOHorses.Enums.Gender;
import relampagorojo93.MMOHorses.Enums.ItemType;
import relampagorojo93.MMOHorses.Enums.Marking;
import relampagorojo93.MMOHorses.Modules.CraftPckg.Objects.CraftableItem;
import relampagorojo93.MMOHorses.Modules.CustomPckg.Objects.CustomData;
import relampagorojo93.MMOHorses.Modules.EntityPckg.Interfaces.Objects.MMOHorse;
import relampagorojo93.MMOHorses.Modules.FilePckg.Messages.MessageString;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingBoolean;
import relampagorojo93.MMOHorses.Modules.HorsePckg.Objects.ClaimedData;
import relampagorojo93.MMOHorses.Modules.HorsePckg.Objects.Modules.DataObject;
import relampagorojo93.MMOHorses.Modules.HorsePckg.Objects.Modules.InventoryObject;
import relampagorojo93.MMOHorses.Modules.HorsePckg.Objects.Modules.LastDataObject;
import relampagorojo93.MMOHorses.Modules.HorsePckg.Objects.Modules.SettingsObject;
import relampagorojo93.MMOHorses.Modules.HorsePckg.Objects.Modules.StatsObject;
import relampagorojo93.MMOHorses.Modules.HorsePckg.Objects.Modules.UpgradesObject;
import relampagorojo93.MMOHorses.Modules.HorsePckg.Objects.Modules.WardrobeObject;
import relampagorojo93.MMOHorses.Enums.Type;

public class HorseModule extends PluginModule {

	@Override
	public boolean load() {
		TasksUtils.execute(MMOHorsesAPI.getPlugin(), () -> {
			for (Player pl : Bukkit.getOnlinePlayers())
				loadHorseObjects(pl.getUniqueId(), true);
		});
		return true;
	}

	@Override
	public boolean unload() {
		for (List<ClaimedData> list : this.horses.values())
			for (ClaimedData h : new ArrayList<>(list))
				unloadHorseObject(h, !h.getSettings().isLinked(), false, null);
		return true;
	}

	@Override
	public LoadOn loadOn() {
		return LoadOn.LOAD;
	}

	@Override
	public boolean optional() {
		return false;
	}

	@Override
	public boolean allowReload() {
		return false;
	}

	private HashMap<UUID, List<ClaimedData>> horses = new HashMap<>();

	public List<ClaimedData> loadHorseObjects(UUID owner, boolean register) {
		List<ClaimedData> list = new ArrayList<>();
		for (ClaimedData h : this.horses.getOrDefault(owner, new ArrayList<>()))
			if (h.getMMOHorse() != null)
				list.add(h);
		ResultSet set = MMOHorsesAPI.getSQLModule().getUnlinkedHorses(owner);
		try {
			while (set.next()) {
				ClaimedData h = null;
				int id = set.getInt("id");
				for (ClaimedData ho : list) {
					if (ho.getData().getId() == id) {
						h = ho;
						break;
					}
				}
				if (h != null)
					continue;
				h = createHorseObject(set);
				if (h != null && !list.contains(h))
					list.add(h);
			}
			set.close();
			list.sort((h1, h2) -> Integer.compare(h1.getData().getId(), h2.getData().getId()));
			if (register)
				this.horses.put(owner, list);
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	public void releaseHorseObject(ClaimedData h) {
		unloadHorseObject(h, false, true, DropReason.RELEASE);
	}

	public void deleteHorseObject(ClaimedData h) {
		unloadHorseObject(h, true, true, null);
	}

	public void unloadHorseObject(ClaimedData h) {
		unloadHorseObject(h, true, false, null);
	}

	public void unloadHorseObject(ClaimedData h, boolean remove, boolean delete, DropReason dropreason) {
		MMOHorse horse = h.getMMOHorse();
		if (horse != null) {
			if (dropreason != null && dropreason != DropReason.DEATH) {
				if (dropreason == DropReason.RELEASE)
					h.drop(dropreason, horse.getLocation());
				else
					h.drop(dropreason == DropReason.WITHITEMS ? horse.getLocation() : null);
				horse.getMMOHorseData().vanilla();
			}
			horse.removePassengers();
			if (remove || delete)
				horse.getMMOHorseData().setClaimedData(null);
			if (remove && horse.getHealth() > 0.0D)
				horse.superDie();
			if (delete)
				TasksUtils.execute(MMOHorsesAPI.getPlugin(),
						() -> MMOHorsesAPI.getSQLModule().deleteHorse(h.getData().getId()), true);
		}
		if (!this.horses.containsKey(h.getData().getOwner()))
			return;
		this.horses.get(h.getData().getOwner()).remove(h);
	}

	public ClaimedData createHorseObject(ResultSet set) {
		int id = -1;
		try {
			id = set.getInt("id");
			ClaimedData data = new ClaimedData();
			data.setData(new DataObject(data, set));
			data.setStats(new StatsObject(data, set));
			data.setSettings(new SettingsObject(data, set));
			data.setUpgrades(new UpgradesObject(data, set));
			data.setInventory(new InventoryObject(data, set));
			data.setCosmetics(new WardrobeObject(data, set));
			data.setLastData(new LastDataObject(data, set));
			return data;
		} catch (Exception e) {
			MMOHorsesAPI.getDebugController().addDebugData(new DebugErrorData("Seems like the horse with ID " + ((id != -1) ? String.valueOf(id) : "Unknown")
							+ " have some wrong data. I suggest you to contact the developer and give him some information about your database. Error: " + e.getMessage()));
			return null;
		}
	}

	public boolean registerHorseObject(ClaimedData hi) {
		if (!this.horses.containsKey(hi.getData().getOwner()))
			this.horses.put(hi.getData().getOwner(), new ArrayList<>());
		return ((List<ClaimedData>) this.horses.get(hi.getData().getOwner())).add(hi);
	}

	public boolean isRegistered(ClaimedData hi) {
		for (List<ClaimedData> list : this.horses.values()) {
			if (list.contains(hi))
				return true;
		}
		return false;
	}

	public boolean unregisterHorseObject(ClaimedData hi) {
		if (!this.horses.containsKey(hi.getData().getOwner()))
			return false;
		return this.horses.get(hi.getData().getOwner()).remove(hi);
	}

	public ClaimedData getHorseObject(int id) {
		for (List<ClaimedData> list : this.horses.values()) {
			for (ClaimedData horse : list) {
				if (horse.getData().getId() == id)
					return horse;
			}
		}
		ResultSet set = MMOHorsesAPI.getSQLModule().getHorse(id);
		try {
			if (set.next())
				return createHorseObject(set);
		} catch (SQLException sQLException) {
		}
		return null;
	}

	public ClaimedData getHorseObject(UUID uuid) {
		for (List<ClaimedData> list : this.horses.values()) {
			for (ClaimedData horse : list) {
				if (horse.getMMOHorse() != null && horse.getMMOHorse().getUniqueID().compareTo(uuid) == 0)
					return horse;
			}
		}
		return null;
	}

	public List<ClaimedData> getHorseObjects() {
		List<ClaimedData> list = new ArrayList<>();
		for (List<ClaimedData> hlist : this.horses.values())
			list.addAll(hlist);
		return list;
	}

	public List<ClaimedData> getOwned(UUID uuid) {
		if (!this.horses.containsKey(uuid))
			this.horses.put(uuid, new ArrayList<>());
		return this.horses.get(uuid);
	}

	public List<ClaimedData> getSummoned(UUID uuid) {
		List<ClaimedData> list = new ArrayList<>();
		for (ClaimedData horse : this.horses.getOrDefault(uuid, new ArrayList<>()))
			if (horse.getMMOHorse() != null && horse.getMMOHorse().isValid())
				list.add(horse);
		return list;
	}

	public ClaimedData registerHorseObject(CommandSender sender, boolean errors, CustomData data, UUID owner) {
		ClaimedData ho = registerHorseObject(sender, errors, data.type, owner, data.name,
				Gender.values()[(int) ((Gender.values()).length * Math.random())], data.colour, data.marking,
				data.health, data.jump, data.speed, false);
		CraftableItem item;
		if ((item = MMOHorsesAPI.getCraftModule().getCraftableItem(data.getArmor())) != null
				&& item.getType() == ItemType.ARMOR)
			ho.getUpgrades().applyItem(item, true);
		if ((item = MMOHorsesAPI.getCraftModule().getCraftableItem(data.getSaddle())) != null
				&& item.getType() == ItemType.SADDLE)
			ho.getUpgrades().applyItem(item, true);
		if ((item = MMOHorsesAPI.getCraftModule().getCraftableItem(data.getSpeedUpgrade())) != null
				&& item.getType() == ItemType.SPEED_UPGRADE)
			ho.getUpgrades().applyItem(item, true);
		if ((item = MMOHorsesAPI.getCraftModule().getCraftableItem(data.getJumpUpgrade())) != null
				&& item.getType() == ItemType.JUMP_UPGRADE)
			ho.getUpgrades().applyItem(item, true);
		if ((item = MMOHorsesAPI.getCraftModule().getCraftableItem(data.getHealthUpgrade())) != null
				&& item.getType() == ItemType.HEALTH_UPGRADE)
			ho.getUpgrades().applyItem(item, true);
		if ((item = MMOHorsesAPI.getCraftModule().getCraftableItem(data.getChestUpgrade())) != null
				&& item.getType() == ItemType.CHEST_UPGRADE)
			ho.getUpgrades().applyItem(item, true);
		return ho;
	}

	public ClaimedData registerHorseObject(CommandSender sender, boolean errors, MMOHorse horse, UUID owner,
			String name) {
		if (SettingBoolean.valueOf("SUPPORT_" + horse.getType().name()).toBoolean()) {
			ClaimedData ho = registerHorseObject(sender, errors, horse.getType(), owner, name,
					horse.getMMOHorseData().getGender(), horse.getMMOHorseData().getColour(),
					horse.getMMOHorseData().getMarking(), horse.getMMOHorseData().getMaxHealth(),
					horse.getMMOHorseData().getJump(), horse.getMMOHorseData().getSpeed(),
					SettingBoolean.LINKONCLAIM.toBoolean());
			if (horse.isCarryingChest())
				ho.getCosmetics().setAccessory(new ItemStack(Material.CHEST));
			if (horse.getSaddle() != null && horse.getSaddle().getType() != Material.AIR)
				ho.getCosmetics().setSaddle(horse.getSaddle());
			if (horse.getBody() != null && horse.getBody().getType() != Material.AIR)
				ho.getCosmetics().setBody(horse.getBody());
			ho.setMMOHorse(horse);
			return ho;
		}
		if (errors)
			MessagesUtils.getMessageBuilder()
					.createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.ENTITYNOTSUPPORTED))
					.sendMessage(sender);
		return null;
	}

	public ClaimedData registerHorseObject(CommandSender sender, boolean errors, Type type, UUID owner, String name,
			Gender gender, Colour colour, Marking marking, double health, double jump, double speed, boolean link) {
		int id = MMOHorsesAPI.getSQLModule().registerHorse(type, owner, name, gender, colour, marking, health, jump,
				speed);
		if (id != -1) {
			ResultSet set = MMOHorsesAPI.getSQLModule().getHorse(id);
			try {
				if (set.next()) {
					ClaimedData h = createHorseObject(set);
					if (link)
						h.getSettings().setLink(true);
					h.getSettings().setNameTagVisible(SettingBoolean.SHOWNAMETAGBYDEFAULT.toBoolean());
					h.getSettings().setFollowOwner(SettingBoolean.FOLLOWOWNERBYDEFAULT.toBoolean());
					h.getSettings().setHealthTagVisible(SettingBoolean.SHOWHEALTHTAGBYDEFAULT.toBoolean());
					h.getSettings().blockSpeedOnUnmount(SettingBoolean.BLOCKUNMOUNTEDSPEEDBYDEFAULT.toBoolean());
					if (!this.horses.containsKey(owner))
						this.horses.put(owner, new ArrayList<>());
					((List<ClaimedData>) this.horses.get(owner)).add(h);
					return h;
				}
			} catch (SQLException e) {
				if (errors)
					MessagesUtils.getMessageBuilder()
							.createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.HORSEERROR))
							.sendMessage(sender);
			}
		}
		return null;
	}

	public boolean reachedMaxClaimed(Player pl) {
		if (SettingBoolean.LIMITCLAIMAMOUNT.toBoolean() && !pl.hasPermission("horse.bypassclaim")
				&& getOwned(pl.getUniqueId()).size() >= MMOHorsesAPI.getUtils().getPermissionValue("horse.max.", pl))
			return true;
		return false;
	}

	public boolean reachedMaxSummoned(Player pl) {
		if (SettingBoolean.LIMITSUMMONAMOUNT.toBoolean() && !pl.hasPermission("horse.bypassmaxsummon")
				&& getSummoned(pl.getUniqueId()).size() >= MMOHorsesAPI.getUtils()
						.getPermissionValue("horse.maxsummon.", pl))
			return true;
		return false;
	}
}
