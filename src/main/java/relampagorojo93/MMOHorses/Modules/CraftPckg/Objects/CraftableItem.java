package relampagorojo93.MMOHorses.Modules.CraftPckg.Objects;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;

import relampagorojo93.LibsCollection.Utils.Bukkit.ItemStacksUtils;
import relampagorojo93.MMOHorses.API.MMOHorsesAPI;
import relampagorojo93.MMOHorses.Enums.ItemType;

public class CraftableItem {
	private int id;
	private String name;
	private ItemType type;
	private ItemStack[] recipe;
	private ItemStack result;
	private int min_level, max_level;
	private float value;
	private ShapedRecipe mrecipe;

	public CraftableItem(int id, String name, ItemStack[] recipe, ItemStack result, ItemType type, int min_level, int max_level,
			float value) {
		this.id = id;
		this.name = name;
		this.recipe = recipe;
		this.result = result;
		this.type = type;
		this.min_level = min_level;
		this.max_level = max_level;
		this.value = value;
	}

	public void unloadRecipe() {
		if (this.mrecipe != null) {
			for (Player pl:Bukkit.getOnlinePlayers()) ItemStacksUtils.undiscoverRecipe(pl, mrecipe);
			Iterator<Recipe> it = Bukkit.recipeIterator();
			List<Recipe> list = new ArrayList<>();
			try {
				boolean found = false;
				while (it.hasNext()) {
					Recipe recipe = it.next();
					if (!found) {
						if (recipe instanceof ShapedRecipe) {
							ShapedRecipe srecipe = (ShapedRecipe) recipe;
							if (srecipe.getResult().isSimilar(mrecipe.getResult())) {
								boolean ifound = true;
								for (int i = 0; i < srecipe.getShape().length && i < mrecipe.getShape().length; i++) {
									char[] shape1 = srecipe.getShape()[i].toCharArray();
									char[] shape2 = mrecipe.getShape()[i].toCharArray();
									for (int j = 0; j < shape1.length && j < shape2.length; j++) {
										ItemStack i1 = srecipe.getIngredientMap().get((Character) shape1[j]);
										ItemStack i2 = mrecipe.getIngredientMap().get((Character) shape2[j]);
										if (i1 == null ? i2 != null : !i1.isSimilar(i2)) {
											ifound = false;
											break;
										}
									}
								}
								if (ifound)
									continue;
							}
						}
					}
					list.add(recipe);
				}
			} catch (NoSuchElementException e) {
			}
			Bukkit.clearRecipes();
			for (Recipe recipe : list)
				Bukkit.addRecipe(recipe);
		}
	}

	public void loadRecipe() {
		if (recipe != null) {
			boolean found = false;
			for (ItemStack i : recipe) 
				if (i != null && i.getType() != Material.AIR) {
					found = true;
					break;
				}
			if (!found)
				return;
			try {
				Class<?> nk = Class.forName("org.bukkit.NamespacedKey");
				this.mrecipe = ShapedRecipe.class.getConstructor(nk, ItemStack.class).newInstance(
						nk.getConstructor(Plugin.class, String.class).newInstance(MMOHorsesAPI.getPlugin(), name),
						result);
			} catch (Exception e) {
				e.printStackTrace();
				try {
					this.mrecipe = ShapedRecipe.class.getConstructor(ItemStack.class).newInstance(result);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			String set = "";
			char c = 97;
			for (ItemStack r : recipe) {
				if (r != null)
					set += c++;
				else
					set += ' ';
			}
			mrecipe.shape(set.substring(0, 3), set.substring(3, 6), set.substring(6));
			c = 97;
			for (ItemStack r : recipe)
				if (r != null)
					mrecipe.setIngredient(c++, r.getType());
			Bukkit.addRecipe(mrecipe);
			for (Player pl:Bukkit.getOnlinePlayers()) ItemStacksUtils.discoverRecipe(pl, mrecipe);
		} else
			mrecipe = null;
	}

	@Override
	public boolean equals(Object o) {
		return o instanceof CraftableItem && ((CraftableItem) o).id == id;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public ItemType getType() {
		return type;
	}

	public ShapedRecipe getRecipe() {
		return mrecipe;
	}

	public ItemStack[] getCompleteRecipe() {
		return recipe;
	}

	public int getMinLevel() {
		return min_level;
	}

	public int getMaxLevel() {
		return max_level;
	}
	
	public float getValue() {
		return value;
	}

	public boolean isItem(ItemStack itemstack) {
		return result.isSimilar(itemstack);
	}

	public ItemStack getResult() {
		return result.clone();
	}

	public ItemStack getResult(ItemStack[] recipe) {
		for (int i = 0; i < recipe.length; i++) {
			ItemStack it1 = this.recipe[i];
			if (it1 == null)
				it1 = new ItemStack(Material.AIR);
			ItemStack it2 = recipe[i];
			if (it2 == null)
				it2 = new ItemStack(Material.AIR);
			if (!it1.isSimilar(it2))
				return null;
		}
		return getResult();
	}

	public void setItemType(ItemType type) {
		this.type = type;
	}

	public void setCompleteRecipe(ItemStack[] recipe) {
		this.recipe = recipe;
	}

	public void setResult(ItemStack result) {
		this.result = result;
	}

	public void setValue(float value) {
		this.value = value;
	}

	public void setMinLevel(int min_level) {
		this.min_level = min_level;
	}
	
	public void setMaxLevel(int max_level) {
		this.max_level = max_level;
	}
}
