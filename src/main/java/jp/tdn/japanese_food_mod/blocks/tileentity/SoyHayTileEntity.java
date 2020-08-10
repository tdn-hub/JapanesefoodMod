package jp.tdn.japanese_food_mod.blocks.tileentity;

import jp.tdn.japanese_food_mod.blocks.SoyHayBlock;
import jp.tdn.japanese_food_mod.init.JPTileEntities;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nonnull;

public class SoyHayTileEntity extends TileEntity implements ITickableTileEntity {
    public static final String FERMENT_LEFT_TIME_TAG = "fermentLeftTime";
    private short fermentLeftTime = 6000;

    public SoyHayTileEntity(){
        super(JPTileEntities.SOY_HAY);
    }

    public short getFermentLeftTime(){
        return this.fermentLeftTime;
    }

    @Override
    public void tick() {
        if(world == null || world.isRemote)return;

        if(getFermentLeftTime() > 0){
            --fermentLeftTime;
        }else{
            world.setBlockState(pos, world.getBlockState(pos).with(SoyHayBlock.COMPLETION, true));
        }
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        this.fermentLeftTime = compound.getShort("fermentLeftTime");
    }

    @Override
    @Nonnull
    public CompoundNBT write(CompoundNBT compound) {
        compound.putShort(FERMENT_LEFT_TIME_TAG, fermentLeftTime);
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
