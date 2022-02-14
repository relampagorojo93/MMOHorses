package relampagorojo93.MMOHorses.Bukkit.Inventories;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import relampagorojo93.LibsCollection.SpigotMessages.MessagesUtils;
import relampagorojo93.MMOHorses.Modules.CraftPckg.Objects.CraftableItem;
import relampagorojo93.MMOHorses.Modules.HorsePckg.Objects.ClaimedData;

public class Sorters {
	public static final List<Sorter<ClaimedData>> HORSE_SORTERS = Arrays.asList(
			new Sorter<>("none", (h1, h2) -> 0),
			new Sorter<>("name", (h1, h2) -> MessagesUtils.strip(h1.getData().getName().toLowerCase()).compareTo(MessagesUtils.strip(h2.getData().getName().toLowerCase()))),
			new Sorter<>("owner", (h1, h2) -> h1.getData().getOwner().compareTo(h2.getData().getOwner())),
			new Sorter<>("type", (h1, h2) -> Integer.compare(h1.getData().getType().ordinal(), h2.getData().getType().ordinal())),
			new Sorter<>("summoned", (h1, h2) -> Boolean.compare(h2.getMMOHorse() != null, h1.getMMOHorse() != null))
		);
	
	public static final List<Sorter<CraftableItem>> ITEM_SORTERS = Arrays.asList(
			new Sorter<>("none", (h1, h2) -> 0),
			new Sorter<>("name", (it1, it2) -> MessagesUtils.strip(it1.getName().toLowerCase()).compareTo(MessagesUtils.strip(it2.getName().toLowerCase()))),
			new Sorter<>("type", (it1, it2) -> Integer.compare(it1.getType().ordinal(), it2.getType().ordinal()))
		);

	public static class Sorter<T> {

		private String name;
		private Comparator<T> comparator;

		public Sorter(String name, Comparator<T> comparator) {
			this.name = name;
			this.comparator = comparator;
		}

		public String getName() {
			return this.name;
		}
		
		public String getCapitalizedName() {
			return this.name.substring(0, 1).toUpperCase() + this.name.substring(1).toLowerCase();
		}

		public Comparator<T> getComparator() {
			return this.comparator;
		}

	}
	
}
