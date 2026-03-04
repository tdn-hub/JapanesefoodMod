package jp.tdn.japanese_food_mod.blocks.tileentity;

import jp.tdn.japanese_food_mod.init.JPBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class UnrefinedSoySauceTileEntity extends UnrefinedTileEntity {
    public UnrefinedSoySauceTileEntity(BlockPos pos, BlockState state){
        super(JPBlockEntities.UNREFINED_SOY_SAUCE.get(), pos, state, (short) 8);
    }
}
