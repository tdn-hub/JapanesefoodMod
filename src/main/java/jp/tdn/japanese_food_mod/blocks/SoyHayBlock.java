package jp.tdn.japanese_food_mod.blocks;

import jp.tdn.japanese_food_mod.init.JPBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HayBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.MapColor;

import javax.annotation.Nullable;

public class SoyHayBlock extends HayBlock implements EntityBlock {
    public static BooleanProperty COMPLETION = BooleanProperty.create("comp");

    public SoyHayBlock(){
        super(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).strength(0.5F).sound(SoundType.GRASS));
        this.registerDefaultState(this.defaultBlockState().setValue(COMPLETION, false).setValue(AXIS, Direction.Axis.Y));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return JPBlockEntities.SOY_HAY.get().create(pos, state);
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        if(state.getValue(COMPLETION)){
            double posX = (double) pos.getX() + random.nextDouble();
            double posY = (double) pos.getY() + 1.0D;
            double posZ = (double) pos.getZ() + random.nextDouble();
            world.addParticle(ParticleTypes.SMOKE, posX, posY, posZ, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(COMPLETION);
    }
}
