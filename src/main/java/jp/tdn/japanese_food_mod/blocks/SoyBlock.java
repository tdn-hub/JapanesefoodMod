package jp.tdn.japanese_food_mod.blocks;

import jp.tdn.japanese_food_mod.JapaneseFoodMod;
import jp.tdn.japanese_food_mod.lists.ItemList;
import net.minecraft.block.CropsBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class SoyBlock extends CropsBlock{
    public SoyBlock(){
        super(Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0f).sound(SoundType.CROP));
        this.setRegistryName(JapaneseFoodMod.MOD_ID, "soy");
    }

    @Override
    protected IItemProvider getSeedsItem(){
        return ItemList.SOY_SEEDS;
    }

    @Override
    public int getMaxAge(){
        return 6;
    }

    @Override
    public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        if(!world.isRemote){
            if(this.isMaxAge(state)){
                world.addEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ItemList.SOY_SEEDS,1)));
                world.setBlockState(pos, this.withAge(0));
                return true;
            }
        }
        return false;
    }
}
