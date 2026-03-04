package jp.tdn.japanese_food_mod.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class CropGrassBlock extends BushBlock {
    public static final MapCodec<CropGrassBlock> CODEC = simpleCodec(p -> new CropGrassBlock());

    @Override
    protected MapCodec<? extends BushBlock> codec() {
        return CODEC;
    }

    public CropGrassBlock(){
        super(BlockBehaviour.Properties.of().noCollission().strength(0f).sound(SoundType.CROP).offsetType(BlockBehaviour.OffsetType.XZ));
    }
}
