package jp.tdn.japanese_food_mod.blocks;

import com.mojang.serialization.MapCodec;
import jp.tdn.japanese_food_mod.init.JPItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

import javax.annotation.Nonnull;

public class RicePlantBlock extends CropBlock {
    public static final MapCodec<RicePlantBlock> CODEC = simpleCodec(p -> new RicePlantBlock());

    @Override
    public MapCodec<? extends CropBlock> codec() {
        return CODEC;
    }

    public RicePlantBlock(){
        super(BlockBehaviour.Properties.of().noCollission().randomTicks().strength(0f).sound(SoundType.CROP));
    }

    @Override
    @Nonnull
    protected ItemLike getBaseSeedId(){
        return JPItems.RICE_SEEDLING.get();
    }

    @Override
    protected boolean mayPlaceOn(BlockState block, BlockGetter reader, BlockPos pos) {
        FluidState fluidState = reader.getFluidState(pos);
        return fluidState.getType() == Fluids.WATER;
    }
}