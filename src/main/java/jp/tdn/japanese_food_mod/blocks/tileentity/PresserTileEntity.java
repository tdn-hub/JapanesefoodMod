package jp.tdn.japanese_food_mod.blocks.tileentity;

import jp.tdn.japanese_food_mod.blocks.PresserBlock;
import jp.tdn.japanese_food_mod.container.PresserContainer;
import jp.tdn.japanese_food_mod.init.JPBlockEntities;
import jp.tdn.japanese_food_mod.init.JPBlocks;
import jp.tdn.japanese_food_mod.recipes.PresserRecipe;
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

import static jp.tdn.japanese_food_mod.init.JPItems.COOKING_OIL;

public class PresserTileEntity extends BlockEntity implements MenuProvider {
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
            PresserTileEntity.this.setChanged();
        }
    };

    public short pressedTimeLeft = -1;
    public short maxPressedTime = -1;
    public short oilRemaining = 0;
    public short maxOilRemaining = 500;
    private boolean lastPressing = false;

    public PresserTileEntity(BlockPos pos, BlockState state){
        super(JPBlockEntities.PRESSER.get(), pos, state);
    }

    private boolean isInput(final ItemStack stack){
        if(stack.isEmpty()) return false;
        return getRecipe(stack).isPresent();
    }

    private boolean isContainerInput(final ItemStack stack){
        return stack.getItem() == Items.GLASS_BOTTLE;
    }

    private boolean isOutput(final ItemStack stack){
        return stack.getItem() == COOKING_OIL.get();
    }

    private Optional<RecipeHolder<PresserRecipe>> getRecipe(final ItemStack input){
        return getRecipe(new SingleRecipeInput(input));
    }

    private Optional<RecipeHolder<PresserRecipe>> getRecipe(final SingleRecipeInput recipeInput){
        return Objects.requireNonNull(level).getRecipeManager().getRecipeFor(PresserRecipe.RECIPE_TYPE, recipeInput, level);
    }

    private short getResult(final ItemStack input){
        return getRecipe(input).map(recipe -> recipe.value().getResult()).orElse(-1).shortValue();
    }

    public static void tick(Level level, BlockPos pos, BlockState state, PresserTileEntity te) {
        if(level == null || level.isClientSide) return;

        boolean isPressing = false;
        final ItemStack input = te.inventory.getStackInSlot(INPUT_SLOT);
        final ItemStack container = te.inventory.getStackInSlot(CONTAINER_SLOT);
        final int result = te.getResult(input);

        if(result != -1 && te.isInput(input)){
            isPressing = true;
            final boolean canInsertResultIntoOilBar = te.oilRemaining < 500;
            if(canInsertResultIntoOilBar){
                if(te.pressedTimeLeft == -1){
                    te.pressedTimeLeft = te.maxPressedTime = te.getPressedTime(input);
                }else{
                    --te.pressedTimeLeft;
                    if(te.pressedTimeLeft == 0){
                        te.oilRemaining = (short)Math.min(500, te.oilRemaining + result);

                        if (input.hasCraftingRemainingItem()) te.insertOrDropContainerItem(input);
                        input.shrink(1);
                        te.inventory.setStackInSlot(INPUT_SLOT, input);
                        te.pressedTimeLeft = -1;
                    }
                }
            }
        }else{
            te.pressedTimeLeft = te.maxPressedTime = -1;
        }

        if(te.oilRemaining >= 100 && te.isContainerInput(container)) {
            te.inventory.insertItem(OUTPUT_SLOT, new ItemStack(COOKING_OIL.get()), false);
            container.shrink(1);
            te.inventory.setStackInSlot(CONTAINER_SLOT, container);
            te.oilRemaining -= 100;
        }

        if(te.lastPressing != isPressing){
            te.setChanged();
            final BlockState newState = level.getBlockState(pos).setValue(PresserBlock.PRESSING, isPressing);
            level.setBlock(pos, newState, 2);
            te.lastPressing = isPressing;
        }

        int oilLevel;
        if(te.oilRemaining == 0){
            oilLevel = 0;
        }else if(te.oilRemaining < 160){
            oilLevel = 1;
        }else{
            oilLevel = 2;
        }
        PresserBlock block = (PresserBlock) level.getBlockState(pos).getBlock();
        block.setOil(level, pos, level.getBlockState(pos), oilLevel);
    }

    private void insertOrDropContainerItem(final ItemStack stack){
        final ItemStack containerItem = stack.getCraftingRemainingItem();
        final boolean canInsertContainerItemIntoSlot = inventory.insertItem(PresserTileEntity.INPUT_SLOT, containerItem, true).isEmpty();
        if(canInsertContainerItemIntoSlot){
            inventory.insertItem(PresserTileEntity.INPUT_SLOT, containerItem, false);
        }else{
            net.minecraft.world.Containers.dropItemStack(Objects.requireNonNull(level), worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), containerItem);
        }
    }

    private short getPressedTime(final ItemStack input){
        return getRecipe(input).map(recipe -> recipe.value().getCookTime()).orElse(200).shortValue();
    }

    @Override
    public void loadAdditional(CompoundTag compound, HolderLookup.Provider registries) {
        super.loadAdditional(compound, registries);
        this.inventory.deserializeNBT(registries, compound.getCompound(INVENTORY_TAG));
        this.pressedTimeLeft = compound.getShort(PRESSED_TIME_LEFT_TAG);
        this.maxPressedTime = compound.getShort(MAX_PRESSED_TIME_TAG);
        this.oilRemaining = compound.getShort(OIL_REMAINING_TAG);
    }

    @Override
    @Nonnull
    public void saveAdditional(CompoundTag compound, HolderLookup.Provider registries) {
        super.saveAdditional(compound, registries);
        compound.put(INVENTORY_TAG, this.inventory.serializeNBT(registries));
        compound.putShort(PRESSED_TIME_LEFT_TAG, this.pressedTimeLeft);
        compound.putShort(MAX_PRESSED_TIME_TAG, this.maxPressedTime);
        compound.putShort(OIL_REMAINING_TAG, this.oilRemaining);
    }

    @Nonnull
    public CompoundTag getUpdateTag(HolderLookup.Provider registries){
        CompoundTag tag = new CompoundTag();
        this.saveAdditional(tag, registries);
        return tag;
    }

    @Nonnull
    @Override
    public Component getDisplayName() {
        return Component.translatable(JPBlocks.PRESSER.get().getDescriptionId());
    }

    @Nonnull
    @Override
    public AbstractContainerMenu createMenu(int windowId, @Nonnull Inventory inventory, @Nonnull Player player) {
        return new PresserContainer(windowId, inventory, this);
    }
}
