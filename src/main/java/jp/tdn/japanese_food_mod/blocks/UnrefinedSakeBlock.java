package jp.tdn.japanese_food_mod.blocks;

import jp.tdn.japanese_food_mod.blocks.tileentity.UnrefinedSakeTileEntity;
import jp.tdn.japanese_food_mod.init.JPItems;
import jp.tdn.japanese_food_mod.init.JPTileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class UnrefinedSakeBlock extends UnrefinedBlock {
    public UnrefinedSakeBlock(){
        super();
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return JPTileEntities.UNREFINED_SAKE.create();
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity entity, Hand hand, BlockRayTraceResult rayTraceResult) {
        if(!world.isRemote){
            if(state.get(SAUCE) && hasUpSideBlock(world, pos)){
                ItemStack insert = new ItemStack(JPItems.SAKE.get());
                TileEntity tileEntity = world.getTileEntity(pos);
                if(tileEntity instanceof UnrefinedSakeTileEntity){
                    if(((UnrefinedSakeTileEntity) tileEntity).getSauceRemaining() > 0) {
                        entity.inventory.addItemStackToInventory(insert);
                        ((UnrefinedSakeTileEntity) tileEntity).useSauce();

                        if (((UnrefinedSakeTileEntity) tileEntity).getSauceRemaining() <= 0) {
                            world.setBlockState(pos, state.with(SAUCE, false));
                        }
                    }
                }
            }
        }
        return ActionResultType.SUCCESS;
    }
}
