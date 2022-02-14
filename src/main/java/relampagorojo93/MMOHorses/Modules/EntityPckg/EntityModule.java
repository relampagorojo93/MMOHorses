package relampagorojo93.MMOHorses.Modules.EntityPckg;

import org.bukkit.entity.Entity;

import relampagorojo93.MMOHorses.Modules.EntityPckg.Interfaces.Managers.CatchersManager;
import relampagorojo93.MMOHorses.Modules.EntityPckg.Interfaces.Managers.RegistryManager;
import relampagorojo93.MMOHorses.Modules.EntityPckg.Interfaces.Objects.MMOHorse;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingBoolean;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingList;
import relampagorojo93.LibsCollection.SpigotPlugin.LoadOn;
import relampagorojo93.LibsCollection.SpigotPlugin.PluginModule;
import relampagorojo93.MMOHorses.API.MMOHorsesAPI;
import relampagorojo93.MMOHorses.API.Hooks.CitizensAPI;
import relampagorojo93.MMOHorses.API.Hooks.MythicMobsAPI;

public class EntityModule extends PluginModule {

	@Override
	public boolean load() {
		try {
			(this.registry = (RegistryManager) Class.forName(
					this.getClass().getPackage().getName() + "." + MMOHorsesAPI.getUtils().VERSION + ".Custom.RegistryCustom")
					.getDeclaredConstructor().newInstance()).register();
			this.catchers = (CatchersManager) Class.forName(
					this.getClass().getPackage().getName() + "." + MMOHorsesAPI.getUtils().VERSION + ".Custom.CatchersCustom")
					.getDeclaredConstructor().newInstance();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean unload() {
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
	
	private RegistryManager registry;
	private CatchersManager catchers;

	public RegistryManager getRegistry() {
		return this.registry;
	}
	
	public CatchersManager getCatchersManager() {
		return this.catchers;
	}

	public void checkEntity(Entity ent) {
		if (ent.isDead())
			return;
		MMOHorse he = MMOHorsesAPI.getUtils().toMMOHorse(ent);
		if (he == null) 
			return;
		if (he.isCustom())
			return;
		if ((SettingList.SELECTED_WORLDS_FOR_REPLACE.toList().isEmpty()
				&& SettingList.IGNORE_WORLDS_FOR_REPLACE.toList()
				.contains(ent.getWorld().getName()))
		|| (!SettingList.SELECTED_WORLDS_FOR_REPLACE.toList().isEmpty()
				&& !SettingList.SELECTED_WORLDS_FOR_REPLACE.toList().contains(
						ent.getWorld().getName())))
			return;
		if (CitizensAPI.isHooked() && CitizensAPI.isNPC(ent))
			return;
		if (MythicMobsAPI.isHooked() && MythicMobsAPI.isMythicMob(ent))
			return;
		if (ent.hasMetadata("PinataParty"))
			return;
		if (!SettingBoolean.valueOf("SUPPORT_" + he.getType().name()).toBoolean())
			return;
		he.setAsCustom(true);
	}
}