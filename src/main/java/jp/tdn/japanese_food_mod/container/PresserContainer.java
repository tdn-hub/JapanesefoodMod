package jp.tdn.japanese_food_mod.container;

import jp.tdn.japanese_food_mod.blocks.tileentity.PresserTileEntity;
import jp.tdn.japanese_food_mod.init.JPBlocks;
import jp.tdn.japanese_food_mod.init.JPContainerTypes;
import jp.tdn.japanese_food_mod.init.JPRecipeTypes;
import jp.tdn.japanese_food_mod.recipes.PresserRecipe;
import net.minecraft.block.ContainerBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntArray;
import net.minecraft.world.World;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;
import java.util.Objects;

public class PresserContainer extends Container {
    private final IIntArray data;
    private final IInventory container;
    protected final World level;
    private final IRecipeType<? extends PresserRecipe> recipeType;

    public PresserContainer(int windowId, PlayerInventory playerInventory){
        this(JPContainerTypes.PRESSER, JPRecipeTypes.PRESSER, windowId, playerInventory);
    }

    public PresserContainer(int windowId, PlayerInventory playerInventory, IInventory container, IIntArray data){
        this(JPContainerTypes.PRESSER, JPRecipeTypes.PRESSER, windowId, playerInventory, container, data);
    }

    protected PresserContainer(ContainerType<?> containerType, IRecipeType<? extends PresserRecipe> recipeType, int windowId, PlayerInventory inventory){
        this(containerType, recipeType, windowId, inventory, new Inventory(3), new IntArray(4));
    }

    protected PresserContainer(ContainerType<?> containerType, IRecipeType<? extends PresserRecipe> recipeType, int windowId, PlayerInventory playerInventory, IInventory container, IIntArray data){
        super(containerType, windowId);
        this.recipeType = recipeType;
        checkContainerSize(container, 3);
        checkContainerDataCount(data, 4);
        this.container = container;
        this.data = data;
        this.level = playerInventory.player.level;
        this.addSlot(new Slot(container, PresserTileEntity.INPUT_SLOT, 34, 8));
        this.addSlot(new Slot(container, PresserTileEntity.OUTPUT_SLOT, 135, 55));
        this.addSlot(new Slot(container, PresserTileEntity.CONTAINER_SLOT, 70, 51));

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

    @Override
    public boolean stillValid(PlayerEntity playerEntity) {
        return this.container.stillValid(playerEntity);
    }

    @Override
    @Nonnull
    public ItemStack quickMoveStack(PlayerEntity player, int index) {
        ItemStack returnStack = ItemStack.EMPTY;
        final Slot slot = this.slots.get(index);
        if(slot != null && slot.hasItem()){
            final ItemStack slotStack = slot.getItem();
            returnStack = slotStack.copy();

            final int containerSlots = this.slots.size() - player.inventory.items.size();
            if(index < containerSlots){
                if(!this.moveItemStackTo(slotStack, containerSlots, this.slots.size(), true)){
                    return ItemStack.EMPTY;
                }
            }else if(!this.moveItemStackTo(slotStack, 0, containerSlots, false)){

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
}
