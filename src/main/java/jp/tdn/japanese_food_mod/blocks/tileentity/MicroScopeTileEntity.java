package jp.tdn.japanese_food_mod.blocks.tileentity;

import com.sun.jna.platform.win32.WinUser;
import jp.tdn.japanese_food_mod.container.MicroScopeContainer;
import jp.tdn.japanese_food_mod.init.JPBlocks;
import jp.tdn.japanese_food_mod.init.JPTileEntities;
import jp.tdn.japanese_food_mod.recipes.MicroScopeRecipe;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RangedWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public class MicroScopeTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider {
    public static final int INPUT_SLOT = 0;
    public static final int OUTPUT_SLOT = 1;

    private static final String INVENTORY_TAG = "inventory";
    private static final String IDENTIFIED_TIME_LEFT_TAG = "identifiedTimeLeft";
    private static final String MAX_IDENTIFIED_TIME_TAG = "maxIdentifiedTime";

    public ItemStackHandler inventory = new ItemStackHandler(2){
        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            switch (slot){
                case INPUT_SLOT:
                    return isInput(stack);
                case OUTPUT_SLOT:
                    return isOutput(stack);
                default:
                    return false;
            }
        }

        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            MicroScopeTileEntity.this.markDirty();
        }
    };

    private final LazyOptional<ItemStackHandler> inventoryCapabilityExternal = LazyOptional.of(() -> this.inventory);
    private final LazyOptional<IItemHandlerModifiable> inventoryCapabilityExternalUpAndSides = LazyOptional.of(() -> new RangedWrapper(this.inventory, INPUT_SLOT, INPUT_SLOT + 1));
    private final LazyOptional<IItemHandlerModifiable> inventoryCapabilityExternalDown = LazyOptional.of(() -> new RangedWrapper(this.inventory, OUTPUT_SLOT, OUTPUT_SLOT + 1));

    public short identifiedTimeLeft = -1;
    public short maxIdentifiedTime = -1;

    public MicroScopeTileEntity(){
        super(JPTileEntities.MICROSCOPE);
    }

    private boolean isInput(final ItemStack stack){
        if(stack.isEmpty()) return false;
        return getRecipe(stack).isPresent();
    }

    private boolean isOutput(final ItemStack stack){
        final Optional<ItemStack> result = getResult(inventory.getStackInSlot(INPUT_SLOT));
        return result.isPresent() && ItemStack.areItemsEqual(result.get(), stack);
    }

    private Optional<MicroScopeRecipe> getRecipe(final ItemStack input){
        return getRecipe(new Inventory(input));
    }

    private Optional<MicroScopeRecipe> getRecipe(final IInventory inventory){
        return world.getRecipeManager().getRecipe(MicroScopeRecipe.RECIPE_TYPE, inventory, world);
    }

    private Optional<ItemStack> getResult(final ItemStack input){
        final Inventory dummyInventory = new Inventory(input);
        return getRecipe(dummyInventory).map(recipe -> recipe.getCraftingResult(dummyInventory));
    }

    @Override
    public void tick() {
        if(world == null || world.isRemote) return;

        final ItemStack input = inventory.getStackInSlot(INPUT_SLOT);
        final ItemStack result = getResult(input).orElse(ItemStack.EMPTY);

        if(!result.isEmpty() && isInput(input)){
            final boolean canInsertResultIntoOutPut = inventory.insertItem(OUTPUT_SLOT, result, true).isEmpty();
            if(canInsertResultIntoOutPut){
                if(identifiedTimeLeft == -1){
                    identifiedTimeLeft = maxIdentifiedTime = getIdentifiedTime(input);
                }else{
                    --identifiedTimeLeft;
                    if(identifiedTimeLeft == 0){
                        inventory.insertItem(OUTPUT_SLOT, result, false);
                        if(input.hasContainerItem()) insertOrDropContainerItem(input, INPUT_SLOT);
                        input.shrink(1);
                        inventory.setStackInSlot(INPUT_SLOT, input);
                        identifiedTimeLeft = -1;
                    }
                }
            }
        }else{
            identifiedTimeLeft = maxIdentifiedTime = -1;
        }
    }

    private void insertOrDropContainerItem(final ItemStack stack, final int slot){
        final ItemStack containerItem = stack.getContainerItem();
        final boolean canInsertContainerItemIntoSlot = inventory.insertItem(slot, containerItem, true).isEmpty();
        if(canInsertContainerItemIntoSlot){
            inventory.insertItem(slot, containerItem, false);
        }else{
            InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), containerItem);
        }
    }

    private short getIdentifiedTime(final ItemStack input){
        return getRecipe(input).map(MicroScopeRecipe::getCookTime).orElse(200).shortValue();
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
            return inventoryCapabilityExternal.cast();
        }

        switch (side){

            case DOWN:
                return inventoryCapabilityExternalDown.cast();
            case UP:
            case NORTH:
            case SOUTH:
            case WEST:
            case EAST:
                return inventoryCapabilityExternalUpAndSides.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        this.inventory.deserializeNBT(compound.getCompound(INVENTORY_TAG));
        this.identifiedTimeLeft = compound.getShort(IDENTIFIED_TIME_LEFT_TAG);
        this.maxIdentifiedTime = compound.getShort(MAX_IDENTIFIED_TIME_TAG);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        compound.put(INVENTORY_TAG, this.inventory.serializeNBT());
        compound.putShort(IDENTIFIED_TIME_LEFT_TAG, this.identifiedTimeLeft);
        compound.putShort(MAX_IDENTIFIED_TIME_TAG, this.maxIdentifiedTime);
        return compound;
    }

    @Nonnull
    public CompoundNBT getUpdateTag(){
        return this.write(new CompoundNBT());
    }

    @Override
    public void remove() {
        super.remove();
        inventoryCapabilityExternal.invalidate();
    }

    @Nonnull
    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent(JPBlocks.MICRO_SCOPE.getTranslationKey());
    }

    @Nonnull
    @Override
    public Container createMenu(int windowId, PlayerInventory inventory, PlayerEntity player) {
        return new MicroScopeContainer(windowId, inventory, this);
    }
}
