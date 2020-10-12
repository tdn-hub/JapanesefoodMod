package jp.tdn.japanese_food_mod.recipes;

import com.google.gson.JsonObject;
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
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class FurnaceCauldronRecipe implements IRecipe<IInventory> {
    public static final Serializer SERIALIZER = new Serializer();
    public static final IRecipeType<FurnaceCauldronRecipe> RECIPE_TYPE = new IRecipeType<FurnaceCauldronRecipe>() {
    };
    protected final ResourceLocation id;
    protected Ingredient ingredient;
    protected ItemStack result;
    protected int cookTime;

    public FurnaceCauldronRecipe(ResourceLocation idIn){
        this.id = idIn;
    }

    public boolean matches(@Nonnull IInventory inventory, @Nonnull World worldIn){
        ItemStack stack = inventory.getStackInSlot(0);
        return ingredient.test(stack);
    }

    @Override
    @Nonnull
    public ItemStack getCraftingResult(@Nonnull IInventory inventory) {
        return new ItemStack(JPItems.SALT.get());
    }

    public ItemStack getResult(){
        return result;
    }

    public boolean canFit(int width, int height){
        return true;
    }

    @Nonnull
    public NonNullList<Ingredient> getIngredients(){
        NonNullList<Ingredient> ingredients = NonNullList.create();
        ingredients.add(ingredient);
        return ingredients;
    }

    @Nonnull
    public ItemStack getRecipeOutput(){
        return new ItemStack(JPItems.SALT.get());
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

        @Override
        @Nonnull
        public FurnaceCauldronRecipe read(@Nonnull ResourceLocation recipeId, @Nonnull JsonObject json) {
            FurnaceCauldronRecipe recipe = new FurnaceCauldronRecipe(recipeId);
            recipe.result = CraftingHelper.getItemStack(JSONUtils.getJsonObject(json, "result"), false);
            recipe.cookTime = JSONUtils.getInt(json, "process_time", 50);
            recipe.ingredient = CraftingHelper.getIngredient(JSONUtils.getJsonObject(json, "ingredient"));
            return recipe;
        }

        @Nullable
        @Override
        public FurnaceCauldronRecipe read(@Nonnull ResourceLocation recipeId, PacketBuffer buffer) {
            FurnaceCauldronRecipe recipe = new FurnaceCauldronRecipe(recipeId);
            recipe.cookTime = buffer.readVarInt();
            recipe.result = buffer.readItemStack();
            recipe.ingredient = Ingredient.read(buffer);
            return recipe;
        }

        @Override
        public void write(PacketBuffer buffer, FurnaceCauldronRecipe recipe) {
            buffer.writeVarInt(recipe.cookTime);
            buffer.writeItemStack(recipe.result);
            recipe.ingredient.write(buffer);
        }
    }
}
