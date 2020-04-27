package jp.tdn.japanese_food_mod.blocks.tileentity;

import jp.tdn.japanese_food_mod.JapaneseFoodMod;
import jp.tdn.japanese_food_mod.container.MicroScopeContainer;
import jp.tdn.japanese_food_mod.container.WoodenBucketContainer;
import jp.tdn.japanese_food_mod.init.JPBlocks;
import jp.tdn.japanese_food_mod.init.JPTileEntities;
import jp.tdn.japanese_food_mod.recipes.MicroScopeRecipe;
import jp.tdn.japanese_food_mod.recipes.WoodenBucketRecipe;
import jp.tdn.japanese_food_mod.recipes.WoodenBucketRecipe;
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
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.RecipeMatcher;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RangedWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WoodenBucketTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider {
    public static final int[] INPUT_SLOT = {0,1,2};
    public static final int OUTPUT_SLOT = 3;

    private static final String INVENTORY_TAG = "inventory";
    private static final String FERMENTATION_TIME_LEFT = "fermentation_time_left";
    private static final String MAX_FERMENTATION_TIME = "max_fermentation_time";

    public ItemStackHandler inventory = new ItemStackHandler(4){
        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            switch (slot){
                // case INPUT_SLOT:
                case 0:
                case 1:
                case 2:
                    return true;
                case OUTPUT_SLOT:
                    return isOutput(stack);
                default:
                    return false;
            }
        }

        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            WoodenBucketTileEntity.this.markDirty();
        }
    };

    private final LazyOptional<ItemStackHandler> inventoryCapabilityExternal = LazyOptional.of(() -> this.inventory);
    private final LazyOptional<IItemHandlerModifiable> inventoryCapabilityExternalUpAndSides = LazyOptional.of(() -> new RangedWrapper(this.inventory, INPUT_SLOT[0], INPUT_SLOT[2] + 1));
    private final LazyOptional<IItemHandlerModifiable> inventoryCapabilityExternalDown = LazyOptional.of(() -> new RangedWrapper(this.inventory, OUTPUT_SLOT, OUTPUT_SLOT + 1));

    public short fermentationTimeLeft = -1;
    public short maxFermentationTime = -1;

    public WoodenBucketTileEntity(){
        super(JPTileEntities.WOODEN_BUCKET);
    }

    private boolean isEmpty(final ItemStack[] stack){
        boolean rec = false;
        for(int index = 0; !rec && index < stack.length; ++index){
            rec = stack[index].isEmpty();
        }
        return rec;
    }

    private boolean isInput(final ItemStack[] stack){
        if(isEmpty(stack)) return false;
        return getRecipe(stack).isPresent();
    }

    private boolean isOutput(final ItemStack stack){
        final Optional<ItemStack> result;
        ItemStack input[] = new ItemStack[3];
        for(int index = 0; index < 3; ++index) {
            input[index] = inventory.getStackInSlot(INPUT_SLOT[index]);
        }
        result = getResult(input);
        return result.isPresent() && ItemStack.areItemsEqual(result.get(), stack);
    }

    private Optional<WoodenBucketRecipe> getRecipe(final ItemStack input){
        return getRecipe(new Inventory(input));
    }

    private Optional<WoodenBucketRecipe> getRecipe(final ItemStack[] input){
        return getRecipe(new Inventory(input));
    }

    private Optional<WoodenBucketRecipe> getRecipe(final IInventory inventory){
        return world.getRecipeManager().getRecipe(WoodenBucketRecipe.RECIPE_TYPE, inventory, world);
    }

    private Optional<ItemStack> getResult(final ItemStack[] input){
        final Inventory dummyInventory = new Inventory(input);
        return getRecipe(dummyInventory).map(recipe -> recipe.getCraftingResult(dummyInventory));
    }

    @Override
    public void tick() {
        if(world == null || world.isRemote) return;

        final List<ItemStack> inputs = new ArrayList();
        for(int index = 0; index <= INPUT_SLOT[2]; ++index){
            inputs.add(inventory.getStackInSlot(index));
        }
        final ItemStack result = getResult(inputs.toArray(new ItemStack[inputs.size()])).orElse(ItemStack.EMPTY);

        if(!result.isEmpty() && isInput(inputs.toArray(new ItemStack[inputs.size()]))){
            final boolean canInsertResultIntoOutPut = inventory.insertItem(OUTPUT_SLOT, result, true).isEmpty();
            if(canInsertResultIntoOutPut){
                if(fermentationTimeLeft == -1){
                    fermentationTimeLeft = maxFermentationTime = getFermentationTime(inputs.toArray(new ItemStack[inputs.size()]));
                }else{
                    --fermentationTimeLeft;
                    if(fermentationTimeLeft == 0){
                        inventory.insertItem(OUTPUT_SLOT, result, false);
                        int i = 0;
                        for(ItemStack input: inputs) {
                            if(i <= INPUT_SLOT[2]) {
                                if (input.hasContainerItem()) insertOrDropContainerItem(input, INPUT_SLOT[i]);
                                input.shrink(1);
                                inventory.setStackInSlot(INPUT_SLOT[i], input);
                                ++i;
                            }
                        }
                        fermentationTimeLeft = -1;
                    }
                }
            }
        }else{
            fermentationTimeLeft = maxFermentationTime = -1;
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

    private short getFermentationTime(final ItemStack[] input){
        return getRecipe(input).map(WoodenBucketRecipe::getCookTime).orElse(200).shortValue();
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
        this.fermentationTimeLeft = compound.getShort(FERMENTATION_TIME_LEFT);
        this.maxFermentationTime = compound.getShort(MAX_FERMENTATION_TIME);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        compound.put(INVENTORY_TAG, this.inventory.serializeNBT());
        compound.putShort(FERMENTATION_TIME_LEFT, this.fermentationTimeLeft);
        compound.putShort(MAX_FERMENTATION_TIME, this.maxFermentationTime);
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
        return new TranslationTextComponent(JPBlocks.WOODEN_BUCKET.getTranslationKey());
    }

    @Nonnull
    @Override
    public Container createMenu(int windowId, PlayerInventory inventory, PlayerEntity player) {
        return new WoodenBucketContainer(windowId, inventory, this);
    }
}
