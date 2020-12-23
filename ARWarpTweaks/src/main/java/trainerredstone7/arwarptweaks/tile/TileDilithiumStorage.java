package trainerredstone7.arwarptweaks.tile;

import javax.annotation.Nonnull;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.oredict.OreDictionary;
import trainerredstone7.arwarptweaks.ARWarpTweaks;
import zmaster587.libVulpes.api.material.AllowedProducts;
import zmaster587.libVulpes.api.material.MaterialRegistry;
import zmaster587.advancedRocketry.api.ARConfiguration;

public class TileDilithiumStorage extends TileEntity {
	public static final int SIZE = 1;
	private int fuel = 0;
	public static final int MAX_FUEL = 1000;
	
	/*
	 * The following methods are all adapted from McJty's 1.12 modding tutorials.
	 */
	private ItemStackHandler itemHandler = new ItemStackHandler(SIZE) {
		@Override
		protected void onContentsChanged(int slot) {
			/*
			 * Convert dilithium into warp fuel
			 * TODO: see if there is a better place to put this
			 */
			ItemStack stack = getStackInSlot(slot);
			if (isWarpFuel(stack)) {
				int stackSize = stack.getCount();
				int fuelPerItem = ARConfiguration.getCurrentConfig().fuelPointsPerDilithium;
				int extractCount = Math.min((MAX_FUEL-fuel)/fuelPerItem, stackSize);
				fuel += extractCount*fuelPerItem;
				extractItem(slot, extractCount, false);
			}
			markDirty();
		}
		
		@Override
		public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
			ARWarpTweaks.logger.info("test");
			//the inventory can only hold dilithium
			if(isWarpFuel(stack)) {
				ItemStack remaining = super.insertItem(slot, stack, simulate);
				return remaining;
			}
			else return stack;
		}
	};
	
	@Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("items")) {
            itemHandler.deserializeNBT((NBTTagCompound) compound.getTag("items"));
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("items", itemHandler.serializeNBT());
        return compound;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return true;
        }
        return super.hasCapability(capability, facing);
    }
    
    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(itemHandler);
        }
        return super.getCapability(capability, facing);
    }
    
    public boolean canInteractWith(EntityPlayer playerIn) {
        return !isInvalid() && playerIn.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64D;
    }
    
    public static boolean isWarpFuel(ItemStack stack) {
    	return OreDictionary.itemMatches(MaterialRegistry.getItemStackFromMaterialAndType("Dilithium", AllowedProducts.getProductByName("GEM")), stack, false);
    }
    
    public int getFuel() {
    	return fuel;
    }
}
