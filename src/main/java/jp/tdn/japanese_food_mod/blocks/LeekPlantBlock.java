package jp.tdn.japanese_food_mod.blocks;

import jp.tdn.japanese_food_mod.init.JPItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

import javax.annotation.Nonnull;

public class LeekPlantBlock extends CropBlock {
    public static final MapCodec<LeekPlantBlock> CODEC = simpleCodec(p -> new LeekPlantBlock());

    @Override
    public MapCodec<? extends CropBlock> codec() {
        return CODEC;
    }

    public LeekPlantBlock(){
        super(BlockBehaviour.Properties.of().noCollission().randomTicks().strength(0f).sound(SoundType.CROP));
    }

    @Override
    @Nonnull
    protected ItemLike getBaseSeedId(){
        return JPItems.LEEK_SEED.get();
    }

//    @Override
//    public InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
//        if(!world.isClientSide){
//            if(this.isMaxAge(state)){
//                world.addFreshEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(JPItems.LEEK, 1)));
//                world.setBlock(pos, this.withAge(0), 3);
//                return InteractionResult.SUCCESS;
//            }
//        }
//        return InteractionResult.FAIL;
//    }
}
