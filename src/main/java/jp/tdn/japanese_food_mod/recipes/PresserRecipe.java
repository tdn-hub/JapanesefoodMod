package jp.tdn.japanese_food_mod.recipes;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import jp.tdn.japanese_food_mod.JapaneseFoodMod;
import jp.tdn.japanese_food_mod.init.JPItems;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;

public class PresserRecipe implements IRecipe<IInventory> {
    public static final IRecipeSerializer SERIALIZER = new Serializer();
    public static final IRecipeType<PresserRecipe> RECIPE_TYPE = new IRecipeType<PresserRecipe>() {
    };
    protected final ResourceLocation id;
    protected Ingredient ingredient;
    protected int result;
    protected int cookTime;

    public PresserRecipe(ResourceLocation idIn){
        this.id = idIn;
    }

    public boolean matches(IInventory inventory, World worldIn){
        return this.ingredient.test(inventory.getStackInSlot(0));
    }

    @Override
    public ItemStack getCraftingResult(IInventory inventory) {
        return new ItemStack(JPItems.COOKING_OIL);
    }

    public int getResult(){
        return result;
    }

    public boolean canFit(int width, int height){
        return true;
    }

    public NonNullList<Ingredient> getIngredients(){
        NonNullList<Ingredient> nonNullList = NonNullList.create();
        nonNullList.add(this.ingredient);
        return nonNullList;
    }

    public ItemStack getRecipeOutput(){
        return new ItemStack(JPItems.COOKING_OIL);
    }

    public int getCookTime(){
        return cookTime;
    }

    public ResourceLocation getId(){
        return id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    public IRecipeType<?> getType(){
        return RECIPE_TYPE;
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<PresserRecipe>{

        @Override
        public PresserRecipe read(ResourceLocation recipeId, JsonObject json) {
            PresserRecipe recipe = new PresserRecipe(recipeId);
            recipe.result = JSONUtils.getInt(json, "result");
            recipe.cookTime = JSONUtils.getInt(json, "process_time", 50);
            recipe.ingredient = Ingredient.deserialize(JSONUtils.getJsonObject(json, "ingredient"));
            return recipe;
        }

        @Nullable
        @Override
        public PresserRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            PresserRecipe recipe = new PresserRecipe(recipeId);
            recipe.cookTime = buffer.readVarInt();
            recipe.result = buffer.readVarInt();
            recipe.ingredient = Ingredient.read(buffer);
            return recipe;
        }

        @Override
        public void write(PacketBuffer buffer, PresserRecipe recipe) {
            buffer.writeVarInt(recipe.cookTime);
            buffer.writeVarInt(recipe.result);
            recipe.ingredient.write(buffer);
            return;
        }
    }
}
