package relampagorojo93.MMOHorses.Bukkit.Events;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import relampagorojo93.LibsCollection.Utils.Bukkit.TasksUtils;
import relampagorojo93.MMOHorses.API.MMOHorsesAPI;
import relampagorojo93.MMOHorses.Bukkit.Events.ChatEventsObjects.InputData;

public class ChatEvents implements Listener {
	private static HashMap<UUID,InputData> inputs = new HashMap<>();
	public ChatEvents() { inputs.clear(); }
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent e) {
		if (inputs.containsKey(e.getPlayer().getUniqueId())) inputs.remove(e.getPlayer().getUniqueId());
	}
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		if (inputs.containsKey(e.getPlayer().getUniqueId())) {
			e.setCancelled(true);
			TasksUtils.execute(MMOHorsesAPI.getPlugin(), () -> {
				inputs.remove(e.getPlayer().getUniqueId()).input(e.getMessage());
			}, false);
		}
	}
	public static boolean isRegistered(UUID uuid) { return inputs.containsKey(uuid); }
	public static void register(InputData input) {
		if (inputs.containsKey(input.getPlayer().getUniqueId())) inputs.remove(input.getPlayer().getUniqueId());
		inputs.put(input.getPlayer().getUniqueId(), input);
	}
}
