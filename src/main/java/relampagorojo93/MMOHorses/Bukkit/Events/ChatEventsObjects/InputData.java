package relampagorojo93.MMOHorses.Bukkit.Events.ChatEventsObjects;

import org.bukkit.entity.Player;

public abstract class InputData {
	private Player player;
	private Task task;
	public InputData(Player player, Task task) { this.player = player; this.task = task; }
	public Player getPlayer() { return player; }
	public void input(String result) { task.method(this, result); }
	public interface Task {
		abstract void method(InputData data, String result);
	}
}
