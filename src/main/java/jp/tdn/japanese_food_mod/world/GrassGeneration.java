package jp.tdn.japanese_food_mod.world;

import jp.tdn.japanese_food_mod.config.GrassgenConfig;
import jp.tdn.japanese_food_mod.init.JPBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.placement.FrequencyConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;

public class GrassGeneration {
    private static final BlockClusterFeatureConfig CROP_GRASS_CONFIG = (new net.minecraft.world.gen.feature.BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(JPBlocks.CROP_GRASS.getDefaultState()), new SimpleBlockPlacer())).tries(32).build();


    public GrassGeneration(){
    }

    // Generate grass
    public static void setupGrassGeneration(){
        if(GrassgenConfig.generate_overworld.get()){
            for(Biome biome : ForgeRegistries.BIOMES){
                // crop grass
                if(biome == Biomes.PLAINS || biome == Biomes.SUNFLOWER_PLAINS) {
                    addGrassGen(biome, JPBlocks.CROP_GRASS.getDefaultState());
                }
            }
        }
    }

    // Add grass to generate
    public static void addGrassGen(Biome biome, BlockState block){
        biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(CROP_GRASS_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP_DOUBLE.configure(new FrequencyConfig(1))));
    }
}
