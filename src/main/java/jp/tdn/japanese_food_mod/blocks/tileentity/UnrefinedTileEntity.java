package jp.tdn.japanese_food_mod.blocks.tileentity;

import jp.tdn.japanese_food_mod.init.JPTileEntities;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class UnrefinedTileEntity extends TileEntity{
    public static final String SAUCE_REMAINING_TAG = "sauceRemaining";
    public short sauceRemaining = 8;

    public UnrefinedTileEntity(){
        super(JPTileEntities.UNREFINED);
    }

    public int useSauce(){
        return --sauceRemaining;
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
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
