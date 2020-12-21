package trainerredstone7.arwarptweaks.block;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import trainerredstone7.arwarptweaks.ARWarpTweaks;
import trainerredstone7.arwarptweaks.tile.TileDilithiumStorage;
import zmaster587.libVulpes.api.material.AllowedProducts;
import zmaster587.libVulpes.api.material.MaterialRegistry;

public class BlockDilithiumStorage extends Block implements ITileEntityProvider {

	public BlockDilithiumStorage() {
		super(Material.ROCK);
		setRegistryName(new ResourceLocation(ARWarpTweaks.MODID, "dilithium_storage"));
		setUnlocalizedName(ARWarpTweaks.MODID + ".dilithium_storage");
		setSoundType(SoundType.STONE);
		setCreativeTab(ARWarpTweaks.tabARWarpTweaks);
//		setHarvestLevel("pickaxe", 1);
		setDefaultState(blockState.getBaseState());
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileDilithiumStorage();
	}

	
    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side,
                float hitX, float hitY, float hitZ) {
        // Only execute on the server
    	if (!world.isRemote) {
        	TileEntity te = world.getTileEntity(pos);
        	if (!(te instanceof TileDilithiumStorage)) {
        		return false;
        	}
        	else {
        		player.openGui(ARWarpTweaks.instance, 0, world, pos.getX(), pos.getY(), pos.getZ());
        	}
        }
        return true;
    }
    
    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
    	TileEntity tileentity = worldIn.getTileEntity(pos);
        IItemHandler itemHandler = tileentity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

        for (int i = 0; i < itemHandler.getSlots(); ++i)
        {
            ItemStack itemstack = itemHandler.getStackInSlot(i);

            if (!itemstack.isEmpty())
            {
                InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), itemstack);
            }
        }

        super.breakBlock(worldIn, pos, state);
    }
	
	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return this.getDefaultState();
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this);
	}
}
