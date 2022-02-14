package relampagorojo93.MMOHorses.Modules.EntityPckg.v1_17_R1.Custom;

import java.lang.reflect.Field;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import com.mojang.datafixers.DataFixUtils;

import net.minecraft.SharedConstants;
import net.minecraft.core.IRegistry;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.util.datafix.DataConverterRegistry;
import net.minecraft.util.datafix.fixes.DataConverterTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumCreatureType;
import net.minecraft.world.entity.animal.horse.*;
import relampagorojo93.LibsCollection.Utils.Shared.Java.FieldHelper;
import relampagorojo93.MMOHorses.Enums.Type;
import relampagorojo93.MMOHorses.Modules.EntityPckg.Interfaces.Managers.RegistryManager;
import relampagorojo93.MMOHorses.Modules.EntityPckg.Interfaces.Objects.MMOHorse;
import relampagorojo93.MMOHorses.Modules.EntityPckg.v1_17_R1.Entities.*;

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

	public final CustomEntityType<?>[] entities = new CustomEntityType<?>[] {
			new CustomEntityType<EntityHorseDonkey>("donkey", EntityTypes.q, DonkeyCustom::new),
			new CustomEntityType<EntityHorse>("horse", EntityTypes.M, HorseCustom::new),
			new CustomEntityType<EntityLlama>("llama", EntityTypes.V, LlamaCustom::new),
			new CustomEntityType<EntityHorseMule>("mule", EntityTypes.ag, MuleCustom::new),
			new CustomEntityType<EntityHorseSkeleton>("skeleton_horse", EntityTypes.aC, SkeletonHorseCustom::new),
			new CustomEntityType<EntityLlamaTrader>("trader_llama", EntityTypes.aR, TraderLlamaCustom::new),
			new CustomEntityType<EntityHorseZombie>("zombie_horse", EntityTypes.bf, ZombieHorseCustom::new) };

	@Override
	public void register() {
		for (CustomEntityType<?> type : entities) {
			register(type);
			oldregister(type);
		}
	}

	private void register(CustomEntityType<?> type) {
		try {
			Field field = EntityTypes.class.getDeclaredField("bm");
			FieldHelper.setFieldUsingUnsafe(field, type.getEntityType(), type.getEntityTypeNew());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private void oldregister(CustomEntityType<?> ent) {
		String name = "mmohorses_" + ent.getName();
		MinecraftKey minecraftKey = MinecraftKey.a(name);
		Map<Object, com.mojang.datafixers.types.Type<?>> typeMap = (Map<Object, com.mojang.datafixers.types.Type<?>>) DataConverterRegistry
				.a().getSchema(DataFixUtils.makeKey(SharedConstants.getGameVersion().getWorldVersion()))
				.findChoiceType(DataConverterTypes.q).types();
		try {
			Object o = ent.getEntityType().getClass().getMethod("j").invoke(ent.getEntityType());
			String key = (String) o.getClass().getMethod("getKey").invoke(o);
			typeMap.put(minecraftKey.toString(),
					typeMap.get("minecraft:" + MinecraftKey.a(key).getKey().split("/")[1]));
			EntityTypes.Builder<net.minecraft.world.entity.Entity> entity = EntityTypes.Builder
					.a(ent.getEntityTypeNew(), EnumCreatureType.b);
			IRegistry.a(IRegistry.Y, name, entity.a(name));
		} catch (Exception e3) {
			e3.printStackTrace();
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
