package jp.tdn.japanese_food_mod.world;

import jp.tdn.japanese_food_mod.config.OystergenConfig;
import jp.tdn.japanese_food_mod.world.gen.feature.OysterFeature;
import jp.tdn.japanese_food_mod.world.gen.feature.WakameFeature;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.placement.FrequencyConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;

public class WakameGeneration {
    public static void setupWakameGeneration(){
        if(OystergenConfig.generate_overworld.get()){
            for(Biome biome: ForgeRegistries.BIOMES){
                if(biome.equals(Biomes.OCEAN) || biome.equals(Biomes.LUKEWARM_OCEAN) || biome.equals(Biomes.WARM_OCEAN)){
                    biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, new WakameFeature(NoFeatureConfig.field_236558_a_).withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.COUNT_TOP_SOLID.configure(new FrequencyConfig(2))));
                }
            }
        }
    }
}
