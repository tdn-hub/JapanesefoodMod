package jp.tdn.japanese_food_mod.blocks;

import jp.tdn.japanese_food_mod.init.JPItems;
import net.minecraft.block.CropsBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.IItemProvider;

import javax.annotation.Nonnull;

public class RadishSproutPlantBlock extends CropsBlock{
    public RadishSproutPlantBlock(){
        super(Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0f).sound(SoundType.CROP));
    }

    @Override
    @Nonnull
    protected IItemProvider getSeedsItem(){
        return JPItems.RADISH_SPROUT_SEED;
    }

//    @Override
//    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
//        if(!world.isRemote){
//            if(this.isMaxAge(state)){
//                world.addEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(JPItems.RADISH_SPROUT, 1)));
//                world.setBlockState(pos, this.withAge(0));
//                return ActionResultType.SUCCESS;
//            }
//        }
//        return ActionResultType.FAIL;
//    }
}
