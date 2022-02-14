package relampagorojo93.MMOHorses.Modules.EntityPckg.Interfaces.Managers;

import org.bukkit.inventory.ItemStack;

import relampagorojo93.MMOHorses.Modules.HorsePckg.Objects.ClaimedData;

public interface CatchersManager {
	
	public abstract ItemStack insertData(ItemStack i, ClaimedData data);
	
	public abstract ItemStack insertData(ItemStack i, String customdata);
	
	public abstract String getType(ItemStack i);
	
	public abstract String getCustom(ItemStack i);
	
	public abstract int getId(ItemStack i);
	
}
