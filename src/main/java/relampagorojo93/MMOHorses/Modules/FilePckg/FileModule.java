package relampagorojo93.MMOHorses.Modules.FilePckg;

import java.io.File;
import java.util.HashMap;
import java.util.Map.Entry;

import relampagorojo93.LibsCollection.SpigotPlugin.LoadOn;
import relampagorojo93.LibsCollection.SpigotPlugin.PluginModule;
import relampagorojo93.LibsCollection.YAMLLib.YAMLFile;
import relampagorojo93.LibsCollection.YAMLLib.YAMLUtils;
import relampagorojo93.LibsCollection.YAMLLib.Objects.Section;
import relampagorojo93.MMOHorses.API.MMOHorsesAPI;
import relampagorojo93.MMOHorses.Enums.WalkMode;
import relampagorojo93.MMOHorses.Modules.FilePckg.Messages.MessageList;
import relampagorojo93.MMOHorses.Modules.FilePckg.Messages.MessageString;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingBoolean;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingDouble;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingInt;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingList;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingString;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingWalkmode;

public class FileModule extends PluginModule {

	@Override
	public boolean load() {
		if (!PLUGIN_FOLDER.exists()) {
			PLUGIN_FOLDER.mkdir();
			YAMLUtils.createYml(new File(PLUGIN_FOLDER.getPath() + "/Items.yml"),
					MMOHorsesAPI.getPlugin().getResource("relampagorojo93/MMOHorses/Resources/Items.yml"));
		}
		if (!CUSTOM_FILE.exists())
			YAMLUtils.createYml(CUSTOM_FILE,
					MMOHorsesAPI.getPlugin().getResource("relampagorojo93/MMOHorses/Resources/Custom.yml"));
		if (!CRATES_FILE.exists())
			YAMLUtils.createYml(CRATES_FILE,
					MMOHorsesAPI.getPlugin().getResource("relampagorojo93/MMOHorses/Resources/Crates.yml"));
		if (!CONFIG_FILE.exists())
			YAMLUtils.createYml(CONFIG_FILE,
					MMOHorsesAPI.getPlugin().getResource("relampagorojo93/MMOHorses/Resources/Config.yml"));
		if (!LANG_FILE.exists())
			YAMLUtils.createYml(LANG_FILE,
					MMOHorsesAPI.getPlugin().getResource("relampagorojo93/MMOHorses/Resources/Lang.yml"));
		if (!LINKS_FILE.exists())
			YAMLUtils.createYml(LINKS_FILE);
		if (!NPCS_FILE.exists())
			YAMLUtils.createYml(NPCS_FILE);
		// Config generation
		try {
			File newConfig = new File(CONFIG_FILE.getPath() + ".new");
			YAMLUtils.createYml(newConfig,
					MMOHorsesAPI.getPlugin().getResource("relampagorojo93/MMOHorses/Resources/Config.yml"));
			fileVersionCheck(CONFIG_FILE, newConfig, new HashMap<>());
			YAMLFile configYml = new YAMLFile(CONFIG_FILE);
			for (SettingString str : SettingString.values()) {
				Section nw = configYml.getSection(str.getPath());
				if (!str.getPath().equals(str.getOldPath())) {
					Section old = configYml.getSection(str.getOldPath());
					if (old != null) {
						if (nw == null)
							nw = configYml.setSection(str.getPath(), old.getData());
						configYml.removeSection(str.getOldPath());
					}
				}
				if (nw == null)
					nw = configYml.setSection(str.getPath(), str.getDefaultContent());
				str.setContent(nw.getString(str.getDefaultContent()));
			}
			for (SettingBoolean bool : SettingBoolean.values()) {
				Section nw = configYml.getSection(bool.getPath());
				if (!bool.getPath().equals(bool.getOldPath())) {
					Section old = configYml.getSection(bool.getOldPath());
					if (old != null) {
						if (nw == null)
							nw = configYml.setSection(bool.getPath(), old.getData());
						configYml.removeSection(bool.getOldPath());
					}
				}
				if (nw == null)
					nw = configYml.setSection(bool.getPath(), bool.getDefaultContent());
				bool.setContent(nw.getBoolean(bool.getDefaultContent()));
			}
			for (SettingDouble doub : SettingDouble.values()) {
				Section nw = configYml.getSection(doub.getPath());
				if (!doub.getPath().equals(doub.getOldPath())) {
					Section old = configYml.getSection(doub.getOldPath());
					if (old != null) {
						if (nw == null)
							nw = configYml.setSection(doub.getPath(), old.getData());
						configYml.removeSection(doub.getOldPath());
					}
				}
				if (nw == null)
					nw = configYml.setSection(doub.getPath(), doub.getDefaultContent());
				doub.setContent(nw.getDouble(doub.getDefaultContent()));
			}
			for (SettingInt inte : SettingInt.values()) {
				Section nw = configYml.getSection(inte.getPath());
				if (!inte.getPath().equals(inte.getOldPath())) {
					Section old = configYml.getSection(inte.getOldPath());
					if (old != null) {
						if (nw == null)
							nw = configYml.setSection(inte.getPath(), old.getData());
						configYml.removeSection(inte.getOldPath());
					}
				}
				if (nw == null)
					nw = configYml.setSection(inte.getPath(), inte.getDefaultContent());
				inte.setContent(nw.getInteger(inte.getDefaultContent()));
			}
			for (SettingWalkmode wm : SettingWalkmode.values()) {
				Section nw = configYml.getSection(wm.getPath());
				if (!wm.getPath().equals(wm.getOldPath())) {
					Section old = configYml.getSection(wm.getOldPath());
					if (old != null) {
						if (nw == null)
							nw = configYml.setSection(wm.getPath(), old.getData());
						configYml.removeSection(wm.getOldPath());
					}
				}
				if (nw == null)
					nw = configYml.setSection(wm.getPath(), wm.getDefaultContent());
				try {
					wm.setContent(WalkMode.valueOf(nw.getString(wm.getDefaultContent().name()).toUpperCase()));
				} catch (Exception e) {
					wm.setContent(WalkMode.GALLOP);
				}
			}
			for (SettingList list : SettingList.values()) {
				Section nw = configYml.getSection(list.getPath());
				if (!list.getPath().equals(list.getOldPath())) {
					Section old = configYml.getSection(list.getOldPath());
					if (old != null) {
						if (nw == null)
							nw = configYml.setSection(list.getPath(), old.getData());
						configYml.removeSection(list.getOldPath());
					}
				}
				if (nw == null)
					nw = configYml.setSection(list.getPath(), list.getDefaultContent());
				list.setContent(nw.getStringList(list.getDefaultContent()));
			}
			configYml.saveYAML(CONFIG_FILE);
			configYml.reset();
			// Lang generation
			File newLang = new File(LANG_FILE.getPath() + ".new");
			YAMLUtils.createYml(newLang,
					MMOHorsesAPI.getPlugin().getResource("relampagorojo93/MMOHorses/Resources/Lang.yml"));
			fileVersionCheck(LANG_FILE, newLang, new HashMap<>());
			YAMLFile langYml = new YAMLFile(LANG_FILE);
			for (MessageString ms : MessageString.values()) {
				Section nw = langYml.getSection(ms.getPath());
				if (!ms.getPath().equals(ms.getOldPath())) {
					Section old = langYml.getSection(ms.getOldPath());
					if (old != null) {
						if (nw == null)
							nw = langYml.setSection(ms.getPath(), old.getData());
						langYml.removeSection(ms.getOldPath());
					}
				}
				if (nw == null)
					nw = langYml.setSection(ms.getPath(), ms.getDefaultContent());
				ms.setContent(nw.getString(ms.getDefaultContent()));
			}
			for (MessageList ml : MessageList.values()) {
				Section nw = langYml.getSection(ml.getPath());
				if (!ml.getPath().equals(ml.getOldPath())) {
					Section old = langYml.getSection(ml.getOldPath());
					if (old != null) {
						if (nw == null)
							nw = langYml.setSection(ml.getPath(), old.getData());
						langYml.removeSection(ml.getOldPath());
					}
				}
				if (nw == null)
					nw = langYml.setSection(ml.getPath(), ml.getDefaultContent());
				ml.setContent(nw.getStringList(ml.getDefaultContent()));
			}
			langYml.saveYAML(LANG_FILE);
			langYml.reset();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean unload() {
		return true;
	}

	@Override
	public LoadOn loadOn() {
		return LoadOn.BEFORE_LOAD;
	}

	@Override
	public boolean optional() {
		return false;
	}

	@Override
	public boolean allowReload() {
		return true;
	}

	public final File PLUGIN_FOLDER = new File("plugins/MMOHorses");
	public final File CUSTOM_FILE = new File(PLUGIN_FOLDER.getPath() + "/Custom.yml");
	public final File CRATES_FILE = new File(PLUGIN_FOLDER.getPath() + "/Crates.yml");
	public final File CONFIG_FILE = new File(PLUGIN_FOLDER.getPath() + "/Config.yml");
	public final File LANG_FILE = new File(PLUGIN_FOLDER.getPath() + "/Lang.yml");
	public final File LINKS_FILE = new File(PLUGIN_FOLDER.getPath() + "/Links.yml");
	public final File NPCS_FILE = new File(PLUGIN_FOLDER.getPath() + "/NPCs.yml");

	private void fileVersionCheck(File oldfile, File newfile, HashMap<String, String> oldtonew) {
		try {
			YAMLFile oldyaml = new YAMLFile(oldfile), newyaml = new YAMLFile(newfile);
			int oldversion = oldyaml.getSection("Version", new Section(0)).getInteger(),
					newversion = newyaml.getSection("Version", new Section(0)).getInteger();
			Section oldsection;
			if (oldversion < newversion) {
				for (Section newsection : newyaml.getSections())
					if ((oldsection = oldyaml.getSection(newsection.getPath())) != null)
						newsection.setData(oldsection.getData());
				for (Entry<String, String> entry : oldtonew.entrySet())
					if ((oldsection = oldyaml.getSection(entry.getKey())) != null)
						newyaml.setSection(entry.getValue(), oldsection.getData());
				newyaml.saveYAML(oldfile);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		newfile.delete();
	}
}
