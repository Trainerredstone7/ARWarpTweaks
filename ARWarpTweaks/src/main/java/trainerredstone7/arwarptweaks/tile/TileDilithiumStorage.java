package trainerredstone7.arwarptweaks.tile;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
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
	 * Many of the following methods are adapted from McJty's 1.12 modding tutorials.
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
			//the inventory can only hold dilithium
			if(isWarpFuel(stack)) {
				return super.insertItem(slot, stack, simulate);
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
        if (compound.hasKey("fuel")) {
        	fuel = compound.getInteger("fuel");
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("items", itemHandler.serializeNBT());
        compound.setInteger("fuel", fuel);
        return compound;
    }
    
    @Override
    public NBTTagCompound getUpdateTag() {
    	NBTTagCompound nbtTag = super.getUpdateTag();
    	nbtTag.setInteger("fuel", fuel);
    	return nbtTag;
    }
    
    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
    	return new SPacketUpdateTileEntity(pos, 1, getUpdateTag());
    }
    
    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
    	fuel = packet.getNbtCompound().getInteger("fuel");
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
    
    /**
     * 
     * @param drain
     * @return The amount of the drain request that was not satisfied
     */
    public int drainFuel(int drain) {
    	int oldFuel = fuel;
    	fuel = Math.max(fuel - drain, 0);
    	return Math.max(drain - oldFuel, 0);
    }
}
