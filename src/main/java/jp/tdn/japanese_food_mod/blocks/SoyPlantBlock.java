package jp.tdn.japanese_food_mod.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class SoyPlantBlock extends CropBlock {
    public static final MapCodec<SoyPlantBlock> CODEC = simpleCodec(SoyPlantBlock::new);

    @Override
    public MapCodec<? extends CropBlock> codec() {
        return CODEC;
    }

    public SoyPlantBlock(BlockBehaviour.Properties properties){
        super(properties);
    }
}