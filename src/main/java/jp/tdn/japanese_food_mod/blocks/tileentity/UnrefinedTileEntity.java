package jp.tdn.japanese_food_mod.blocks.tileentity;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

import javax.annotation.Nonnull;

public class UnrefinedTileEntity extends TileEntity{
    public static final String SAUCE_REMAINING_TAG = "sauceRemaining";
    private short sauceRemaining;

    public UnrefinedTileEntity(TileEntityType<?> type, short sauceRemaining){
        super(type);
        this.sauceRemaining = sauceRemaining;
    }

    public void useSauce(){
        --sauceRemaining;
    }

    public short getSauceRemaining(){
        return this.sauceRemaining;
    }

    @Override
    public void func_230337_a_(BlockState state, CompoundNBT compound) {
        super.func_230337_a_(state, compound);
        this.sauceRemaining = compound.getShort("sauceRemaining");
    }

    @Override
    @Nonnull
    public CompoundNBT write(CompoundNBT compound) {
        compound.putShort(SAUCE_REMAINING_TAG, sauceRemaining);
        return super.write(compound);
    }

    @Nonnull
    public CompoundNBT getUpdateTag(){
        return this.write(new CompoundNBT());
    }

    @Override
    public void remove() {
        super.remove();
    }
}
