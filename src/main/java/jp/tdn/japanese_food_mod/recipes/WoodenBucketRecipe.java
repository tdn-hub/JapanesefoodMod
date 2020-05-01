package jp.tdn.japanese_food_mod.recipes;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.RecipeMatcher;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WoodenBucketRecipe implements IRecipe<IInventory> {
    public static final Serializer SERIALIZER = new Serializer();
    public static final IRecipeType<WoodenBucketRecipe> RECIPE_TYPE = new IRecipeType<WoodenBucketRecipe>() {
    };
    protected final ResourceLocation id;
    protected NonNullList<Ingredient> ingredients;
    protected ItemStack result;
    protected int cookTime;

    public WoodenBucketRecipe(ResourceLocation idIn){
        this.id = idIn;
    }

    public boolean matches(@Nonnull IInventory inventory, @Nonnull World worldIn){
        boolean check;
        List<ItemStack> inputs = Lists.newArrayList();
        for(int i = 0; i < inventory.getSizeInventory(); ++i){
            ItemStack stack = inventory.getStackInSlot(i);
            if(!stack.isEmpty()){
                inputs.add(stack);
            }
        }

        check = RecipeMatcher.findMatches(inputs, this.ingredients) != null;

        return check;
    }

    @Override
    @Nonnull
    public ItemStack getCraftingResult(@Nonnull IInventory inventory) {
        return this.result.copy();
    }

    public boolean canFit(int width, int height){
        return true;
    }

    @Nonnull
    public NonNullList<Ingredient> getIngredients(){
        NonNullList<Ingredient> nonNullList = NonNullList.create();
        nonNullList.addAll(this.ingredients);
        return nonNullList;
    }

    @Nonnull
    public ItemStack getRecipeOutput(){
        return this.result;
    }

    @Nonnull
    public ResourceLocation getId(){
        return id;
    }

    public int getCookTime(){
        return cookTime;
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

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<WoodenBucketRecipe>{

        private static NonNullList<Ingredient> readIngredients(JsonArray ingredients){
            NonNullList<Ingredient> rec = NonNullList.create();

            for(int i = 0; i < ingredients.size(); ++i){
                Ingredient ingredient = Ingredient.deserialize(JSONUtils.getJsonObject(ingredients.get(i), "ingredient"));
                if(!ingredient.hasNoMatchingItems()){
                    rec.add(ingredient);
                }
            }

            return rec;
        }

        @Override
        @Nonnull
        public WoodenBucketRecipe read(@Nonnull ResourceLocation recipeId, JsonObject json) {
            WoodenBucketRecipe recipe = new WoodenBucketRecipe(recipeId);
            if(!json.has("result")){
                throw new JsonSyntaxException("Missing result, expected to find a string or object");
            }else {
                if(json.get("result").isJsonObject()) {
                    recipe.result = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "result"));
                }
                recipe.cookTime = JSONUtils.getInt(json, "process_time");
                recipe.ingredients = readIngredients(JSONUtils.getJsonArray(json, "ingredients"));
            }

            return recipe;
        }

        @Nullable
        @Override
        public WoodenBucketRecipe read(@Nonnull ResourceLocation recipeId, PacketBuffer buffer) {
            WoodenBucketRecipe recipe = new WoodenBucketRecipe(recipeId);
            int index = buffer.readVarInt();
            NonNullList<Ingredient> nonnulllist = NonNullList.withSize(index, Ingredient.EMPTY);
            for(int j = 0; j < nonnulllist.size(); ++j) {
                nonnulllist.set(j, Ingredient.read(buffer));
            }
            recipe.result = buffer.readItemStack();
            recipe.cookTime = buffer.readVarInt();
            return recipe;
        }

        @Override
        public void write(@Nonnull PacketBuffer buffer, WoodenBucketRecipe recipe) {
            for (Ingredient ingredient : recipe.ingredients) {
                ingredient.write(buffer);
            }
            buffer.writeItemStack(recipe.result);
            buffer.writeVarInt(recipe.cookTime);
        }
    }
}
