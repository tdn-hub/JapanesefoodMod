package jp.tdn.japanese_food_mod.container;

import jp.tdn.japanese_food_mod.blocks.tileentity.WoodenBucketTileEntity;
import jp.tdn.japanese_food_mod.init.JPBlocks;
import jp.tdn.japanese_food_mod.init.JPContainerTypes;
import jp.tdn.japanese_food_mod.init.JPRecipeTypes;
import jp.tdn.japanese_food_mod.recipes.FermentationRecipe;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.AbstractCookingRecipe;
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

public class WoodenBucketContainer extends Container {
    private final IInventory container;
    private final IIntArray data;
    protected final World level;
    private final IRecipeType<? extends FermentationRecipe> recipeType;

    public WoodenBucketContainer(int windowId, PlayerInventory playerInventory, IInventory container, IIntArray data){
        this(JPContainerTypes.WOODEN_BUCKET, JPRecipeTypes.FERMENTATION, windowId, playerInventory, container, data);
    }

    protected WoodenBucketContainer(ContainerType<?> containerType, IRecipeType<? extends FermentationRecipe> recipeType, int windowId, PlayerInventory playerInventory){
        this(containerType, recipeType, windowId, playerInventory, new Inventory(10), new IntArray(2));
    }

    public WoodenBucketContainer(ContainerType<?> containerType, IRecipeType<? extends FermentationRecipe> recipeType, int windowId, PlayerInventory playerInventory, IInventory container, IIntArray data){
        super(containerType, windowId);
        this.recipeType = recipeType;
        checkContainerSize(container, 10);
        checkContainerDataCount(data, 2);
        this.container = container;
        this.data = data;
        this.level = playerInventory.player.level;

        // InputSlot
        final int inputStartX = 21;
        final int inputStartY = 20;
        final int slotSizePlus2 = 18;
        int index = 0;
        for(int row = 0;row < 3; ++row){
            for(int col = 0;col < 2; ++col){
                this.addSlot(new Slot(container, WoodenBucketTileEntity.INPUT_SLOT[index], inputStartX + (col * slotSizePlus2), inputStartY + (row * slotSizePlus2)));
                ++index;
            }
        }

        // OutputSlot
        this.addSlot(new Slot(container, WoodenBucketTileEntity.OUTPUT_SLOT, 115, 34));

        // ReturnSlot
        final int returnStartX = 97;
        final int returnStartY = 64;
        for(index = 0; index < WoodenBucketTileEntity.RETURN_SLOT.length; ++index){
            this.addSlot(new Slot(container, WoodenBucketTileEntity.RETURN_SLOT[index], returnStartX + (index * slotSizePlus2), returnStartY));
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
    public boolean stillValid(PlayerEntity playerEntity) {
        return this.container.stillValid(playerEntity);
    }
}
