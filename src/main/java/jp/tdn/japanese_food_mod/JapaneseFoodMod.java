package jp.tdn.japanese_food_mod;

import jp.tdn.japanese_food_mod.items.MisoSoupItem;
import jp.tdn.japanese_food_mod.lists.BlockList;
import jp.tdn.japanese_food_mod.lists.ItemList;
import jp.tdn.japanese_food_mod.world.OreGeneration;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.GrassBlock;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(JapaneseFoodMod.MOD_ID)
public class JapaneseFoodMod {
     public static final String MOD_ID = "japanese_food_mod";

     // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();


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

    // You can use EventBusSubscriber to automatically subscribe events on the contained class
    // (This is subscribing MOD Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents{
        @SubscribeEvent
        public static void registerItems(final RegistryEvent.Register<Item> event){

            event.getRegistry().registerAll(
                    ItemList.TYAWAN,
                    ItemList.POT,
                    ItemList.SOY_BEANS,
                    ItemList.SALT,
                    ItemList.ROCK_SALT,
                    ItemList.ROCK_SALT_BLOCK,
                    ItemList.BOILED_SOY_BEANS,
                    ItemList.RICE,
                    ItemList.RICE_SEEDLING,
                    ItemList.MISO_SOUP);
        }

        @SubscribeEvent
        public static void registerBlocks(final RegistryEvent.Register<Block> blockRegisterEvent){
            // register a new block here
            blockRegisterEvent.getRegistry().registerAll(
                    BlockList.SOY,
                    BlockList.RICE_PLANT,
                    BlockList.ROCK_SALT_BLOCK);
        }
    }
}
