package jp.tdn.japanese_food_mod.world.gen.feature;

import com.mojang.serialization.Codec;
import jp.tdn.japanese_food_mod.init.JPBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class WakameFeature extends Feature<NoneFeatureConfiguration> {
    public WakameFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        BlockPos blockPos = context.origin();
        var level = context.level();

        if (level.getBlockState(blockPos).getBlock() == Blocks.WATER
                && level.getBlockState(blockPos.below()).getBlock() != Blocks.WATER) {
            level.setBlock(blockPos, JPBlocks.WAKAME_BLOCK.get().defaultBlockState(), 1);
            level.setBlock(blockPos.above(),
                    JPBlocks.WAKAME_BLOCK.get().defaultBlockState()
                            .setValue(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER),
                    1);
            return true;
        }
        return false;
    }
}
