package jp.tdn.japanese_food_mod.blocks.tileentity;

import jp.tdn.japanese_food_mod.init.JPBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;

public class MortarTileEntity extends BlockEntity {
    private boolean hasRice = false;
    private short poundCount = 0;
    private boolean isComplete = false;

    public static final int POUNDS_TO_COMPLETE = 10;

    public MortarTileEntity(BlockPos pos, BlockState state) {
        super(JPBlockEntities.MORTAR.get(), pos, state);
    }

    public boolean hasRice() {
        return hasRice;
    }

    public void setRice(boolean value) {
        this.hasRice = value;
        setChanged();
    }

    public boolean isComplete() {
        return isComplete;
    }

    public short getPoundCount() {
        return poundCount;
    }

    public void pound() {
        if (hasRice && !isComplete) {
            poundCount++;
            if (poundCount >= POUNDS_TO_COMPLETE) {
                isComplete = true;
            }
            setChanged();
        }
    }

    public void reset() {
        hasRice = false;
        poundCount = 0;
        isComplete = false;
        setChanged();
    }

    public int getPoundingStage() {
        if (!hasRice) return 0;
        if (isComplete) return 3;
        if (poundCount >= 5) return 2;
        return 1;
    }

    @Override
    public void loadAdditional(CompoundTag compound, HolderLookup.Provider registries) {
        super.loadAdditional(compound, registries);
        this.hasRice = compound.getBoolean("hasRice");
        this.poundCount = compound.getShort("poundCount");
        this.isComplete = compound.getBoolean("isComplete");
    }

    @Override
    public void saveAdditional(CompoundTag compound, HolderLookup.Provider registries) {
        compound.putBoolean("hasRice", hasRice);
        compound.putShort("poundCount", poundCount);
        compound.putBoolean("isComplete", isComplete);
        super.saveAdditional(compound, registries);
    }

    @Nonnull
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = new CompoundTag();
        this.saveAdditional(tag, registries);
        return tag;
    }
}
