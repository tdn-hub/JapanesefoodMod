package jp.tdn.japanese_food_mod.blocks.tileentity;

import com.google.common.collect.Lists;
import jp.tdn.japanese_food_mod.blocks.WoodenBucketBlock;
import jp.tdn.japanese_food_mod.container.WoodenBucketContainer;
import jp.tdn.japanese_food_mod.init.JPBlockEntities;
import jp.tdn.japanese_food_mod.init.JPBlocks;
import jp.tdn.japanese_food_mod.init.JPItems;
import jp.tdn.japanese_food_mod.recipes.WoodenBucketRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.item.crafting.RecipeInput;
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
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class WoodenBucketTileEntity extends BlockEntity implements MenuProvider {
    public static final int[] INPUT_SLOT = {0,1,2,3,4,5};
    public static final int OUTPUT_SLOT = 6;
    public static final int[] RETURN_SLOT = {7, 8, 9};

    private static final String INVENTORY_TAG = "inventory";
    private static final String FERMENTATION_TIME_LEFT = "fermentation_time_left";
    private static final String MAX_FERMENTATION_TIME = "max_fermentation_time";

    public ItemStackHandler inventory = new ItemStackHandler(10){
        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            switch (slot){
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                    return true;
                case OUTPUT_SLOT:
                    return isOutput(stack);
                case 7:
                case 8:
                case 9:
                    return isReturnOutput(stack);
                default:
                    return false;
            }
        }

        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            WoodenBucketTileEntity.this.setChanged();
        }
    };

    public short fermentationTimeLeft = -1;
    public short maxFermentationTime = -1;
    private boolean lastActive = false;

    public WoodenBucketTileEntity(BlockPos pos, BlockState state){
        super(JPBlockEntities.WOODEN_BUCKET.get(), pos, state);
    }

    private boolean isEmpty(final ItemStack[] stack){
        boolean rec = false;
        for(int index = 0; !rec && index < stack.length; ++index){
            rec = stack[index].isEmpty();
        }
        return rec;
    }

    private boolean isInput(final ItemStack[] stack){
        if(isEmpty(stack)) {
            return false;
        }
        return getRecipe(stack).isPresent();
    }

    private boolean isOutput(final ItemStack stack){
        final Optional<ItemStack> result;
        ItemStack[] input = new ItemStack[INPUT_SLOT.length];
        for(int index = 0; index < INPUT_SLOT.length; ++index) {
            input[index] = inventory.getStackInSlot(INPUT_SLOT[index]);
        }
        result = getResult(input);
        return result.isPresent() && ItemStack.isSameItem(result.get(), stack);
    }

    private boolean isReturnOutput(final ItemStack stack){
        return stack.getItem() == Items.GLASS_BOTTLE || stack.getItem() == JPItems.CUP.get();
    }

    @SuppressWarnings("unused")
    private Optional<RecipeHolder<WoodenBucketRecipe>> getRecipe(final ItemStack input){
        return getRecipe(createRecipeInput(input));
    }

    private Optional<RecipeHolder<WoodenBucketRecipe>> getRecipe(final ItemStack[] input){
        return getRecipe(createRecipeInput(input));
    }

    private Optional<RecipeHolder<WoodenBucketRecipe>> getRecipe(final RecipeInput recipeInput){
        return Objects.requireNonNull(level).getRecipeManager().getRecipeFor(WoodenBucketRecipe.RECIPE_TYPE, recipeInput, level);
    }

    private Optional<ItemStack> getResult(final ItemStack[] input){
        final RecipeInput recipeInput = createRecipeInput(input);
        return getRecipe(recipeInput).map(recipe -> recipe.value().getResultItem(level.registryAccess()));
    }

    private static RecipeInput createRecipeInput(ItemStack... items) {
        return new RecipeInput() {
            @Override
            public ItemStack getItem(int index) { return items[index]; }
            @Override
            public int size() { return items.length; }
        };
    }

    public static void tick(Level level, BlockPos pos, BlockState state, WoodenBucketTileEntity te) {
        if(level == null || level.isClientSide) {
            return;
        }
        boolean isActive = false;

        final List<ItemStack> inputs = Lists.newArrayList();
        for(int index = 0; index < INPUT_SLOT.length; ++index){
            ItemStack stack = te.inventory.getStackInSlot(index);
            if(!stack.isEmpty()){
                inputs.add(stack);
            }
        }
        final ItemStack result = te.getResult(inputs.toArray(new ItemStack[0])).orElse(ItemStack.EMPTY);

        if(!result.isEmpty() && te.isInput(inputs.toArray(new ItemStack[0]))){
            final boolean canInsertResultIntoOutPut = te.inventory.insertItem(OUTPUT_SLOT, result, true).isEmpty();
            if(canInsertResultIntoOutPut){
                isActive = true;
                if(te.fermentationTimeLeft == -1){
                    te.fermentationTimeLeft = te.maxFermentationTime = te.getFermentationTime(inputs.toArray(new ItemStack[0]));
                }else{
                    --te.fermentationTimeLeft;
                    if(te.fermentationTimeLeft == 0){
                        te.inventory.insertItem(OUTPUT_SLOT, result, false);
                        int i = 0;
                        for(ItemStack input: inputs) {
                            if(i < INPUT_SLOT.length) {
                                if (input.hasCraftingRemainingItem()) {
                                    te.insertOrDropContainerItem(input, INPUT_SLOT[i]);
                                }
                                input.shrink(1);
                                te.inventory.setStackInSlot(INPUT_SLOT[i], input);
                                ++i;
                            }
                        }
                        te.fermentationTimeLeft = -1;
                    }
                }
            }
        }else{
            te.fermentationTimeLeft = te.maxFermentationTime = -1;
        }

        if(te.lastActive != isActive){
            te.setChanged();
            final BlockState newState = level.getBlockState(pos).setValue(WoodenBucketBlock.FER, isActive);
            level.setBlock(pos, newState, 3);
            te.lastActive = isActive;
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
                net.minecraft.world.Containers.dropItemStack(Objects.requireNonNull(level), worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), containerItem);
            }
        }
    }

    private short getFermentationTime(final ItemStack[] input){
        return getRecipe(input).map(recipe -> recipe.value().getCookTime()).orElse(200).shortValue();
    }

    @Override
    public void loadAdditional(CompoundTag compound, HolderLookup.Provider registries) {
        super.loadAdditional(compound, registries);
        this.inventory.deserializeNBT(registries, compound.getCompound(INVENTORY_TAG));
        this.fermentationTimeLeft = compound.getShort(FERMENTATION_TIME_LEFT);
        this.maxFermentationTime = compound.getShort(MAX_FERMENTATION_TIME);
    }

    @Override
    @Nonnull
    public void saveAdditional(CompoundTag compound, HolderLookup.Provider registries) {
        super.saveAdditional(compound, registries);
        compound.put(INVENTORY_TAG, this.inventory.serializeNBT(registries));
        compound.putShort(FERMENTATION_TIME_LEFT, this.fermentationTimeLeft);
        compound.putShort(MAX_FERMENTATION_TIME, this.maxFermentationTime);
    }

    @Override
    @Nonnull
    public CompoundTag getUpdateTag(HolderLookup.Provider registries){
        CompoundTag tag = new CompoundTag();
        this.saveAdditional(tag, registries);
        return tag;
    }

    @Nonnull
    @Override
    public Component getDisplayName() {
        return Component.translatable(JPBlocks.WOODEN_BUCKET.get().getDescriptionId());
    }

    @Nonnull
    @Override
    public AbstractContainerMenu createMenu(int windowId, @Nonnull Inventory inventory, @Nonnull Player player) {
        return new WoodenBucketContainer(windowId, inventory, this);
    }
}
