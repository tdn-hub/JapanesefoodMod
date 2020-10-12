package jp.tdn.japanese_food_mod.world;

import jp.tdn.japanese_food_mod.config.OregenConfig;
import jp.tdn.japanese_food_mod.init.JPBlocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;

public class OreGeneration {

    public OreGeneration(){
    }

    // Generate ores
    public static void setupOreGeneration(){
        if(OregenConfig.generate_overworld.get()){
            for(Biome biome : ForgeRegistries.BIOMES){
                biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, JPBlocks.ROCK_SALT_BLOCK.get().getDefaultState(), 8)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(14, 0, 0, 64))));
                biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, JPBlocks.TRONA_ORE.get().getDefaultState(), 8)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(4, 0, 0, 32))));
            }
        }
    }
}
