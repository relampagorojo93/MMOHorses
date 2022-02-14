package relampagorojo93.MMOHorses.Modules.EntityPckg.Interfaces.Managers;

import org.bukkit.Location;

import relampagorojo93.MMOHorses.Enums.Type;
import relampagorojo93.MMOHorses.Modules.EntityPckg.Interfaces.Objects.MMOHorse;

public interface RegistryManager {

	public abstract void register();

	public abstract MMOHorse spawnEntity(Type type, Location l);
	
}
