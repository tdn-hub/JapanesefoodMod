package jp.tdn.japanese_food_mod.blocks.tileentity;

import jp.tdn.japanese_food_mod.blocks.FurnaceCauldronBlock;
import jp.tdn.japanese_food_mod.container.FurnaceCauldronContainer;
import jp.tdn.japanese_food_mod.init.JPBlocks;
import jp.tdn.japanese_food_mod.init.JPItems;
import jp.tdn.japanese_food_mod.init.JPRecipeTypes;
import jp.tdn.japanese_food_mod.init.JPTileEntities;
import jp.tdn.japanese_food_mod.recipes.FurnaceCauldronRecipe;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.*;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
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
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
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

import static jp.tdn.japanese_food_mod.init.JPItems.SALT;

public class FurnaceCauldronTileEntity extends LockableTileEntity implements IInventory, IRecipeHelperPopulator, ITickableTileEntity {
    public static final int INPUT_SLOT = 0;
    public static final int OUTPUT_SLOT = 1;
    public static final int[] RETURN_SLOT = {2, 3, 4};

    private static final String INVENTORY_TAG = "inventory";
    private static final String HEATING_TIME_LEFT_TAG = "heatingTimeLeft";
    private static final String HEATING_MAX_TIME_TAG = "heatingMaxTime";
    private static final String WATER_REMAINING_TAG = "waterRemaining";

    protected NonNullList<ItemStack> items;
    private final LazyOptional<? extends IItemHandler> handler;
    protected final IRecipeType<? extends FurnaceCauldronRecipe> recipeType;

    private int heatingTimeLeft = 0;
    private int maxHeatingTime = 0;
    private int waterRemaining = 0;
    private int maxWater = 1000;
    private int needWater = 100;
    private boolean lastActive = false;

    public FurnaceCauldronTileEntity(TileEntityType<?> entityType){
        super(entityType);
        this.items = NonNullList.withSize(5, ItemStack.EMPTY);
        this.handler = LazyOptional.of(() -> new ItemStackHandler(5));
        this.recipeType = JPRecipeTypes.CAULDRON;
    }

    @Override
    public void tick() {
        if(this.level == null || this.level.isClientSide) {
            return;
        }
        boolean isActive = this.isHeating();
        boolean lastActive = false;

        final ItemStack input = this.items.get(INPUT_SLOT);

        if(!isActive && this.isRemainWater()){
            if(this.heatingTimeLeft > 0){
                this.heatingTimeLeft = 0;
            }
        } else {
            FurnaceCauldronRecipe recipe = this.level.getRecipeManager().getRecipeFor(this.recipeType, this, this.level).orElse(null);
            if(!isActive && this.canAddWater())
        }
        if(!input.isEmpty() && canAddWater()){
            addWater(input);
            //JapaneseFoodMod.LOGGER.info(waterRemaining);
            if (input.hasContainerItem()) {
                this.insertOrDropContainerItem(input);
                input.shrink(1);
                this.items.set(INPUT_SLOT, input);
            }
        }

        if(waterRemaining >= needWater){
            isActive = true;
            if(heatingTimeLeft == -1){
                heatingTimeLeft = maxHeatingTime = getHeatingTime(input);
            }else{
                --heatingTimeLeft;
                if(heatingTimeLeft <= 0){
                    waterRemaining -= needWater;
                    this.items.insertItem(OUTPUT_SLOT, new ItemStack(SALT.get()), false);
                    insertOrDropItem(new ItemStack(JPItems.BITTERN.get()));
                    heatingTimeLeft = -1;
                }
            }
        }else{
            heatingTimeLeft = maxHeatingTime = -1;
        }

        if(isActive != this.isHeating()){
            lastActive = true;
        }

        if(lastActive){
            this.setChanged();
        }

        FurnaceCauldronBlock block = (FurnaceCauldronBlock) this.level.getBlockState(this.worldPosition).getBlock();
        block.setWaterLevel(this.level, this.worldPosition, this.level.getBlockState(this.worldPosition), waterRemaining, maxWater);
    }

    private void insertOrDropContainerItem(ItemStack item){
        for(int index = 0; index < RETURN_SLOT.length; ++index) {
            ItemStack ret = this.items.get(RETURN_SLOT[index]);
            if (!item.isEmpty()) {
                if (ret.isEmpty()) {
                    this.items.set(RETURN_SLOT[index], item.getContainerItem());
                } else if (ret.sameItem(item.getContainerItem()) && item.getCount() + ret.getCount() < ret.getMaxStackSize()) {
                    ret.grow(item.getCount());
                } else {
                    this.level.get
                }
            }
        }
    }

    private boolean isHeating(){
        return this.heatingTimeLeft > 0;
    }

    private boolean isRemainWater(){
        return this.waterRemaining >= this.needWater;
    }

    private int getHeatingTime(){
        return this.level.getRecipeManager().getRecipeFor(this.recipeType, this, this.level).map(FurnaceCauldronRecipe::getCookTime).orElse(1000);
    }

    public boolean canAddWater(){
        return waterRemaining < maxWater;
    }

    public void addWater(ItemStack interact){
        int plus = 0;
        if (Items.WATER_BUCKET.equals(interact.getItem())) {
            plus = 500;
        }else if(JPItems.CUP_WITH_WATER.get().equals(interact.getItem())){
            plus = 50;
        }
        this.waterRemaining = Math.min(maxWater, waterRemaining + plus);
    }

    public int getWaterRemaining() {
        return waterRemaining;
    }

    public int getMaxWater() {
        return maxWater;
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
    public void load(BlockState state, CompoundNBT compound) {
        super.load(state, compound);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, this.items);
        this.heatingTimeLeft = compound.getInt(HEATING_TIME_LEFT_TAG);
        this.maxHeatingTime = compound.getInt(HEATING_MAX_TIME_TAG);
        this.waterRemaining = compound.getInt(WATER_REMAINING_TAG);
    }

    @Override
    public CompoundNBT save(CompoundNBT compound) {
        super.save(compound);
        ItemStackHelper.saveAllItems(compound, this.items);
        compound.putInt(HEATING_TIME_LEFT_TAG, this.heatingTimeLeft);
        compound.putInt(HEATING_MAX_TIME_TAG, this.maxHeatingTime);
        compound.putInt(WATER_REMAINING_TAG, this.waterRemaining);
        return compound;
    }

    @Override
    public int getContainerSize() {
        return this.items.size();
    }

    @Override
    public boolean isEmpty() {
        Iterator var1 = this.items.iterator();

        ItemStack itemstack;
        do {
            if (!var1.hasNext()) {
                return true;
            }

            itemstack = (ItemStack)var1.next();
        } while(itemstack.isEmpty());

        return false;
    }

    @Override
    public ItemStack getItem(int i) {
        return (ItemStack)this.items.get(i);
    }

    @Override
    public ItemStack removeItem(int i, int i1) {
        return ItemStackHelper.removeItem(this.items, i, i1);
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
            this.maxHeatingTime = this.getHeatingTime();
            this.heatingTimeLeft = 0;
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
        if(i == 0){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return this.save(new CompoundNBT());
    }

    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container.furnace_cauldron");
    }

    @Override
    protected Container createMenu(int i, PlayerInventory playerInventory) {
        return new FurnaceCauldronContainer(i, playerInventory, this);
    }
}
