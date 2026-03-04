package jp.tdn.japanese_food_mod.blocks.tileentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;

public class UnrefinedTileEntity extends BlockEntity {
    public static final String SAUCE_REMAINING_TAG = "sauceRemaining";
    private short sauceRemaining;

    public UnrefinedTileEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, short sauceRemaining){
        super(type, pos, state);
        this.sauceRemaining = sauceRemaining;
    }

    public void useSauce(){
        --sauceRemaining;
    }

    public short getSauceRemaining(){
        return this.sauceRemaining;
    }

    @Override
    public void loadAdditional(CompoundTag compound, HolderLookup.Provider registries) {
        super.loadAdditional(compound, registries);
        this.sauceRemaining = compound.getShort("sauceRemaining");
    }

    @Override
    @Nonnull
    public void saveAdditional(CompoundTag compound, HolderLookup.Provider registries) {
        compound.putShort(SAUCE_REMAINING_TAG, sauceRemaining);
        super.saveAdditional(compound, registries);
    }

    @Nonnull
    public CompoundTag getUpdateTag(HolderLookup.Provider registries){
        CompoundTag tag = new CompoundTag();
        this.saveAdditional(tag, registries);
        return tag;
    }
}
