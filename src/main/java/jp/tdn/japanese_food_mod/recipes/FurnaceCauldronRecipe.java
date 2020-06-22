package jp.tdn.japanese_food_mod.recipes;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import jp.tdn.japanese_food_mod.JapaneseFoodMod;
import jp.tdn.japanese_food_mod.init.JPItems;
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
import net.minecraftforge.common.util.RecipeMatcher;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FurnaceCauldronRecipe implements IRecipe<IInventory> {
    public static final Serializer SERIALIZER = new Serializer();
    public static final IRecipeType<FurnaceCauldronRecipe> RECIPE_TYPE = new IRecipeType<FurnaceCauldronRecipe>() {
    };
    protected final ResourceLocation id;
    protected NonNullList<Ingredient> ingredients;
    protected int result;
    protected int cookTime;

    public FurnaceCauldronRecipe(ResourceLocation idIn){
        this.id = idIn;
    }

    public boolean matches(@Nonnull IInventory inventory, @Nonnull World worldIn){
        boolean check = false;
        ItemStack stack = inventory.getStackInSlot(0);
        for(int i = 0; i < ingredients.size() && !check; ++i){
            check = ingredients.get(i).test(stack);
        }

        return check;
    }

    @Override
    @Nonnull
    public ItemStack getCraftingResult(@Nonnull IInventory inventory) {
        return new ItemStack(JPItems.SALT);
    }

    public int getResult(){
        return result;
    }

    public boolean canFit(int width, int height){
        return true;
    }

    @Nonnull
    public NonNullList<Ingredient> getIngredients(){
        return ingredients;
    }

    @Nonnull
    public ItemStack getRecipeOutput(){
        return new ItemStack(JPItems.SALT);
    }

    public int getCookTime(){
        return cookTime;
    }

    @Nonnull
    public ResourceLocation getId(){
        return id;
    }

    @Override
    @Nonnull
    public IRecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    @Nonnull
    public IRecipeType<?> getType(){
        return RECIPE_TYPE;
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<FurnaceCauldronRecipe>{
        Serializer(){
            this.setRegistryName(new ResourceLocation(JapaneseFoodMod.MOD_ID, "salt_making"));
        }

        private static NonNullList<Ingredient> readIngredients(JsonArray ingredients){
            NonNullList<Ingredient> rec = NonNullList.create();

            for(final JsonElement element: ingredients) {
                rec.add(CraftingHelper.getIngredient(element));

            }

            if(rec.isEmpty())
                throw new JsonParseException("No ingredients for shapeless recipe");
            return rec;
        }

        @Override
        @Nonnull
        public FurnaceCauldronRecipe read(@Nonnull ResourceLocation recipeId, @Nonnull JsonObject json) {
            FurnaceCauldronRecipe recipe = new FurnaceCauldronRecipe(recipeId);
            recipe.result = JSONUtils.getInt(json, "result");
            recipe.cookTime = JSONUtils.getInt(json, "process_time", 50);
            recipe.ingredients = readIngredients(JSONUtils.getJsonArray(json, "ingredients"));
            return recipe;
        }

        @Nullable
        @Override
        public FurnaceCauldronRecipe read(@Nonnull ResourceLocation recipeId, PacketBuffer buffer) {
            FurnaceCauldronRecipe recipe = new FurnaceCauldronRecipe(recipeId);
            recipe.cookTime = buffer.readVarInt();
            recipe.result = buffer.readVarInt();
            final int index = buffer.readVarInt();
            NonNullList<Ingredient> nonnulllist = NonNullList.withSize(index, Ingredient.EMPTY);
            for(int j = 0; j < nonnulllist.size(); ++j) {
                Ingredient ingredient = Ingredient.read(buffer);
                nonnulllist.set(j, ingredient); }
            recipe.ingredients = nonnulllist;
            return recipe;
        }

        @Override
        public void write(PacketBuffer buffer, FurnaceCauldronRecipe recipe) {
            buffer.writeVarInt(recipe.cookTime);
            buffer.writeVarInt(recipe.result);
            buffer.writeVarInt(recipe.ingredients.size());
            for (Ingredient ingredient : recipe.ingredients) {
                ingredient.write(buffer);
            }
        }
    }
}
