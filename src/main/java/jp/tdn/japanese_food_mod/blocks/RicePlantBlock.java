package jp.tdn.japanese_food_mod.blocks;

import jp.tdn.japanese_food_mod.init.JPItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nonnull;

public class RicePlantBlock extends CropsBlock{
    public RicePlantBlock(Properties properties){
        super(properties);
    }

//    @Override
//    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
//        if(!world.isRemote){
//            if(this.isMaxAge(state)){
//                world.addEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(JPItems.RICE, RANDOM.nextInt(2) + 1)));
//                world.setBlockState(pos, this.withAge(0));
//                return ActionResultType.SUCCESS;
//            }
//        }
//        return ActionResultType.FAIL;
//    }
}
