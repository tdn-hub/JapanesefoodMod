package jp.tdn.japanese_food_mod.blocks.tileentity;

import com.google.common.collect.Lists;
import jp.tdn.japanese_food_mod.blocks.WoodenBucketBlock;
import jp.tdn.japanese_food_mod.container.WoodenBucketContainer;
import jp.tdn.japanese_food_mod.init.JPBlocks;
import jp.tdn.japanese_food_mod.init.JPTileEntities;
import jp.tdn.japanese_food_mod.recipes.WoodenBucketRecipe;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
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
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class WoodenBucketTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider {
    public static final int[] INPUT_SLOT = {0,1,2,3,4,5};
    public static final int OUTPUT_SLOT = 6;

    private static final String INVENTORY_TAG = "inventory";
    private static final String FERMENTATION_TIME_LEFT = "fermentation_time_left";
    private static final String MAX_FERMENTATION_TIME = "max_fermentation_time";

    public ItemStackHandler inventory = new ItemStackHandler(7){
        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            switch (slot){
                // case INPUT_SLOT:
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
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
    private final LazyOptional<IItemHandlerModifiable> inventoryCapabilityExternalUpAndSides = LazyOptional.of(() -> new RangedWrapper(this.inventory, INPUT_SLOT[0], INPUT_SLOT[5] + 1));
    private final LazyOptional<IItemHandlerModifiable> inventoryCapabilityExternalDown = LazyOptional.of(() -> new RangedWrapper(this.inventory, OUTPUT_SLOT, OUTPUT_SLOT + 1));

    public short fermentationTimeLeft = -1;
    public short maxFermentationTime = -1;
    private boolean lastActive = false;

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
        ItemStack[] input = new ItemStack[INPUT_SLOT.length];
        for(int index = 0; index < INPUT_SLOT.length; ++index) {
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
        return Objects.requireNonNull(world).getRecipeManager().getRecipe(WoodenBucketRecipe.RECIPE_TYPE, inventory, world);
    }

    private Optional<ItemStack> getResult(final ItemStack[] input){
        final Inventory dummyInventory = new Inventory(input);
        return getRecipe(dummyInventory).map(recipe -> recipe.getCraftingResult(dummyInventory));
    }

    @Override
    public void tick() {
        if(world == null || world.isRemote) return;
        boolean isActive = false;

        final List<ItemStack> inputs = Lists.newArrayList();
        for(int index = 0; index < INPUT_SLOT.length; ++index){
            ItemStack stack = inventory.getStackInSlot(index);
            if(!stack.isEmpty()){
                inputs.add(stack);
            }
        }
        final ItemStack result = getResult(inputs.toArray(new ItemStack[0])).orElse(ItemStack.EMPTY);

        if(!result.isEmpty() && isInput(inputs.toArray(new ItemStack[0]))){
            final boolean canInsertResultIntoOutPut = inventory.insertItem(OUTPUT_SLOT, result, true).isEmpty();
            if(canInsertResultIntoOutPut){
                isActive = true;
                if(fermentationTimeLeft == -1){
                    fermentationTimeLeft = maxFermentationTime = getFermentationTime(inputs.toArray(new ItemStack[0]));
                }else{
                    --fermentationTimeLeft;
                    if(fermentationTimeLeft == 0){
                        inventory.insertItem(OUTPUT_SLOT, result, false);
                        int i = 0;
                        for(ItemStack input: inputs) {
                            if(i < INPUT_SLOT.length) {
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

        if(lastActive != isActive){
            markDirty();
            final BlockState newState = world.getBlockState(pos).with(WoodenBucketBlock.FER, isActive);
            world.setBlockState(pos, newState);
            lastActive = isActive;
        }
    }

    private void insertOrDropContainerItem(final ItemStack stack, final int slot){
        final ItemStack containerItem = stack.getContainerItem();
        final boolean canInsertContainerItemIntoSlot = inventory.insertItem(slot, containerItem, true).isEmpty();
        if(canInsertContainerItemIntoSlot){
            inventory.insertItem(slot, containerItem, false);
        }else{
            InventoryHelper.spawnItemStack(Objects.requireNonNull(world), pos.getX(), pos.getY(), pos.getZ(), containerItem);
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

        switch (Objects.requireNonNull(side)){

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
    @Nonnull
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
    public Container createMenu(int windowId, @Nonnull PlayerInventory inventory, @Nonnull PlayerEntity player) {
        return new WoodenBucketContainer(windowId, inventory, this);
    }
}
