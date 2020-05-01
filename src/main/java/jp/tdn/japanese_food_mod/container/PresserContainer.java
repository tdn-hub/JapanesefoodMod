package jp.tdn.japanese_food_mod.container;

import jp.tdn.japanese_food_mod.blocks.tileentity.PresserTileEntity;
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

public class PresserContainer extends Container {
    public final PresserTileEntity tileEntity;
    private final IWorldPosCallable canInteractWithCallable;

    public PresserContainer(final int windowId, final PlayerInventory playerInventory, final PacketBuffer data){
        this(windowId, playerInventory, getTileEntity(playerInventory, data));
    }

    public PresserContainer(final int windowId, final PlayerInventory playerInventory, final PresserTileEntity tileEntity){
        super(JPContainerTypes.PRESSER, windowId);
        this.tileEntity = tileEntity;
        this.canInteractWithCallable = IWorldPosCallable.of(Objects.requireNonNull(tileEntity.getWorld()), tileEntity.getPos());

        this.trackInt(new FunctionalIntReferenceHolder(() -> tileEntity.pressedTimeLeft, v -> tileEntity.pressedTimeLeft = (short)v));
        this.trackInt(new FunctionalIntReferenceHolder(() -> tileEntity.maxPressedTime, v -> tileEntity.maxPressedTime = (short)v));
        this.trackInt(new FunctionalIntReferenceHolder(() -> tileEntity.oilRemaining, v -> tileEntity.oilRemaining = (short)v));
        this.trackInt(new FunctionalIntReferenceHolder(() -> tileEntity.maxOilRemaining, v -> tileEntity.maxOilRemaining = (short)v));

        this.addSlot(new SlotItemHandler(tileEntity.inventory, PresserTileEntity.INPUT_SLOT, 34, 8));
        this.addSlot(new SlotItemHandler(tileEntity.inventory, PresserTileEntity.OUTPUT_SLOT, 135, 55));
        this.addSlot(new SlotItemHandler(tileEntity.inventory, PresserTileEntity.CONTAINER_SLOT, 70, 51));

        final int playerInventoryStartX = 8;
        final int playerInventoryStartY = 88;
        final int slotSizePlus2 = 18;

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

    private static PresserTileEntity getTileEntity(final PlayerInventory playerInventory, final PacketBuffer data){
        Objects.requireNonNull(playerInventory, "playerInventory cannot be null");
        Objects.requireNonNull(data, "data cannot be null");
        final TileEntity tileAtPos = playerInventory.player.world.getTileEntity(data.readBlockPos());
        if(tileAtPos instanceof PresserTileEntity) return (PresserTileEntity)tileAtPos;
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
        return isWithinUsableDistance(canInteractWithCallable, player, JPBlocks.PRESSER);
    }
}
