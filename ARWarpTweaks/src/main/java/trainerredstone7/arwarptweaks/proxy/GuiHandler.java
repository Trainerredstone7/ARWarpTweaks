package trainerredstone7.arwarptweaks.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import trainerredstone7.arwarptweaks.container.ContainerDilithiumStorage;
import trainerredstone7.arwarptweaks.gui.GuiDilithiumStorage;
import trainerredstone7.arwarptweaks.tile.TileDilithiumStorage;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileDilithiumStorage) {
            return new ContainerDilithiumStorage(player.inventory, (TileDilithiumStorage) te);
        }
        return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileDilithiumStorage) {
            TileDilithiumStorage containerTileEntity = (TileDilithiumStorage) te;
            return new GuiDilithiumStorage(new ContainerDilithiumStorage(player.inventory, containerTileEntity));
        }
        return null;
	}

}
