package jp.tdn.japanese_food_mod.blocks.tileentity;

import jp.tdn.japanese_food_mod.blocks.SoyHayBlock;
import jp.tdn.japanese_food_mod.init.JPBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;

public class SoyHayTileEntity extends BlockEntity {
    public static final String FERMENT_LEFT_TIME_TAG = "fermentLeftTime";
    private short fermentLeftTime = 6000;

    public SoyHayTileEntity(BlockPos pos, BlockState state){
        super(JPBlockEntities.SOY_HAY.get(), pos, state);
    }

    public short getFermentLeftTime(){
        return this.fermentLeftTime;
    }

    public static void tick(Level level, BlockPos pos, BlockState state, SoyHayTileEntity te) {
        if(level == null || level.isClientSide) return;

        if(te.getFermentLeftTime() > 0){
            --te.fermentLeftTime;
        }else{
            level.setBlockAndUpdate(pos, level.getBlockState(pos).setValue(SoyHayBlock.COMPLETION, true));
        }
    }

    @Override
    public void loadAdditional(CompoundTag compound, HolderLookup.Provider registries) {
        super.loadAdditional(compound, registries);
        this.fermentLeftTime = compound.getShort("fermentLeftTime");
    }

    @Override
    @Nonnull
    public void saveAdditional(CompoundTag compound, HolderLookup.Provider registries) {
        compound.putShort(FERMENT_LEFT_TIME_TAG, fermentLeftTime);
        super.saveAdditional(compound, registries);
    }

    @Nonnull
    public CompoundTag getUpdateTag(HolderLookup.Provider registries){
        CompoundTag tag = new CompoundTag();
        this.saveAdditional(tag, registries);
        return tag;
    }
}
