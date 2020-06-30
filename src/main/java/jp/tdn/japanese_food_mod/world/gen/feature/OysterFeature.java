package jp.tdn.japanese_food_mod.world.gen.feature;

import com.mojang.serialization.Codec;
import jp.tdn.japanese_food_mod.JapaneseFoodUtil;
import jp.tdn.japanese_food_mod.init.JPBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.StructureManager;

import java.util.Random;

public class OysterFeature extends Feature<NoFeatureConfig> {
    public OysterFeature(Codec<NoFeatureConfig> deserializer) {
        super(deserializer);
    }

    @Override
    public boolean func_230362_a_(ISeedReader iSeedReader, StructureManager structureManager, ChunkGenerator chunkGenerator, Random random, BlockPos blockPos, NoFeatureConfig noFeatureConfig) {
        if (iSeedReader.getBlockState(blockPos).getBlock() == Blocks.WATER && iSeedReader.getBlockState(blockPos.down()).getBlock() != Blocks.WATER) {
            iSeedReader.setBlockState(blockPos, JPBlocks.OYSTER_SHELL.getDefaultState().rotate(JapaneseFoodUtil.rotations.get(JapaneseFoodUtil.rand.nextInt(JapaneseFoodUtil.rotations.size()))), 1);
            return true;
        }
        return false;
    }
}
