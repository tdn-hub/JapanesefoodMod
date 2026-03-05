package jp.tdn.japanese_food_mod.blocks.tileentity;

import jp.tdn.japanese_food_mod.blocks.FurnaceCauldronBlock;
import jp.tdn.japanese_food_mod.container.FurnaceCauldronContainer;
import jp.tdn.japanese_food_mod.init.JPBlockEntities;
import jp.tdn.japanese_food_mod.init.JPBlocks;
import jp.tdn.japanese_food_mod.init.JPItems;
import jp.tdn.japanese_food_mod.recipes.FurnaceCauldronRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.Optional;

import static jp.tdn.japanese_food_mod.init.JPItems.SALT;

public class FurnaceCauldronTileEntity extends BlockEntity implements MenuProvider {
    public static final int INPUT_SLOT = 0;
    public static final int OUTPUT_SLOT = 1;
    public static final int[] RETURN_SLOT = {2, 3, 4};

    private static final String INVENTORY_TAG = "inventory";
    private static final String HEATING_TIME_LEFT_TAG = "heatingTimeLeft";
    private static final String HEATING_MAX_TIME_TAG = "heatingMaxTime";
    private static final String WATER_REMAINING_TAG = "waterRemaining";

    public ItemStackHandler inventory = new ItemStackHandler(5){
        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            switch (slot){
                case 0:
                    return isInput(stack);
                case 1:
                    return isOutPut(stack);
                case 2:
                case 3:
                case 4:
                    return isReturnOutput(stack);
                default:
                    return false;
            }
        }

        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            FurnaceCauldronTileEntity.this.setChanged();
        }
    };

    public int heatingTimeLeft = -1;
    public int maxHeatingTime = -1;
    public int waterRemaining = 0;
    public int maxWater = 1000;
    public int needWater = 100;
    private boolean lastActive = false;

    public FurnaceCauldronTileEntity(BlockPos pos, BlockState state){
        super(JPBlockEntities.FURNACE_CAULDRON.get(), pos, state);
    }

    private boolean isInput(ItemStack stack){
        if(stack.isEmpty()){
            return false;
        }
        return getRecipe(stack).isPresent();
    }

    private boolean isOutPut(ItemStack stack){
        return stack.getItem() == SALT.get();
    }

    private boolean isReturnOutput(final ItemStack stack){
        return stack.getItem() == Items.GLASS_BOTTLE || stack.getItem() == JPItems.CUP.get() || stack.getItem() == Items.BUCKET || stack.getItem() == JPItems.BITTERN.get();
    }

    private Optional<RecipeHolder<FurnaceCauldronRecipe>> getRecipe(final ItemStack input){
        return getRecipe(new SingleRecipeInput(input));
    }

    private Optional<RecipeHolder<FurnaceCauldronRecipe>> getRecipe(final SingleRecipeInput recipeInput){
        return Objects.requireNonNull(level).getRecipeManager().getRecipeFor(FurnaceCauldronRecipe.RECIPE_TYPE, recipeInput, level);
    }

    @SuppressWarnings("unused")
    private Optional<ItemStack> getResult(final ItemStack input){
        final SingleRecipeInput dummyInput = new SingleRecipeInput(input);
        return getRecipe(dummyInput).map(recipe -> recipe.value().getResultItem(level.registryAccess()));
    }

    public static void tick(Level level, BlockPos pos, BlockState state, FurnaceCauldronTileEntity te) {
        if(level == null || level.isClientSide) {
            return;
        }
        boolean isActive = false;

        final ItemStack input = te.inventory.getStackInSlot(INPUT_SLOT);
        if(!input.isEmpty() && te.canAddWater()){
            te.addWater(input);
            if (input.hasCraftingRemainingItem()) {
                te.insertOrDropContainerItem(input, INPUT_SLOT);
                input.shrink(1);
                te.inventory.setStackInSlot(INPUT_SLOT, input);
            }
        }

        if(te.waterRemaining >= te.needWater){
            isActive = true;
            if(te.heatingTimeLeft == -1){
                te.heatingTimeLeft = te.maxHeatingTime = te.getHeatingTime(input);
            }else{
                --te.heatingTimeLeft;
                if(te.heatingTimeLeft <= 0){
                    te.waterRemaining -= te.needWater;
                    te.inventory.insertItem(OUTPUT_SLOT, new ItemStack(SALT.get()), false);
                    te.insertOrDropItem(new ItemStack(JPItems.BITTERN.get()));
                    te.heatingTimeLeft = -1;
                }
            }
        }else{
            te.heatingTimeLeft = te.maxHeatingTime = -1;
        }

        if(te.lastActive != isActive){
            te.setChanged();
            te.lastActive = isActive;
        }

        FurnaceCauldronBlock block = (FurnaceCauldronBlock) level.getBlockState(pos).getBlock();
        block.setWaterLevel(level, pos, level.getBlockState(pos), te.waterRemaining, te.maxWater);
    }

    private void insertOrDropItem(final ItemStack stack){
        int index;
        boolean canInsertItem = false;
        for(index = 0; index < RETURN_SLOT.length && !(canInsertItem = inventory.insertItem(RETURN_SLOT[index], stack, true).isEmpty()); ++index){
        }
        if(canInsertItem){
            inventory.insertItem(RETURN_SLOT[index], stack, false);
        }else{
            Containers.dropItemStack(level, worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), stack);
        }
    }

    private void insertOrDropContainerItem(final ItemStack stack, final int slot){
        int index;
        final ItemStack containerItem = stack.getCraftingRemainingItem();
        boolean canInsertContainerItemIntoReturnSlot = false;
        for(index = 0; index < RETURN_SLOT.length && !(canInsertContainerItemIntoReturnSlot = inventory.insertItem(RETURN_SLOT[index], containerItem, true).isEmpty()); ++index);

        if(canInsertContainerItemIntoReturnSlot){
            inventory.insertItem(RETURN_SLOT[index], containerItem, false);
        }else {
            final boolean canInsertContainerItemIntoSlot = inventory.insertItem(slot, containerItem, true).isEmpty();
            if (canInsertContainerItemIntoSlot) {
                inventory.insertItem(slot, containerItem, false);
            } else {
                Containers.dropItemStack(Objects.requireNonNull(level), worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), containerItem);
            }
        }
    }

    private int getHeatingTime(ItemStack input){
        return getRecipe(input).map(recipe -> recipe.value().getCookTime()).orElse(1000);
    }

    public boolean canAddWater(){
        return waterRemaining < maxWater;
    }

    public void addWater(ItemStack interact){
        int plus = 0;
        if (Items.WATER_BUCKET.equals(interact.getItem())) {
            plus = 500;
        }else if(JPItems.CUP_WITH_WATER.equals(interact.getItem())){
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

    @Override
    public void loadAdditional(CompoundTag compound, HolderLookup.Provider registries) {
        super.loadAdditional(compound, registries);
        this.inventory.deserializeNBT(registries, compound.getCompound(INVENTORY_TAG));
        this.heatingTimeLeft = compound.getInt(HEATING_TIME_LEFT_TAG);
        this.maxHeatingTime = compound.getInt(HEATING_MAX_TIME_TAG);
        this.waterRemaining = compound.getInt(WATER_REMAINING_TAG);
    }

    @Override
    public void saveAdditional(CompoundTag compound, HolderLookup.Provider registries) {
        super.saveAdditional(compound, registries);
        compound.put(INVENTORY_TAG, this.inventory.serializeNBT(registries));
        compound.putInt(HEATING_TIME_LEFT_TAG, this.heatingTimeLeft);
        compound.putInt(HEATING_MAX_TIME_TAG, this.maxHeatingTime);
        compound.putInt(WATER_REMAINING_TAG, this.waterRemaining);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = new CompoundTag();
        this.saveAdditional(tag, registries);
        return tag;
    }

    @Nonnull
    @Override
    public Component getDisplayName() {
        return Component.translatable(JPBlocks.FURNACE_CAULDRON.get().getDescriptionId());
    }

    @Nonnull
    @Override
    public AbstractContainerMenu createMenu(int windowId, @Nonnull Inventory inventory, @Nonnull Player player) {
        return new FurnaceCauldronContainer(windowId, inventory, this);
    }
}
