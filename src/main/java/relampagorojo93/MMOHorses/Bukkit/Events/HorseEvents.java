package relampagorojo93.MMOHorses.Bukkit.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPortalEvent;

import relampagorojo93.MMOHorses.API.MMOHorsesAPI;

public class HorseEvents implements Listener {
	
	@EventHandler
	public void onPortal(EntityPortalEvent e) {
		if(MMOHorsesAPI.getUtils().toMMOHorse(e.getEntity()) != null) e.setCancelled(true);
	}

}