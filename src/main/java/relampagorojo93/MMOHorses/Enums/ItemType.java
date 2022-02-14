package relampagorojo93.MMOHorses.Enums;

import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import relampagorojo93.LibsCollection.SpigotMessages.MessagesUtils;
import relampagorojo93.LibsCollection.Utils.Bukkit.ItemStacksUtils;
import relampagorojo93.MMOHorses.API.MMOHorsesAPI;
import relampagorojo93.MMOHorses.Modules.CraftPckg.Objects.CraftableItem;
import relampagorojo93.MMOHorses.Modules.CustomPckg.Objects.CustomData;
import relampagorojo93.MMOHorses.Modules.EntityPckg.Interfaces.Objects.MMOHorse;
import relampagorojo93.MMOHorses.Modules.FilePckg.Messages.MessageString;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingBoolean;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingDouble;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingInt;
import relampagorojo93.MMOHorses.Modules.HorsePckg.Objects.ClaimedData;
import relampagorojo93.MMOHorses.Modules.HorsePckg.Objects.UserAccess;

public enum ItemType {
	SPEED_UPGRADE {
		@Override
		public void event(PlayerInteractEntityEvent e, CraftableItem ci, MMOHorse ent) {
			e.setCancelled(true);
			if (!e.getPlayer().hasPermission("Horse.BypassOwnership") && ent.getMMOHorseData().isClaimed()) {
				UserAccess access = ent.getMMOHorseData().getClaimedData().getData()
						.getTrusted(e.getPlayer().getUniqueId());
				if (access == null || !access.hasAccess(Action.ITEM_USAGE))
					return;
			}
			if (ent.getMMOHorseData().isClaimed()
					&& ent.getMMOHorseData().getClaimedData().getUpgrades().applyItem(ci, false))
				ItemStacksUtils.reduceItemInMainHand(e.getPlayer());
		}
	},
	JUMP_UPGRADE {
		@Override
		public void event(PlayerInteractEntityEvent e, CraftableItem ci, MMOHorse ent) {
			e.setCancelled(true);
			if (!e.getPlayer().hasPermission("Horse.BypassOwnership") && ent.getMMOHorseData().isClaimed()) {
				UserAccess access = ent.getMMOHorseData().getClaimedData().getData()
						.getTrusted(e.getPlayer().getUniqueId());
				if (access == null || !access.hasAccess(Action.ITEM_USAGE))
					return;
			}
			if (ent.getMMOHorseData().isClaimed()
					&& ent.getMMOHorseData().getClaimedData().getUpgrades().applyItem(ci, false))
				ItemStacksUtils.reduceItemInMainHand(e.getPlayer());
		}
	},
	HEALTH_UPGRADE {
		@Override
		public void event(PlayerInteractEntityEvent e, CraftableItem ci, MMOHorse ent) {
			e.setCancelled(true);
			if (!e.getPlayer().hasPermission("Horse.BypassOwnership") && ent.getMMOHorseData().isClaimed()) {
				UserAccess access = ent.getMMOHorseData().getClaimedData().getData()
						.getTrusted(e.getPlayer().getUniqueId());
				if (access == null || !access.hasAccess(Action.ITEM_USAGE))
					return;
			}
			if (ent.getMMOHorseData().isClaimed()
					&& ent.getMMOHorseData().getClaimedData().getUpgrades().applyItem(ci, false))
				ItemStacksUtils.reduceItemInMainHand(e.getPlayer());
		}
	},
	CHEST_UPGRADE {
		@Override
		public void event(PlayerInteractEntityEvent e, CraftableItem ci, MMOHorse ent) {
			e.setCancelled(true);
			if (!e.getPlayer().hasPermission("Horse.BypassOwnership") && ent.getMMOHorseData().isClaimed()) {
				UserAccess access = ent.getMMOHorseData().getClaimedData().getData()
						.getTrusted(e.getPlayer().getUniqueId());
				if (access == null || !access.hasAccess(Action.ITEM_USAGE))
					return;
			}
			if (ent.getMMOHorseData().isClaimed()
					&& ent.getMMOHorseData().getClaimedData().getUpgrades().applyItem(ci, false))
				ItemStacksUtils.reduceItemInMainHand(e.getPlayer());
		}
	},
	SADDLE {
		@Override
		public void event(PlayerInteractEntityEvent e, CraftableItem ci, MMOHorse ent) {
			e.setCancelled(true);
			if (!e.getPlayer().hasPermission("Horse.BypassOwnership") && ent.getMMOHorseData().isClaimed()) {
				UserAccess access = ent.getMMOHorseData().getClaimedData().getData()
						.getTrusted(e.getPlayer().getUniqueId());
				if (access == null || !access.hasAccess(Action.ITEM_USAGE))
					return;
			}
			if (ent.getMMOHorseData().isClaimed()
					&& ent.getMMOHorseData().getClaimedData().getUpgrades().applyItem(ci, false))
				ItemStacksUtils.reduceItemInMainHand(e.getPlayer());
		}
	},
	ARMOR {
		@Override
		public void event(PlayerInteractEntityEvent e, CraftableItem ci, MMOHorse ent) {
			e.setCancelled(true);
			if (!e.getPlayer().hasPermission("Horse.BypassOwnership") && ent.getMMOHorseData().isClaimed()) {
				UserAccess access = ent.getMMOHorseData().getClaimedData().getData()
						.getTrusted(e.getPlayer().getUniqueId());
				if (access == null || !access.hasAccess(Action.ITEM_USAGE))
					return;
			}
			if (ent.getMMOHorseData().isClaimed()
					&& ent.getMMOHorseData().getClaimedData().getUpgrades().applyItem(ci, false))
				ItemStacksUtils.reduceItemInMainHand(e.getPlayer());
		}
	},
	HAIRBRUSH {
		@Override
		public void event(PlayerInteractEntityEvent e, CraftableItem ci, MMOHorse ent) {
			e.setCancelled(true);
			MMOHorsesAPI.getUtils().randomParticles(MMOHorsesAPI.getUtils().getParticle("VILLAGER_HAPPY", "COMPOSTER"),
					ent.getLocation(), 10, 2.0D);
			double maxhealth = ent.getMMOHorseData().getTotalMaxHealth();
			if (ent.getHealth() < maxhealth) {
				if (ent.getHealth() + ci.getValue() >= maxhealth)
					ent.setHealth(maxhealth);
				else
					ent.setHealth(ent.getHealth() + ci.getValue());
			}
		}
	},
	CUSTOM {
	},
	COSMETIC {
	},
	FOOD {
		@Override
		public void event(PlayerInteractEntityEvent e, CraftableItem ci, MMOHorse ent) {
			e.setCancelled(true);
			if (!e.getPlayer().hasPermission("Horse.BypassOwnership") && ent.getMMOHorseData().isClaimed()) {
				UserAccess access = ent.getMMOHorseData().getClaimedData().getData()
						.getTrusted(e.getPlayer().getUniqueId());
				if (access == null || !access.hasAccess(Action.ITEM_USAGE))
					return;
			}
			double maxhealth = ent.getMMOHorseData().getTotalMaxHealth();
			if (ci.getValue() < 0.0F) {
				ent.getMMOHorseData()
						.setFood(ent.getMMOHorseData().getFood() + SettingDouble.FOODRECOVERYPERFOOD.toDouble());
				ent.damage((ci.getValue() * -1.0F), e.getPlayer());
				ent.getLocation().getWorld().spawnParticle(Particle.CRIT_MAGIC, ent.getEyeLocation(), 1);
				ItemStacksUtils.reduceItemInMainHand(e.getPlayer());
			} else {
				if (ent.getHealth() == maxhealth
						&& (!SettingBoolean.FOODSYSTEM.toBoolean() || ent.getMMOHorseData().getFood() >= 1D))
					ent.getLocation().getWorld().playSound(e.getPlayer().getLocation(), Sound.ENTITY_HORSE_ANGRY, 1.0F,
							1.0F);
				else {
					ent.getMMOHorseData()
							.setFood(ent.getMMOHorseData().getFood() + SettingDouble.FOODRECOVERYPERFOOD.toDouble());
					ent.setHealth(ent.getHealth() + ci.getValue());
					ent.getLocation().getWorld().playSound(e.getPlayer().getLocation(), Sound.ENTITY_HORSE_EAT, 1.0F,
							1.0F);
					ItemStacksUtils.reduceItemInMainHand(e.getPlayer());
				}
			}
		}
	},
	BREEDING_FOOD {
		@Override
		public void event(PlayerInteractEntityEvent e, CraftableItem ci, MMOHorse ent) {
			e.setCancelled(true);
			if (!e.getPlayer().hasPermission("Horse.BypassOwnership") && ent.getMMOHorseData().isClaimed()) {
				UserAccess access = ent.getMMOHorseData().getClaimedData().getData()
						.getTrusted(e.getPlayer().getUniqueId());
				if (access == null || !access.hasAccess(Action.ITEM_USAGE))
					return;
			}
			if (ent.isTamed() && !ent.isInLove()) {
				ent.superEnterLoveMode(e.getPlayer());
				ItemStacksUtils.reduceItemInMainHand(e.getPlayer());
			}
		}
	},
	LEVEL_BOOSTER {
		@Override
		public void event(PlayerInteractEntityEvent e, CraftableItem ci, MMOHorse ent) {
			e.setCancelled(true);
			if (!e.getPlayer().hasPermission("Horse.BypassOwnership") && ent.getMMOHorseData().isClaimed()) {
				UserAccess access = ent.getMMOHorseData().getClaimedData().getData()
						.getTrusted(e.getPlayer().getUniqueId());
				if (access == null || !access.hasAccess(Action.ITEM_USAGE))
					return;
			}
			if (ent.getMMOHorseData().isClaimed()) {
				ClaimedData cd = ent.getMMOHorseData().getClaimedData();
				if (ci.getValue() > 0 && cd.getStats().getHorseLevel() < SettingInt.MAXIMUM_HORSE_LEVEL.toInt()) {
					cd.getStats().addLevel(Stat.HORSE_LEVEL, (int) ci.getValue());
					MMOHorsesAPI.getUtils().randomParticles(Particle.FIREWORKS_SPARK, ent.getEyeLocation(), 10, 2.0D);
					e.getPlayer().playSound(e.getPlayer().getLocation(),
							MMOHorsesAPI.getUtils().getSound("UI_TOAST_CHALLENGE_COMPLETE", "ENTITY_PLAYER_LEVELUP"),
							1.0F, 1.0F);
					MessagesUtils.getMessageBuilder()
							.createMessage(
									MMOHorsesAPI.getUtils()
											.applyPrefix(MessageString.HORSELEVELUP.toString().replace("%level%",
													String.valueOf(cd.getStats().getHorseLevel()))))
							.sendMessage(e.getPlayer());
					ItemStacksUtils.reduceItemInMainHand(e.getPlayer());
				} else
					MessagesUtils.getMessageBuilder()
							.createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.HORSEMAXLEVEL))
							.sendMessage(e.getPlayer());
			}
		}
	},
	CATCHER {
		@Override
		public void event(PlayerInteractEvent e, CraftableItem ci, MMOHorse ent) {
			if (ent == null) {
				String type = MMOHorsesAPI.getEntityModule().getCatchersManager()
						.getType(ItemStacksUtils.getItemInMainHand(e.getPlayer()));;
				if (type != null) {
					MMOHorse ec = null;
					switch (type) {
					case "custom":
						e.setCancelled(true);
						CustomData data = MMOHorsesAPI.getCustomModule()
								.getCustomEntityData(MMOHorsesAPI.getEntityModule().getCatchersManager()
										.getCustom(ItemStacksUtils.getItemInMainHand(e.getPlayer())));
						if (data != null) {
							ClaimedData h = MMOHorsesAPI.getHorseModule().registerHorseObject(e.getPlayer(), true, data,
									e.getPlayer().getUniqueId());
							ec = MMOHorsesAPI.getEntityModule().getRegistry().spawnEntity(h.getData().getType(),
									e.getPlayer().getLocation());
							h.setMMOHorse(ec);
							if (SettingBoolean.LINKONSPAWN.toBoolean() && !h.getSettings().isLinked())
								h.getSettings().setLink(true);
							ItemStacksUtils.reduceItemInMainHand(e.getPlayer());
						}
						break;
					case "claimed":
						e.setCancelled(true);
						ClaimedData cd = MMOHorsesAPI.getHorseModule()
								.getHorseObject(MMOHorsesAPI.getEntityModule().getCatchersManager()
										.getId(ItemStacksUtils.getItemInMainHand(e.getPlayer())));
						if (cd != null && !MMOHorsesAPI.getHorseModule().isRegistered(cd)) {
							cd.getData().setOwner(e.getPlayer().getUniqueId());
							ItemStacksUtils.reduceItemInMainHand(e.getPlayer());
							ec = MMOHorsesAPI.getEntityModule().getRegistry().spawnEntity(cd.getData().getType(),
									(e.getClickedBlock() != null ? e.getClickedBlock().getLocation()
											: e.getPlayer().getLocation()).add(0.5D, 1D, 0.5D));
							if (ec != null) {
								cd.setMMOHorse(ec);
								if (SettingBoolean.LINKONSPAWN.toBoolean() && !cd.getSettings().isLinked())
									cd.getSettings().setLink(true);
							}
						} else
							ItemStacksUtils.reduceItemInMainHand(e.getPlayer());
						break;
					default:
						break;
					}
					if (ec != null) {
						MMOHorsesAPI.getUtils().randomParticles(Particle.SPELL_WITCH,
								ec.getLocation().add(0D, 0.5D, 0D), 20, 1d);
						e.getPlayer().playSound(ec.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1F, 1F);
					}
				}
			}
		}

		@Override
		public void event(PlayerInteractEntityEvent e, CraftableItem ci, MMOHorse ent) {
			e.setCancelled(true);
			if (!e.getPlayer().hasPermission("Horse.BypassOwnership") && ent.getMMOHorseData().isClaimed()) {
				UserAccess access = ent.getMMOHorseData().getClaimedData().getData()
						.getTrusted(e.getPlayer().getUniqueId());
				if (access == null || !access.hasAccess(Action.ITEM_USAGE))
					return;
			}
			e.getPlayer().getInventory().setItem(e.getPlayer().getInventory().getHeldItemSlot(),
					MMOHorsesAPI.getEntityModule().getCatchersManager().insertData(
							ItemStacksUtils.getItemInMainHand(e.getPlayer()), ent.getMMOHorseData().getClaimedData()));
			MMOHorsesAPI.getUtils().randomParticles(Particle.SMOKE_LARGE, ent.getLocation().add(0D, 0.5D, 0D), 10, 1D);
			ent.superDie();
			ClaimedData data = ent.getMMOHorseData().getClaimedData();
			MMOHorsesAPI.getHorseModule().unloadHorseObject(data);
			data.getSettings().setLink(true);
		}
	},
	NAMETAG {
		@Override
		public void event(PlayerInteractEntityEvent e, CraftableItem ci, MMOHorse ent) {
			e.setCancelled(true);
			if (!e.getPlayer().hasPermission("Horse.BypassOwnership") && ent.getMMOHorseData().isClaimed()) {
				UserAccess access = ent.getMMOHorseData().getClaimedData().getData()
						.getTrusted(e.getPlayer().getUniqueId());
				if (access == null || !access.hasAccess(Action.ITEM_USAGE))
					return;
			}
			ItemStack item = ItemStacksUtils.getItemInMainHand(e.getPlayer());
			if (MMOHorsesAPI.getUtils().isSame(e.getPlayer().getUniqueId(),
					ent.getMMOHorseData().getClaimedData().getData().getOwner())
					&& item.getItemMeta().hasDisplayName()) {
				ent.getMMOHorseData().getClaimedData().getData()
						.setName(MessagesUtils.color(item.getItemMeta().getDisplayName()));
				ItemStacksUtils.reduceItemInMainHand(e.getPlayer());
			}
		}
	};

	@Override
	public String toString() {
		String r = "";
		for (String s : this.name().split("_"))
			r += (r.isEmpty() ? "" : " ") + s.substring(0, 1) + s.substring(1).toLowerCase();
		return r;
	}

	public void event(PlayerInteractEvent e, CraftableItem ci, MMOHorse ent) {
		e.setCancelled(true);
	}

	public void event(PlayerInteractEvent e, CraftableItem ci) {
		event(e, ci, e.getPlayer().isInsideVehicle() ? MMOHorsesAPI.getUtils().toMMOHorse(e.getPlayer().getVehicle())
				: null);
	}

	public void event(PlayerInteractEntityEvent e, CraftableItem ci, MMOHorse ent) {
		e.setCancelled(true);
	}

	public void event(PlayerInteractEntityEvent e, CraftableItem ci) {
		event(e, ci, e.getPlayer().isInsideVehicle() ? MMOHorsesAPI.getUtils().toMMOHorse(e.getPlayer().getVehicle())
				: null);
	}
}
