package relampagorojo93.MMOHorses.Modules.CraftPckg;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import relampagorojo93.LibsCollection.SpigotDebug.Data.DebugErrorData;
import relampagorojo93.LibsCollection.SpigotPlugin.LoadOn;
import relampagorojo93.LibsCollection.SpigotPlugin.PluginModule;
import relampagorojo93.LibsCollection.Utils.Bukkit.ItemStacksUtils;
import relampagorojo93.LibsCollection.Utils.Bukkit.TasksUtils;
import relampagorojo93.MMOHorses.API.MMOHorsesAPI;
import relampagorojo93.MMOHorses.Enums.ItemType;
import relampagorojo93.MMOHorses.Modules.CraftPckg.Objects.CraftableItem;
import relampagorojo93.MMOHorses.Modules.HorsePckg.Objects.ClaimedData;

public class CraftModule extends PluginModule {

	@Override
	public boolean load() {
		List<Integer> ids = new ArrayList<>();
		ResultSet set = MMOHorsesAPI.getSQLModule().getItems();
		if (set != null) {
			try {
				while (set.next()) {
					CraftableItem ci;
					int id = set.getInt("id");
					if ((ci = getCraftableItem(id)) != null) {
						ci.setCompleteRecipe(ItemStacksUtils.itemsParse(set.getBytes("recipe")));
						ci.setItemType(ItemType.values()[set.getInt("item_type")]);
						ci.setMinLevel(set.getInt("min_level"));
						ci.setResult(ItemStacksUtils.itemsParse(set.getBytes("result"))[0]);
						ci.setValue(set.getFloat("value"));
					} else {
						try {
							this.items.add(new CraftableItem(set.getInt("id"), set.getString("name"),
									ItemStacksUtils.itemsParse(set.getBytes("recipe")),
									ItemStacksUtils.itemsParse(set.getBytes("result"))[0],
									ItemType.values()[set.getInt("item_type")],
									set.getInt("min_level"), set.getInt("max_level"), set.getFloat("value")));
						} catch (Exception e) {
							MMOHorsesAPI.getDebugController().addDebugData(new DebugErrorData("Seems like the recipe with name "
											+ set.getString("name") + " contains wrong data. Error: " + e.getMessage()));
						}
					}
					ids.add(id);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		for (CraftableItem item : new ArrayList<>(items)) {
			if (!ids.contains((Integer) item.getId())) {
				items.remove(item);
				if (MMOHorsesAPI.getHorseModule() != null)
					for (ClaimedData h : MMOHorsesAPI.getHorseModule().getHorseObjects())
						h.getUpgrades().removeItem(item, true);
			}
			else
				item.loadRecipe();
		}
		return true;
	}

	@Override
	public boolean unload() {
		for (CraftableItem item : this.items)
			item.unloadRecipe();
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
		return true;
	}
	
	private List<CraftableItem> items = new ArrayList<>();

	public void addItem(String name, ItemStack result, ItemType type) {
		int id = MMOHorsesAPI.getSQLModule().registerItem(name, new ItemStack[9], result, type, 1, 100, 0.0F);
		if (id != -1)
			this.items.add(new CraftableItem(id, name, new ItemStack[9], result, type, 1, 100, 0.0F));
	}

	public void updateItem(CraftableItem ci) {
		TasksUtils.execute(MMOHorsesAPI.getPlugin(), () -> MMOHorsesAPI.getSQLModule().updateItem(ci), true);
		for (ClaimedData horse:MMOHorsesAPI.getHorseModule().getHorseObjects()) if (horse.getMMOHorse() != null && horse.getUpgrades().getArmorItem() == ci) horse.getMMOHorse().getMMOHorseData().updateAll();
	}

	public void removeItem(CraftableItem ci) {
		ci.unloadRecipe();
		this.items.remove(ci);
		TasksUtils.execute(MMOHorsesAPI.getPlugin(), () -> MMOHorsesAPI.getSQLModule().deleteItem(ci.getId()), true);
		for (ClaimedData i : MMOHorsesAPI.getHorseModule().getHorseObjects())
			i.getUpgrades().removeItem(ci, true);
	}

	public List<CraftableItem> getCraftableItems() {
		return this.items;
	}

	public CraftableItem getCraftableItem(ShapedRecipe recipe) {
		for (CraftableItem item : this.items) {
			boolean empty = true;
			byte b;
			int i;
			ItemStack[] arrayOfItemStack;
			for (i = (arrayOfItemStack = item.getCompleteRecipe()).length, b = 0; b < i;) {
				ItemStack it = arrayOfItemStack[b];
				if (it != null && it.getType() != Material.AIR) {
					empty = false;
					break;
				}
				b++;
			}
			if (!empty && recipe.getResult().isSimilar(item.getRecipe().getResult())) {
				boolean ifound = true;
				for (int j = 0; j < (recipe.getShape()).length && j < (item.getRecipe().getShape()).length; j++) {
					char[] shape1 = recipe.getShape()[j].toCharArray();
					char[] shape2 = item.getRecipe().getShape()[j].toCharArray();
					for (int k = 0; k < shape1.length && k < shape2.length; k++) {
						ItemStack i1 = (ItemStack) recipe.getIngredientMap().get(Character.valueOf(shape1[k]));
						ItemStack i2 = (ItemStack) item.getRecipe().getIngredientMap()
								.get(Character.valueOf(shape2[k]));
						if ((i1 == null) ? (i2 != null) : !i1.isSimilar(i2)) {
							ifound = false;
							break;
						}
					}
				}
				if (ifound)
					return item;
			}
		}
		return null;
	}

	public CraftableItem getCraftableItem(ItemStack itemstack) {
		for (CraftableItem item : this.items)
			if (item.isItem(itemstack))
				return item;
		return null;
	}

	public CraftableItem getCraftableItem(String name) {
		for (CraftableItem item : this.items)
			if (item.getName().equalsIgnoreCase(name))
				return item;
		return null;
	}

	public CraftableItem getCraftableItem(int id) {
		for (CraftableItem item : this.items)
			if (item.getId() == id)
				return item;
		return null;
	}
}
