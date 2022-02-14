package relampagorojo93.MMOHorses.Modules.EntityPckg.v1_12_R1.Entities.Pathfinders;

import java.util.List;

import org.bukkit.entity.Player;

import net.minecraft.server.v1_12_R1.Entity;
import net.minecraft.server.v1_12_R1.EntityHorseAbstract;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import net.minecraft.server.v1_12_R1.PathfinderGoalTame;
import relampagorojo93.LibsCollection.SpigotMessages.MessagesUtils;
import relampagorojo93.MMOHorses.API.MMOHorsesAPI;
import relampagorojo93.MMOHorses.Modules.EntityPckg.v1_12_R1.Entities.NMSMMOHorse;
import relampagorojo93.MMOHorses.Modules.FilePckg.Messages.MessageString;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingBoolean;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingList;

public class MMOHorsePathfinderGoalTame extends PathfinderGoalTame {

	private NMSMMOHorse entity;

	public MMOHorsePathfinderGoalTame(NMSMMOHorse entityhorseabstract) {
		super((EntityHorseAbstract) entityhorseabstract, 1.2D);
		this.entity = entityhorseabstract;
	}

	@Override
	public void e() {
		if (!this.entity.isCustom())
			super.e();
		else {
			super.e();
			if (this.entity.isTamed() && SettingBoolean.CLAIMONTAMING.toBoolean()) {
				if (!SettingBoolean.valueOf("SUPPORT_" + entity.getType().name()).toBoolean())
					return;
				List<Entity> ents = this.entity.getPassengers();
				if (ents.size() == 0)
					return;
				Entity ent = ents.get(0);
				if (ent instanceof EntityPlayer) {
					Player pl = (Player) ent.getBukkitEntity();
					if (!MMOHorsesAPI.getHorseModule().reachedMaxClaimed(pl)
							&& !MMOHorsesAPI.getHorseModule().reachedMaxSummoned(pl)) {
						String name = (this.entity.getCustomEntityName() == null
								|| this.entity.getCustomEntityName().isEmpty())
										? SettingList.DEFAULTCLAIMNAMES.toList().get(
												(int) (SettingList.DEFAULTCLAIMNAMES.toList().size() * Math.random()))
										: this.entity.getCustomEntityName();
						if (name.length() <= 32) {
							if (MMOHorsesAPI.getHorseModule().registerHorseObject(pl, false, this.entity, pl.getUniqueId(),
									name) != null)
								MessagesUtils.getMessageBuilder()
										.createMessage(MMOHorsesAPI.getUtils().applyPrefix(
												MessageString.HORSECLAIMED.toString().replace("%name%", name)))
										.sendMessage(pl);
						} else
							MessagesUtils.getMessageBuilder()
									.createMessage(
											MMOHorsesAPI.getUtils().applyPrefix(MessageString.NAMEMAXLENGTHREACHED))
									.sendMessage(pl);
					}
				}
			}
		}
	}
}
