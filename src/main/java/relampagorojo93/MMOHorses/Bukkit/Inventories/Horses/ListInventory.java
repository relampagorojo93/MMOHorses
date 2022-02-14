package relampagorojo93.MMOHorses.Bukkit.Inventories.Horses;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import relampagorojo93.MMOHorses.Enums.Type;
import relampagorojo93.LibsCollection.SpigotMessages.MessagesUtils;
import relampagorojo93.LibsCollection.Utils.Bukkit.ItemStacksUtils;
import relampagorojo93.LibsCollection.Utils.Bukkit.TasksUtils;
import relampagorojo93.LibsCollection.Utils.Bukkit.Inventories.Objects.Button;
import relampagorojo93.LibsCollection.Utils.Bukkit.Inventories.Objects.Item;
import relampagorojo93.MMOHorses.API.MMOHorsesAPI;
import relampagorojo93.MMOHorses.API.Hooks.WorldGuardAPI;
import relampagorojo93.MMOHorses.Bukkit.Inventories.BaseInventory;
import relampagorojo93.MMOHorses.Bukkit.Inventories.Sorters;
import relampagorojo93.MMOHorses.Bukkit.Inventories.Abstracts.ChestInventoryWithAdmin;
import relampagorojo93.MMOHorses.Bukkit.Inventories.Sorters.Sorter;
import relampagorojo93.MMOHorses.Modules.EntityPckg.Interfaces.Objects.MMOHorse;
import relampagorojo93.MMOHorses.Modules.FilePckg.Messages.MessageString;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingBoolean;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingInt;
import relampagorojo93.MMOHorses.Modules.HorsePckg.Objects.ClaimedData;
import relampagorojo93.MMOHorses.Modules.UtilsPckg.PHApplierPckg.Instances.PHDataApplyRequest;

public class ListInventory extends ChestInventoryWithAdmin {

	private OfflinePlayer other;
	private int sorter = 0;
	private int page = 1;
	private List<ClaimedData> horses;

	public ListInventory(Player player, boolean hasadminmode) {
		this(player, null, hasadminmode);
	}

	public ListInventory(Player player, OfflinePlayer other, boolean hasadminmode) {
		super(player, hasadminmode);
		this.other = other;
		setName(MessageString.LISTGUI_TITLE.toString());
		setSize(54);
		setBackground(BaseInventory.getBase());
	}

	@Override
	public Inventory getInventory() {
		if (horses == null) {
			horses = new ArrayList<>();
			if (!hasAdminMode()) {
				horses = MMOHorsesAPI.getHorseModule().getOwned(getPlayer().getUniqueId());
			} else {
				if (other != null)
					horses = MMOHorsesAPI.getHorseModule().loadHorseObjects(other.getUniqueId(), false);
				else {
					ResultSet set = MMOHorsesAPI.getSQLModule().getAllHorses();
					try {
						while (set.next()) {
							ClaimedData hi = MMOHorsesAPI.getHorseModule().getHorseObject(set.getInt("id"));
							if (hi == null)
								hi = MMOHorsesAPI.getHorseModule().createHorseObject(set);
							if (hi != null)
								horses.add(hi);
						}
					} catch (Exception exception) {
					}
				}
			}
		}
		List<ClaimedData> data = new ArrayList<>(horses);
		String value = "";
		Sorter<ClaimedData> cdsorter = Sorters.HORSE_SORTERS.get(this.sorter);
		data.sort(cdsorter.getComparator());
		switch (cdsorter.getName()) {
			case "name":
				value = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTZjYjY1NzM4MWVlOTZmNWVhZGU0YzczMGVlMWExYjE0NTUyNzY1ZjFkZWUyYmNmZGFlMTc1NzkyYjAxNmZiIn19fQ==";
				break;
			case "owner":
				value = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjVlNTIyMzMxN2E4OTBhMzAzNTFmNmY3OGQwYWJmOGRkNzZjYmQwOGRmNmY5MTg4ODM5MzQ1NjRkMjhlNThlIn19fQ==";
				break;
			case "type":
				value = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjBhMmRiMmYxZWI5M2U1OTc4ZDJkYzkxYTc0ZGY0M2Q3Yjc1ZDllYzBlNjk0ZmQ3ZjJhNjUyZmJkMTUifX19";
				break;
			case "summoned":
				value = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWM2ZDVhYmJmNjhjY2IyMzg2YmYxNmFmMjVhYzM4ZDhiNzdiYjBlMDQzMTUyNDYxYmQ5N2YzZjYzMGRiYjhiYyJ9fX0=";
				break;
			default:
				value = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTY5NTkwNThjMGMwNWE0MTdmZDc1N2NiODViNDQxNWQ5NjZmMjczM2QyZTdjYTU0ZjdiYTg2OGUzMjQ5MDllMiJ9fX0=";
				break;
		}
		int m = (int) (data.size() / 28.0D + 0.99D);
		if (page > m)
			page = m;
		ItemStack l = ItemStacksUtils.getItemStack("SKULL_ITEM", (short) 3, "PLAYER_HEAD");
		ItemMeta lm = l.getItemMeta();
		for (int j = 0; j < 28; j++) {
			int slot = 10 + j + j / 7 * 2;
			int hl = 28 * (page - 1) + j;
			if (hl >= 0 && hl < data.size()) {
				ClaimedData hi = data.get(hl);
				lm.setDisplayName(
						new PHDataApplyRequest(hi, getPlayer(), MessageString.LISTGUI_HORSEITEMNAME.toString())
								.generate().getStrings().get(0));
				List<String> lore = new ArrayList<>();
				if (hasAdminMode() || getPlayer().hasPermission("Horse.TeleportToYou"))
					lore.add(MessageString.LISTGUI_LEFTCLICKTEXT.toString());
				if (hasAdminMode() || getPlayer().hasPermission("Horse.Teleport"))
					lore.add(MessageString.LISTGUI_RIGHTCLICKTEXT.toString());
				if (hasAdminMode() || getPlayer().hasPermission("Horse.Unsummon"))
					lore.add(MessageString.LISTGUI_QKEYTEXT.toString());
				if (hasAdminMode()) {
					lore.add(MessageString.LISTGUI_SHIFTLEFTCLICKTEXT.toString());
					lore.add(MessageString.LISTGUI_SHIFTRIGHTCLICKTEXT.toString());
				}
				lm.setLore(lore);
				l.setItemMeta(lm);
				if (hi.getData().getType() == Type.HORSE)
					l = ItemStacksUtils.setSkin(l, hi.getData().getColour().getHorseSkin());
				else if (hi.getData().getType() == Type.LLAMA || hi.getData().getType() == Type.TRADERLLAMA)
					l = ItemStacksUtils.setSkin(l, hi.getData().getColour().getLlamaSkin());
				else
					l = ItemStacksUtils.setSkin(l, hi.getData().getType().getSkin());
				setSlot(slot, new Button(l) {
					private ClaimedData data = hi;

					@Override
					public void onClick(InventoryClickEvent e) {
						if (e.isLeftClick()) {
							if (hasAdminMode() || getPlayer().hasPermission("Horse.TeleportToYou")) {
								if (WorldGuardAPI.isHooked()
										&& !WorldGuardAPI.getSummoningFlag().flagState(getPlayer())) {
									MessagesUtils.getMessageBuilder()
											.createMessage(
													MMOHorsesAPI.getUtils().applyPrefix(MessageString.SUMMONNOTALLOWED))
											.sendMessage(getPlayer());
									return;
								}
								if (hasAdminMode() && e.isShiftClick()) {
									new HorseInventory(getPlayer(), hasAdminMode(), data)
											.openInventory(MMOHorsesAPI.getPlugin());
								} else if (data.getMMOHorse() != null && data.getMMOHorse().isAlive()) {
									if (!getPlayer().hasPermission("Horse.BypassCooldown")) {
										int s = (int) ((System.currentTimeMillis() - data.getCooldown()) / 1000L);
										if (s < SettingInt.TELEPORTCOOLDOWN.toInt()) {
											MessagesUtils.getMessageBuilder()
													.createMessage(MMOHorsesAPI.getUtils()
															.applyPrefix(MessageString.COOLDOWNACTIVE.toString()
																	.replace("%remaining%", String.valueOf(
																			SettingInt.TELEPORTCOOLDOWN.toInt() - s))))
													.sendMessage(getPlayer());
											return;
										}
									}
									if (SettingBoolean.HORSESCANSWITCHWORLD.toBoolean() || data.getMMOHorse()
											.getLocation().getWorld().equals(getPlayer().getWorld())) {
										ClaimedData.teleportToYou(data, (Entity) getPlayer());
										if (SettingBoolean.MOUNTONSPAWN.toBoolean()) {
											closeInventory(MMOHorsesAPI.getPlugin());
											TasksUtils.executeWithDelay(MMOHorsesAPI.getPlugin(),
													() -> data.getMMOHorse().addEntityPassengers(getPlayer()), 10L);
										}
									} else {
										MessagesUtils.getMessageBuilder()
												.createMessage(MMOHorsesAPI.getUtils()
														.applyPrefix(MessageString.WORLDSWITCHBLOCKED))
												.sendMessage(getPlayer());
									}
								} else {
									if (!getPlayer().hasPermission("Horse.BypassRespawn")) {
										int s = (int) ((System.currentTimeMillis()
												- data.getData().getDeathTime().getTime()) / 1000L);
										if (s < SettingInt.RESPAWNCOOLDOWN.toInt()) {
											MessagesUtils.getMessageBuilder()
													.createMessage(MMOHorsesAPI.getUtils()
															.applyPrefix(MessageString.RESPAWNACTIVE.toString()
																	.replace("%remaining%", String.valueOf(
																			SettingInt.RESPAWNCOOLDOWN.toInt() - s))))
													.sendMessage(getPlayer());
											return;
										}
									}
									if (!getPlayer().hasPermission("Horse.BypassCooldown")) {
										int s = (int) ((System.currentTimeMillis() - data.getCooldown()) / 1000L);
										if (s < SettingInt.TELEPORTCOOLDOWN.toInt()) {
											MessagesUtils.getMessageBuilder()
													.createMessage(MMOHorsesAPI.getUtils()
															.applyPrefix(MessageString.COOLDOWNACTIVE.toString()
																	.replace("%remaining%", String.valueOf(
																			SettingInt.TELEPORTCOOLDOWN.toInt() - s))))
													.sendMessage(getPlayer());
											return;
										}
									}
									if (MMOHorsesAPI.getHorseModule().reachedMaxSummoned(getPlayer())) {
										MessagesUtils.getMessageBuilder()
												.createMessage(MMOHorsesAPI.getUtils()
														.applyPrefix(MessageString.TOOMANYSUMMONEDHORSES))
												.sendMessage(getPlayer());
										return;
									}
									MMOHorse ec = MMOHorsesAPI.getEntityModule().getRegistry()
											.spawnEntity(data.getData().getType(), getPlayer().getLocation());
									data.setMMOHorse(ec);
									data.setCooldown();
									if (SettingBoolean.LINKONSPAWN.toBoolean() && !data.getSettings().isLinked())
										data.getSettings().setLink(true);
									if (!MMOHorsesAPI.getHorseModule().isRegistered(data))
										MMOHorsesAPI.getHorseModule().registerHorseObject(data);
									if (SettingBoolean.MOUNTONSPAWN.toBoolean()) {
										closeInventory(MMOHorsesAPI.getPlugin());
										TasksUtils.execute(MMOHorsesAPI.getPlugin(),
												() -> data.getMMOHorse().addEntityPassengers(getPlayer()));
									} else
										updateInventory(MMOHorsesAPI.getPlugin());
								}
							}
						} else if (e.isRightClick()) {
							if (hasAdminMode() || getPlayer().hasPermission("Horse.Teleport"))
								if (hasAdminMode() && e.isShiftClick()) {
									MMOHorsesAPI.getHorseModule().deleteHorseObject(data);
									MessagesUtils.getMessageBuilder()
											.createMessage(
													MMOHorsesAPI.getUtils().applyPrefix(MessageString.HORSEDELETED))
											.sendMessage(getPlayer());
									openInventory(MMOHorsesAPI.getPlugin());
								} else if (data.getMMOHorse() != null && data.getMMOHorse().isAlive()) {
									if (!getPlayer().hasPermission("Horse.BypassCooldown")) {
										int s = (int) ((System.currentTimeMillis() - data.getCooldown()) / 1000L);
										if (s < SettingInt.TELEPORTCOOLDOWN.toInt()) {
											MessagesUtils.getMessageBuilder()
													.createMessage(MMOHorsesAPI.getUtils()
															.applyPrefix(MessageString.COOLDOWNACTIVE.toString()
																	.replace("%remaining%", String.valueOf(
																			SettingInt.TELEPORTCOOLDOWN.toInt() - s))))
													.sendMessage(getPlayer());
											return;
										}
									}
									ClaimedData.teleport(data, (Entity) getPlayer());
									closeInventory(MMOHorsesAPI.getPlugin());
								} else {
									MessagesUtils.getMessageBuilder()
											.createMessage(
													MMOHorsesAPI.getUtils().applyPrefix(MessageString.HORSENOTSUMMONED))
											.sendMessage(getPlayer());
								}
						} else if ((e.getAction() == InventoryAction.DROP_ONE_SLOT
								|| e.getAction() == InventoryAction.DROP_ALL_SLOT)
								&& (hasAdminMode() || getPlayer().hasPermission("Horse.Unsummon"))) {
							if (data.getMMOHorse() != null) {
								if (data.getSettings().isLinked()) {
									if (!SettingBoolean.ALLOWUNLINK.toBoolean()) {
										MessagesUtils.getMessageBuilder()
												.createMessage(MMOHorsesAPI.getUtils()
														.applyPrefix(MessageString.DISABLEDUNLINK))
												.sendMessage(getPlayer());
										return;
									}
									data.getSettings().setLink(false);
								}
								data.getMMOHorse().superDie();
								data.setMMOHorse(null);
								updateInventory(MMOHorsesAPI.getPlugin());
							} else
								MessagesUtils.getMessageBuilder()
										.createMessage(
												MMOHorsesAPI.getUtils().applyPrefix(MessageString.HORSENOTSUMMONED))
										.sendMessage(getPlayer());
						}
					}
				});
			} else
				setSlot(slot, new Item(null));
		}
		if (page > 1) {
			lm.setDisplayName(MessagesUtils.getMessageBuilder()
					.createMessage(MessageString.COMMONGUI_LEFTARROWNAME.toString()).toString());
			lm.setLore(null);
			l.setItemMeta(lm);
			l = ItemStacksUtils.setSkin(l,
					"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmQ2OWUwNmU1ZGFkZmQ4NGU1ZjNkMWMyMTA2M2YyNTUzYjJmYTk0NWVlMWQ0ZDcxNTJmZGM1NDI1YmMxMmE5In19fQ==");
			setSlot(48, new Button(l) {
				@Override
				public void onClick(InventoryClickEvent e) {
					page -= 1;
					updateInventory(MMOHorsesAPI.getPlugin());
				}
			});
		}
		if (page < m) {
			lm.setDisplayName(MessagesUtils.getMessageBuilder()
					.createMessage(MessageString.COMMONGUI_RIGHTARROWNAME.toString()).toString());
			lm.setLore(null);
			l.setItemMeta(lm);
			l = ItemStacksUtils.setSkin(l,
					"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTliZjMyOTJlMTI2YTEwNWI1NGViYTcxM2FhMWIxNTJkNTQxYTFkODkzODgyOWM1NjM2NGQxNzhlZDIyYmYifX19");
			setSlot(50, new Button(l) {
				@Override
				public void onClick(InventoryClickEvent e) {
					page += 1;
					updateInventory(MMOHorsesAPI.getPlugin());
				}
			});
		}
		lm.setDisplayName(MessageString.COMMONGUI_SORTINGNAME.toString().replace("%sorter%", cdsorter.getCapitalizedName()));
		lm.setLore(null);
		l.setItemMeta(lm);
		l = ItemStacksUtils.setSkin(l, value);
		setSlot(49, new Button(l) {
			@Override
			public void onClick(InventoryClickEvent e) {
				if (++sorter >= Sorters.HORSE_SORTERS.size())
					sorter = 0;
				updateInventory(MMOHorsesAPI.getPlugin());
			}
		});
		return super.getInventory();
	}

	public static Inventory getEzInventory(Player player) {
		return new ListInventory(player, player.hasPermission("Horse.Admin.List")).getInventory();
	}
}
