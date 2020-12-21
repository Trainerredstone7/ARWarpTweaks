package trainerredstone7.arwarptweaks.proxy;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import trainerredstone7.arwarptweaks.ARWarpTweaks;
import trainerredstone7.arwarptweaks.ModBlocks;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = ARWarpTweaks.MODID)
public class ClientProxy implements IProxy {

	@Override
	public void preInit(FMLPreInitializationEvent e) {
	}

	@Override
	public void init(FMLInitializationEvent e) {
	}

	@Override
	public void postInit(FMLPostInitializationEvent e) {
	}
	
    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
    	registerBlockModel(ModBlocks.BLOCK_DILITHIUM_STORAGE, 0);
    }
    
    private static void registerBlockModel(Block block, int meta) {
    	registerModel(Item.getItemFromBlock(block), 0);
    }
    
    private static void registerModel(Item item, int meta) {
    	ModelLoader.setCustomModelResourceLocation(item, meta, 
    				new ModelResourceLocation(item.getRegistryName(), "inventory"));
    } 

}
