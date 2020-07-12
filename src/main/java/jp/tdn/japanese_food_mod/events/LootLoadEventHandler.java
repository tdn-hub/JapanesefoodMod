package jp.tdn.japanese_food_mod.events;

import jp.tdn.japanese_food_mod.JapaneseFoodMod;
import jp.tdn.japanese_food_mod.config.FishingConfig;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.TableLootEntry;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = JapaneseFoodMod.MOD_ID)
public class LootLoadEventHandler {
    //private static ResourceLocation grass = new ResourceLocation("minecraft", "blocks/grass");
    private static ResourceLocation sea_grass = new ResourceLocation("minecraft", "blocks/seagrass");
    private static ResourceLocation squid = new ResourceLocation("minecraft", "entities/squid");
    private static ResourceLocation pig = new ResourceLocation("minecraft", "entities/pig");
    //private static ResourceLocation bamboo = new ResourceLocation("minecraft", "blocks/bamboo_sapling");
    private static ResourceLocation fish = new ResourceLocation("minecraft", "gameplay/fishing");

    @SubscribeEvent
    public static void onLootLoad(LootTableLoadEvent event){
//        if(event.getName().equals(grass)){
//            event.getTable().addPool(LootPool.builder().addEntry(TableLootEntry.builder(new ResourceLocation(JapaneseFoodMod.MOD_ID, "blocks/grass"))).build());
//        }

        if(event.getName().equals(sea_grass)){
            event.getTable().addPool(LootPool.builder().addEntry(TableLootEntry.builder(new ResourceLocation(JapaneseFoodMod.MOD_ID, "blocks/seagrass"))).build());
        }

        if(event.getName().equals(squid)){
            event.getTable().addPool(LootPool.builder().addEntry(TableLootEntry.builder(new ResourceLocation(JapaneseFoodMod.MOD_ID, "entities/squid"))).build());
        }

        if(event.getName().equals(pig)){
            event.getTable().addPool(LootPool.builder().addEntry(TableLootEntry.builder(new ResourceLocation(JapaneseFoodMod.MOD_ID, "entities/pig"))).build());
        }

        if(FishingConfig.fishing_overworld.get() && event.getName().equals(fish)){
            event.getTable().removePool("main");
            event.getTable().addPool(LootPool.builder().addEntry(TableLootEntry.builder(new ResourceLocation(JapaneseFoodMod.MOD_ID, "gameplay/fishing"))).build());
        }
    }
}
