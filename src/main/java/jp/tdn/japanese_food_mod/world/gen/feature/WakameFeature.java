package jp.tdn.japanese_food_mod.world.gen.feature;

import com.mojang.serialization.Codec;
import jp.tdn.japanese_food_mod.init.JPBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.StructureManager;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

public class WakameFeature extends Feature<NoFeatureConfig> {
    public WakameFeature(Codec<NoFeatureConfig> deserializer) {
        super(deserializer);
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random, BlockPos blockPos, NoFeatureConfig noFeatureConfig) {
        if (iSeedReader.getBlockState(blockPos).getBlock() == Blocks.WATER && iSeedReader.getBlockState(blockPos.down()).getBlock() != Blocks.WATER) {
            iSeedReader.setBlockState(blockPos, JPBlocks.WAKAME_BLOCK.get().getDefaultState(), 1);
            return true;
        }
        return false;
    }
}
