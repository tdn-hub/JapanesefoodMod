package jp.tdn.japanese_food_mod.blocks.tileentity;

import jp.tdn.japanese_food_mod.container.WoodenBucketContainer;
import jp.tdn.japanese_food_mod.init.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nonnull;

public class WoodenBucketTileEntity extends AbstractBucketTileEntity {


    public WoodenBucketTileEntity(){
        super(JPTileEntities.WOODEN_BUCKET);
    }

    @Nonnull
    @Override
    protected Container createMenu(int windowId, @Nonnull PlayerInventory inventory) {
        return new WoodenBucketContainer(windowId, inventory, this, this.dataAccess);
    }

    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container.wooden_bucket");
    }
}
