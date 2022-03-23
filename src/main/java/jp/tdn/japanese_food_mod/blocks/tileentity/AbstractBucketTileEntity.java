package jp.tdn.japanese_food_mod.blocks.tileentity;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import jp.tdn.japanese_food_mod.blocks.WoodenBucketBlock;
import jp.tdn.japanese_food_mod.init.JPItems;
import jp.tdn.japanese_food_mod.init.JPRecipeTypes;
import jp.tdn.japanese_food_mod.recipes.FermentationRecipe;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.*;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.NonNullSupplier;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractBucketTileEntity extends LockableTileEntity implements IInventory, IRecipeHelperPopulator, ITickableTileEntity {
    public static final int[] INPUT_SLOT = {0,1,2,3,4,5};
    public static final int OUTPUT_SLOT = 6;
    public static final int[] RETURN_SLOT = {7, 8, 9};

    private static final String INVENTORY_TAG = "inventory";
    private static final String FERMENTATION_TIME_LEFT = "fermentation_time_left";
    private static final String MAX_FERMENTATION_TIME = "max_fermentation_time";

    protected NonNullList<ItemStack> items;
    private int ferProgress;
    private int ferTotalTime;
    private IIntArray dataAccess;
    private final Object2IntOpenHashMap<ResourceLocation> recipesUsed;
    protected final IRecipeType<? extends FermentationRecipe> recipeType;
    LazyOptional<? extends IItemHandler> handler;

    protected AbstractBucketTileEntity(TileEntityType<?> entityType){
        super(entityType);
        this.items = NonNullList.withSize(10, ItemStack.EMPTY);
        this.dataAccess = new IIntArray() {
            @Override
            public int get(int i) {
                switch (i){
                    case 0:
                        return AbstractBucketTileEntity.this.ferProgress;
                    case 1:
                        return AbstractBucketTileEntity.this.ferTotalTime;
                    default:
                        return 0;
                }
            }

            @Override
            public void set(int i, int i1) {
                switch (i){
                    case 0:
                        AbstractBucketTileEntity.this.ferProgress = i1;
                        break;
                    case 1:
                        AbstractBucketTileEntity.this.ferTotalTime = i1;
                        break;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };

        this.recipesUsed = new Object2IntOpenHashMap();
        this.handler = LazyOptional.of(() -> new ItemStackHandler(10));
        this.recipeType = JPRecipeTypes.FERMENTATION;
    }

    @Override
    public void load(BlockState state, CompoundNBT nbt) {
        super.load(state, nbt);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(nbt, this.items);
        this.ferProgress = nbt.getInt(FERMENTATION_TIME_LEFT);
        this.ferTotalTime = nbt.getInt(MAX_FERMENTATION_TIME);
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        super.save(nbt);
        nbt.putInt(FERMENTATION_TIME_LEFT, this.ferProgress);
        nbt.putInt(MAX_FERMENTATION_TIME, this.ferTotalTime);
        ItemStackHelper.saveAllItems(nbt, this.items);
        return nbt;
    }

    @Override
    public void tick() {
        boolean isActive = this.isFerment();
        boolean lastActive = false;
        if(this.level.isClientSide) {
            return;
        }

        final List<ItemStack> inputs = Lists.newArrayList();
        for(int index = 0; index < INPUT_SLOT.length; ++index){
            ItemStack stack = this.items.get(index);
            if(!stack.isEmpty()){
                inputs.add(stack);
            }
        }

        if(!isActive && inputs.isEmpty()) {
            if (this.ferProgress > 0) {
                this.ferProgress = MathHelper.clamp(this.ferProgress - 2, 0, this.ferTotalTime);
            }
        } else {
            FermentationRecipe recipe = this.level.getRecipeManager().getRecipeFor(this.recipeType, this, this.level).orElse(null);
            if(!isActive && this.canFerment(recipe)) {
                isActive = true;
            }

            if(isActive && this.canFerment(recipe)) {
                ++this.ferProgress;
                if(this.ferProgress == this.ferTotalTime) {
                    this.ferProgress = 0;
                    this.ferTotalTime = this.getTotalFermentTime();
                    this.ferment(recipe);
                }
            } else {
                this.ferProgress = 0;
            }
        }

        if(isActive != this.isFerment()){
            lastActive = true;
            final BlockState newState = this.level.getBlockState(this.worldPosition).setValue(WoodenBucketBlock.FER, isActive);
            this.level.setBlock(this.worldPosition, newState, 0);
        }

        if(lastActive){
            this.setChanged();
        }
    }

    protected  boolean isFerment(){
        return this.ferProgress > 0;
    }

    protected boolean canFerment(FermentationRecipe recipe){
        final List<ItemStack> inputs = Lists.newArrayList();
        for(int index = 0; index < INPUT_SLOT.length; ++index){
            ItemStack stack = this.items.get(index);
            if(!stack.isEmpty()){
                inputs.add(stack);
            }
        }

        if((!inputs.isEmpty()) &&  recipe != null){
            ItemStack result = recipe.assemble(this);
            if(result.isEmpty()){
                return false;
            }else{
                ItemStack slotItem = this.items.get(OUTPUT_SLOT);
                if(slotItem.isEmpty()){
                    return true;
                }else if(!slotItem.sameItem(result)){
                    return false;
                }else if(result.getCount() + slotItem.getCount() <= this.getMaxStackSize() && result.getCount() + slotItem.getCount() <= slotItem.getMaxStackSize()){
                    return true;
                }else{
                    return result.getCount() + slotItem.getCount() <= result.getMaxStackSize();
                }
            }
        }else{
            return false;
        }
    }

    protected void ferment(FermentationRecipe recipe){
        if(recipe != null && this.canFerment(recipe)) {
            final List<ItemStack> inputs = Lists.newArrayList();
            for (int index = 0; index < INPUT_SLOT.length; ++index) {
                ItemStack stack = this.items.get(index);
                if (!stack.isEmpty()) {
                    inputs.add(stack);
                }
            }

            ItemStack result = recipe.assemble(this);
            ItemStack output = this.items.get(OUTPUT_SLOT);

            if(output.isEmpty()){
                this.items.set(OUTPUT_SLOT, result.copy());
            }else if(result.sameItem(output)){
                output.grow(result.getCount());
            }

            for(int index = 0; index < inputs.size(); ++index){
                ItemStack retItem = inputs.get(index);
                if(retItem.getItem() == JPItems.CUP_WITH_MILK.get() || retItem.getItem() == JPItems.CUP_WITH_WATER.get()){
                    for(int retIndex = 0; retIndex < RETURN_SLOT.length; ++retIndex){
                        ItemStack stack = this.items.get(RETURN_SLOT[retIndex]);
                        if(stack.isEmpty()){
                            this.items.set(RETURN_SLOT[retIndex], JPItems.CUP.get().getDefaultInstance());
                        }else if(stack.sameItem(retItem)){
                            stack.grow(1);
                        }
                    }
                }

                retItem.shrink(1);
            }
        }
    }

    protected int getTotalFermentTime() {
        return (Integer)this.level.getRecipeManager().getRecipeFor(this.recipeType, this, this.level).map(FermentationRecipe::getFermentTime).orElse(1000);
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
            this.ferTotalTime = this.getTotalFermentTime();
            this.ferProgress = 0;
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
        if(Arrays.asList(RETURN_SLOT).contains(i)){
            return false;
        }else if(OUTPUT_SLOT == i){
            return false;
        }else if(Arrays.asList(INPUT_SLOT).contains(i)){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction side) {
        if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
            return this.handler.cast();
        }
        return super.getCapability(capability, side);
    }

    @Override
    protected void invalidateCaps() {
        super.invalidateCaps();
        this.handler.invalidate();
    }
}
