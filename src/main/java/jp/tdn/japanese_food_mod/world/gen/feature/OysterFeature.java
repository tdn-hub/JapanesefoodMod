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

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

public class OysterFeature extends Feature<NoFeatureConfig> {
    public OysterFeature(Codec<NoFeatureConfig> deserializer) {
        super(deserializer);
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random, BlockPos blockPos, NoFeatureConfig noFeatureConfig) {
        if (iSeedReader.getBlockState(blockPos).getBlock() == Blocks.WATER && iSeedReader.getBlockState(blockPos.down()).getBlock() != Blocks.WATER) {
            iSeedReader.setBlockState(blockPos, JPBlocks.OYSTER_SHELL.get().getDefaultState().rotate(iSeedReader.getWorld(), blockPos, JapaneseFoodUtil.rotations.get(random.nextInt(4))), 1);
            return true;
        }
        return false;
    }
}
