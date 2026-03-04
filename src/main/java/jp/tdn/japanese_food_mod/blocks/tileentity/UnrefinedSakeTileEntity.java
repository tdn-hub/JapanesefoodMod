package jp.tdn.japanese_food_mod.blocks.tileentity;

import jp.tdn.japanese_food_mod.init.JPBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class UnrefinedSakeTileEntity extends UnrefinedTileEntity {
    public UnrefinedSakeTileEntity(BlockPos pos, BlockState state){
        super(JPBlockEntities.UNREFINED_SAKE.get(), pos, state, (short) 4);
    }
}
