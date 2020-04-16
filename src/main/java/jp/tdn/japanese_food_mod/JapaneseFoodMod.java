package jp.tdn.japanese_food_mod;

import jp.tdn.japanese_food_mod.init.JPBlocks;
import jp.tdn.japanese_food_mod.world.OreGeneration;
import net.minecraft.block.Block;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(JapaneseFoodMod.MOD_ID)
public class JapaneseFoodMod {
    public static final String MOD_ID = "japanese_food_mod";

     // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();


    public JapaneseFoodMod() {
        // Register the setup method for mod-loading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the doClientStuff method for mod-loading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event){
        OreGeneration.setupOreGeneration();
    }

    private void doClientStuff(final FMLClientSetupEvent event){
        // do something that can only be done on the client
        LOGGER.info("Hello from Client Setup");
    }
}
