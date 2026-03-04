package jp.tdn.japanese_food_mod.container;

import jp.tdn.japanese_food_mod.blocks.tileentity.PresserTileEntity;
import jp.tdn.japanese_food_mod.init.JPBlocks;
import jp.tdn.japanese_food_mod.init.JPMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.SlotItemHandler;

import javax.annotation.Nonnull;
import java.util.Objects;

public class PresserContainer extends AbstractContainerMenu {
    public final PresserTileEntity tileEntity;
    private final ContainerLevelAccess canInteractWithCallable;

    public PresserContainer(final int windowId, final Inventory playerInventory, final FriendlyByteBuf data){
        this(windowId, playerInventory, getTileEntity(playerInventory, data));
    }

    public PresserContainer(final int windowId, final Inventory playerInventory, final PresserTileEntity tileEntity){
        super(JPMenuTypes.PRESSER.get(), windowId);
        this.tileEntity = tileEntity;
        this.canInteractWithCallable = ContainerLevelAccess.create(Objects.requireNonNull(tileEntity.getLevel()), tileEntity.getBlockPos());

        this.addDataSlot(new FunctionalIntReferenceHolder(() -> (int) tileEntity.pressedTimeLeft, v -> tileEntity.pressedTimeLeft = (short) v));
        this.addDataSlot(new FunctionalIntReferenceHolder(() -> (int) tileEntity.maxPressedTime, v -> tileEntity.maxPressedTime = (short) v));
        this.addDataSlot(new FunctionalIntReferenceHolder(() -> (int) tileEntity.oilRemaining, v -> tileEntity.oilRemaining = (short) v));
        this.addDataSlot(new FunctionalIntReferenceHolder(() -> (int) tileEntity.maxOilRemaining, v -> tileEntity.maxOilRemaining = (short) v));

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
        for(int column = 0; column < 9; ++column){
            this.addSlot(new Slot(playerInventory, column, playerInventoryStartX + (column * slotSizePlus2), playerHotbarY));
        }
    }

    private static PresserTileEntity getTileEntity(final Inventory playerInventory, final FriendlyByteBuf data){
        Objects.requireNonNull(playerInventory, "playerInventory cannot be null");
        Objects.requireNonNull(data, "data cannot be null");
        final BlockEntity tileAtPos = playerInventory.player.level().getBlockEntity(data.readBlockPos());
        if(tileAtPos instanceof PresserTileEntity) return (PresserTileEntity) tileAtPos;
        throw new IllegalStateException("Tile entity is not correct" + tileAtPos);
    }

    @Override
    @Nonnull
    public ItemStack quickMoveStack(@Nonnull Player player, int index) {
        ItemStack returnStack = ItemStack.EMPTY;
        final Slot slot = this.slots.get(index);
        if(slot.hasItem()){
            final ItemStack slotStack = slot.getItem();
            returnStack = slotStack.copy();

            final int containerSlots = this.slots.size() - player.getInventory().items.size();
            if(index < containerSlots){
                if(!moveItemStackTo(slotStack, containerSlots, this.slots.size(), true)){
                    return ItemStack.EMPTY;
                }
            }else if(!moveItemStackTo(slotStack, 0, containerSlots, false)){
                return ItemStack.EMPTY;
            }

            if(slotStack.getCount() == 0){
                slot.set(ItemStack.EMPTY);
            }else{
                slot.setChanged();
            }

            if(slotStack.getCount() == returnStack.getCount()){
                return ItemStack.EMPTY;
            }

            slot.onTake(player, slotStack);
        }
        return returnStack;
    }

    @Override
    public boolean stillValid(@Nonnull final Player player) {
        return stillValid(canInteractWithCallable, player, JPBlocks.PRESSER.get());
    }
}
