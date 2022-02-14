package relampagorojo93.MMOHorses.Enums;

import org.bukkit.entity.Horse.Style;

public enum Marking {
	SOCKS('S', Style.WHITE), PINTO('P', Style.WHITEFIELD), ROAN('R', Style.WHITE_DOTS), BALDFACE('B', Style.BLACK_DOTS),
	NONE('N', Style.NONE), NULL('-', null);

	char id;
	Style style;

	Marking(char id, Style style) {
		this.id = id;
		this.style = style;
	}

	public char getId() {
		return id;
	}

	public Style toBukkit() {
		return style;
	}

	public static Marking getById(char id) {
		for (Marking marking : Marking.values())
			if (marking.id == id)
				return marking;
		return NULL;
	}

	public static Marking getByBukkit(Style style) {
		for (Marking marking : Marking.values())
			if (marking.style == style)
				return marking;
		return NULL;
	}
}
