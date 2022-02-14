package relampagorojo93.MMOHorses.Modules.CommandsPckg.Commands.ItemsSubCommands;

import java.io.File;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;

import relampagorojo93.LibsCollection.SpigotCommands.Objects.Command;
import relampagorojo93.LibsCollection.SpigotCommands.Objects.SubCommand;
import relampagorojo93.LibsCollection.Utils.Bukkit.ItemStacksUtils;
import relampagorojo93.LibsCollection.YAMLLib.YAMLFile;
import relampagorojo93.MMOHorses.API.MMOHorsesAPI;
import relampagorojo93.MMOHorses.Modules.CraftPckg.Objects.CraftableItem;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingList;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingString;

public class ExportSubCommand extends SubCommand {
	
	public ExportSubCommand(Command command) {
		super(command, "export", SettingString.HORSEITEMS_EXPORT_NAME.toString(), SettingString.HORSEITEMS_EXPORT_PERMISSION.toString(),
				SettingString.HORSEITEMS_EXPORT_DESCRIPTION.toString(), SettingString.HORSEITEMS_EXPORT_PARAMETERS.toString(),
				SettingList.HORSEITEMS_EXPORT_ALIASES.toList());
	}

	@Override
	public List<String> tabComplete(Command cmd, CommandSender sender, String[] args) {
		return new ArrayList<>();
	}

	@Override
	public boolean execute(Command cmd, CommandSender sender, String[] args, boolean useids) {
		String name = args.length > 1 ? args[1] : "Items";
		YAMLFile yaml = new YAMLFile();
		for (CraftableItem ci:MMOHorsesAPI.getCraftModule().getCraftableItems()) {
			yaml.setSection(ci.getName() + ".ItemStack", Base64.getEncoder().encodeToString(ItemStacksUtils.itemsParse(new ItemStack[] { ci.getResult() })));
			yaml.setSection(ci.getName() + ".Recipe", Base64.getEncoder().encodeToString(ItemStacksUtils.itemsParse(ci.getCompleteRecipe())));
			yaml.setSection(ci.getName() + ".Min-level", ci.getMinLevel());
			yaml.setSection(ci.getName() + ".Max-level", ci.getMaxLevel());
			yaml.setSection(ci.getName() + ".Value", ci.getValue());
			yaml.setSection(ci.getName() + ".Item-type", ci.getType().name());
		}
		try {
			yaml.saveYAML(new File(MMOHorsesAPI.getFileModule().PLUGIN_FOLDER.getPath() + "/" + name + ".yml"));
			sender.sendMessage(MMOHorsesAPI.getUtils().applyPrefix("Exported all items!"));
		} catch (Exception e) {
			e.printStackTrace();
			sender.sendMessage(MMOHorsesAPI.getUtils().applyPrefix("Error!"));
		}
		return true;
	}
}
