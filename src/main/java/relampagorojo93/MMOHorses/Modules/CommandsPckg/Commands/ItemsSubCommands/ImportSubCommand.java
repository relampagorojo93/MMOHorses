package relampagorojo93.MMOHorses.Modules.CommandsPckg.Commands.ItemsSubCommands;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;

import relampagorojo93.LibsCollection.SpigotCommands.Objects.Command;
import relampagorojo93.LibsCollection.SpigotCommands.Objects.SubCommand;
import relampagorojo93.LibsCollection.Utils.Bukkit.ItemStacksUtils;
import relampagorojo93.LibsCollection.YAMLLib.YAMLFile;
import relampagorojo93.LibsCollection.YAMLLib.Objects.Data;
import relampagorojo93.LibsCollection.YAMLLib.Objects.Section;
import relampagorojo93.MMOHorses.API.MMOHorsesAPI;
import relampagorojo93.MMOHorses.Enums.ItemType;
import relampagorojo93.MMOHorses.Modules.CraftPckg.Objects.CraftableItem;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingList;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingString;

public class ImportSubCommand extends SubCommand {
	
	public ImportSubCommand(Command command) {
		super(command, "import", SettingString.HORSEITEMS_IMPORT_NAME.toString(), SettingString.HORSEITEMS_IMPORT_PERMISSION.toString(),
				SettingString.HORSEITEMS_IMPORT_DESCRIPTION.toString(), SettingString.HORSEITEMS_IMPORT_PARAMETERS.toString(),
				SettingList.HORSEITEMS_IMPORT_ALIASES.toList());
	}

	@Override
	public List<String> tabComplete(Command cmd, CommandSender sender, String[] args) {
		return new ArrayList<>();
	}

	@Override
	public boolean execute(Command cmd, CommandSender sender, String[] args, boolean useids) {
		String name = args.length > 1 ? args[1] : "Items";
		YAMLFile yaml;
		try {
			yaml = new YAMLFile(new File(MMOHorsesAPI.getFileModule().PLUGIN_FOLDER.getPath() + "/" + name + ".yml"));
			for (Data d:yaml.getSection("", new Section()).getChilds()) {
				if (d instanceof Section) {
					Section item = (Section) d;
					ItemStack is = ItemStacksUtils.itemsParse(Base64.getDecoder().decode(item.getChild("ItemStack").getString()))[0];
					ItemStack[] recipe = ItemStacksUtils.itemsParse(Base64.getDecoder().decode(item.getChild("Recipe").getString()));
					int minlevel = item.getChild("Min-level").getInteger();
					int maxlevel = item.getChild("Max-level").getInteger();
					float value = item.getChild("Value").getFloat();
					ItemType type = ItemType.valueOf(item.getChild("Item-type").getString().toUpperCase());
					CraftableItem ci = MMOHorsesAPI.getCraftModule().getCraftableItem(item.getPath());
					if (ci == null) {
						MMOHorsesAPI.getCraftModule().addItem(item.getPath(), is, type);
						ci = MMOHorsesAPI.getCraftModule().getCraftableItem(item.getPath());
					}
					if (ci != null) {
						ci.unloadRecipe();
						ci.setResult(is);
						ci.setCompleteRecipe(recipe);
						ci.setMinLevel(minlevel);
						ci.setMaxLevel(maxlevel);
						ci.setValue(value);
						ci.setItemType(type);
						ci.loadRecipe();
						MMOHorsesAPI.getCraftModule().updateItem(ci);
					}
				}
			}
			sender.sendMessage(MMOHorsesAPI.getUtils().applyPrefix("Imported all items!"));
		} catch (IOException e) {
			e.printStackTrace();
			sender.sendMessage(MMOHorsesAPI.getUtils().applyPrefix("Error!"));
		}
		return true;
	}
}
