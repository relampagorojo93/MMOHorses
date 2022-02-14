package relampagorojo93.MMOHorses.Enums;

import org.bukkit.entity.EntityType;

import relampagorojo93.LibsCollection.SpigotDebug.Data.DebugAlertData;
import relampagorojo93.MMOHorses.API.MMOHorsesAPI;

public enum Type {
	HORSE('H', "HORSE", 100,
			"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjI4ZDFhYjRiZTFlMjhiN2I0NjFmZGVhNDYzODFhYzM2M2E3ZTVjMzU5MWM5ZTVkMjY4M2ZiZTFlYzlmY2QzIn19fQ=="),
	SKELETONHORSE('S', "SKELETON_HORSE", 28,
			"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDdlZmZjZTM1MTMyYzg2ZmY3MmJjYWU3N2RmYmIxZDIyNTg3ZTk0ZGYzY2JjMjU3MGVkMTdjZjg5NzNhIn19fQ=="),
	ZOMBIEHORSE('Z', "ZOMBIE_HORSE", 29,
			"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjg5NmExZGJkZTFhMTk1NDBjZTczMzZjNmM5NGY1OTY1MmFhOThiYjEwNjhiMmVmOGM4ZmE2ZWY4NTgwNGY1NyJ9fX0="),
	DONKEY('D', "DONKEY", 31,
			"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjE0NGJkYWQ2YmMxOGEzNzE2YjE5NmRjNGE0YmQ2OTUyNjVlY2NhYWRkMGQ5NDViZWI5NzY0NDNmODI2OTNiIn19fQ=="),
	MULE('M', "MULE", 32,
			"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTA0ODZhNzQyZTdkZGEwYmFlNjFjZTJmNTVmYTEzNTI3ZjFjM2IzMzRjNTdjMDM0YmI0Y2YxMzJmYjVmNWYifX19"),
	LLAMA('L', "LLAMA", 103,
			"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmE1ZjEwZTZlNjIzMmYxODJmZTk2NmY1MDFmMWMzNzk5ZDQ1YWUxOTAzMWExZTQ5NDFiNWRlZTBmZWZmMDU5YiJ9fX0="),
	TRADERLLAMA('T', "TRADER_LLAMA", 103,
			"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTg5YTJlYjE3NzA1ZmU3MTU0YWIwNDFlNWM3NmEwOGQ0MTU0NmEzMWJhMjBlYTMwNjBlM2VjOGVkYzEwNDEyYyJ9fX0=");

	char id;
	int entity_id;
	EntityType type;
	String skin;

	Type(char id, String type, int entity_id, String skin) {
		try {
			this.type = EntityType.valueOf(type);
			this.id = id;
			this.entity_id = entity_id;
			this.skin = skin;
		} catch (Exception e) {
			MMOHorsesAPI.getDebugController().addDebugData(new DebugAlertData("Not able to load entity data " + this.name()));
		}
	}

	public char getId() {
		return id;
	}

	public int getEntityId() {
		return entity_id;
	}

	public String getSkin() {
		return skin;
	}

	public EntityType toBukkit() {
		return type;
	}

	public static Type getById(char id) {
		for (Type colour : Type.values())
			if (colour.id == id)
				return colour;
		return null;
	}

	public static Type getByBukkit(EntityType enttype) {
		for (Type type : Type.values())
			if (type.type == enttype)
				return type;
		return null;
	}
}
