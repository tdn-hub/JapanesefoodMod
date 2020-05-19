package jp.tdn.japanese_food_mod.recipes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import jp.tdn.japanese_food_mod.JapaneseFoodMod;
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

public class MicroScopeRecipe implements IRecipe<IInventory> {
    public static final Serializer SERIALIZER = new Serializer();
    public static final IRecipeType<MicroScopeRecipe> RECIPE_TYPE = new IRecipeType<MicroScopeRecipe>() {
    };
    protected final ResourceLocation id;
    protected Ingredient ingredient;
    protected ItemStack result;
    protected float experience;
    protected int cookTime;

    public MicroScopeRecipe(ResourceLocation idIn, Ingredient ingredient, ItemStack result, float experience, int cookTime){
        this.id = idIn;
        this.ingredient = ingredient;
        this.result = result;
        this.experience = experience;
        this.cookTime = cookTime;
    }

    public boolean matches(IInventory inventory, @Nonnull World worldIn){
        return this.ingredient.test(inventory.getStackInSlot(0));
    }

    @Nonnull
    @Override
    public ItemStack getCraftingResult(@Nonnull IInventory inventory) {
        return this.result.copy();
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

    public float getExperience(){
        return this.experience;
    }

    @Nonnull
    public ItemStack getRecipeOutput(){
        return this.result;
    }

    public int getCookTime(){
        return this.cookTime;
    }

    @Nonnull
    public ResourceLocation getId(){
        return this.id;
    }

    @Nonnull
    @Override
    public IRecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    @Nonnull
    public IRecipeType<?> getType(){
        return RECIPE_TYPE;
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<MicroScopeRecipe>{
        public Serializer(){
            this.setRegistryName(new ResourceLocation(JapaneseFoodMod.MOD_ID, "identifying"));
        }

        @Nonnull
        @Override
        public MicroScopeRecipe read(@Nonnull ResourceLocation recipeId, @Nonnull JsonObject json) {
            final JsonElement inputElement = JSONUtils.isJsonArray(json, "ingredient") ? JSONUtils.getJsonArray(json, "ingredient") : JSONUtils.getJsonObject(json, "ingredient");
            ItemStack result = CraftingHelper.getItemStack(JSONUtils.getJsonObject(json, "result"), true);
            Ingredient ingredient = CraftingHelper.getIngredient(inputElement);
            int cookTime = JSONUtils.getInt(json, "process_time", 50);
            float experience = JSONUtils.getFloat(json, "xp", 0.0f);
            //JapaneseFoodMod.LOGGER.info(recipe.ingredient);

            return new MicroScopeRecipe(recipeId, ingredient, result, experience, cookTime);
        }

        @Nullable
        @Override
        public MicroScopeRecipe read(@Nonnull ResourceLocation recipeId, @Nonnull PacketBuffer buffer) {
            Ingredient ingredient = Ingredient.read(buffer);
            ItemStack result = buffer.readItemStack();
            int cookTime = buffer.readVarInt();
            float experience = buffer.readFloat();

            return new MicroScopeRecipe(recipeId, ingredient, result, experience, cookTime);
        }

        @Override
        public void write(@Nonnull PacketBuffer buffer, MicroScopeRecipe recipe) {
            recipe.ingredient.write(buffer);
            buffer.writeItemStack(recipe.result);
            buffer.writeVarInt(recipe.cookTime);
            buffer.writeFloat(recipe.experience);
        }
    }
}
