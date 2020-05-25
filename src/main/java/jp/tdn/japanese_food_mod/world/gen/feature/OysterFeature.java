package jp.tdn.japanese_food_mod.world.gen.feature;

import com.mojang.datafixers.Dynamic;
import jp.tdn.japanese_food_mod.JapaneseFoodMod;
import jp.tdn.japanese_food_mod.init.JPBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class OysterFeature extends Feature<NoFeatureConfig> {
    public OysterFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> deserializer){
        super(deserializer);
    }

    @Override
    public boolean place(IWorld iWorld, ChunkGenerator<? extends GenerationSettings> chunkGenerator, Random random, BlockPos blockPos, NoFeatureConfig noFeatureConfig) {
        if(iWorld.getBlockState(blockPos).getBlock() == Blocks.WATER && iWorld.getBlockState(blockPos.down()).getBlock() != Blocks.WATER){
            iWorld.setBlockState(blockPos, JPBlocks.OYSTER_SHELL.getDefaultState(), 1);
            return true;
        }
        return false;
    }
}
