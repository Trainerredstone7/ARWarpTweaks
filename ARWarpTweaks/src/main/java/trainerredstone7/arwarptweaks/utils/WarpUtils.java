package trainerredstone7.arwarptweaks.utils;

import net.minecraft.util.math.MathHelper;
import zmaster587.advancedRocketry.dimension.DimensionManager;
import zmaster587.advancedRocketry.dimension.DimensionProperties;
import zmaster587.advancedRocketry.entity.EntityRocket;

public class WarpUtils {
	public static int getTravelCost(EntityRocket rocket) {
		int dimID = rocket.world.provider.getDimension();
		int destDimID = rocket.storage.getDestinationDimId(dimID, (int)rocket.posX, (int)rocket.posZ);
		DimensionProperties dimProperties = DimensionManager.getInstance().getDimensionProperties(dimID);
		DimensionProperties destDimProperties = DimensionManager.getInstance().getDimensionProperties(destDimID);

		if(dimProperties == DimensionManager.defaultSpaceDimensionProperties)
			return Integer.MAX_VALUE;

		if(destDimProperties.getStar() != dimProperties.getStar())
			return 500;

		while(destDimProperties.getParentProperties() != null && destDimProperties.isMoon())
			destDimProperties = destDimProperties.getParentProperties();

		if((destDimProperties.isMoon() && destDimProperties.getParentPlanet() == dimProperties.getId()) || (dimProperties.isMoon() && dimProperties.getParentPlanet() == destDimProperties.getId()))
			return 0;

		while(dimProperties.isMoon())
			dimProperties = dimProperties.getParentProperties();

		//TODO: actual trig
		if(dimProperties.getStar().getId() == destDimProperties.getStar().getId()) {
			double x1 = dimProperties.orbitalDist*MathHelper.cos((float) dimProperties.orbitTheta);
			double y1 = dimProperties.orbitalDist*MathHelper.sin((float) dimProperties.orbitTheta);
			double x2 = destDimProperties.orbitalDist*MathHelper.cos((float) destDimProperties.orbitTheta);
			double y2 = destDimProperties.orbitalDist*MathHelper.sin((float) destDimProperties.orbitTheta);

			return Math.max((int)Math.sqrt(Math.pow((x1 - x2),2) + Math.pow((y1 - y2),2)),1);

			//return Math.abs(properties.orbitalDist - destProperties.orbitalDist);
		}
		return Integer.MAX_VALUE;
	}
}
