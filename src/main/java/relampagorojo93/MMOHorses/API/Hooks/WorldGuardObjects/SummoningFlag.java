package relampagorojo93.MMOHorses.API.Hooks.WorldGuardObjects;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.StateFlag.State;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;

import relampagorojo93.LibsCollection.SpigotDebug.Data.DebugAlertData;
import relampagorojo93.MMOHorses.API.MMOHorsesAPI;
import relampagorojo93.MMOHorses.API.Hooks.WorldGuardAPI;

public class SummoningFlag {
	StateFlag flag;

	public SummoningFlag() {
		FlagRegistry fr;
		try {
			Object inst = Class.forName("com.sk89q.worldguard.WorldGuard").getMethod("getInstance").invoke(null);
			fr = (FlagRegistry) inst.getClass().getMethod("getFlagRegistry").invoke(inst);
		} catch (Exception e) {
			try {
				fr = (FlagRegistry) WorldGuardAPI.getWorldGuardPlugin().getClass().getMethod("getFlagRegistry")
						.invoke(WorldGuardAPI.getWorldGuardPlugin());
			} catch (Exception e2) {
				return;
			}
		}
		try {
			StateFlag f = new StateFlag("equestrian-summon", true);
			fr.register(f);
			flag = f;
		} catch (FlagConflictException e) {
			MMOHorsesAPI.getDebugController().addDebugData(new DebugAlertData("Seems like another flag is conflicting with MMOHorses' equestrian-summon flag."));
		}
	}

	public StateFlag getFlag() {
		return flag;
	}

	public boolean flagState(Player pl) {
		if (flag == null || pl.hasPermission("horse.summonbypass"))
			return true;
		Object regioncontainer = WorldGuardAPI.getRegionContainer();
		ApplicableRegionSet set;
		try {
			Class<?> adapter = Class.forName("com.sk89q.worldedit.bukkit.BukkitAdapter");
			Object regions = regioncontainer.getClass()
					.getMethod("get", Class.forName("com.sk89q.worldedit.world.World"))
					.invoke(regioncontainer, adapter.getMethod("adapt", World.class).invoke(null, pl.getWorld()));
			set = (ApplicableRegionSet) regions.getClass()
					.getMethod("getApplicableRegions", Class.forName("com.sk89q.worldedit.math.BlockVector3"))
					.invoke(regions, adapter.getMethod("asBlockVector", Location.class).invoke(null, pl.getLocation()));
		} catch (Exception e) {
			try {
				e.printStackTrace();
				Object regions = regioncontainer.getClass().getMethod("get", World.class).invoke(regioncontainer,
						pl.getWorld());
				set = (ApplicableRegionSet) regions.getClass().getMethod("getApplicableRegions", Location.class)
						.invoke(regions, pl.getLocation());
			} catch (Exception e2) {
				e2.printStackTrace();
				return true;
			}
		}
		State state = set.queryValue(WorldGuardAPI.getWorldGuardPlugin().wrapPlayer(pl), flag);
		if (state != null && state == State.DENY)
			return false;
		return true;
	}
}
