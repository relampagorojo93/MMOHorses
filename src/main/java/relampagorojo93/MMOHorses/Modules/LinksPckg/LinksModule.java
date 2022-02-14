package relampagorojo93.MMOHorses.Modules.LinksPckg;

import java.util.ArrayList;
import java.util.List;

import relampagorojo93.MMOHorses.Modules.HorsePckg.Objects.ClaimedData;
import relampagorojo93.LibsCollection.SpigotPlugin.LoadOn;
import relampagorojo93.LibsCollection.SpigotPlugin.PluginModule;
import relampagorojo93.LibsCollection.YAMLLib.YAMLFile;
import relampagorojo93.MMOHorses.API.MMOHorsesAPI;

public class LinksModule extends PluginModule {

	@Override
	public boolean load() {
		try {
			YAMLFile links = new YAMLFile(MMOHorsesAPI.getFileModule().LINKS_FILE);
			if (links.getSection("Locations") != null) locations = links.getSection("Locations").getStringList();
			links.reset();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean unload() {
		saveLinks();
		locations.clear();
		return true;
	}

	@Override
	public LoadOn loadOn() {
		return LoadOn.LOAD;
	}

	@Override
	public boolean optional() {
		return false;
	}

	@Override
	public boolean allowReload() {
		return false;
	}

	private List<String> locations = new ArrayList<>();

	public LinksModule() {
	}

	public List<String> getLocations() {
		return this.locations;
	}

	public void saveLinks() {
		List<String> locs = new ArrayList<>();
		YAMLFile yml = new YAMLFile();
		for (ClaimedData hi : MMOHorsesAPI.getHorseModule().getHorseObjects())
			if (hi.getSettings().isLinked() && hi.getMMOHorse() != null)
				locs.add(MMOHorsesAPI.getUtils().locParse(hi.getMMOHorse().getLocation()));
		yml.setSection("Locations", locs);
		try {
			yml.saveYAML(MMOHorsesAPI.getFileModule().LINKS_FILE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
