package jp.tdn.japanese_food_mod.world.gen.feature;

import com.mojang.serialization.Codec;
import jp.tdn.japanese_food_mod.init.JPBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class OysterFeature extends Feature<NoneFeatureConfiguration> {
    private static final Direction[] HORIZONTALS = {Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};

    public OysterFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        BlockPos blockPos = context.origin();
        var level = context.level();
        var random = context.random();

        if (level.getBlockState(blockPos).getBlock() == Blocks.WATER
                && level.getBlockState(blockPos.below()).getBlock() != Blocks.WATER) {
            Direction dir = HORIZONTALS[random.nextInt(4)];
            level.setBlock(blockPos,
                    JPBlocks.OYSTER_SHELL.get().defaultBlockState()
                            .setValue(HorizontalDirectionalBlock.FACING, dir),
                    1);
            return true;
        }
        return false;
    }
}
