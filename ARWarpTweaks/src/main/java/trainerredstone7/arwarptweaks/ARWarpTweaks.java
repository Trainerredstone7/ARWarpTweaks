package trainerredstone7.arwarptweaks;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import trainerredstone7.arwarptweaks.block.BlockDilithiumStorage;
import trainerredstone7.arwarptweaks.proxy.IProxy;
import trainerredstone7.arwarptweaks.tile.TileDilithiumStorage;
import zmaster587.advancedRocketry.api.RocketEvent.RocketPreLaunchEvent;
import zmaster587.advancedRocketry.entity.EntityRocket;
import zmaster587.advancedRocketry.util.StorageChunk;

import org.apache.logging.log4j.Logger;

@Mod(modid = ARWarpTweaks.MODID, name = ARWarpTweaks.NAME, version = ARWarpTweaks.VERSION, dependencies = "required-after:advancedrocketry;required-after:libvulpes")
@Mod.EventBusSubscriber
public class ARWarpTweaks
{
    public static final String MODID = "arwarptweaks";
    public static final String NAME = "AR Warp Tweaks";
    public static final String VERSION = "0.1";

    public static Logger logger;

    @Mod.Instance
    public static ARWarpTweaks instance;
    
    @SidedProxy(clientSide = "trainerredstone7.arwarptweaks.proxy.ClientProxy", serverSide = "trainerredstone7.arwarptweaks.proxy.ServerProxy")
    public static IProxy proxy;
    
	public static CreativeTabs tabARWarpTweaks = new CreativeTabs("AR Warp Tweaks") {
		@Override
		public ItemStack getTabIconItem() {return new ItemStack(ModBlocks.BLOCK_DILITHIUM_STORAGE); }
	};
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        logger = e.getModLog();
        proxy.preInit(e);
    }

    @EventHandler
    public void init(FMLInitializationEvent e) {
    	proxy.init(e);
    }
    
    
    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
    	event.getRegistry().register(new BlockDilithiumStorage());
    	GameRegistry.registerTileEntity(TileDilithiumStorage.class, new ResourceLocation(MODID, "dilithium_storage"));
    }
    
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
    	registerBlockItem(ModBlocks.BLOCK_DILITHIUM_STORAGE, event);
    }
    
    private static void registerBlockItem(Block block, RegistryEvent.Register<Item> event) {
    	event.getRegistry().register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
    }
    
    //TODO: Need to finish this
    @SubscribeEvent
    public static void burnWarpFuel(RocketPreLaunchEvent event) {
    	if (!(event.getEntity() instanceof EntityRocket)) return; //if the rocket isn't an EntityRocket we can't check its inventory anyways
    	EntityRocket rocket = (EntityRocket) event.getEntity();
    	StorageChunk storage = rocket.storage;
    }
    
}
