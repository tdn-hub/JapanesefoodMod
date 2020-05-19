package jp.tdn.japanese_food_mod.blocks.tileentity;

import jp.tdn.japanese_food_mod.blocks.PresserBlock;
import jp.tdn.japanese_food_mod.container.PresserContainer;
import jp.tdn.japanese_food_mod.init.JPBlocks;
import jp.tdn.japanese_food_mod.init.JPTileEntities;
import jp.tdn.japanese_food_mod.recipes.PresserRecipe;
import net.minecraft.block.BlockState;
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
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.Optional;

import static jp.tdn.japanese_food_mod.init.JPItems.COOKING_OIL;

public class PresserTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider {
    public static final int INPUT_SLOT = 0;
    public static final int CONTAINER_SLOT = 2;
    public static final int OUTPUT_SLOT = 1;

    private static final String INVENTORY_TAG = "inventory";
    private static final String PRESSED_TIME_LEFT_TAG = "pressedTimeLeft";
    private static final String MAX_PRESSED_TIME_TAG = "maxPressedTime";
    private static final String OIL_REMAINING_TAG = "oilRemaining";

    public ItemStackHandler inventory = new ItemStackHandler(3){
        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            switch (slot){
                case INPUT_SLOT:
                    return isInput(stack);
                case OUTPUT_SLOT:
                    return isOutput(stack);
                case CONTAINER_SLOT:
                    return isContainerInput(stack);
                default:
                    return false;
            }
        }

        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            PresserTileEntity.this.markDirty();
        }
    };

//    private final LazyOptional<ItemStackHandler> inventoryCapabilityExternal = LazyOptional.of(() -> this.inventory);
//    private final LazyOptional<IItemHandlerModifiable> inventoryCapabilityExternalUpAndSides = LazyOptional.of(() -> new RangedWrapper(this.inventory, INPUT_SLOT, INPUT_SLOT + 1));
//    private final LazyOptional<IItemHandlerModifiable> inventoryCapabilityExternalDown = LazyOptional.of(() -> new RangedWrapper(this.inventory, OUTPUT_SLOT, OUTPUT_SLOT + 1));

    public short pressedTimeLeft = -1;
    public short maxPressedTime = -1;
    public short oilRemaining = 0;
    public short maxOilRemaining = 500;
    private boolean lastPressing = false;

    public PresserTileEntity(){
        super(JPTileEntities.PRESSER);
    }

    private boolean isInput(final ItemStack stack){
        if(stack.isEmpty()) return false;
        return getRecipe(stack).isPresent();
    }

    private boolean isContainerInput(final ItemStack stack){
        return stack.getItem() == Items.GLASS_BOTTLE;
    }

    private boolean isOutput(final ItemStack stack){
        return stack.getItem() == COOKING_OIL;
    }

    private Optional<PresserRecipe> getRecipe(final ItemStack input){
        return getRecipe(new Inventory(input));
    }

    private Optional<PresserRecipe> getRecipe(final IInventory inventory){
        return Objects.requireNonNull(world).getRecipeManager().getRecipe(PresserRecipe.RECIPE_TYPE, inventory, world);
    }

    private short getResult(final ItemStack input){
        return getRecipe(input).map(PresserRecipe::getResult).orElse(-1).shortValue();
    }

    @Override
    public void tick() {
        if(world == null || world.isRemote) return;

        boolean isPressing = false;
        final ItemStack input = inventory.getStackInSlot(INPUT_SLOT);
        final ItemStack container = inventory.getStackInSlot(CONTAINER_SLOT);
        final int result = getResult(input);

        if(result != -1 && isInput(input)){
            isPressing = true;
            final boolean canInsertResultIntoOilBar = oilRemaining < 500;
            if(canInsertResultIntoOilBar){
                if(pressedTimeLeft == -1){
                    pressedTimeLeft = maxPressedTime = getPressedTime(input);
                }else{
                    --pressedTimeLeft;
                    if(pressedTimeLeft == 0){
                        oilRemaining = (short)Math.min(500, oilRemaining + result);

                        if (input.hasContainerItem()) insertOrDropContainerItem(input);
                        input.shrink(1);
                        inventory.setStackInSlot(INPUT_SLOT, input);
                        pressedTimeLeft = -1;
                    }
                }
            }
        }else{
            pressedTimeLeft = maxPressedTime = -1;
        }

        if(oilRemaining >= 100 && isContainerInput(container)) {
            inventory.insertItem(OUTPUT_SLOT, new ItemStack(COOKING_OIL), false);
            container.shrink(1);
            inventory.setStackInSlot(CONTAINER_SLOT, container);
            oilRemaining -= 100;
        }

        if(lastPressing != isPressing){
            this.markDirty();
            final BlockState newState = world.getBlockState(pos).with(PresserBlock.PRESSING, isPressing);
            world.setBlockState(pos, newState, 2);
            lastPressing = isPressing;
        }

        int level;
        if(oilRemaining == 0){
            level = 0;
        }else if(oilRemaining < 160){
            level = 1;
        }else{
            level = 2;
        }
        PresserBlock block = (PresserBlock) world.getBlockState(pos).getBlock();
        block.setOil(world, pos, world.getBlockState(pos), level);

    }

    private void insertOrDropContainerItem(final ItemStack stack){
        final ItemStack containerItem = stack.getContainerItem();
        final boolean canInsertContainerItemIntoSlot = inventory.insertItem(PresserTileEntity.INPUT_SLOT, containerItem, true).isEmpty();
        if(canInsertContainerItemIntoSlot){
            inventory.insertItem(PresserTileEntity.INPUT_SLOT, containerItem, false);
        }else{
            InventoryHelper.spawnItemStack(Objects.requireNonNull(world), pos.getX(), pos.getY(), pos.getZ(), containerItem);
        }
    }

    private short getPressedTime(final ItemStack input){
        return getRecipe(input).map(PresserRecipe::getCookTime).orElse(200).shortValue();
    }

//    @Nonnull
//    @Override
//    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
//        if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
//            return inventoryCapabilityExternal.cast();
//        }
//
//        switch (Objects.requireNonNull(side)){
//
//            case DOWN:
//                return inventoryCapabilityExternalDown.cast();
//            case UP:
//            case NORTH:
//            case SOUTH:
//            case WEST:
//            case EAST:
//                return inventoryCapabilityExternalUpAndSides.cast();
//        }
//
//        return super.getCapability(cap, side);
//    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        this.inventory.deserializeNBT(compound.getCompound(INVENTORY_TAG));
        this.pressedTimeLeft = compound.getShort(PRESSED_TIME_LEFT_TAG);
        this.maxPressedTime = compound.getShort(MAX_PRESSED_TIME_TAG);
        this.oilRemaining = compound.getShort(OIL_REMAINING_TAG);
    }

    @Override
    @Nonnull
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        compound.put(INVENTORY_TAG, this.inventory.serializeNBT());
        compound.putShort(PRESSED_TIME_LEFT_TAG, this.pressedTimeLeft);
        compound.putShort(MAX_PRESSED_TIME_TAG, this.maxPressedTime);
        compound.putShort(OIL_REMAINING_TAG, this.oilRemaining);
        return compound;
    }

    @Nonnull
    public CompoundNBT getUpdateTag(){
        return this.write(new CompoundNBT());
    }

//    @Override
//    public void remove() {
//        super.remove();
//        inventoryCapabilityExternal.invalidate();
//    }

    @Nonnull
    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent(JPBlocks.PRESSER.getTranslationKey());
    }

    @Nonnull
    @Override
    public Container createMenu(int windowId, @Nonnull PlayerInventory inventory, @Nonnull PlayerEntity player) {
        return new PresserContainer(windowId, inventory, this);
    }
}
