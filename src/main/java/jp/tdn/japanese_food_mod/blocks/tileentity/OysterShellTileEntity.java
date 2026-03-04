package jp.tdn.japanese_food_mod.blocks.tileentity;

import jp.tdn.japanese_food_mod.init.JPBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;

public class OysterShellTileEntity extends BlockEntity {
    public static final String NORI_REMAINING_TAG = "noriRemaining";
    public static final String ELAPSED_TIME_TAG = "elapsedTime";
    private short noriRemaining = -1;
    private short elapsedTime = -1;
    private short maxNori = 10;
    private short timeToGrow = 1000;

    public OysterShellTileEntity(BlockPos pos, BlockState state){
        super(JPBlockEntities.OYSTER_SHELL.get(), pos, state);
    }

    public void useNori(){
        if(getnoriRemaining() >= 0)
            --noriRemaining;
    }

    private void growNori(){
        ++noriRemaining;
    }

    public short getnoriRemaining(){
        return this.noriRemaining;
    }

    public boolean isEmpty(){
        return noriRemaining < 0;
    }

    public static void tick(Level level, BlockPos pos, BlockState state, OysterShellTileEntity te) {
        if(level == null || level.isClientSide) return;
        if(level.getFluidState(pos).is(FluidTags.WATER) && te.getnoriRemaining() < te.maxNori){
            if(++te.elapsedTime > te.timeToGrow){
                te.elapsedTime = -1;
                te.growNori();
            }
        }
    }

    @Override
    public void loadAdditional(CompoundTag compound, HolderLookup.Provider registries) {
        super.loadAdditional(compound, registries);
        this.noriRemaining = compound.getShort("noriRemaining");
        this.elapsedTime = compound.getShort("elapsedTime");
    }

    @Override
    @Nonnull
    public void saveAdditional(CompoundTag compound, HolderLookup.Provider registries) {
        compound.putShort(NORI_REMAINING_TAG, noriRemaining);
        compound.putShort(ELAPSED_TIME_TAG, elapsedTime);
        super.saveAdditional(compound, registries);
    }

    @Nonnull
    public CompoundTag getUpdateTag(HolderLookup.Provider registries){
        CompoundTag tag = new CompoundTag();
        this.saveAdditional(tag, registries);
        return tag;
    }
}
