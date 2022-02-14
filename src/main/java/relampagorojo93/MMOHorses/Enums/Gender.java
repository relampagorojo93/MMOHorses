package relampagorojo93.MMOHorses.Enums;

import relampagorojo93.MMOHorses.Modules.FilePckg.Messages.MessageString;

public enum Gender {
	MARE('M'), STALLION('S'), GELDING('G');

	private char id;

	private Gender(char id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return MessageString.valueOf("GENDER_" + this.name()).toString();
	}

	public char getId() {
		return id;
	}

	public static Gender getById(char id) {
		for (Gender g : Gender.values())
			if (g.getId() == id)
				return g;
		return null;
	}
}
