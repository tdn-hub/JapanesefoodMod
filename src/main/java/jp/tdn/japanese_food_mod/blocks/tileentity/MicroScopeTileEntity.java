package jp.tdn.japanese_food_mod.blocks.tileentity;

import jp.tdn.japanese_food_mod.JapaneseFoodUtil;
import jp.tdn.japanese_food_mod.blocks.MicroScopeBlock;
import jp.tdn.japanese_food_mod.container.MicroScopeContainer;
import jp.tdn.japanese_food_mod.init.JPBlocks;
import jp.tdn.japanese_food_mod.init.JPRecipeTypes;
import jp.tdn.japanese_food_mod.init.JPTileEntities;
import jp.tdn.japanese_food_mod.recipes.FermentationRecipe;
import jp.tdn.japanese_food_mod.recipes.IdentifiedRecipe;
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
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;

public class MicroScopeTileEntity extends LockableTileEntity implements IInventory, IRecipeHelperPopulator, ITickableTileEntity {
    public static final int INPUT_SLOT = 0;
    public static final int CONTAINER_SLOT = 2;
    public static final int OUTPUT_SLOT = 1;

    private static final String INVENTORY_TAG = "inventory";
    private static final String IDENTIFIED_TIME_LEFT_TAG = "identifiedTimeLeft";
    private static final String MAX_IDENTIFIED_TIME_TAG = "maxIdentifiedTime";

    protected NonNullList<ItemStack> items;
    public int identifiedProgress;
    public int identifiedTotalTime;
    private IIntArray dataAccess;
    protected final IRecipeType<? extends IdentifiedRecipe> recipeType;
    LazyOptional<? extends IItemHandler> handler;

    public MicroScopeTileEntity(){
        super(JPTileEntities.MICROSCOPE);
        items = NonNullList.withSize(3, ItemStack.EMPTY);
        dataAccess = new IIntArray() {
            @Override
            public int get(int i) {
                switch (i){
                    case 0:
                        return identifiedProgress;
                    case 1:
                        return identifiedTotalTime;
                    default:
                        return 0;
                }
            }

            @Override
            public void set(int i, int i1) {
                switch (i){
                    case 0:
                        identifiedProgress = i1;
                        break;
                    case 1:
                        identifiedTotalTime = i1;
                        break;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
        recipeType = JPRecipeTypes.IDENTIFIED;
        handler = LazyOptional.of(() -> new ItemStackHandler(3));
    }

    @Override
    public void load(BlockState state, CompoundNBT nbt) {
        super.load(state, nbt);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(nbt, items);
        this.identifiedProgress = nbt.getInt(IDENTIFIED_TIME_LEFT_TAG);
        this.identifiedTotalTime = nbt.getInt(MAX_IDENTIFIED_TIME_TAG);
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        super.save(nbt);
        nbt.putInt(IDENTIFIED_TIME_LEFT_TAG, identifiedProgress);
        nbt.putInt(MAX_IDENTIFIED_TIME_TAG, identifiedTotalTime);
        ItemStackHelper.saveAllItems(nbt, items);
        return nbt;
    }

    public ItemStack getItem(int i){
        if(i < 0 || i >= this.items.size()){
            return ItemStack.EMPTY;
        }
        return this.items.get(i);
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

        if(i == INPUT_SLOT && !flag){
            this.identifiedTotalTime = this.getTotalIdentifyTime();
            this.identifiedProgress = 0;
            this.setChanged();
        }
    }

    @Override
    public void tick() {
        boolean isActive = this.isIdentify();
        boolean lastActive = false;
        if(this.level == null || this.level.isClientSide) {
            return;
        }

        ItemStack input = getItem(INPUT_SLOT);
        ItemStack container = getItem(CONTAINER_SLOT);

        if(!isActive && input.isEmpty()){
            if(this.identifiedProgress > 0){
                this.identifiedProgress = MathHelper.clamp(this.identifiedProgress - 2, 0, this.identifiedTotalTime);
            }
        } else {
            IdentifiedRecipe recipe = this.level.getRecipeManager().getRecipeFor(this.recipeType, this, this.level).orElse(null);
            if(!isActive && this.canIdentify(recipe)){
                isActive = true;
            }

            if(isActive && this.canIdentify(recipe)){
                ++this.identifiedProgress;
                if(this.identifiedProgress == this.identifiedTotalTime){
                    this.identifiedProgress = 0;
                    this.identifiedTotalTime = this.getTotalIdentifyTime();
                    this.identify(recipe);
                }
            } else {
                this.identifiedProgress = 0;
            }
        }

        if(isActive != this.isIdentify()){
            lastActive = true;
            final BlockState newState = this.level.getBlockState(this.worldPosition).setValue(MicroScopeBlock.IDENTIFY, isActive);
            this.level.setBlock(this.worldPosition, newState, 0);
        }

        if(lastActive){
            this.setChanged();
        }
    }

    private boolean canIdentify(IdentifiedRecipe recipe){
        ItemStack input = this.getItem(INPUT_SLOT);

        if(!input.isEmpty() && recipe != null){
            ItemStack result = recipe.assemble(this);
            if(result.isEmpty()){
                return false;
            } else {
                ItemStack container = this.getItem(CONTAINER_SLOT);
                ItemStack slotItem = this.getItem(OUTPUT_SLOT);
                if(container.getItem() == Items.GLASS_BOTTLE) {
                    if (slotItem.isEmpty()) {
                        return true;
                    } else if (!slotItem.sameItem(result)) {
                        return false;
                    } else if (result.getCount() + slotItem.getCount() <= this.getMaxStackSize() && result.getCount() + slotItem.getCount() <= slotItem.getMaxStackSize()) {
                        return true;
                    } else {
                        return result.getCount() + slotItem.getCount() <= result.getMaxStackSize();
                    }
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }
    }

    protected void identify(IdentifiedRecipe recipe){
        if(recipe != null && this.canIdentify(recipe)){
            ItemStack input = this.getItem(INPUT_SLOT);
            ItemStack container = this.getItem(CONTAINER_SLOT);
            ItemStack output = this.getItem(OUTPUT_SLOT);
            ItemStack result = recipe.assemble(this);

            if(output.isEmpty()){
                this.items.set(OUTPUT_SLOT, result);
            } else if(result.sameItem(output)){
                output.grow(result.getCount());
            }

            input.shrink(1);
            container.shrink(1);
        }
    }

    private boolean isIdentify() {
        return this.identifiedProgress > 0;
    }

    protected int getTotalIdentifyTime(){
        return (Integer)this.level.getRecipeManager().getRecipeFor(this.recipeType, this, this.level).map(IdentifiedRecipe::getIdentifyTime).orElse(100);
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
            if (!var1.hasNext()) {
                return true;
            }

            itemStack = (ItemStack)var1.next();
        } while(itemStack.isEmpty());

        return false;
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
            return playerEntity.distanceToSqr((double) this.worldPosition.getX() + 0.5D, (double) this.worldPosition.getY() + 0.5D, (double) this.worldPosition.getZ() + 0.5D) <= 64.0D;
        }
    }

    @Override
    public void clearContent() {
        this.items.clear();
    }

    @Override
    public boolean canPlaceItem(int i, ItemStack itemStack) {
        if(i == INPUT_SLOT){
            return true;
        }else if(i == CONTAINER_SLOT && itemStack.getItem() == Items.GLASS_BOTTLE){
            return true;
        }else{
            return false;
        }
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
            return this.handler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    protected void invalidateCaps() {
        super.invalidateCaps();
        this.handler.invalidate();
    }

    @Nonnull
    @Override
    public ITextComponent getDefaultName() {
        return new TranslationTextComponent("container.microscope");
    }

    @Override
    protected Container createMenu(int i, PlayerInventory playerInventory) {
        return new MicroScopeContainer(i, playerInventory, this, this.dataAccess);
    }
}
