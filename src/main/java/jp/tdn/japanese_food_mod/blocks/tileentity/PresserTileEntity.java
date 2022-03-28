package jp.tdn.japanese_food_mod.blocks.tileentity;

import jp.tdn.japanese_food_mod.blocks.PresserBlock;
import jp.tdn.japanese_food_mod.container.PresserContainer;
import jp.tdn.japanese_food_mod.init.JPBlocks;
import jp.tdn.japanese_food_mod.init.JPItems;
import jp.tdn.japanese_food_mod.init.JPRecipeTypes;
import jp.tdn.japanese_food_mod.init.JPTileEntities;
import jp.tdn.japanese_food_mod.recipes.PresserRecipe;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.*;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RangedWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;

import static jp.tdn.japanese_food_mod.init.JPItems.COOKING_OIL;

public class PresserTileEntity extends LockableTileEntity implements IInventory, IRecipeHelperPopulator, ITickableTileEntity {
    public static final int INPUT_SLOT = 0;
    public static final int CONTAINER_SLOT = 2;
    public static final int OUTPUT_SLOT = 1;

    private static final String INVENTORY_TAG = "inventory";
    private static final String PRESSED_TIME_LEFT_TAG = "pressedTimeLeft";
    private static final String MAX_PRESSED_TIME_TAG = "maxPressedTime";
    private static final String OIL_REMAINING_TAG = "oilRemaining";

    private IIntArray dataAccess;
    protected NonNullList<ItemStack> items;
    protected final IRecipeType<? extends PresserRecipe> recipeType;
    LazyOptional<? extends IItemHandler> handler;

    private int pressedProgress = 0;
    private int pressedTotalTime = 0;
    private int oilRemaining = 0;
    private int oilMax = 500;

    public PresserTileEntity(TileEntityType<PresserTileEntity> entityType){
        super(entityType);
        this.items = NonNullList.withSize(3, ItemStack.EMPTY);
        this.dataAccess = new IIntArray() {
            @Override
            public int get(int i) {
                switch (i){
                    case 0:
                        return PresserTileEntity.this.pressedProgress;
                    case 1:
                        return PresserTileEntity.this.pressedTotalTime;
                    case 2:
                        return PresserTileEntity.this.oilRemaining;
                    case 3:
                        return PresserTileEntity.this.oilMax;
                    default:
                        return 0;
                }
            }

            @Override
            public void set(int i, int i1) {
                switch (i){
                    case 0:
                        PresserTileEntity.this.pressedProgress = i1;
                        break;
                    case 1:
                        PresserTileEntity.this.pressedTotalTime = i1;
                        break;
                    case 2:
                        PresserTileEntity.this.oilRemaining = i1;
                        break;
                    case 3:
                        PresserTileEntity.this.oilMax = i1;
                        break;
                }
            }

            @Override
            public int getCount() {
                return 4;
            }
        };
        this.recipeType = JPRecipeTypes.PRESSER;
        this.handler = LazyOptional.of(() -> new ItemStackHandler(3));
    }

    @Override
    public void tick() {
        boolean isActive = this.isPressing();
        boolean lastActive = false;
        if(this.level == null || this.level.isClientSide) return;

        final ItemStack input = this.items.get(INPUT_SLOT);
        final ItemStack container = this.items.get(CONTAINER_SLOT);

        if(!isActive && input.isEmpty()){
            if(this.pressedProgress > 0){
                this.pressedProgress = 0;
            }
        } else {
            PresserRecipe recipe = this.level.getRecipeManager().getRecipeFor(this.recipeType, this, this.level).orElse(null);
            if(!isActive && this.canPressing(recipe)){
                isActive = true;
            }

            if(isActive && this.canPressing(recipe)){
                ++this.pressedProgress;
                if(this.pressedProgress == this.pressedTotalTime){
                    this.pressedProgress = 0;
                    this.pressedTotalTime = this.getTotalPressedTime();
                    this.pressed(recipe);
                }
            } else {
                this.pressedProgress = 0;
            }
        }

        if(this.oilRemaining >= 100 && this.isContainer(container)) {
            ItemStack output = this.items.get(OUTPUT_SLOT);

            if(output.isEmpty()){
                this.items.set(OUTPUT_SLOT, new ItemStack(COOKING_OIL.get()));
            } else {
                output.grow(1);
            }
            container.shrink(1);
            oilRemaining = MathHelper.clamp(this.oilRemaining - 100, 0, this.oilMax);
        }

        if(isActive != this.isPressing()){
            lastActive = true;
            final BlockState newState = this.level.getBlockState(this.worldPosition).setValue(PresserBlock.PRESSING, isActive);
            this.level.setBlock(this.worldPosition, newState, 2);
            lastActive = isActive;
        }

        if(lastActive){
            this.setChanged();
        }
    }

    @Override
    public int getContainerSize() {
        return this.items.size();
    }

    @Override
    public boolean isEmpty() {
        Iterator var1 = this.items.iterator();

        ItemStack itemStack;
        do {
            if(!var1.hasNext()) {
                return true;
            }

            itemStack = (ItemStack)var1.next();
        } while (itemStack.isEmpty());

        return false;
    }

    @Override
    public ItemStack getItem(int i) {
        return this.items.get(i);
    }

    @Override
    public ItemStack removeItem(int i, int i1) {
        return ItemStackHelper.removeItem(this.items, i ,i1);
    }

    @Override
    public ItemStack removeItemNoUpdate(int i) {
        return ItemStackHelper.takeItem(this.items, i);
    }

    @Override
    public void setItem(int i, ItemStack itemStack) {
        ItemStack oldItemStack = (ItemStack)this.items.get(i);
        boolean flag = !itemStack.isEmpty() && itemStack.sameItem(oldItemStack) && ItemStack.tagMatches(itemStack, oldItemStack);
        this.items.set(i, itemStack);
        if(itemStack.getCount() > this.getMaxStackSize()){
            itemStack.setCount(this.getMaxStackSize());
        }

        if(i == 0 && !flag){
            this.pressedProgress = 0;
            this.pressedTotalTime = this.getTotalPressedTime();
            this.setChanged();
        }
    }

    @Override
    public void fillStackedContents(RecipeItemHelper recipeItemHelper) {
        Iterator itr = this.items.iterator();

        while (itr.hasNext()){
            ItemStack itemStack = (ItemStack)itr.next();
            recipeItemHelper.accountStack(itemStack);
        }
    }

    @Override
    public boolean stillValid(PlayerEntity playerEntity) {
        if(this.level.getBlockEntity(this.worldPosition) != this){
            return false;
        } else {
            return playerEntity.distanceToSqr((double)this.worldPosition.getX() + 0.5D, (double)this.worldPosition.getY() + 0.5D, (double)this.worldPosition.getZ() + 0.5D) <= 64.0D;
        }
    }

    @Override
    public void clearContent() {
        this.items.clear();
    }

    @Override
    public boolean canPlaceItem(int i, ItemStack itemStack) {
        if(i == OUTPUT_SLOT){
            return false;
        } else {
            return true;
        }
    }

    private int getTotalPressedTime(){
        return (Integer) this.level.getRecipeManager().getRecipeFor(this.recipeType, this, this.level).map(PresserRecipe::getCookTime).orElse(100);
    }

    private boolean isPressing(){
        return this.pressedProgress > 0;
    }

    private boolean isContainer(ItemStack itemStack){
        return itemStack.getItem() == Items.GLASS_BOTTLE;
    }

    private boolean canPressing(PresserRecipe recipe){
        ItemStack input = this.items.get(INPUT_SLOT);

        if(!input.isEmpty() && recipe != null){
            int result = recipe.getResult();
            if(result <= 0){
                return false;
            } else {
                return this.oilRemaining + result > this.oilMax;
            }
        } else {
            return false;
        }
    }

    private void pressed(PresserRecipe recipe){
        if(recipe != null && this.canPressing(recipe)){
            ItemStack input = this.items.get(INPUT_SLOT);
            int result = recipe.getResult();

            this.oilRemaining = MathHelper.clamp(this.oilRemaining + result, 0, this.oilMax);

            input.shrink(1);
        }
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
            return handler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    protected void invalidateCaps() {
        super.invalidateCaps();
        this.handler.invalidate();
    }

    @Override
    public void load(BlockState state,CompoundNBT compound) {
        super.load(state, compound);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, this.items);
        this.pressedProgress = compound.getInt(PRESSED_TIME_LEFT_TAG);
        this.pressedTotalTime = compound.getInt(MAX_PRESSED_TIME_TAG);
        this.oilRemaining = compound.getInt(OIL_REMAINING_TAG);
    }

    @Override
    @Nonnull
    public CompoundNBT save(CompoundNBT compound) {
        super.save(compound);
        ItemStackHelper.saveAllItems(compound, this.items);
        compound.putInt(PRESSED_TIME_LEFT_TAG, this.pressedProgress);
        compound.putInt(MAX_PRESSED_TIME_TAG, this.pressedTotalTime);
        compound.putInt(OIL_REMAINING_TAG, this.oilRemaining);
        return compound;
    }

    @Nonnull
    public CompoundNBT getUpdateTag(){
        return this.save(new CompoundNBT());
    }

    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container.presser");
    }

    @Override
    protected Container createMenu(int i, PlayerInventory playerInventory) {
        return new PresserContainer(i, playerInventory, this, this.dataAccess);
    }
}
