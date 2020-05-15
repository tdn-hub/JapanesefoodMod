package jp.tdn.japanese_food_mod.world;

import jp.tdn.japanese_food_mod.config.GrassgenConfig;
import jp.tdn.japanese_food_mod.init.JPBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.BushConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.placement.FrequencyConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;

public class GrassGeneration {

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
        biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(Feature.BUSH, new BushConfig(block), Placement.COUNT_HEIGHTMAP_DOUBLE, new FrequencyConfig(2)));
    }
}
