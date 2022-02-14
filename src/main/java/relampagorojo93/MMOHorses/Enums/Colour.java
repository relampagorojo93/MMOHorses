package relampagorojo93.MMOHorses.Enums;

import org.bukkit.entity.Horse;
import org.bukkit.entity.Llama;

public enum Colour {
	WHITE('W', Horse.Color.WHITE, Llama.Color.WHITE,
			"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjBhMmRiMmYxZWI5M2U1OTc4ZDJkYzkxYTc0ZGY0M2Q3Yjc1ZDllYzBlNjk0ZmQ3ZjJhNjUyZmJkMTUifX19",
			"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODNkOWI1OTE1OTEyZmZjMmI4NTc2MWQ2YWRjYjQyOGE4MTJmOWI4M2ZmNjM0ZTMzMTE2MmNlNDZjOTllOSJ9fX0="),
	BLACK('B', Horse.Color.BLACK, null,
			"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjRiN2ZjNWY3YTlkZGZkZDFhYTc5MzE3NDEwZmMxOTI5ZjkxYmRkZjk4NTg1OTM4YTJhNTYxOTlhNjMzY2MifX19",
			""),
	BROWN('R', Horse.Color.BROWN, Llama.Color.BROWN,
			"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmVkZjczZWExMmNlNmJkOTBhNGFlOWE4ZDE1MDk2NzQ5Y2ZlOTE4MjMwZGM4MjliMjU4MWQyMjNiMWEyYTgifX19",
			"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODE4Y2Q0NTdmYmFmMzI3ZmEzOWYxMGI1YjM2MTY2ZmQwMTgyNjQwMzY4NjUxNjRjMDJkOWU1ZmY1M2Y0NSJ9fX0="),
	DARKBAY('D', Horse.Color.DARK_BROWN, null,
			"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjY2MWYyM2ZiNzY2MjRmZmJhYmJkYTMxY2E0YTM4YjQwNGZlNjNlZjM3ZDRiYTRlNGM1NDQxYTIxZTNhNiJ9fX0=",
			""),
	PALOMINO('P', Horse.Color.CREAMY, Llama.Color.CREAMY,
			"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjI4ZDFhYjRiZTFlMjhiN2I0NjFmZGVhNDYzODFhYzM2M2E3ZTVjMzU5MWM5ZTVkMjY4M2ZiZTFlYzlmY2QzIn19fQ==",
			"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmE1ZjEwZTZlNjIzMmYxODJmZTk2NmY1MDFmMWMzNzk5ZDQ1YWUxOTAzMWExZTQ5NDFiNWRlZTBmZWZmMDU5YiJ9fX0="),
	CHESTNUT('C', Horse.Color.CHESTNUT, null,
			"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjY2YjJiMzJkMzE1MzljNzM4M2Q5MjNiYWU0ZmFhZjY1ZGE2NzE1Y2Q1MjZjMzVkMmU0ZTY4MjVkYTExZmIifX19",
			""),
	GRAY('G', Horse.Color.GRAY, Llama.Color.GRAY,
			"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDY2NzZjNGQ2ZjBmNWVkNjA2YTM1NmYzY2M1YTI5ZDE0YWFmZTY1NzIxYmExYTFhOTVjNWFjNGM1ZTIzOWU1In19fQ==",
			"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2YyNGU1NmZkOWZmZDcxMzNkYTZkMWYzZTJmNDU1OTUyYjFkYTQ2MjY4NmY3NTNjNTk3ZWU4MjI5OWEifX19"),
	NULL('-', null, null, "", "");

	char id;
	Horse.Color horsecolor;
	Llama.Color llamacolor;
	String horseskin, llamaskin;

	Colour(char id, Horse.Color horsecolor, Llama.Color llamacolor, String horseskin, String llamaskin) {
		this.id = id;
		this.horsecolor = horsecolor;
		this.llamacolor = llamacolor;
		this.horseskin = horseskin;
		this.llamaskin = llamaskin;
	}

	public char getId() {
		return id;
	}

	public Horse.Color toBukkitHorse() {
		return horsecolor;
	}

	public Llama.Color toBukkitLlama() {
		return llamacolor;
	}

	public String getHorseSkin() {
		return horseskin;
	}

	public String getLlamaSkin() {
		return !llamaskin.isEmpty() ? llamaskin : Colour.values()[0].llamaskin;
	}

	public static Colour getById(char id) {
		for (Colour colour : Colour.values())
			if (colour.id == id)
				return colour;
		return NULL;
	}

	public static Colour getByBukkit(Horse.Color style) {
		for (Colour colour : Colour.values())
			if (colour.horsecolor == style)
				return colour;
		return NULL;
	}

	public static Colour getByBukkit(Llama.Color style) {
		for (Colour colour : Colour.values())
			if (colour.llamacolor == style)
				return colour;
		return NULL;
	}
}
