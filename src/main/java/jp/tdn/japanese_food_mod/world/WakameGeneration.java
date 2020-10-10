package jp.tdn.japanese_food_mod.world;

import jp.tdn.japanese_food_mod.config.OystergenConfig;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraftforge.registries.ForgeRegistries;

public class WakameGeneration {
    public static void setupWakameGeneration(){
        if(OystergenConfig.generate_overworld.get()){
            for(Biome biome: ForgeRegistries.BIOMES){
                if(biome.equals(Biomes.OCEAN) || biome.equals(Biomes.LUKEWARM_OCEAN) || biome.equals(Biomes.WARM_OCEAN)){
//                    biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, new WakameFeature(NoFeatureConfig.field_236558_a_).withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.TOP_SOLID_HEIGHTMAP.configure(new NoPlacementConfig())));
                }
            }
        }
    }
}
