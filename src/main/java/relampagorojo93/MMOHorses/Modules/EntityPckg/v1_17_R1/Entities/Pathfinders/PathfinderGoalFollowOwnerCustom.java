package relampagorojo93.MMOHorses.Modules.EntityPckg.v1_17_R1.Entities.Pathfinders;

import java.util.EnumSet;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import net.minecraft.world.entity.ai.goal.PathfinderGoal;
import net.minecraft.world.entity.ai.navigation.NavigationAbstract;
import relampagorojo93.MMOHorses.API.MMOHorsesAPI;
import relampagorojo93.MMOHorses.Modules.EntityPckg.v1_17_R1.Entities.NMSMMOHorse;

public class PathfinderGoalFollowOwnerCustom extends PathfinderGoal {
	
	private final NMSMMOHorse entity;

	private Player player;

	private NavigationAbstract navigation;

	private boolean running;

	public PathfinderGoalFollowOwnerCustom(NMSMMOHorse entity) {
		this.entity = entity;
		this.navigation = this.entity.getNMSEntity().getNavigation();
	}

	public boolean a() {
		if (entity.isCustom()) {
			if (!running) {
				if (!this.entity.getMMOHorseData().isClaimed()
						|| this.entity.getMMOHorseData().getClaimedData().getSettings().blockSpeedOnUnmount()
						|| !this.entity.getMMOHorseData().getClaimedData().getSettings().isFollowOwner())
					return false;
				if (player == null || !player.isValid() || player.getGameMode() == GameMode.SPECTATOR) {
					player = null;
					for (Entity ent : this.entity.getBukkitEntity().getNearbyEntities(12D, 12D, 12D))
						if (ent instanceof Player && MMOHorsesAPI.getUtils().isSame(ent.getUniqueId(),
								this.entity.getMMOHorseData().getClaimedData().getData().getOwner())) {
							this.player = (Player) ent;
							break;
						}
					if (player == null)
						return false;
				}
				if (!player.getWorld().getName().equals(this.entity.getLocation().getWorld().getName()))
					return false;
				double distance = this.entity.getLocation().distance(player.getLocation());
				if (distance < 4D || distance > 12D || !this.entity.hasLineOfSight(((CraftPlayer) player).getHandle()))
					return false;
				a(EnumSet.of(PathfinderGoal.Type.a));
				running = true;
			}
			return running;
		}
		return false;
	}

	public void e() {
		if (this.entity.isCustom()) {
			if (player != null && player.isValid() && player.getGameMode() != GameMode.SPECTATOR) {
				Location l1 = this.entity.getLocation().clone(), l2 = this.player.getLocation().clone();
				double distance = l1.distance(l2);
				if (distance >= 4D && distance <= 12D) {
					this.navigation.a(l2.getX(), l2.getY(), l2.getZ(), 1.40D);
					return;
				}
			}
			this.navigation.a(this.entity.getLocation().getX(), this.entity.getLocation().getY(),
					this.entity.getLocation().getZ(), 0D);
			running = false;
		}
	}
	
}
