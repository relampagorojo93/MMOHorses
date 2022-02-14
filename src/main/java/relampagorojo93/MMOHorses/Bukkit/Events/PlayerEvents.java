package relampagorojo93.MMOHorses.Bukkit.Events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import relampagorojo93.MMOHorses.Enums.ItemType;
import relampagorojo93.MMOHorses.Enums.Action;
import relampagorojo93.MMOHorses.Modules.CraftPckg.Objects.CraftableItem;
import relampagorojo93.MMOHorses.Modules.CustomPckg.Objects.CustomData;
import relampagorojo93.MMOHorses.Modules.EntityPckg.Interfaces.Objects.MMOHorse;
import relampagorojo93.MMOHorses.Modules.FilePckg.Messages.MessageList;
import relampagorojo93.MMOHorses.Modules.FilePckg.Messages.MessageString;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingBoolean;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingDouble;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingList;
import relampagorojo93.MMOHorses.Modules.HorsePckg.Objects.ClaimedData;
import relampagorojo93.MMOHorses.Modules.HorsePckg.Objects.UserAccess;
import relampagorojo93.MMOHorses.Modules.UtilsPckg.PHApplierPckg.Instances.PHDataApplyRequest;
import relampagorojo93.LibsCollection.SpigotMessages.MessagesUtils;
import relampagorojo93.LibsCollection.Utils.Bukkit.ItemStacksUtils;
import relampagorojo93.MMOHorses.API.MMOHorsesAPI;
import relampagorojo93.MMOHorses.Bukkit.Inventories.Storage.ChestInventory;

public class PlayerEvents implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerLogin(AsyncPlayerPreLoginEvent e) {
		if (e.getLoginResult() == Result.ALLOWED && SettingBoolean.LISTUNLINKED.toBoolean())
			MMOHorsesAPI.getHorseModule().loadHorseObjects(e.getUniqueId(), true);
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		if (!e.getPlayer().hasPlayedBefore())
			for (String name : SettingList.FIRSTJOINHORSES.toList()) {
				CustomData cd = MMOHorsesAPI.getCustomModule().getCustomEntityData(name);
				if (cd != null)
					MMOHorsesAPI.getHorseModule().registerHorseObject(Bukkit.getConsoleSender(), false, cd,
							e.getPlayer().getUniqueId());
			}
		if (SettingBoolean.DISCOVER_ALL_RECIPES.toBoolean())
			for (CraftableItem ci : MMOHorsesAPI.getCraftModule().getCraftableItems())
				if (ci.getRecipe() != null)
					ItemStacksUtils.discoverRecipe(e.getPlayer(), ci.getRecipe());
	}

	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent e) {
		if (e.getPlayer().isInsideVehicle() && MMOHorsesAPI.getUtils().toMMOHorse(e.getPlayer().getVehicle()) != null)
			e.getPlayer().leaveVehicle();
		for (ClaimedData hi : new ArrayList<>(MMOHorsesAPI.getHorseModule().getOwned(e.getPlayer().getUniqueId())))
			if (!hi.getSettings().isLinked())
				MMOHorsesAPI.getHorseModule().unloadHorseObject(hi);
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		if (!e.isCancelled() && SettingBoolean.FOODSYSTEM.toBoolean() && e.getPlayer().isInsideVehicle()) {
			MMOHorse horse = MMOHorsesAPI.getUtils().toMMOHorse(e.getPlayer().getVehicle());
			if (horse != null && horse.isCustom() && e.getFrom().getX() != e.getTo().getX()
					&& e.getFrom().getZ() != e.getTo().getZ() && horse.getSaddle() != null)
				horse.getMMOHorseData().setFood(horse.getMMOHorseData().getFood()
						- (SettingDouble.FOODCONSUMPERBLOCK.toDouble() * e.getFrom().distance(e.getTo())));
		}
	}

	@EventHandler
	public void onItemSwap(PlayerSwapHandItemsEvent e) {
		if (e.getPlayer().isInsideVehicle()) {
			MMOHorse horse = MMOHorsesAPI.getUtils().toMMOHorse(e.getPlayer().getVehicle());
			if (horse != null && horse.isCustom()) {
				e.setCancelled(true);
				horse.getMMOHorseData().switchWalkMode(true, false);
			}
		}
	}

	@EventHandler
	public void onItemUsage(PlayerInteractEvent e) {
		if (e.getHand() == EquipmentSlot.HAND) {
			ItemStack i = ItemStacksUtils.getItemInMainHand(e.getPlayer());
			if (i != null) {
				if (i.getType() == Material.BLAZE_ROD && e.getPlayer().isInsideVehicle()) {
					MMOHorse horse = MMOHorsesAPI.getUtils().toMMOHorse(e.getPlayer().getVehicle());
					if (horse != null && horse.isCustom())
						horse.getMMOHorseData()
								.switchWalkMode(e.getAction().name().contains("RIGHT_CLICK") ? false : true, true);
				} else
					ItemType.CATCHER.event(e, null);
			}
		}
	}

	@EventHandler
	public void onPlaceBlock(PlayerInteractEvent e) {
		if (SettingBoolean.BLOCK_INTERACTIONS_WITH_REGISTERED_ITEMS.toBoolean() && e.getItem() != null
				&& MMOHorsesAPI.getCraftModule().getCraftableItem(e.getItem()) != null)
			e.setCancelled(true);
	}

	@EventHandler
	public void onItemUsage(PlayerInteractEntityEvent e) {
		if (e.getHand() == EquipmentSlot.HAND) {
			MMOHorse horse = MMOHorsesAPI.getUtils().toMMOHorse(e.getRightClicked());
			UserAccess user = horse.getMMOHorseData().isClaimed()
					? horse.getMMOHorseData().getClaimedData().getData().getTrusted(e.getPlayer().getUniqueId())
					: null;
			if (horse != null && horse.isCustom()) {
				horse.getMMOHorseData().setLastActivity(System.currentTimeMillis());
				ClaimedData ho = horse.getMMOHorseData().getClaimedData();
				if (e.getPlayer().isSneaking()) {
					if (!SettingBoolean.OPEN_CHEST_ON_SHIFT_RIGHT_CLICK.toBoolean() || ho == null || user == null
							|| !user.hasAccess(Action.OPEN_VIRTUALCHEST)) {
						List<String> list = ho != null ? MessageList.CLAIMEDHORSEINFO.toList()
								: MessageList.NONCLAIMEDHORSEINFO.toList();
						if (!list.isEmpty()) {
							e.setCancelled(true);
							for (String m : list)
								new PHDataApplyRequest(horse, e.getPlayer(), m).generate().sendMessage(e.getPlayer());
						}
					} else {
						e.setCancelled(true);
						new ChestInventory(e.getPlayer(), user, ho).openInventory(MMOHorsesAPI.getPlugin());
					}
				} else {
					ItemStack item = ItemStacksUtils.getItemInMainHand(e.getPlayer());
					if (item != null) {
						if ((ho == null && SettingBoolean.DISABLEEQUIPABLEITEMSUSAGE.toBoolean()
								&& item.getType() == Material.SADDLE || item.getType().name().endsWith("_BARDING"))
								|| (ho != null && (user == null || !user.hasAccess(Action.ITEM_USAGE)))) {
							e.setCancelled(true);
							return;
						}
						CraftableItem ci = MMOHorsesAPI.getCraftModule().getCraftableItem(item);
						if (ci != null) {
							if (horse.getMMOHorseData().isClaimed()) {
								ClaimedData data = horse.getMMOHorseData().getClaimedData();
								if (data.getStats().getHorseLevel() >= ci.getMinLevel()
										&& data.getStats().getHorseLevel() <= ci.getMaxLevel())
									ci.getType().event(e, ci, horse);
								else {
									e.setCancelled(true);
									if (data.getStats().getHorseLevel() < ci.getMinLevel())
										MessagesUtils.getMessageBuilder()
												.createMessage(MMOHorsesAPI.getUtils()
														.applyPrefix(MessageString.ITEMMINLEVELNOTREACHED))
												.sendMessage(e.getPlayer());
									else if (data.getStats().getHorseLevel() > ci.getMaxLevel())
										MessagesUtils.getMessageBuilder()
												.createMessage(MMOHorsesAPI.getUtils()
														.applyPrefix(MessageString.ITEMMAXLEVELEXCEEDED))
												.sendMessage(e.getPlayer());
								}
							}
						} else if (item.getType() == Material.NAME_TAG && ho != null)
							ItemType.NAMETAG.event(e, ci, horse);
						else if ((item.getType().name().contains("BARDING")
								|| item.getType().name().contains("HORSE_ARMOR") || item.getType() == Material.SADDLE)
								&& ho != null)
							e.setCancelled(true);
					}
				}
			}
		}
	}

	@EventHandler
	public void onPlayerCraft(PrepareItemCraftEvent e) {
		if (e.getRecipe() != null && e.getRecipe() instanceof ShapedRecipe) {
			CraftableItem ci = MMOHorsesAPI.getCraftModule().getCraftableItem((ShapedRecipe) e.getRecipe());
			if (ci != null)
				e.getInventory().setResult(ci.getResult(e.getInventory().getMatrix()));
		}
	}

	@EventHandler
	public void onPlayerUnmount(VehicleExitEvent e) {
		MMOHorse horse = MMOHorsesAPI.getUtils().toMMOHorse(e.getVehicle());
		if (horse != null && horse.isCustom() && horse.getMMOHorseData().isClaimed()
				&& SettingBoolean.UNSUMMONONUNMOUNT.toBoolean()) {
			ClaimedData data = horse.getMMOHorseData().getClaimedData();
			if (data.getSettings().isLinked()) {
				if (!SettingBoolean.ALLOWUNLINK.toBoolean())
					return;
				data.getSettings().setLink(false);
			}
			data.getMMOHorse().superDie();
			data.setMMOHorse(null);
		}
	}
}
