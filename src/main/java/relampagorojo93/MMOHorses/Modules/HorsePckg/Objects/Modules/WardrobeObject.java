package relampagorojo93.MMOHorses.Modules.HorsePckg.Objects.Modules;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.inventory.ItemStack;

import relampagorojo93.LibsCollection.Utils.Bukkit.ItemStacksUtils;
import relampagorojo93.LibsCollection.Utils.Bukkit.TasksUtils;
import relampagorojo93.MMOHorses.API.MMOHorsesAPI;
import relampagorojo93.MMOHorses.Modules.HorsePckg.Objects.ClaimedData;

public class WardrobeObject {
	private ClaimedData data;
	private ItemStack saddle, body, accessory;

	public WardrobeObject(ClaimedData data, ResultSet set) throws SQLException {
		this.data = data;
		this.saddle = (set.getBytes("saddle") != null) ? ItemStacksUtils.itemsParse(set.getBytes("saddle"))[0]
				: null;
		this.body = (set.getBytes("body") != null) ? ItemStacksUtils.itemsParse(set.getBytes("body"))[0] : null;
		this.accessory = (set.getBytes("accessory") != null)
				? ItemStacksUtils.itemsParse(set.getBytes("accessory"))[0]
				: null;
	}

	public ItemStack getBody() {
		return body;
	}

	public void setBody(ItemStack body) {
		this.body = body;
		TasksUtils.execute(MMOHorsesAPI.getPlugin(),
				() -> MMOHorsesAPI.getSQLModule().setCosmeticBody(data.getData().getId(), body), true);
		if (data.getMMOHorse() != null)
			data.getMMOHorse().getMMOHorseData().updateBody();
	}

	public ItemStack getSaddle() {
		return saddle;
	}

	public void setSaddle(ItemStack saddle) {
		this.saddle = saddle;
		TasksUtils.execute(MMOHorsesAPI.getPlugin(),
				() -> MMOHorsesAPI.getSQLModule().setCosmeticSaddle(data.getData().getId(), saddle), true);
		if (data.getMMOHorse() != null)
			data.getMMOHorse().getMMOHorseData().updateSaddle();
	}

	public ItemStack getAccessory() {
		return accessory;
	}

	public void setAccessory(ItemStack accessory) {
		this.accessory = accessory;
		TasksUtils.execute(MMOHorsesAPI.getPlugin(),
				() -> MMOHorsesAPI.getSQLModule().setCosmeticAccessory(data.getData().getId(), accessory), true);
		if (data.getMMOHorse() != null)
			data.getMMOHorse().getMMOHorseData().updateAccessory();
	}
	
}
