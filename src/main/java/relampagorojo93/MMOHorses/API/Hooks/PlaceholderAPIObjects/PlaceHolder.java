package relampagorojo93.MMOHorses.API.Hooks.PlaceholderAPIObjects;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import relampagorojo93.LibsCollection.SpigotMessages.MessagesUtils;
import relampagorojo93.MMOHorses.API.MMOHorsesAPI;
import relampagorojo93.MMOHorses.Enums.WalkMode;
import relampagorojo93.MMOHorses.Modules.EntityPckg.Interfaces.Objects.MMOHorse;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingWalkmode;

public class PlaceHolder extends PlaceholderExpansion {
	@Override
	public String getAuthor() {
		return "RelampagoRojo93";
	}

	@Override
	public String getIdentifier() {
		return "mmohorses";
	}

	@Override
	public String getVersion() {
		return "1.0";
	}

	@Override
	public boolean persist() {
		return true;
	}

	@Override
	public String onPlaceholderRequest(Player player, String identifier) {
		if (player != null) {
			if (player.getVehicle() != null) {
				try {
					MMOHorse horse = MMOHorsesAPI.getUtils().toMMOHorse(player.getVehicle());
					if (horse != null && horse.isCustom()) {
						if (identifier.equalsIgnoreCase("speed"))
							return MMOHorsesAPI.getUtils()
									.generateBar((double) horse.getMMOHorseData().getWalkmode().ordinal()
											/ (double) (WalkMode.values().length - 1));
						else if (identifier.equalsIgnoreCase("jump"))
							return MMOHorsesAPI.getUtils().generateBar(horse.getMMOHorseData().getWalkmode()
									.ordinal() < SettingWalkmode.WALKMODEFORMAXJUMP.toWalkMode().ordinal()
											? (double) horse.getMMOHorseData().getWalkmode().ordinal()
													/ (double) SettingWalkmode.WALKMODEFORMAXJUMP.toWalkMode().ordinal()
											: 1D);
						else if (identifier.equalsIgnoreCase("walkmode"))
							return horse.getMMOHorseData().getWalkmode().toString();
						else if (identifier.equalsIgnoreCase("gender"))
							return horse.getMMOHorseData().getGender().toString();
						else if (identifier.equalsIgnoreCase("speedamount"))
							return String.valueOf(
									MessagesUtils.round(horse.getMMOHorseData().getEffectiveSpeed(), 2));
						else if (identifier.equalsIgnoreCase("jumpamount"))
							return String.valueOf(
									MessagesUtils.round(horse.getMMOHorseData().getEffectiveJump(), 2));
						else if (identifier.equalsIgnoreCase("foodlevel"))
							return String.valueOf(
									MMOHorsesAPI.getUtils().generateFoodBar(horse.getMMOHorseData().getFood()));
						if (horse.getMMOHorseData().getClaimedData() != null) {
							if (identifier.equalsIgnoreCase("name"))
								return MessagesUtils
										.color(horse.getMMOHorseData().getClaimedData().getData().getName());
							else if (identifier.equalsIgnoreCase("owner")) {
								OfflinePlayer pl = Bukkit.getOfflinePlayer(
										horse.getMMOHorseData().getClaimedData().getData().getOwner());
								return pl != null ? pl.getName() : "???";
							} else if (identifier.equalsIgnoreCase("price"))
								return String.valueOf(MessagesUtils
										.round(horse.getMMOHorseData().getClaimedData().getData().getPrice(), 2));
						}
					}
				} catch (Exception e) {
				}
			}
			if (identifier.equalsIgnoreCase("claimed"))
				return String.valueOf(MMOHorsesAPI.getHorseModule().getOwned(player.getUniqueId()).size());
		}
		return "";
	}
}
