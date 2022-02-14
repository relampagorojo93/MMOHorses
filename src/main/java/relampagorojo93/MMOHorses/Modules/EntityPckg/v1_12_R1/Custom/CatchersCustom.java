package relampagorojo93.MMOHorses.Modules.EntityPckg.v1_12_R1.Custom;


import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_12_R1.NBTTagCompound;
import relampagorojo93.MMOHorses.Modules.EntityPckg.Interfaces.Managers.CatchersManager;
import relampagorojo93.MMOHorses.Modules.HorsePckg.Objects.ClaimedData;

public class CatchersCustom implements CatchersManager {
	
	public ItemStack insertData(ItemStack i, ClaimedData data) {
		return this.insertData(i, "horse_type", "claimed", "horse_id", String.valueOf(data.getData().getId()));
	}
	
	public ItemStack insertData(ItemStack i, String customdata) {
		return this.insertData(i, "horse_type", "custom", "horse_custom_data_id", customdata);
	}
	
	private ItemStack insertData(ItemStack i, String field1, String value1, String field2, String value2) {
		try {
			net.minecraft.server.v1_12_R1.ItemStack item = CraftItemStack.asNMSCopy(i);
			NBTTagCompound comp = item.getTag();
			comp.setString(field1, value1);
			comp.setString(field2, value2);
			return CraftItemStack.asBukkitCopy(item);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getType(ItemStack i) {
		NBTTagCompound tag = CraftItemStack.asNMSCopy(i).getTag();
		return tag != null ? tag.getString("horse_type") : null;
	}

	@Override
	public String getCustom(ItemStack i) {
		NBTTagCompound tag = CraftItemStack.asNMSCopy(i).getTag();
		return tag != null ? tag.getString("horse_custom_data_id") : null;
	}

	@Override
	public int getId(ItemStack i) {
		NBTTagCompound tag = CraftItemStack.asNMSCopy(i).getTag();
		return tag != null ? tag.getInt("horse_id") : null;
	}
	
}
