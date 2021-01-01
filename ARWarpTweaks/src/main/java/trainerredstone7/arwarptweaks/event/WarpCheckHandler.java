package trainerredstone7.arwarptweaks.event;

import java.util.List;
import java.util.stream.Collectors;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import trainerredstone7.arwarptweaks.ARWarpTweaks;
import trainerredstone7.arwarptweaks.tile.TileDilithiumStorage;
import trainerredstone7.arwarptweaks.utils.WarpUtils;
import zmaster587.advancedRocketry.api.RocketEvent.RocketPreLaunchEvent;
import zmaster587.advancedRocketry.entity.EntityRocket;

@Mod.EventBusSubscriber
public class WarpCheckHandler {
	public static final int MESSAGE_RADIUS = 10;

	@SubscribeEvent
	public static void burnWarpFuel(RocketPreLaunchEvent event) {
		if (!(event.getEntity() instanceof EntityRocket)) return; //if the rocket isn't an EntityRocket we can't check its inventory anyways
		EntityRocket rocket = (EntityRocket) event.getEntity();
		List<TileDilithiumStorage> dsTiles = rocket.storage.getTileEntityList().stream()
				.filter(p -> p instanceof TileDilithiumStorage).map(p -> (TileDilithiumStorage) p).collect(Collectors.toList());
		int cost = WarpUtils.getTravelCost(rocket);
		int fuel = dsTiles.stream().reduce(0, (p,q) -> p + q.getFuel(), (p,q) -> p + q);
		if (fuel >= cost) {
			//Have enough fuel, so remove fuel from storages sequentially until cost is fulfilled
			for (TileDilithiumStorage tileDilithiumStorage : dsTiles) {
				cost = tileDilithiumStorage.drainFuel(cost);
				if (cost == 0) break;
			}
		}
		
		else {
			event.setCanceled(true);
			ARWarpTweaks.logger.info("canceled event");
			ARWarpTweaks.logger.info(rocket.getEntityWorld().isRemote);
			if(!rocket.getEntityWorld().isRemote) {
				AxisAlignedBB messageArea = new AxisAlignedBB(new BlockPos(rocket.posX, rocket.posY, rocket.posZ)).grow(WarpCheckHandler.MESSAGE_RADIUS);
				ARWarpTweaks.logger.info(rocket.world.getEntitiesWithinAABB(EntityPlayer.class, messageArea));
				for (EntityPlayer player : rocket.world.getEntitiesWithinAABB(EntityPlayer.class, messageArea)) {
		    		ARWarpTweaks.logger.info(player.getEntityWorld().isRemote);
					player.sendMessage(new TextComponentString("Not enough warp fuel in dilithium storage! Need " + (cost - fuel) + " more."));
				}
			}
		}
	}
}
