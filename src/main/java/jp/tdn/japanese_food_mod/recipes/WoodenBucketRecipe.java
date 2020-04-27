package jp.tdn.japanese_food_mod.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mojang.realmsclient.util.JsonUtils;
import jp.tdn.japanese_food_mod.JapaneseFoodMod;
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

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WoodenBucketRecipe implements IRecipe<IInventory> {
    public static final IRecipeSerializer SERIALIZER = new Serializer();
    public static final IRecipeType<WoodenBucketRecipe> RECIPE_TYPE = new IRecipeType<WoodenBucketRecipe>() {
    };
    protected final ResourceLocation id;
    protected NonNullList<Ingredient> ingredients;
    protected ItemStack result;
    protected int cookTime;

    public WoodenBucketRecipe(ResourceLocation idIn){
        this.id = idIn;
    }

    public boolean matches(IInventory inventory, World worldIn){
        boolean check;
        List<ItemStack> inputs = new ArrayList();
        for(int i = 0; i < 3; ++i){
            inputs.add(inventory.getStackInSlot(i));
        }

        if(RecipeMatcher.findMatches(inputs, this.ingredients) != null){
            check = true;
        }else{
            check = false;
        }

        return check;
    }

    @Override
    public ItemStack getCraftingResult(IInventory inventory) {
        return this.result.copy();
    }

    public boolean canFit(int width, int height){
        return true;
    }

    public NonNullList<Ingredient> getIngredients(){
        NonNullList<Ingredient> nonNullList = NonNullList.create();
        for(Ingredient ingredient: this.ingredients) {
            nonNullList.add(ingredient);
        }
        return nonNullList;
    }

    public ItemStack getRecipeOutput(){
        return this.result;
    }

    public ResourceLocation getId(){
        return id;
    }

    public int getCookTime(){
        return cookTime;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

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
        public WoodenBucketRecipe read(ResourceLocation recipeId, JsonObject json) {
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
        public WoodenBucketRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
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
        public void write(PacketBuffer buffer, WoodenBucketRecipe recipe) {
            Iterator it = recipe.ingredients.iterator();
            while(it.hasNext()){
                Ingredient ingredient = (Ingredient)it.next();
                ingredient.write(buffer);
            }
            buffer.writeItemStack(recipe.result);
            buffer.writeVarInt(recipe.cookTime);
            return;
        }
    }
}
