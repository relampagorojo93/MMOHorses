package relampagorojo93.MMOHorses.Modules.HorsePckg.Objects.Modules;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import relampagorojo93.LibsCollection.Utils.Bukkit.TasksUtils;
import relampagorojo93.MMOHorses.API.MMOHorsesAPI;
import relampagorojo93.MMOHorses.Enums.Colour;
import relampagorojo93.MMOHorses.Enums.Gender;
import relampagorojo93.MMOHorses.Enums.Marking;
import relampagorojo93.MMOHorses.Enums.Type;
import relampagorojo93.MMOHorses.Modules.HorsePckg.Objects.ClaimedData;
import relampagorojo93.MMOHorses.Modules.HorsePckg.Objects.UserAccess;

public class DataObject {
	private ClaimedData data;
	private int id;
	private Type type;
	private String name;
	private UUID owner;
	private Gender gender;
	private Colour colour;
	private Marking marking;
	private double health, jump, speed, price;
	private List<UserAccess> trusted;
	private Date lifetime, deathtime;
	
	public DataObject(ClaimedData data, ResultSet set) throws SQLException {
		this.data = data;
		this.id = set.getInt("id");
		this.type = Type.getById(set.getString("type").charAt(0));
		this.owner = UUID.fromString(set.getString("owner"));
		this.name = set.getString("name");
		this.gender = Gender.getById(set.getString("gender").charAt(0));
		this.price = set.getDouble("price");
		this.colour = Colour.getById(set.getString("colour").charAt(0));
		this.marking = Marking.getById(set.getString("marking").charAt(0));
		this.health = set.getDouble("health");
		this.jump = set.getDouble("jump");
		this.speed = set.getDouble("speed");
		try {
			try {
				lifetime = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(set.getString("lifetime"));
			} catch (SQLException e) {
				throw new Exception();
			}
		} catch (Exception e) {
			lifetime = new Date(set.getTimestamp("lifetime").getTime());
		}
		try {
			try {
				deathtime = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(set.getString("deathtime"));
			} catch (SQLException e) {
				throw new Exception();
			}
		} catch (Exception e) {
			deathtime = new Date(set.getTimestamp("deathtime").getTime());
		}
		ResultSet trustset = MMOHorsesAPI.getSQLModule().getTrusteds(id);
		trusted = new ArrayList<>();
		while (trustset.next()) {
			trusted.add(new UserAccess(id, UUID.fromString(trustset.getString(2)), trustset.getBoolean(3),
					trustset.getBoolean(4), trustset.getBoolean(5), trustset.getBoolean(6), trustset.getBoolean(7),
					trustset.getBoolean(8), trustset.getBoolean(9)));
		}
	}

	////////////////////////////////////////////////////////////////////////
	//
	// Final data
	//
	////////////////////////////////////////////////////////////////////////
	public int getId() {
		return this.id;
	}

	public Date getLifeTime() {
		return this.lifetime;
	}

	public Type getType() {
		return this.type;
	}

	////////////////////////////////////////////////////////////////////////
	//
	// Variable data
	//
	////////////////////////////////////////////////////////////////////////
	
	public UUID getOwner() {
		return this.owner;
	}

	public void setOwner(UUID owner) {
		MMOHorsesAPI.getHorseModule().unregisterHorseObject(data);
		this.owner = owner;
		MMOHorsesAPI.getHorseModule().registerHorseObject(data);
		TasksUtils.execute(MMOHorsesAPI.getPlugin(), () -> MMOHorsesAPI.getSQLModule().setOwner(this.id, owner), true);
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
		TasksUtils.execute(MMOHorsesAPI.getPlugin(), () -> MMOHorsesAPI.getSQLModule().setName(this.id, name), true);
	}

	public Gender getGender() {
		return this.gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
		TasksUtils.execute(MMOHorsesAPI.getPlugin(), () -> MMOHorsesAPI.getSQLModule().setGender(this.id, gender), true);
	}

	public Colour getColour() {
		return this.colour;
	}

	public void setColour(Colour colour) {
		this.colour = colour;
		TasksUtils.execute(MMOHorsesAPI.getPlugin(), () -> MMOHorsesAPI.getSQLModule().setColour(this.id, colour), true);
	}

	public Marking getMarking() {
		return this.marking;
	}

	public void setMarking(Marking marking) {
		this.marking = marking;
		TasksUtils.execute(MMOHorsesAPI.getPlugin(), () -> MMOHorsesAPI.getSQLModule().setMarking(this.id, marking), true);
	}

	public double getHealth() {
		return this.health;
	}

	public void setHealth(double health) {
		this.health = health;
		TasksUtils.execute(MMOHorsesAPI.getPlugin(), () -> MMOHorsesAPI.getSQLModule().setHealth(this.id, health), true);
	}

	public double getJump() {
		return this.jump;
	}

	public void setJump(double jump) {
		this.jump = jump;
		TasksUtils.execute(MMOHorsesAPI.getPlugin(), () -> MMOHorsesAPI.getSQLModule().setJump(this.id, jump), true);
	}

	public double getSpeed() {
		return this.speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
		TasksUtils.execute(MMOHorsesAPI.getPlugin(), () -> MMOHorsesAPI.getSQLModule().setSpeed(this.id, speed), true);
	}

	public double getPrice() {
		return this.price;
	}

	public void setPrice(double price) {
		this.price = price;
		TasksUtils.execute(MMOHorsesAPI.getPlugin(), () -> MMOHorsesAPI.getSQLModule().setPrice(this.id, price), true);
	}

	public Date getDeathTime() {
		return this.deathtime;
	}

	public void setDeathTime() {
		this.deathtime = new Date(System.currentTimeMillis());
		TasksUtils.execute(MMOHorsesAPI.getPlugin(),
				() -> MMOHorsesAPI.getSQLModule().setDeathTime(this.id, deathtime.getTime()), true);
	}
	
	private UserAccess owneraccess = new UserAccess(id, owner, true);

	public List<UserAccess> getTrusteds() {
		return this.trusted;
	}

	public UserAccess getTrusted(UUID uuid) {
		if (owner.compareTo(uuid) == 0)
			return owneraccess;
		if (data.getSettings().isPublic())
			return new UserAccess(id, uuid);
		for (UserAccess user : trusted)
			if (user.getUniqueID().compareTo(uuid) == 0)
				return user;
		return null;
	}

	public void registerTrusted(UserAccess user) {
		this.trusted.add(user);
		TasksUtils.execute(MMOHorsesAPI.getPlugin(),
				() -> MMOHorsesAPI.getSQLModule().registerTrusted(this.id, user.getUniqueID()), true);
	}

	public void unregisterTrusted(UserAccess user) {
		this.trusted.remove(user);
		TasksUtils.execute(MMOHorsesAPI.getPlugin(),
				() -> MMOHorsesAPI.getSQLModule().unregisterTrusted(this.id, user.getUniqueID()), true);
	}

	public void unregisterTrusteds() {
		this.trusted.clear();
		TasksUtils.execute(MMOHorsesAPI.getPlugin(), () -> MMOHorsesAPI.getSQLModule().unregisterTrusteds(this.id), true);
	}
}