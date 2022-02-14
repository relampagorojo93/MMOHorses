package relampagorojo93.MMOHorses.Modules.CustomPckg.Objects;

import relampagorojo93.MMOHorses.Enums.Colour;
import relampagorojo93.MMOHorses.Enums.Marking;
import relampagorojo93.MMOHorses.Enums.Type;

public class CustomData {
	public final String name;
	public final Type type;
	public final Colour colour;
	public final Marking marking;
	public final double jump;
	public final double speed;
	public final double health;
	private String armor;
	private String saddle;
	private String jump_upgrade;
	private String speed_upgrade;
	private String health_upgrade;
	private String chest_upgrade;

	public CustomData(String name, Type type, Colour colour, Marking marking, double jump, double speed, double health,
			String armor, String saddle, String jump_upgrade, String speed_upgrade, String health_upgrade,
			String chest_upgrade) {
		this.name = name;
		this.type = type;
		this.colour = colour;
		this.marking = marking;
		this.jump = jump;
		this.speed = speed;
		this.health = health;
		this.armor = armor;
		this.saddle = saddle;
		this.jump_upgrade = jump_upgrade;
		this.speed_upgrade = speed_upgrade;
		this.health_upgrade = health_upgrade;
		this.chest_upgrade = chest_upgrade;
	}

	public String getArmor() {
		return armor;
	}

	public String getSaddle() {
		return saddle;
	}

	public String getJumpUpgrade() {
		return jump_upgrade;
	}

	public String getSpeedUpgrade() {
		return speed_upgrade;
	}

	public String getHealthUpgrade() {
		return health_upgrade;
	}

	public String getChestUpgrade() {
		return chest_upgrade;
	}
}
