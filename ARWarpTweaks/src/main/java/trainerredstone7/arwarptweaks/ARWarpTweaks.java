package trainerredstone7.arwarptweaks;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = ARWarpTweaks.MODID, name = ARWarpTweaks.NAME, version = ARWarpTweaks.VERSION, dependencies = "required-after:advancedrocketry;required-after:libvulpes")
public class ARWarpTweaks
{
    public static final String MODID = "arwarptweaks";
    public static final String NAME = "AR Warp Tweaks";
    public static final String VERSION = "0.1";

    private static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    }
    
    
}
