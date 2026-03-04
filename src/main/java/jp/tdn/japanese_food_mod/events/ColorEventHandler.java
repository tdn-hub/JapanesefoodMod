package jp.tdn.japanese_food_mod.events;

import jp.tdn.japanese_food_mod.JapaneseFoodMod;
import jp.tdn.japanese_food_mod.init.JPBlocks;
import jp.tdn.japanese_food_mod.init.JPItems;
import net.minecraft.client.renderer.BiomeColors;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

@EventBusSubscriber(modid = JapaneseFoodMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ColorEventHandler {

    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
        event.register((blockState, iEnviromentBlockReader, blockPos, i) -> 0xffb4935d, JPBlocks.PRESSER.get());
        event.register((blockState, iEnviromentBlockReader, blockPos, i) -> 0xff2D8C00, JPBlocks.CROP_GRASS.get());
        event.register((blockState, iLightReader, blockPos, i) -> BiomeColors.getAverageWaterColor(iLightReader, blockPos), JPBlocks.FURNACE_CAULDRON.get());
    }

    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        event.register((itemStack, i) -> 0xff2D8C00, JPItems.CROP_GRASS.get());
    }
}
