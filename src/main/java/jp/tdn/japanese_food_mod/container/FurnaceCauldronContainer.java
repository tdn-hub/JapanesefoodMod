package jp.tdn.japanese_food_mod.container;

import jp.tdn.japanese_food_mod.blocks.tileentity.FurnaceCauldronTileEntity;
import jp.tdn.japanese_food_mod.blocks.tileentity.WoodenBucketTileEntity;
import jp.tdn.japanese_food_mod.init.JPBlocks;
import jp.tdn.japanese_food_mod.init.JPContainerTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;
import java.util.Objects;

public class FurnaceCauldronContainer extends Container {
    public final FurnaceCauldronTileEntity tileEntity;
    private final IWorldPosCallable canInteractWithCallable;

    public FurnaceCauldronContainer(final int windowId, final PlayerInventory playerInventory, final PacketBuffer data){
        this(windowId, playerInventory, getTileEntity(playerInventory, data));
    }

    public FurnaceCauldronContainer(final int windowId, final PlayerInventory playerInventory, final FurnaceCauldronTileEntity tileEntity){
        super(JPContainerTypes.FURNACE_CAULDRON, windowId);
        this.tileEntity = tileEntity;
        this.canInteractWithCallable = IWorldPosCallable.of(Objects.requireNonNull(tileEntity.getWorld()), tileEntity.getPos());

        this.trackInt(new FunctionalIntReferenceHolder(() -> tileEntity.heatingTimeLeft, v -> tileEntity.heatingTimeLeft = (int) v));
        this.trackInt(new FunctionalIntReferenceHolder(() -> tileEntity.maxHeatingTime, v -> tileEntity.maxHeatingTime = (int) v));
        this.trackInt(new FunctionalIntReferenceHolder(() -> tileEntity.waterRemaining, v -> tileEntity.waterRemaining = v));

        // InputSlot
        this.addSlot(new SlotItemHandler(tileEntity.inventory, FurnaceCauldronTileEntity.INPUT_SLOT, 12, 14));

        // OutputSlot
        this.addSlot(new SlotItemHandler(tileEntity.inventory, FurnaceCauldronTileEntity.OUTPUT_SLOT, 115, 34));

        // ReturnSlot
        int index = 0;
        final int slotSizePlus2 = 18;
        final int returnStartX = 97;
        final int returnStartY = 64;
        for(index = 0; index < WoodenBucketTileEntity.RETURN_SLOT.length; ++index){
            this.addSlot(new SlotItemHandler(tileEntity.inventory, FurnaceCauldronTileEntity.RETURN_SLOT[index], returnStartX + (index * slotSizePlus2), returnStartY));
        }

        // InventorySlot
        final int playerInventoryStartX = 8;
        final int playerInventoryStartY = 92;
        for(int row = 0; row < 3; ++row){
            for(int column = 0; column < 9; ++column){
                this.addSlot(new Slot(playerInventory, 9 + (row * 9) + column, playerInventoryStartX + (column * slotSizePlus2), playerInventoryStartY + (row * slotSizePlus2)));
            }
        }

        final int playerHotbarY = playerInventoryStartY + slotSizePlus2 * 3 + 4;
        for(int column = 0;column < 9; ++column){
            this.addSlot(new Slot(playerInventory, column, playerInventoryStartX + (column * slotSizePlus2), playerHotbarY));
        }
    }

    private static FurnaceCauldronTileEntity getTileEntity(final PlayerInventory playerInventory, final PacketBuffer data){
        Objects.requireNonNull(playerInventory, "playerInventory cannot be null");
        Objects.requireNonNull(data, "data cannot be null");
        final TileEntity tileAtPos = playerInventory.player.world.getTileEntity(data.readBlockPos());
        if(tileAtPos instanceof FurnaceCauldronTileEntity) {
            return (FurnaceCauldronTileEntity)tileAtPos;
        }
        throw new IllegalStateException("Tile entity is not correct" + tileAtPos);
    }

    @Override
    @Nonnull
    public ItemStack transferStackInSlot(PlayerEntity player, int index) {
        ItemStack returnStack = ItemStack.EMPTY;
        final Slot slot = this.inventorySlots.get(index);
        if(slot != null && slot.getHasStack()){
            final ItemStack slotStack = slot.getStack();
            returnStack = slotStack.copy();

            final int containerSlots = this.inventorySlots.size() - player.inventory.mainInventory.size();
            if(index < containerSlots){
                if(!mergeItemStack(slotStack, containerSlots, this.inventorySlots.size(), true)){
                    return ItemStack.EMPTY;
                }
            }else if(!mergeItemStack(slotStack, 0, containerSlots, false)){

                return ItemStack.EMPTY;
            }

            if(slotStack.getCount() == 0){
                slot.putStack(ItemStack.EMPTY);
            }else{
                slot.onSlotChanged();
            }

            if(slotStack.getCount() == returnStack.getCount()){
                return ItemStack.EMPTY;
            }

            slot.onTake(player, slotStack);
        }
        return returnStack;
    }

    @Override
    public boolean canInteractWith(@Nonnull final PlayerEntity player) {
        return isWithinUsableDistance(canInteractWithCallable, player, JPBlocks.FURNACE_CAULDRON.get());
    }
}
