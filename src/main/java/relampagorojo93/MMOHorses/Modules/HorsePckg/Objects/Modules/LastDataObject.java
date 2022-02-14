package relampagorojo93.MMOHorses.Modules.HorsePckg.Objects.Modules;

import java.sql.ResultSet;
import java.sql.SQLException;

import relampagorojo93.LibsCollection.Utils.Bukkit.TasksUtils;
import relampagorojo93.MMOHorses.API.MMOHorsesAPI;
import relampagorojo93.MMOHorses.Modules.HorsePckg.Objects.ClaimedData;

public class LastDataObject {
	private ClaimedData data;
	private double food = 0D, health = 0D;

	public LastDataObject(ClaimedData data, ResultSet set) throws SQLException {
		this.data = data;
		this.food = set.getDouble("last_food");
		this.health = set.getDouble("last_health");
	}

	public double getLastFood() {
		return food;
	}

	public void setLastFood(double food) {
		this.food = food;
	}

	public double getLastHealth() {
		return health;
	}

	public void setLastHealth(double health) {
		this.health = health;
	}
	
	public void updateDatabase() {
		TasksUtils.execute(MMOHorsesAPI.getPlugin(),
				() -> MMOHorsesAPI.getSQLModule().setLastHealth(data.getData().getId(), health), true);
		TasksUtils.execute(MMOHorsesAPI.getPlugin(),
				() -> MMOHorsesAPI.getSQLModule().setLastFood(data.getData().getId(), food), true);
	}
}
