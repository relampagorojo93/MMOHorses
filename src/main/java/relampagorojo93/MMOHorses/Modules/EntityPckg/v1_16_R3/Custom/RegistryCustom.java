package relampagorojo93.MMOHorses.Modules.EntityPckg.v1_16_R3.Custom;

import java.lang.reflect.Field;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import com.mojang.datafixers.DataFixUtils;

import net.minecraft.server.v1_16_R3.EntityHorseAbstract;
import net.minecraft.server.v1_16_R3.EntityHorseDonkey;
import net.minecraft.server.v1_16_R3.EntityHorseMule;
import net.minecraft.server.v1_16_R3.EntityHorseSkeleton;
import net.minecraft.server.v1_16_R3.EntityHorseZombie;
import net.minecraft.server.v1_16_R3.EntityLlama;
import net.minecraft.server.v1_16_R3.EntityLlamaTrader;
import net.minecraft.server.v1_16_R3.EntityTypes;
import net.minecraft.server.v1_16_R3.DataConverterRegistry;
import net.minecraft.server.v1_16_R3.DataConverterTypes;
import net.minecraft.server.v1_16_R3.Entity;
import net.minecraft.server.v1_16_R3.EntityHorse;
import net.minecraft.server.v1_16_R3.EnumCreatureType;
import net.minecraft.server.v1_16_R3.IRegistry;
import net.minecraft.server.v1_16_R3.MinecraftKey;
import net.minecraft.server.v1_16_R3.SharedConstants;
import relampagorojo93.LibsCollection.Utils.Shared.Java.FieldHelper;
import relampagorojo93.MMOHorses.Enums.Type;
import relampagorojo93.MMOHorses.Modules.EntityPckg.Interfaces.Managers.RegistryManager;
import relampagorojo93.MMOHorses.Modules.EntityPckg.Interfaces.Objects.MMOHorse;
import relampagorojo93.MMOHorses.Modules.EntityPckg.v1_16_R3.Entities.DonkeyCustom;
import relampagorojo93.MMOHorses.Modules.EntityPckg.v1_16_R3.Entities.HorseCustom;
import relampagorojo93.MMOHorses.Modules.EntityPckg.v1_16_R3.Entities.LlamaCustom;
import relampagorojo93.MMOHorses.Modules.EntityPckg.v1_16_R3.Entities.MuleCustom;
import relampagorojo93.MMOHorses.Modules.EntityPckg.v1_16_R3.Entities.SkeletonHorseCustom;
import relampagorojo93.MMOHorses.Modules.EntityPckg.v1_16_R3.Entities.TraderLlamaCustom;
import relampagorojo93.MMOHorses.Modules.EntityPckg.v1_16_R3.Entities.ZombieHorseCustom;

public class RegistryCustom implements RegistryManager {

	public class CustomEntityType<T extends Entity> {
		
		private String name;
		private EntityTypes<? extends EntityHorseAbstract> entityType;
		private EntityTypes.b<T> entityTypeNew;

		private CustomEntityType(String name, EntityTypes<? extends EntityHorseAbstract> entityType,
				EntityTypes.b<T> entityTypeNew) {
			this.name = name;
			this.entityType = entityType;
			this.entityTypeNew = entityTypeNew;
		}
		
		public String getName() {
			return name;
		}

		public EntityTypes<? extends EntityHorseAbstract> getEntityType() {
			return entityType;
		}

		public EntityTypes.b<T> getEntityTypeNew() {
			return entityTypeNew;
		}
	}
	
	public final CustomEntityType<?>[] entities = new CustomEntityType<?> [] {
		new CustomEntityType<EntityHorseDonkey>("donkey", EntityTypes.DONKEY, DonkeyCustom::new),
		new CustomEntityType<EntityHorse>("horse", EntityTypes.HORSE, HorseCustom::new),
		new CustomEntityType<EntityLlama>("llama", EntityTypes.LLAMA, LlamaCustom::new),
		new CustomEntityType<EntityHorseMule>("mule", EntityTypes.MULE, MuleCustom::new),
		new CustomEntityType<EntityHorseSkeleton>("skeleton_horse", EntityTypes.SKELETON_HORSE, SkeletonHorseCustom::new),
		new CustomEntityType<EntityLlamaTrader>("trader_llama", EntityTypes.TRADER_LLAMA, TraderLlamaCustom::new),
		new CustomEntityType<EntityHorseZombie>("zombie_horse", EntityTypes.ZOMBIE_HORSE, ZombieHorseCustom::new)
	};

	@Override
	public void register() {
		for (CustomEntityType<?> type : entities) {
			register(type);
			oldregister(type);
		}
	}

	private void register(CustomEntityType<?> type) {
        try {
			Field field = EntityTypes.class.getDeclaredField("bf");
			FieldHelper.setFieldUsingUnsafe(field, type.getEntityType(), type.getEntityTypeNew());
        } catch (Exception e) { e.printStackTrace(); }
	}
	
	//Old system

	@SuppressWarnings("unchecked")
	private void oldregister(CustomEntityType<?> ent) {
		String name = "mmohorses_" + ent.getName();
		MinecraftKey minecraftKey = MinecraftKey.a(name);
		Map<Object, com.mojang.datafixers.types.Type<?>> typeMap = (Map<Object, com.mojang.datafixers.types.Type<?>>) DataConverterRegistry
				.a().getSchema(DataFixUtils.makeKey(SharedConstants.getGameVersion().getWorldVersion()))
				.findChoiceType(DataConverterTypes.ENTITY).types();
		try {
			Object o = ent.getEntityType().getClass().getMethod("i").invoke(ent.getEntityType());
			String key = (String) o.getClass().getMethod("getKey").invoke(o);
			typeMap.put(minecraftKey.toString(),
					typeMap.get("minecraft:" + MinecraftKey.a(key).getKey().split("/")[1]));
			EntityTypes.Builder<net.minecraft.server.v1_16_R3.Entity> entity = EntityTypes.Builder.a(ent.getEntityTypeNew(), EnumCreatureType.CREATURE);
			IRegistry.a(IRegistry.ENTITY_TYPE, name, entity.a(name));
		} catch (Exception e3) {
			Bukkit.getLogger().info("Error loading " + name);
		}
	}

	@Override
	public MMOHorse spawnEntity(Type type, Location l) {
		if (type == Type.DONKEY)
			return new DonkeyCustom(l);
		else if (type == Type.LLAMA)
			return new LlamaCustom(l);
		else if (type == Type.MULE)
			return new MuleCustom(l);
		else if (type == Type.SKELETONHORSE)
			return new SkeletonHorseCustom(l);
		else if (type == Type.TRADERLLAMA)
			return new TraderLlamaCustom(l);
		else if (type == Type.ZOMBIEHORSE)
			return new ZombieHorseCustom(l);
		else
			return new HorseCustom(l);
	}
	
}
