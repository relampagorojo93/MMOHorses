package relampagorojo93.MMOHorses.Modules.CommandsPckg.Commands;

import relampagorojo93.LibsCollection.SpigotCommands.Objects.Command;
import relampagorojo93.MMOHorses.API.Hooks.LandsAPI;
import relampagorojo93.MMOHorses.API.Hooks.VaultAPI;
import relampagorojo93.MMOHorses.Modules.CommandsPckg.Base.HelpSubCommand;
import relampagorojo93.MMOHorses.Modules.CommandsPckg.Commands.ItemsSubCommands.ItemsCommand;
import relampagorojo93.MMOHorses.Modules.CommandsPckg.Commands.LandsSubCommands.LandsCommand;
import relampagorojo93.MMOHorses.Modules.CommandsPckg.Commands.SQLSubCommands.SQLCommand;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingList;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingString;

public class HorseCommand extends Command {
	public HorseCommand() {
		super("horse", SettingString.HORSE_NAME.toString(), SettingString.HORSE_PERMISSION.toString(),
				SettingString.HORSE_DESCRIPTION.toString(), SettingString.HORSE_PARAMETERS.toString(),
				SettingList.HORSE_ALIASES.toList());
		if (VaultAPI.isHooked()) {
			addCommand(new SellSubCommand(this));
			addCommand(new BuySubCommand(this));
		}
		addCommand(new DebugSubCommand(this));
		addCommand(new GivePresentSubCommand(this));
		addCommand(new ClaimSubCommand(this));
		addCommand(new ReleaseSubCommand(this));
		addCommand(new RenameSubCommand(this));
		addCommand(new ListSubCommand(this));
		addCommand(new GiftSubCommand(this));
		addCommand(new TrustSubCommand(this));
		addCommand(new UntrustSubCommand(this));
		addCommand(new GeldSubCommand(this));
		addCommand(new GiveSubCommand(this));
		addCommand(new ListAllSubCommand(this));
		addCommand(new HealSubCommand(this));
		addCommand(new SetHealthSubCommand(this));
		addCommand(new SetJumpSubCommand(this));
		addCommand(new SetSpeedSubCommand(this));
		addCommand(new SetColorSubCommand(this));
		addCommand(new SetMarkingSubCommand(this));
		addCommand(new SetGenderSubCommand(this));
		addCommand(new SetOwnerSubCommand(this));
		addCommand(new DeleteSubCommand(this));
		addCommand(new ReloadSubCommand(this));
		addCommand(new InfoSubCommand(this));
		addCommand(new LungeSubCommand(this));
		addCommand(new UnsummonSubCommand(this));
		sortCommands();
		if (LandsAPI.isHooked())
			addCommand(new LandsCommand(this), 0);
		addCommand(new SQLCommand(this), 0);
		addCommand(new ItemsCommand(this), 0);
		addCommand(new HelpSubCommand(this), 0);
	}
}