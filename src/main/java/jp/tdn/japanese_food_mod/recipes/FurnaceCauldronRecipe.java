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

public class FurnaceCauldronRecipe implements IRecipe<IInventory> {
    protected final IRecipeType<FurnaceCauldronRecipe> recipeType;
    protected final ResourceLocation id;
    private Ingredient ingredient;
    private ItemStack result;
    private int cookTime;

    public FurnaceCauldronRecipe(ResourceLocation idIn, Ingredient ingredient, ItemStack result, int cookTime){
        this.id = idIn;
        this.recipeType = JPRecipeTypes.CAULDRON;
        this.ingredient = ingredient;
        this.result = result;
        this.cookTime = cookTime;
    }

    public boolean matches(@Nonnull IInventory inventory, @Nonnull World worldIn){
        ItemStack stack = inventory.getItem(0);
        return ingredient.test(stack);
    }

    @Override
    public ItemStack assemble(IInventory iInventory) {
        return this.result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return this.result;
    }

    public int getCookTime(){
        return this.cookTime;
    }

    @Nonnull
    public NonNullList<Ingredient> getIngredients(){
        NonNullList<Ingredient> ingredients = NonNullList.create();
        ingredients.add(ingredient);
        return ingredients;
    }

    @Nonnull
    public ResourceLocation getId(){
        return id;
    }

    @Override
    @Nonnull
    public IRecipeSerializer<?> getSerializer() {
        return JPRecipeSerializers.CAULDRON.get();
    }

    @Nonnull
    public IRecipeType<?> getType(){
        return this.recipeType;
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<FurnaceCauldronRecipe>{

        @Override
        @Nonnull
        public FurnaceCauldronRecipe fromJson(@Nonnull ResourceLocation recipeId, @Nonnull JsonObject json) {
            ItemStack result = CraftingHelper.getItemStack(JSONUtils.getAsJsonObject(json, "result"), false);
            int cookTime = JSONUtils.getAsInt(json, "process_time", 50);
            Ingredient ingredient = CraftingHelper.getIngredient(JSONUtils.getAsJsonObject(json, "ingredient"));
            return new FurnaceCauldronRecipe(recipeId, ingredient, result, cookTime);
        }

        @Nullable
        @Override
        public FurnaceCauldronRecipe fromNetwork(@Nonnull ResourceLocation recipeId, PacketBuffer buffer) {
            int cookTime = buffer.readVarInt();
            ItemStack result = buffer.readItem();
            Ingredient ingredient = Ingredient.fromNetwork(buffer);
            return new FurnaceCauldronRecipe(recipeId, ingredient, result, cookTime);
        }

        @Override
        public void toNetwork(PacketBuffer buffer, FurnaceCauldronRecipe recipe) {
            buffer.writeVarInt(recipe.cookTime);
            buffer.writeItem(recipe.result);
            recipe.ingredient.toNetwork(buffer);
        }
    }
}
