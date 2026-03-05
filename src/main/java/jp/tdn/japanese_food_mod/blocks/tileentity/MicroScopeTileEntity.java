package jp.tdn.japanese_food_mod.blocks.tileentity;

import java.util.concurrent.ThreadLocalRandom;
import jp.tdn.japanese_food_mod.container.MicroScopeContainer;
import jp.tdn.japanese_food_mod.init.JPBlockEntities;
import jp.tdn.japanese_food_mod.init.JPBlocks;
import jp.tdn.japanese_food_mod.recipes.MicroScopeRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.crafting.SingleRecipeInput;
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

public class MicroScopeTileEntity extends BlockEntity implements MenuProvider {
    public static final int INPUT_SLOT = 0;
    public static final int CONTAINER_SLOT = 2;
    public static final int OUTPUT_SLOT = 1;

    private static final String INVENTORY_TAG = "inventory";
    private static final String IDENTIFIED_TIME_LEFT_TAG = "identifiedTimeLeft";
    private static final String MAX_IDENTIFIED_TIME_TAG = "maxIdentifiedTime";

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
            MicroScopeTileEntity.this.setChanged();
        }
    };

    public short identifiedTimeLeft = -1;
    public short maxIdentifiedTime = -1;

    public MicroScopeTileEntity(BlockPos pos, BlockState state){
        super(JPBlockEntities.MICROSCOPE.get(), pos, state);
    }

    private boolean isInput(final ItemStack stack){
        if(stack.isEmpty()) {
            return false;
        }
        return getRecipe(stack).isPresent();
    }

    private boolean isContainerInput(final ItemStack stack){
        return stack.getItem() == Items.GLASS_BOTTLE;
    }

    private boolean isOutput(final ItemStack stack){
        final Optional<ItemStack> result = getResult(inventory.getStackInSlot(INPUT_SLOT));
        return result.isPresent() && ItemStack.isSameItem(result.get(), stack);
    }

    private Optional<RecipeHolder<MicroScopeRecipe>> getRecipe(final ItemStack input){
        return getRecipe(new SingleRecipeInput(input));
    }

    private Optional<RecipeHolder<MicroScopeRecipe>> getRecipe(final SingleRecipeInput recipeInput){
        return Objects.requireNonNull(level).getRecipeManager().getRecipeFor(MicroScopeRecipe.RECIPE_TYPE, recipeInput, level);
    }

    private Optional<ItemStack> getResult(final ItemStack input){
        final SingleRecipeInput dummyInput = new SingleRecipeInput(input);
        return getRecipe(dummyInput).map(recipe -> recipe.value().getResultItem(level.registryAccess()));
    }

    public ItemStack getInventory(){
        return inventory.getStackInSlot(INPUT_SLOT);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, MicroScopeTileEntity te) {
        if(level == null || level.isClientSide) {
            return;
        }

        final ItemStack input = te.inventory.getStackInSlot(INPUT_SLOT);
        final ItemStack container = te.inventory.getStackInSlot(CONTAINER_SLOT);
        final ItemStack result = te.getResult(input).orElse(ItemStack.EMPTY);

        if(!result.isEmpty() && te.isInput(input) && te.isContainerInput(container)){
            final boolean canInsertResultIntoOutPut = te.inventory.insertItem(OUTPUT_SLOT, result, true).isEmpty();
            if(canInsertResultIntoOutPut){
                if(te.identifiedTimeLeft == -1){
                    te.identifiedTimeLeft = te.maxIdentifiedTime = te.getIdentifiedTime(input);
                }else{
                    --te.identifiedTimeLeft;
                    if(te.identifiedTimeLeft == 0){
                        if(ThreadLocalRandom.current().nextInt(101) <= te.getRecipe(input).get().value().getProbability() * 100){
                            te.inventory.insertItem(OUTPUT_SLOT, result, false);
                            if (input.hasCraftingRemainingItem()) {
                                te.insertOrDropContainerItem(input);
                            }
                            container.shrink(1);
                            te.inventory.setStackInSlot(CONTAINER_SLOT, container);
                        }
                        input.shrink(1);

                        te.inventory.setStackInSlot(INPUT_SLOT, input);
                        te.identifiedTimeLeft = -1;
                    }
                }
            }
        }else{
            te.identifiedTimeLeft = te.maxIdentifiedTime = -1;
        }
    }

    private void insertOrDropContainerItem(final ItemStack stack){
        final ItemStack containerItem = stack.getCraftingRemainingItem();
        final boolean canInsertContainerItemIntoSlot = inventory.insertItem(MicroScopeTileEntity.INPUT_SLOT, containerItem, true).isEmpty();
        if(canInsertContainerItemIntoSlot){
            inventory.insertItem(MicroScopeTileEntity.INPUT_SLOT, containerItem, false);
        }else{
            net.minecraft.world.Containers.dropItemStack(Objects.requireNonNull(level), worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), containerItem);
        }
    }

    private short getIdentifiedTime(final ItemStack input){
        return getRecipe(input).map(recipe -> recipe.value().getCookTime()).orElse(200).shortValue();
    }

    @Override
    public void loadAdditional(CompoundTag compound, HolderLookup.Provider registries) {
        super.loadAdditional(compound, registries);
        this.inventory.deserializeNBT(registries, compound.getCompound(INVENTORY_TAG));
        this.identifiedTimeLeft = compound.getShort(IDENTIFIED_TIME_LEFT_TAG);
        this.maxIdentifiedTime = compound.getShort(MAX_IDENTIFIED_TIME_TAG);
    }

    @Override
    @Nonnull
    public void saveAdditional(CompoundTag compound, HolderLookup.Provider registries) {
        super.saveAdditional(compound, registries);
        compound.put(INVENTORY_TAG, this.inventory.serializeNBT(registries));
        compound.putShort(IDENTIFIED_TIME_LEFT_TAG, this.identifiedTimeLeft);
        compound.putShort(MAX_IDENTIFIED_TIME_TAG, this.maxIdentifiedTime);
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
        return Component.translatable(JPBlocks.MICRO_SCOPE.get().getDescriptionId());
    }

    @Nonnull
    @Override
    public AbstractContainerMenu createMenu(int windowId, @Nonnull Inventory inventory, @Nonnull Player player) {
        return new MicroScopeContainer(windowId, inventory, this);
    }
}
