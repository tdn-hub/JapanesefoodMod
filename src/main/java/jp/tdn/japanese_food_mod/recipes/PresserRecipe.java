package jp.tdn.japanese_food_mod.recipes;

import com.google.gson.JsonObject;
import jp.tdn.japanese_food_mod.JapaneseFoodMod;
import jp.tdn.japanese_food_mod.init.JPItems;
import jp.tdn.japanese_food_mod.init.JPRecipeSerializers;
import jp.tdn.japanese_food_mod.init.JPRecipeTypes;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PresserRecipe implements IRecipe<IInventory> {
    protected final IRecipeType<PresserRecipe> type;
    protected final ResourceLocation id;
    private final Ingredient ingredient;
    private final int result;
    private final int cookTime;

    public PresserRecipe(ResourceLocation idIn, Ingredient ingredient, int result, int cookTime){
        this.id = idIn;
        this.type = JPRecipeTypes.PRESSER;
        this.ingredient = ingredient;
        this.result = result;
        this.cookTime = cookTime;
    }

    public boolean matches(IInventory inventory, @Nonnull World worldIn){
        return this.ingredient.test(inventory.getItem(0));
    }

    @Override
    public ItemStack assemble(IInventory iInventory) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return true;
    }

    public int getResult(){
        return result;
    }

    public boolean canFit(int width, int height){
        return true;
    }

    @Nonnull
    public NonNullList<Ingredient> getIngredients(){
        NonNullList<Ingredient> nonNullList = NonNullList.create();
        nonNullList.add(this.ingredient);
        return nonNullList;
    }

    @Override
    public ItemStack getResultItem() {
        return ItemStack.EMPTY;
    }

    public int getCookTime(){
        return this.cookTime;
    }

    @Nonnull
    public ResourceLocation getId(){
        return this.id;
    }

    @Override
    @Nonnull
    public IRecipeSerializer<?> getSerializer() {
        return JPRecipeSerializers.PRESSER.get();
    }

    @Nonnull
    public IRecipeType<?> getType(){
        return this.type;
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<PresserRecipe>{

        @Override
        @Nonnull
        public PresserRecipe fromJson(@Nonnull ResourceLocation recipeId, @Nonnull JsonObject json) {
            int result = JSONUtils.getAsInt(json, "result");
            int cookTime = JSONUtils.getAsInt(json, "process_time", 50);
            Ingredient ingredient = CraftingHelper.getIngredient(JSONUtils.getAsJsonObject(json, "ingredient"));
            return new PresserRecipe(recipeId, ingredient, result, cookTime);
        }

        @Nullable
        @Override
        public PresserRecipe fromNetwork(@Nonnull ResourceLocation recipeId, PacketBuffer buffer) {
            int cookTime = buffer.readVarInt();
            int result = buffer.readVarInt();
            Ingredient ingredient = Ingredient.fromNetwork(buffer);
            return new PresserRecipe(recipeId, ingredient, result, cookTime);
        }

        @Override
        public void toNetwork(PacketBuffer buffer, PresserRecipe recipe) {
            buffer.writeVarInt(recipe.cookTime);
            buffer.writeVarInt(recipe.result);
            recipe.ingredient.toNetwork(buffer);
        }
    }
}
