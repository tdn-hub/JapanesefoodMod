package jp.tdn.japanese_food_mod.blocks;

import jp.tdn.japanese_food_mod.JapaneseFoodMod;
import jp.tdn.japanese_food_mod.init.JPItems;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class RicePlantBlock extends CropsBlock{
    public RicePlantBlock(){
        super(Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0f).sound(SoundType.CROP));
    }

    @Override
    @Nonnull
    protected IItemProvider getSeedsItem(){
        return JPItems.RICE_SEEDLING;
    }

    @Override
    protected boolean isValidGround(BlockState block, IBlockReader reader, BlockPos pos){
        return block.getBlock() == Blocks.FARMLAND;
    }

    @Override
    public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        if(!world.isRemote){
            if(this.isMaxAge(state)){
                world.addEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(JPItems.RICE, RANDOM.nextInt(2) + 1)));
                world.setBlockState(pos, this.withAge(0));
                return true;
            }
        }
        return false;
    }

    /*
    //Plant in the water.
    @Override
    public IFluidState getFluidState(BlockState p_204507_1_) {
        return Fluids.WATER.getStillFluidState(false);
    }

    @Override
    public boolean canContainFluid(IBlockReader iBlockReader, BlockPos blockPos, BlockState blockState, Fluid fluid) {
        return false;
    }

    @Override
    public boolean receiveFluid(IWorld p_204509_1_, BlockPos p_204509_2_, BlockState p_204509_3_, IFluidState p_204509_4_) {
        return false;
    }*/
}
