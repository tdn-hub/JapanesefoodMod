package jp.tdn.japanese_food_mod.world;

import jp.tdn.japanese_food_mod.config.OregenConfig;
import jp.tdn.japanese_food_mod.init.JPBlocks;
import net.minecraft.block.BlockState;
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
                // rock_salt
                addOreGen(20, 0, 0, 128, biome, JPBlocks.ROCK_SALT_BLOCK.getDefaultState(), OregenConfig.rock_salt_chance.get());
            }
        }
    }

    // Add ore to generate
    public static void addOreGen(int chance, int minHeight, int maxHeightBase, int maxHeight, Biome biome, BlockState block, int veinSize){
        CountRangeConfig placement = new CountRangeConfig(chance, minHeight, maxHeightBase, maxHeight);
        biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, block, 8), Placement.COUNT_RANGE, placement));
    }
}
