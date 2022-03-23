package jp.tdn.japanese_food_mod.events;

import jp.tdn.japanese_food_mod.JapaneseFoodMod;
import jp.tdn.japanese_food_mod.init.JPBlocks;
import jp.tdn.japanese_food_mod.world.gen.feature.JPConfiguredFeatures;
import jp.tdn.japanese_food_mod.world.gen.feature.JPFeatures;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class BiomeLoadEventHandler {
    @SubscribeEvent
    public void onBiomeLoadEvent(BiomeLoadingEvent event){
        // Block
        event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).add(() -> JPConfiguredFeatures.ROCK_SALT);
        event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).add(() -> JPConfiguredFeatures.TRONA_ORE);

        ResourceLocation name = event.getName();
        //JapaneseFoodMod.LOGGER.info("onBiomeLoadingEvent: " + name);
        if(compareBiomeName(name, "ocean") || compareBiomeName(name, "lukewarm_ocean")){
            event.getGeneration().getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION).add(() -> JPConfiguredFeatures.OYSTER);
            event.getGeneration().getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION).add(() -> JPConfiguredFeatures.WAKAME);
        }

        if(compareBiomeName(name, "plains") || compareBiomeName(name, "sunflower_plains")){
            event.getGeneration().getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION).add(() -> JPConfiguredFeatures.WEED);
        }
    }

    private boolean compareBiomeName(ResourceLocation name, String biome){
        return name.equals(new ResourceLocation("minecraft", biome));
    }
}
