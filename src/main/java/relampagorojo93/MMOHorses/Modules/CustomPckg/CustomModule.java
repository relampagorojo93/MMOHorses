package relampagorojo93.MMOHorses.Modules.CustomPckg;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import relampagorojo93.MMOHorses.Enums.Colour;
import relampagorojo93.MMOHorses.Enums.Marking;
import relampagorojo93.MMOHorses.Modules.CustomPckg.Objects.CustomData;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingList;
import relampagorojo93.MMOHorses.Enums.Type;
import relampagorojo93.LibsCollection.SpigotDebug.Data.DebugErrorData;
import relampagorojo93.LibsCollection.SpigotPlugin.LoadOn;
import relampagorojo93.LibsCollection.SpigotPlugin.PluginModule;
import relampagorojo93.LibsCollection.YAMLLib.YAMLFile;
import relampagorojo93.LibsCollection.YAMLLib.Objects.Data;
import relampagorojo93.LibsCollection.YAMLLib.Objects.Section;
import relampagorojo93.MMOHorses.API.MMOHorsesAPI;

public class CustomModule extends PluginModule {

	@Override
	public boolean load() {
		YAMLFile yml;
		try {
			yml = new YAMLFile(MMOHorsesAPI.getFileModule().CUSTOM_FILE);
		} catch (IOException e1) {
			System.out.println(MMOHorsesAPI.getUtils().applyPrefix("There's something wrong trying to load the Custom.yml file"));
			return false;
		}
		for (Data data : yml.getSection("").getChilds()) {
			if (data.isSection()) {
				Section section = data.asSection(), child;
				try {
					Type type;
					Colour colour;
					Marking marking;
					String name = (child = section.getChild("Name")) != null ? child.getString()
							: SettingList.DEFAULTCLAIMNAMES.toList()
									.get((int) (SettingList.DEFAULTCLAIMNAMES.toList().size() * Math.random()));
					try {
						type = (child = section.getChild("Type")) != null ? Type.valueOf(child.getString(Type.values()[0].name()).toUpperCase())
								: Type.values()[0];
					} catch (Exception e) {
						type = Type.values()[0];
					}
					try {
						colour = (child = section.getChild("Color")) != null
								? Colour.valueOf(child.getString(Colour.values()[0].name()).toUpperCase())
								: Colour.values()[0];
					} catch (Exception e) {
						colour = Colour.values()[0];
					}
					try {
						marking = (child = section.getChild("Marking")) != null
								? Marking.valueOf(child.getString(Marking.values()[0].name()).toUpperCase())
								: Marking.values()[0];
					} catch (Exception e) {
						marking = Marking.values()[0];
					}
					double jump = (child = section.getChild("Jump")) != null ? child.getDouble(2.0D) : 2.0D;
					double speed = (child = section.getChild("Speed")) != null ? child.getDouble(6.0D) : 6.0D;
					double health = (child = section.getChild("Health")) != null ? child.getDouble(20.0D) : 20.0D;
					String saddle = (child = section.getChild("Saddle")) != null ? child.getString() : null;
					String armor = (child = section.getChild("Armor")) != null ? child.getString() : null;
					String jump_upgrade = (child = section.getChild("Jump_upgrade")) != null
							? child.getString()
							: null;
					String speed_upgrade = (child = section.getChild("Speed_upgrade")) != null
							? child.getString()
							: null;
					String health_upgrade = (child = section.getChild("Health_upgrade")) != null
							? child.getString()
							: null;
					String chest_upgrade = (child = section.getChild("Chest_upgrade")) != null
							? child.getString()
							: null;
					this.ced.put(section.getName().toUpperCase(), new CustomData(name, type, colour, marking, jump,
							speed, health, armor, saddle, jump_upgrade, speed_upgrade, health_upgrade, chest_upgrade));
				} catch (Exception e) {
					MMOHorsesAPI.getDebugController().addDebugData(new DebugErrorData("There's something wrong with the custom entity " + section.getName() + ". Error: " + e.getMessage()));
				}
			}
		}
		return true;
	}

	@Override
	public boolean unload() {
		this.ced.clear();
		return true;
	}

	@Override
	public LoadOn loadOn() {
		return LoadOn.ENABLE;
	}

	@Override
	public boolean optional() {
		return false;
	}

	@Override
	public boolean allowReload() {
		return true;
	}
	
	private HashMap<String, CustomData> ced = new HashMap<>();

	public List<String> getIDs() {
		return new ArrayList<>(this.ced.keySet());
	}

	public CustomData getCustomEntityData(String id) {
		return this.ced.get(id.toUpperCase());
	}
}
