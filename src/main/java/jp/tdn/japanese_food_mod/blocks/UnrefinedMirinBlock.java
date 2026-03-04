package jp.tdn.japanese_food_mod.blocks;

import jp.tdn.japanese_food_mod.blocks.tileentity.UnrefinedMirinTileEntity;
import jp.tdn.japanese_food_mod.init.JPBlockEntities;
import jp.tdn.japanese_food_mod.init.JPItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;

public class UnrefinedMirinBlock extends UnrefinedBlock implements EntityBlock {

    public UnrefinedMirinBlock(){
        super();
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return JPBlockEntities.UNREFINED_MIRIN.get().create(pos, state);
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player entity, BlockHitResult rayTraceResult) {
        if(!world.isClientSide){
            if(state.getValue(SAUCE) && hasUpSideBlock(world, pos)){
                ItemStack insert = new ItemStack(JPItems.MIRIN.get());
                BlockEntity tileEntity = world.getBlockEntity(pos);
                if(tileEntity instanceof UnrefinedMirinTileEntity unrefinedMirinBlockEntity){
                    if(unrefinedMirinBlockEntity.getSauceRemaining() > 0) {
                        entity.getInventory().add(insert);
                        unrefinedMirinBlockEntity.useSauce();

                        if (unrefinedMirinBlockEntity.getSauceRemaining() <= 0) {
                            world.setBlock(pos, state.setValue(SAUCE, false), 3);
                        }
                    }
                }
            }
        }
        return InteractionResult.SUCCESS;
    }
}
