package relampagorojo93.MMOHorses.Modules.HorsePckg.Objects.Modules;

import java.sql.ResultSet;
import java.sql.SQLException;

import relampagorojo93.LibsCollection.Utils.Bukkit.TasksUtils;
import relampagorojo93.MMOHorses.API.MMOHorsesAPI;
import relampagorojo93.MMOHorses.Modules.HorsePckg.Objects.ClaimedData;

public class SettingsObject {
	private ClaimedData data;
	private boolean nametag, link, healthtag, blockspeedunmount, follow, _public;

	public SettingsObject(ClaimedData data, ResultSet set) throws SQLException {
		this.data = data;
		this.nametag = set.getBoolean("nametag");
		this.link = set.getBoolean("link");
		this.healthtag = set.getBoolean("healthtag");
		this.blockspeedunmount = set.getBoolean("blockspeedunmount");
		this.follow = set.getBoolean("follow");
		this.follow = set.getBoolean("public");
	}

	public boolean blockSpeedOnUnmount() {
		return blockspeedunmount;
	}

	public boolean isNameTagVisible() {
		return nametag;
	}

	public boolean isHealthTagVisible() {
		return healthtag;
	}

	public boolean isLinked() {
		return link;
	}

	public boolean isFollowOwner() {
		return follow;
	}

	public boolean isPublic() {
		return _public;
	}

	public void setHealthTagVisible(boolean healthtag) {
		this.healthtag = healthtag;
		TasksUtils.executeOnAsync(MMOHorsesAPI.getPlugin(),
				() -> MMOHorsesAPI.getSQLModule().setHealthTagVisible(data.getData().getId(), healthtag));
	}

	public void blockSpeedOnUnmount(boolean blockspeedunmount) {
		this.blockspeedunmount = blockspeedunmount;
		TasksUtils.executeOnAsync(MMOHorsesAPI.getPlugin(),
				() -> MMOHorsesAPI.getSQLModule().setBlockSpeedOnUnmount(data.getData().getId(), blockspeedunmount));
	}

	public void setNameTagVisible(boolean nametag) {
		this.nametag = nametag;
		TasksUtils.executeOnAsync(MMOHorsesAPI.getPlugin(),
				() -> MMOHorsesAPI.getSQLModule().setNameTagVisible(data.getData().getId(), nametag));
	}

	public void setLink(boolean link) {
		this.link = link;
		TasksUtils.executeOnAsync(MMOHorsesAPI.getPlugin(),
				() -> MMOHorsesAPI.getSQLModule().setLink(data.getData().getId(), link));
	}

	public void setFollowOwner(boolean follow) {
		this.follow = follow;
		TasksUtils.executeOnAsync(MMOHorsesAPI.getPlugin(),
				() -> MMOHorsesAPI.getSQLModule().setFollowOwner(data.getData().getId(), follow));
	}

	public void setPublic(boolean _public) {
		this._public = _public;
		TasksUtils.executeOnAsync(MMOHorsesAPI.getPlugin(),
				() -> MMOHorsesAPI.getSQLModule().setPublic(data.getData().getId(), _public));
	}
}
