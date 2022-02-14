package relampagorojo93.MMOHorses.Modules.SQLPckg;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import relampagorojo93.LibsCollection.SpigotMessages.MessagesUtils;
import relampagorojo93.LibsCollection.SpigotPlugin.LoadOn;
import relampagorojo93.LibsCollection.SpigotPlugin.PluginModule;
import relampagorojo93.LibsCollection.Utils.Bukkit.ItemStacksUtils;
import relampagorojo93.LibsCollection.Utils.Shared.SQL.SQLObject;
import relampagorojo93.LibsCollection.Utils.Shared.SQL.Abstracts.ConnectionData;
import relampagorojo93.LibsCollection.Utils.Shared.SQL.Enums.ConditionType;
import relampagorojo93.LibsCollection.Utils.Shared.SQL.Enums.SQLType;
import relampagorojo93.LibsCollection.Utils.Shared.SQL.Objects.Data;
import relampagorojo93.LibsCollection.Utils.Shared.SQL.Objects.Conditions.Condition;
import relampagorojo93.LibsCollection.Utils.Shared.SQL.Objects.ConnectionData.MySQLConnectionData;
import relampagorojo93.LibsCollection.Utils.Shared.SQL.Objects.ConnectionData.SQLiteConnectionData;
import relampagorojo93.LibsCollection.Utils.Shared.SQL.Objects.DataModel.Column;
import relampagorojo93.LibsCollection.Utils.Shared.SQL.Objects.DataModel.Database;
import relampagorojo93.LibsCollection.Utils.Shared.SQL.Objects.DataModel.Table;
import relampagorojo93.LibsCollection.Utils.Shared.SQL.Objects.DataModel.Constraints.ForeignConstraint;
import relampagorojo93.LibsCollection.Utils.Shared.SQL.Objects.DataModel.Constraints.UniqueConstraint;
import relampagorojo93.LibsCollection.Utils.Shared.SQL.Objects.DataModel.Joins.LeftJoin;
import relampagorojo93.LibsCollection.Utils.Shared.SQL.Objects.DataModel.SubDatabases.MySQLDatabase;
import relampagorojo93.LibsCollection.Utils.Shared.SQL.Objects.DataModel.SubDatabases.SQLiteDatabase;
import relampagorojo93.MMOHorses.API.MMOHorsesAPI;
import relampagorojo93.MMOHorses.Enums.Colour;
import relampagorojo93.MMOHorses.Enums.Gender;
import relampagorojo93.MMOHorses.Enums.ItemType;
import relampagorojo93.MMOHorses.Enums.Marking;
import relampagorojo93.MMOHorses.Enums.Action;
import relampagorojo93.MMOHorses.Enums.Stat;
import relampagorojo93.MMOHorses.Enums.Type;
import relampagorojo93.MMOHorses.Modules.CraftPckg.Objects.CraftableItem;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingString;

public class SQLModule extends PluginModule {

	@Override
	public boolean load() {
		return true;
	}

	@Override
	public boolean unload() {
		close();
		return true;
	}

	@Override
	public LoadOn loadOn() {
		return LoadOn.BEFORE_LOAD;
	}

	@Override
	public boolean optional() {
		return false;
	}

	@Override
	public boolean allowReload() {
		return false;
	}

	protected final SQLObject sql = new SQLObject();
	protected String prefix = "";
	protected Database database;

	public SQLType getType() {
		return sql.getType();
	}

	public boolean isConnected() {
		return this.sql.isConnected();
	}

	public boolean connect(ConnectionData data) {
		MessagesUtils.getMessageBuilder()
				.createMessage(MMOHorsesAPI.getUtils().applyPrefix("Connecting to database..."))
				.sendMessage(Bukkit.getConsoleSender());
		if (!sql.request(data))
			return false;
		if (data.getType() == SQLType.MYSQL)
			database = new MySQLDatabase(SettingString.TABLEPREFIX.toString());
		else
			database = new SQLiteDatabase();
		if (!generateDatabase(this.database).updateTables(String.valueOf(MMOHorsesAPI.getSQLVersion()), sql))
			return false;
		if (data.getType() == SQLType.MYSQL) {
			MySQLConnectionData mysqldata = (MySQLConnectionData) data;
			MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils()
							.applyPrefix("Connected successfully to " + mysqldata.getHost() + ":" + mysqldata.getPort()
									+ " for database " + mysqldata.getDatabase() + " with username "
									+ mysqldata.getUsername() + ".")).sendMessage(Bukkit.getConsoleSender());
		} else if (data.getType() == SQLType.SQLITE) {
			SQLiteConnectionData sqlitedata = (SQLiteConnectionData) data;
			MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils()
					.applyPrefix("Connected successfully to " + sqlitedata.toString() + " file.")).sendMessage(Bukkit.getConsoleSender());
		}
		return true;
	}

	private Database generateDatabase(Database database) {
		Table t;
		database.addTable((t = new Table(database, "horses")).addColumns(
				new Column(t, "id", "INTEGER", Types.INTEGER).setPrimary(true).setUnsigned(true).setAutoIncrement(true)
						.setNotNull(true),
				new Column(t, "type", "CHAR(1)", Types.CHAR).setNotNull(true),
				new Column(t, "owner", "CHAR(36)", Types.CHAR).setNotNull(true),
				new Column(t, "name", "VARCHAR(32)", Types.VARCHAR).setNotNull(true),
				new Column(t, "colour", "CHAR(1)", Types.CHAR).setNotNull(true),
				new Column(t, "marking", "CHAR(1)", Types.CHAR).setNotNull(true),
				new Column(t, "health", "DOUBLE", Types.DOUBLE, "20").setNotNull(true),
				new Column(t, "jump", "DOUBLE", Types.DOUBLE).setNotNull(true),
				new Column(t, "speed", "DOUBLE", Types.DOUBLE).setNotNull(true)));
		database.addTable((t = new Table(database, "mmo_data"))
				.addColumns(
						new Column(t, "horses_id", "INTEGER", Types.INTEGER).setPrimary(true).setUnsigned(true)
								.setNotNull(true),
						new Column(t, "gender", "CHAR(1)", 1).setNotNull(true),
						new Column(t, "price", "DOUBLE", Types.DOUBLE, "0").setNotNull(true),
						new Column(t, "lifetime", "TIMESTAMP", Types.TIMESTAMP, "CURRENT_TIMESTAMP").setNotNull(true),
						new Column(t, "deathtime", "TIMESTAMP", Types.TIMESTAMP, "'2000-01-01 00:00:00'")
								.setNotNull(true))
				.addForeignConstraint(new ForeignConstraint(Arrays.asList(t.getColumn("horses_id")),
						Arrays.asList(database.getTable("horses").getColumn("id")))));
		database.addTable((t = new Table(database, "last_data"))
				.addColumns(
						new Column(t, "horses_id", "INTEGER", Types.INTEGER).setPrimary(true).setUnsigned(true)
								.setAutoIncrement(true).setNotNull(true),
						new Column(t, "last_health", "DOUBLE", Types.DOUBLE, "0.0").setNotNull(true),
						new Column(t, "last_food", "DOUBLE(10,9)", Types.DOUBLE, "0.0").setNotNull(true))
				.addForeignConstraint(new ForeignConstraint(Arrays.asList(t.getColumn("horses_id")),
						Arrays.asList(database.getTable("horses").getColumn("id")))));
		database.addTable((t = new Table(database, "stats"))
				.addColumns(
						new Column(t, "horses_id", "INTEGER", Types.INTEGER).setPrimary(true).setUnsigned(true)
								.setNotNull(true),
						new Column(t, "stat", "TINYINT", Types.TINYINT).setPrimary(true).setNotNull(true),
						new Column(t, "level", "INTEGER", Types.INTEGER, "1").setUnsigned(true).setNotNull(true))
				.addForeignConstraint(new ForeignConstraint(Arrays.asList(t.getColumn("horses_id")),
						Arrays.asList(database.getTable("horses").getColumn("id")))));
		database.addTable((t = new Table(database, "settings"))
				.addColumns(
						new Column(t, "horses_id", "INTEGER", Types.INTEGER).setPrimary(true).setUnsigned(true)
								.setNotNull(true),
						new Column(t, "nametag", "BOOLEAN", Types.BOOLEAN, "0").setNotNull(true),
						new Column(t, "healthtag", "BOOLEAN", Types.BOOLEAN, "0").setNotNull(true),
						new Column(t, "follow", "BOOLEAN", Types.BOOLEAN, "0").setNotNull(true),
						new Column(t, "link", "BOOLEAN", Types.BOOLEAN, "0").setNotNull(true),
						new Column(t, "blockspeedunmount", "BOOLEAN", Types.BOOLEAN, "0").setNotNull(true),
						new Column(t, "public", "BOOLEAN", Types.BOOLEAN, "0").setNotNull(true))
				.addForeignConstraint(new ForeignConstraint(Arrays.asList(t.getColumn("horses_id")),
						Arrays.asList(database.getTable("horses").getColumn("id")))));
		database.addTable((t = new Table(database, "trust"))
				.addColumns(
						new Column(t, "horses_id", "INTEGER", Types.INTEGER).setPrimary(true).setUnsigned(true)
								.setNotNull(true),
						new Column(t, "player_uuid", "CHAR(36)", Types.CHAR).setPrimary(true).setNotNull(true),
						new Column(t, "open_upgrades", "BOOLEAN", Types.BOOLEAN, "0").setNotNull(true),
						new Column(t, "open_virtualchest", "BOOLEAN", Types.BOOLEAN, "0").setNotNull(true),
						new Column(t, "open_settings", "BOOLEAN", Types.BOOLEAN, "0").setNotNull(true),
						new Column(t, "open_trusting", "BOOLEAN", Types.BOOLEAN, "0").setNotNull(true),
						new Column(t, "open_wardrobe", "BOOLEAN", Types.BOOLEAN, "0").setNotNull(true),
						new Column(t, "change_name", "BOOLEAN", Types.BOOLEAN, "0").setNotNull(true),
						new Column(t, "item_usage", "BOOLEAN", Types.BOOLEAN, "0").setNotNull(true))
				.addForeignConstraint(new ForeignConstraint(Arrays.asList(t.getColumn("horses_id")),
						Arrays.asList(database.getTable("horses").getColumn("id")))));
		database.addTable((t = new Table(database, "items"))
				.addColumns(
						new Column(t, "id", "INTEGER", Types.INTEGER).setPrimary(true).setUnsigned(true)
								.setAutoIncrement(true).setNotNull(true),
						new Column(t, "name", "VARCHAR(32)", Types.VARCHAR).setNotNull(true),
						new Column(t, "result", "BLOB", Types.BLOB).setNotNull(true),
						new Column(t, "recipe", "MEDIUMBLOB", Types.BLOB).setNotNull(true),
						new Column(t, "item_type", "TINYINT", Types.TINYINT).setUnsigned(true).setNotNull(true),
						new Column(t, "min_level", "TINYINT", Types.TINYINT).setUnsigned(true).setNotNull(true),
						new Column(t, "max_level", "TINYINT", Types.TINYINT, "100").setUnsigned(true).setNotNull(true),
						new Column(t, "value", "FLOAT", Types.FLOAT).setUnsigned(true).setNotNull(true))
				.addUniqueConstraint(new UniqueConstraint(t.getColumn("name"))));
		database.addTable((t = new Table(database, "trail_items"))
				.addColumns(
						new Column(t, "items_id", "INTEGER", Types.INTEGER).setPrimary(true).setUnsigned(true)
								.setAutoIncrement(true),
						new Column(t, "trail_type", "TINYINT", Types.TINYINT).setNotNull(true),
						new Column(t, "particle_type", "TINYINT", Types.TINYINT).setNotNull(true))
				.addForeignConstraint(new ForeignConstraint(Arrays.asList(t.getColumn("items_id")),
						Arrays.asList(database.getTable("items").getColumn("id")))));
		database.addTable((t = new Table(database, "upgrades"))
				.addColumns(
						new Column(t, "horses_id", "INTEGER", Types.INTEGER).setPrimary(true).setUnsigned(true)
								.setNotNull(true),
						new Column(t, "speed_item", "INTEGER", Types.INTEGER),
						new Column(t, "jump_item", "INTEGER", Types.INTEGER),
						new Column(t, "health_item", "INTEGER", Types.INTEGER),
						new Column(t, "chest_item", "INTEGER", Types.INTEGER),
						new Column(t, "saddle_item", "INTEGER", Types.INTEGER),
						new Column(t, "armor_item", "INTEGER", Types.INTEGER),
						new Column(t, "skill_item", "INTEGER", Types.INTEGER))
				.addForeignConstraint(new ForeignConstraint(Arrays.asList(t.getColumn("horses_id")),
						Arrays.asList(database.getTable("horses").getColumn("id")))));
		database.addTable((t = new Table(database, "inventories"))
				.addColumns(new Column(t, "horses_id", "INTEGER", Types.INTEGER).setPrimary(true).setUnsigned(true)
						.setNotNull(true), new Column(t, "inventory", "MEDIUMBLOB", Types.BLOB))
				.addForeignConstraint(new ForeignConstraint(Arrays.asList(t.getColumn("horses_id")),
						Arrays.asList(database.getTable("horses").getColumn("id")))));
		database.addTable((t = new Table(database, "cosmetics"))
				.addColumns(
						new Column(t, "horses_id", "INTEGER", Types.INTEGER).setPrimary(true).setUnsigned(true)
								.setNotNull(true),
						new Column(t, "saddle", "BLOB", Types.BLOB), new Column(t, "body", "BLOB", Types.BLOB),
						new Column(t, "accessory", "BLOB", Types.BLOB))
				.addForeignConstraint(new ForeignConstraint(Arrays.asList(t.getColumn("horses_id")),
						Arrays.asList(database.getTable("horses").getColumn("id")))));
		return database;
	}

	public void close() {
		this.sql.close();
	}

	public boolean deleteHorses() {
		return this.database.delete(this.sql, this.database.getTable("horses"));
	}

	public boolean deleteItems() {
		return this.database.delete(this.sql, this.database.getTable("items"));
	}

	public boolean unlinkHorses() {
		Table table = this.database.getTable("settings");
		HashMap<Column, Data> values = new HashMap<>();
		values.put(table.getColumn("link"), new Data(Types.BOOLEAN, false));
		return this.database.update(this.sql, table, values);
	}

	public ResultSet getAllHorses() {
		Table horses = this.database.getTable("horses");
		Column id = horses.getColumn("id");
		return this.database.select(this.sql,
				Arrays.asList(horses, this.database.getTable("mmo_data"), this.database.getTable("settings"),
						this.database.getTable("upgrades"), this.database.getTable("inventories"),
						this.database.getTable("cosmetics")),
				new ArrayList<>(),
				Arrays.asList(new LeftJoin(this.database.getTable("mmo_data").getColumn("horses_id"), id),
						new LeftJoin(this.database.getTable("settings").getColumn("horses_id"), id),
						new LeftJoin(this.database.getTable("upgrades").getColumn("horses_id"), id),
						new LeftJoin(this.database.getTable("inventories").getColumn("horses_id"), id),
						new LeftJoin(this.database.getTable("cosmetics").getColumn("horses_id"), id)));
	}

	public ResultSet getAllHorses(UUID uuid) {
		Table horses = this.database.getTable("horses");
		Column id = horses.getColumn("id");
		return this.database
				.select(this.sql,
						Arrays.asList(horses, this.database.getTable("mmo_data"), this.database.getTable("last_data"),
								this.database.getTable("settings"), this.database.getTable("upgrades"),
								this.database.getTable("inventories"), this.database.getTable("cosmetics")),
						new ArrayList<>(),
						Arrays.asList(new LeftJoin(this.database.getTable("mmo_data").getColumn("horses_id"), id),
								new LeftJoin(this.database.getTable("last_data").getColumn("horses_id"), id),
								new LeftJoin(this.database.getTable("settings").getColumn("horses_id"), id),
								new LeftJoin(this.database.getTable("upgrades").getColumn("horses_id"), id),
								new LeftJoin(this.database.getTable("inventories").getColumn("horses_id"), id),
								new LeftJoin(this.database.getTable("cosmetics").getColumn("horses_id"), id)),
						new Condition(horses.getColumn("owner"), new Data(1, uuid.toString()), ConditionType.EQUAL));
	}

	public ResultSet getItems() {
		Table items = this.database.getTable("items");
		Column id = items.getColumn("id");
		return this.database.select(this.sql, Arrays.asList(items, this.database.getTable("trail_items")),
				new ArrayList<>(),
				Arrays.asList(new LeftJoin(this.database.getTable("trail_items").getColumn("items_id"), id)));
	}

	public ResultSet getUnlinkedHorses(UUID uuid) {
		Table horses = this.database.getTable("horses");
		Column id = horses.getColumn("id");
		return this.database.select(this.sql,
				Arrays.asList(horses, this.database.getTable("mmo_data"), this.database.getTable("last_data"),
						this.database.getTable("settings"), this.database.getTable("upgrades"),
						this.database.getTable("inventories"), this.database.getTable("cosmetics")),
				new ArrayList<>(),
				Arrays.asList(new LeftJoin(this.database.getTable("mmo_data").getColumn("horses_id"), id),
						new LeftJoin(this.database.getTable("last_data").getColumn("horses_id"), id),
						new LeftJoin(this.database.getTable("settings").getColumn("horses_id"), id),
						new LeftJoin(this.database.getTable("upgrades").getColumn("horses_id"), id),
						new LeftJoin(this.database.getTable("inventories").getColumn("horses_id"), id),
						new LeftJoin(this.database.getTable("cosmetics").getColumn("horses_id"), id)),
				new Condition(horses.getColumn("owner"), new Data(1, uuid.toString()), ConditionType.EQUAL),
				new Condition(ConditionType.AND),
				new Condition(
						new Condition(this.database.getTable("settings").getColumn("link"),
								new Data(Types.BOOLEAN, false), ConditionType.EQUAL),
						new Condition(ConditionType.OR),
						new Condition(this.database.getTable("settings").getColumn("link"), ConditionType.IS_NULL)));
	}

	public ResultSet getHorse(int id) {
		Table horses = this.database.getTable("horses");
		Column cid = horses.getColumn("id");
		return this.database
				.select(this.sql,
						Arrays.asList(horses, this.database.getTable("mmo_data"), this.database.getTable("last_data"),
								this.database.getTable("settings"), this.database.getTable("upgrades"),
								this.database.getTable("inventories"), this.database.getTable("cosmetics")),
						new ArrayList<>(),
						Arrays.asList(new LeftJoin(this.database.getTable("mmo_data").getColumn("horses_id"), cid),
								new LeftJoin(this.database.getTable("last_data").getColumn("horses_id"), cid),
								new LeftJoin(this.database.getTable("settings").getColumn("horses_id"), cid),
								new LeftJoin(this.database.getTable("upgrades").getColumn("horses_id"), cid),
								new LeftJoin(this.database.getTable("inventories").getColumn("horses_id"), cid),
								new LeftJoin(this.database.getTable("cosmetics").getColumn("horses_id"), cid)),
						new Condition(cid, new Data(4, id), ConditionType.EQUAL));
	}

	public ResultSet getTrusteds(int id) {
		Table table = this.database.getTable("trust");
		return this.database.select(sql, Arrays.asList(table),
				new Condition(table.getColumn("horses_id"), new Data(4, Integer.valueOf(id)), ConditionType.EQUAL));
	}

	public ResultSet getStats(int id) {
		Table table = this.database.getTable("stats");
		return this.database.select(sql, Arrays.asList(table),
				new Condition(table.getColumn("horses_id"), new Data(4, Integer.valueOf(id)), ConditionType.EQUAL));
	}

	public int registerItem(String name, ItemStack[] recipe, ItemStack result, ItemType type, int min_level,
			int max_level) {
		return this.registerItem(name, recipe, result, type, min_level, max_level, 0, 0, 0);
	}

	public int registerItem(String name, ItemStack[] recipe, ItemStack result, ItemType type, int min_level,
			int max_level, float value) {
		return this.registerItem(name, recipe, result, type, min_level, max_level, value, 0, 0);
	}

	public int registerItem(String name, ItemStack[] recipe, ItemStack result, ItemType type, int min_level,
			int max_level, float value, int trailtype, int particletype) {
		Table table = this.database.getTable("items");
		HashMap<Column, Data> values = new HashMap<>();
		values.put(table.getColumn("name"), new Data(12, name));
		values.put(table.getColumn("result"),
				new Data(2004, ItemStacksUtils.itemsParse(new ItemStack[] { result })));
		values.put(table.getColumn("recipe"), new Data(2004, ItemStacksUtils.itemsParse(recipe)));
		values.put(table.getColumn("item_type"), new Data(Types.TINYINT, type.ordinal()));
		values.put(table.getColumn("min_level"), new Data(-6, Integer.valueOf(min_level)));
		values.put(table.getColumn("max_level"), new Data(-6, Integer.valueOf(max_level)));
		values.put(table.getColumn("value"), new Data(Types.FLOAT, Integer.valueOf(min_level)));
		return this.database.insertWithID(this.sql, table, values);
	}

	public boolean updateItem(CraftableItem item) {
		Table table = this.database.getTable("items");
		HashMap<Column, Data> values = new HashMap<>();
		values.put(table.getColumn("name"), new Data(12, item.getName()));
		values.put(table.getColumn("result"),
				new Data(2004, ItemStacksUtils.itemsParse(new ItemStack[] { item.getResult() })));
		values.put(table.getColumn("recipe"),
				new Data(2004, ItemStacksUtils.itemsParse(item.getCompleteRecipe())));
		values.put(table.getColumn("item_type"), new Data(Types.TINYINT, item.getType().ordinal()));
		values.put(table.getColumn("min_level"), new Data(-6, Integer.valueOf(item.getMinLevel())));
		values.put(table.getColumn("max_level"), new Data(-6, Integer.valueOf(item.getMaxLevel())));
		values.put(table.getColumn("value"), new Data(Types.FLOAT, Float.valueOf(item.getValue())));
		return this.database.update(this.sql, table, values,
				new Condition(table.getColumn("id"), new Data(4, item.getId()), ConditionType.EQUAL));
	}

	public boolean deleteItem(int id) {
		Table table = this.database.getTable("items");
		return this.database.delete(this.sql, table,
				new Condition(table.getColumn("id"), new Data(4, id), ConditionType.EQUAL));
	}

	public int registerHorse(Type type, UUID owner, String name, Gender gender, Colour color, Marking marking,
			double health, double jump, double speed) {
		Table table = this.database.getTable("horses");
		HashMap<Column, Data> values = new HashMap<>();
		values.put(table.getColumn("type"), new Data(1, Character.valueOf(type.getId())));
		values.put(table.getColumn("owner"), new Data(1, owner.toString()));
		values.put(table.getColumn("name"), new Data(12, name));
		values.put(table.getColumn("colour"), new Data(1, Character.valueOf(color.getId())));
		values.put(table.getColumn("marking"), new Data(1, Character.valueOf(marking.getId())));
		values.put(table.getColumn("health"), new Data(8, Double.valueOf(health)));
		values.put(table.getColumn("jump"), new Data(8, Double.valueOf(jump)));
		values.put(table.getColumn("speed"), new Data(8, Double.valueOf(speed)));
		int id = this.database.insertWithID(this.sql, table, values);
		if (id == -1)
			return id;
		table = this.database.getTable("mmo_data");
		values = new HashMap<>();
		values.put(table.getColumn("horses_id"), new Data(4, id));
		values.put(table.getColumn("gender"), new Data(1, Character.valueOf(gender.getId())));
		if (!this.database.insert(this.sql, table, values))
			return -1;
		return id;
	}

	public boolean registerTrusted(int id, UUID trusted) {
		Table table = this.database.getTable("trust");
		HashMap<Column, Data> values = new HashMap<>();
		values.put(table.getColumn("horses_id"), new Data(4, id));
		values.put(table.getColumn("player_uuid"), new Data(1, trusted.toString()));
		return this.database.insert(this.sql, table, values);
	}

	public boolean unregisterTrusted(int id, UUID trusted) {
		Table table = this.database.getTable("trust");
		return this.database.delete(this.sql, table,
				new Condition(table.getColumn("horses_id"), new Data(Types.INTEGER, id), ConditionType.EQUAL),
				new Condition(ConditionType.AND),
				new Condition(table.getColumn("player_uuid"), new Data(1, trusted.toString()), ConditionType.EQUAL));
	}

	public boolean unregisterTrusteds(int id) {
		Table table = this.database.getTable("trust");
		return this.database.delete(this.sql, table,
				new Condition(table.getColumn("horses_id"), new Data(Types.INTEGER, id), ConditionType.EQUAL));
	}

	public boolean setOwner(int id, UUID owner) {
		Table table = this.database.getTable("horses");
		HashMap<Column, Data> values = new HashMap<>();
		values.put(table.getColumn("owner"), new Data(1, owner.toString()));
		return this.database.update(this.sql, table, values,
				new Condition(table.getColumn("id"), new Data(Types.INTEGER, id), ConditionType.EQUAL));
	}

	public boolean setName(int id, String name) {
		Table table = this.database.getTable("horses");
		HashMap<Column, Data> values = new HashMap<>();
		values.put(table.getColumn("name"), new Data(12, name));
		return this.database.update(this.sql, table, values,
				new Condition(table.getColumn("id"), new Data(Types.INTEGER, id), ConditionType.EQUAL));
	}

	public boolean setHealth(int id, double health) {
		Table table = this.database.getTable("horses");
		HashMap<Column, Data> values = new HashMap<>();
		values.put(table.getColumn("health"), new Data(8, health));
		return this.database.update(this.sql, table, values,
				new Condition(table.getColumn("id"), new Data(Types.INTEGER, id), ConditionType.EQUAL));
	}

	public boolean setJump(int id, double jump) {
		Table table = this.database.getTable("horses");
		HashMap<Column, Data> values = new HashMap<>();
		values.put(table.getColumn("jump"), new Data(8, jump));
		return this.database.update(this.sql, table, values,
				new Condition(table.getColumn("id"), new Data(Types.INTEGER, id), ConditionType.EQUAL));
	}

	public boolean setSpeed(int id, double speed) {
		Table table = this.database.getTable("horses");
		HashMap<Column, Data> values = new HashMap<>();
		values.put(table.getColumn("speed"), new Data(8, speed));
		return this.database.update(this.sql, table, values,
				new Condition(table.getColumn("id"), new Data(Types.INTEGER, id), ConditionType.EQUAL));
	}

	public boolean setColour(int id, Colour colour) {
		Table table = this.database.getTable("horses");
		HashMap<Column, Data> values = new HashMap<>();
		values.put(table.getColumn("colour"), new Data(1, colour.getId()));
		return this.database.update(this.sql, table, values,
				new Condition(table.getColumn("id"), new Data(Types.INTEGER, id), ConditionType.EQUAL));
	}

	public boolean setMarking(int id, Marking marking) {
		Table table = this.database.getTable("horses");
		HashMap<Column, Data> values = new HashMap<>();
		values.put(table.getColumn("marking"), new Data(1, marking.getId()));
		return this.database.update(this.sql, table, values,
				new Condition(table.getColumn("id"), new Data(Types.INTEGER, id), ConditionType.EQUAL));
	}

	public boolean setGender(int id, Gender gender) {
		Table table = this.database.getTable("mmo_data");
		HashMap<Column, Data> values = new HashMap<>();
		values.put(table.getColumn("horses_id"), new Data(4, id));
		values.put(table.getColumn("gender"), new Data(1, gender.getId()));
		return this.database.insertOrUpdate(this.sql, table, values,
				new Condition(table.getColumn("horses_id"), new Data(Types.INTEGER, id), ConditionType.EQUAL));
	}

	public boolean setDeathTime(int id, long deathtime) {
		Table table = this.database.getTable("mmo_data");
		HashMap<Column, Data> values = new HashMap<>();
		values.put(table.getColumn("horses_id"), new Data(4, id));
		values.put(table.getColumn("deathtime"), new Data(93, new Timestamp(deathtime)));
		return this.database.insertOrUpdate(this.sql, table, values,
				new Condition(table.getColumn("horses_id"), new Data(Types.INTEGER, id), ConditionType.EQUAL));
	}

	public boolean setPrice(int id, double price) {
		Table table = this.database.getTable("mmo_data");
		HashMap<Column, Data> values = new HashMap<>();
		values.put(table.getColumn("horses_id"), new Data(4, id));
		values.put(table.getColumn("price"), new Data(8, price));
		return this.database.insertOrUpdate(this.sql, table, values,
				new Condition(table.getColumn("horses_id"), new Data(Types.INTEGER, id), ConditionType.EQUAL));
	}

	public boolean setInventorySlots(int id, int invslots) {
		Table table = this.database.getTable("mmo_data");
		HashMap<Column, Data> values = new HashMap<>();
		values.put(table.getColumn("horses_id"), new Data(4, id));
		values.put(table.getColumn("inv_slots"), new Data(4, invslots));
		return this.database.insertOrUpdate(this.sql, table, values,
				new Condition(table.getColumn("horses_id"), new Data(4, id), ConditionType.EQUAL));
	}

	public boolean setInventory(int id, ItemStack[] inventory) {
		HashMap<Column, Data> values = new HashMap<>();
		Table table = this.database.getTable("inventories");
		values.put(table.getColumn("horses_id"), new Data(4, id));
		values.put(table.getColumn("inventory"), new Data(2004, ItemStacksUtils.itemsParse(inventory)));
		return this.database.insertOrUpdate(this.sql, table, values,
				new Condition(table.getColumn("horses_id"), new Data(4, id), ConditionType.EQUAL));
	}

	public boolean setNameTagVisible(int id, boolean nametag) {
		Table table = this.database.getTable("settings");
		HashMap<Column, Data> values = new HashMap<>();
		values.put(table.getColumn("horses_id"), new Data(4, id));
		values.put(table.getColumn("nametag"), new Data(Types.BOOLEAN, nametag));
		return this.database.insertOrUpdate(this.sql, table, values,
				new Condition(table.getColumn("horses_id"), new Data(Types.INTEGER, id), ConditionType.EQUAL));
	}

	public boolean setHealthTagVisible(int id, boolean healthtag) {
		Table table = this.database.getTable("settings");
		HashMap<Column, Data> values = new HashMap<>();
		values.put(table.getColumn("horses_id"), new Data(4, id));
		values.put(table.getColumn("healthtag"), new Data(Types.BOOLEAN, healthtag));
		return this.database.insertOrUpdate(this.sql, table, values,
				new Condition(table.getColumn("horses_id"), new Data(Types.INTEGER, id), ConditionType.EQUAL));
	}

	public boolean setLink(int id, boolean link) {
		Table table = this.database.getTable("settings");
		HashMap<Column, Data> values = new HashMap<>();
		values.put(table.getColumn("horses_id"), new Data(4, id));
		values.put(table.getColumn("link"), new Data(Types.BOOLEAN, link));
		return this.database.insertOrUpdate(this.sql, table, values,
				new Condition(table.getColumn("horses_id"), new Data(Types.INTEGER, id), ConditionType.EQUAL));
	}

	public boolean setFollowOwner(int id, boolean follow) {
		Table table = this.database.getTable("settings");
		HashMap<Column, Data> values = new HashMap<>();
		values.put(table.getColumn("horses_id"), new Data(4, id));
		values.put(table.getColumn("follow"), new Data(Types.BOOLEAN, follow));
		return this.database.insertOrUpdate(this.sql, table, values,
				new Condition(table.getColumn("horses_id"), new Data(Types.INTEGER, id), ConditionType.EQUAL));
	}

	public boolean setPublic(int id, boolean _public) {
		Table table = this.database.getTable("settings");
		HashMap<Column, Data> values = new HashMap<>();
		values.put(table.getColumn("horses_id"), new Data(4, id));
		values.put(table.getColumn("public"), new Data(Types.BOOLEAN, _public));
		return this.database.insertOrUpdate(this.sql, table, values,
				new Condition(table.getColumn("horses_id"), new Data(Types.INTEGER, id), ConditionType.EQUAL));
	}

	public boolean setBlockSpeedOnUnmount(int id, boolean blockspeedunmount) {
		Table table = this.database.getTable("settings");
		HashMap<Column, Data> values = new HashMap<>();
		values.put(table.getColumn("horses_id"), new Data(4, id));
		values.put(table.getColumn("blockspeedunmount"), new Data(Types.BOOLEAN, blockspeedunmount));
		return this.database.insertOrUpdate(this.sql, table, values,
				new Condition(table.getColumn("horses_id"), new Data(Types.INTEGER, id), ConditionType.EQUAL));
	}

	public boolean setCosmeticBody(int id, ItemStack body) {
		HashMap<Column, Data> values = new HashMap<>();
		Table table = this.database.getTable("cosmetics");
		values.put(table.getColumn("horses_id"), new Data(4, id));
		values.put(table.getColumn("body"),
				new Data(2004, ItemStacksUtils.itemsParse(new ItemStack[] { body })));
		return this.database.insertOrUpdate(this.sql, table, values,
				new Condition(table.getColumn("horses_id"), new Data(4, id), ConditionType.EQUAL));
	}

	public boolean setCosmeticSaddle(int id, ItemStack saddle) {
		HashMap<Column, Data> values = new HashMap<>();
		Table table = this.database.getTable("cosmetics");
		values.put(table.getColumn("horses_id"), new Data(4, id));
		values.put(table.getColumn("saddle"),
				new Data(2004, ItemStacksUtils.itemsParse(new ItemStack[] { saddle })));
		return this.database.insertOrUpdate(this.sql, table, values,
				new Condition(table.getColumn("horses_id"), new Data(4, id), ConditionType.EQUAL));
	}

	public boolean setCosmeticAccessory(int id, ItemStack accessory) {
		HashMap<Column, Data> values = new HashMap<>();
		Table table = this.database.getTable("cosmetics");
		values.put(table.getColumn("horses_id"), new Data(4, id));
		values.put(table.getColumn("accessory"),
				new Data(2004, ItemStacksUtils.itemsParse(new ItemStack[] { accessory })));
		return this.database.insertOrUpdate(this.sql, table, values,
				new Condition(table.getColumn("horses_id"), new Data(4, id), ConditionType.EQUAL));
	}

	public boolean setAccess(int id, UUID uuid, Action action, boolean access) {
		Table table = this.database.getTable("trust");
		HashMap<Column, Data> values = new HashMap<>();
		values.put(table.getColumn("horses_id"), new Data(4, id));
		values.put(table.getColumn(action.name().toLowerCase()), new Data(Types.BOOLEAN, access));
		return this.database.insertOrUpdate(this.sql, table, values,
				new Condition(table.getColumn("horses_id"), new Data(Types.INTEGER, id), ConditionType.EQUAL),
				new Condition(ConditionType.AND),
				new Condition(table.getColumn("player_uuid"), new Data(1, uuid.toString()), ConditionType.EQUAL));
	}

	public boolean setStat(int id, Stat stat, int level) {
		Table table = this.database.getTable("stats");
		HashMap<Column, Data> values = new HashMap<>();
		values.put(table.getColumn("horses_id"), new Data(4, id));
		values.put(table.getColumn("stat"), new Data(Types.TINYINT, stat.ordinal()));
		values.put(table.getColumn("level"), new Data(Types.INTEGER, level));
		return this.database.insertOrUpdate(this.sql, table, values,
				new Condition(table.getColumn("horses_id"), new Data(Types.INTEGER, id), ConditionType.EQUAL),
				new Condition(ConditionType.AND),
				new Condition(table.getColumn("stat"), new Data(Types.TINYINT, stat.ordinal()), ConditionType.EQUAL));
	}

	public boolean setLastHealth(int id, double health) {
		Table table = this.database.getTable("last_data");
		HashMap<Column, Data> values = new HashMap<>();
		values.put(table.getColumn("last_health"), new Data(8, health));
		return this.database.insertOrUpdate(this.sql, table, values,
				new Condition(table.getColumn("horses_id"), new Data(Types.INTEGER, id), ConditionType.EQUAL));
	}

	public boolean setLastFood(int id, double food) {
		Table table = this.database.getTable("last_data");
		HashMap<Column, Data> values = new HashMap<>();
		values.put(table.getColumn("last_food"), new Data(8, food));
		return this.database.insertOrUpdate(this.sql, table, values,
				new Condition(table.getColumn("horses_id"), new Data(Types.INTEGER, id), ConditionType.EQUAL));
	}

	public boolean removeUpgrade(int id, ItemType type) {
		String field = "";
		if (type == ItemType.SADDLE)
			field = "saddle_item";
		else if (type == ItemType.ARMOR)
			field = "armor_item";
		else if (type == ItemType.SPEED_UPGRADE)
			field = "speed_item";
		else if (type == ItemType.JUMP_UPGRADE)
			field = "jump_item";
		else if (type == ItemType.HEALTH_UPGRADE)
			field = "health_item";
		else if (type == ItemType.CHEST_UPGRADE)
			field = "chest_item";
		else if (type.name().contains("SKILL"))
			field = "skill_item";
		if (field.isEmpty())
			return false;
		HashMap<Column, Data> values = new HashMap<>();
		Table table = database.getTable("upgrades");
		values.put(table.getColumn("horses_id"), new Data(4, id));
		values.put(table.getColumn(field), new Data(4, 0));
		return this.database.insertOrUpdate(this.sql, table, values,
				new Condition(table.getColumn("horses_id"), new Data(4, id), ConditionType.EQUAL));
	}

	public boolean applyUpgrade(int id, CraftableItem item) {
		String field = "";
		if (item.getType() == ItemType.SADDLE)
			field = "saddle_item";
		else if (item.getType() == ItemType.ARMOR)
			field = "armor_item";
		else if (item.getType() == ItemType.SPEED_UPGRADE)
			field = "speed_item";
		else if (item.getType() == ItemType.JUMP_UPGRADE)
			field = "jump_item";
		else if (item.getType() == ItemType.HEALTH_UPGRADE)
			field = "health_item";
		else if (item.getType() == ItemType.CHEST_UPGRADE)
			field = "chest_item";
		else if (item.getType().name().contains("SKILL"))
			field = "skill_item";
		if (field.isEmpty())
			return false;
		HashMap<Column, Data> values = new HashMap<>();
		Table table = database.getTable("upgrades");
		values.put(table.getColumn("horses_id"), new Data(4, id));
		values.put(table.getColumn(field), new Data(4, item.getId()));
		return this.database.insertOrUpdate(this.sql, table, values,
				new Condition(table.getColumn("horses_id"), new Data(4, id), ConditionType.EQUAL));
	}

	public boolean deleteHorse(int id) {
		return this.database.delete(sql, database.getTable("horses"),
				new Condition(database.getTable("horses").getColumn("id"), new Data(4, id), ConditionType.EQUAL));
	}

	public boolean parseData(SQLObject dest, Database destdb) {
		boolean result = false;
		try {
			generateDatabase(destdb).updateTables(MMOHorsesAPI.getPlugin().getDescription().getVersion(), dest);
			destdb.setForeignKeyCheck(dest, false);
			for (Table table : destdb.getTables())
				if (!destdb.truncateTable(dest, table))
					throw new Exception();
			for (Table table : this.database.getTables()) {
				Table desttable = destdb.getTable(table.getName());
				ResultSet set = this.database.select(this.sql, Arrays.asList(table));
				while (set.next()) {
					HashMap<Column, Data> map = new HashMap<>();
					for (Column column : table.getColumns()) {
						Object o = set.getObject(column.getName());
						if (column.getTypeId() == Types.TIMESTAMP) {
							Long date = null;
							if (o.getClass() == String.class)
								date = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse((String) o).getTime();
							else if (o.getClass() == Timestamp.class)
								date = ((Timestamp) o).getTime();
							else if (o.getClass() == Integer.class)
								date = Long.valueOf((Integer) o);
							else if (o.getClass() == Long.class)
								date = (Long) o;
							if (date != null) {
								if (date < 946684800L)
									date = 946684800L;
								o = new Timestamp(date);
							}
						}
						map.put(desttable.getColumn(column.getName()), new Data(column.getTypeId(), o));
					}
					if (!destdb.insert(dest, desttable, map))
						throw new Exception();
				}
				set.close();
			}
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			destdb.setForeignKeyCheck(dest, true);
		}
		return result;
	}

}