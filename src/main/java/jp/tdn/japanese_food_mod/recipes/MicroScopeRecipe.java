package jp.tdn.japanese_food_mod.recipes;

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
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;

public class MicroScopeRecipe implements IRecipe<IInventory> {
    public static final IRecipeSerializer SERIALIZER = new ShapedRecipe.Serializer();
    public static final IRecipeType<MicroScopeRecipe> RECIPE_TYPE = new IRecipeType<MicroScopeRecipe>() {
    };
    protected final ResourceLocation id;
    protected Ingredient ingredient;
    protected ItemStack result;
    protected float experience;
    protected int cookTime;

    public MicroScopeRecipe(ResourceLocation idIn){
        this.id = idIn;
    }

    public boolean matches(IInventory inventory, World worldIn){
        return this.ingredient.test(inventory.getStackInSlot(0));
    }

    @Override
    public ItemStack getCraftingResult(IInventory inventory) {
        return this.result.copy();
    }

    public boolean canFit(int width, int height){
        return true;
    }

    public NonNullList<Ingredient> getIngrediens(){
        NonNullList<Ingredient> nonNullList = NonNullList.create();
        nonNullList.add(this.ingredient);
        return nonNullList;
    }

    public float getExperience(){
        return this.experience;
    }

    public ItemStack getRecipeOutput(){
        return this.result;
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

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<MicroScopeRecipe>{

        @Override
        public MicroScopeRecipe read(ResourceLocation recipeId, JsonObject json) {
            MicroScopeRecipe recipe = new MicroScopeRecipe(recipeId);
            if(!json.has("result")){
                throw new JsonSyntaxException("Missing result, expected to find a string or object");
            }else {
                if(json.get("result").isJsonObject()) {
                    recipe.result = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "result"));
                }
                recipe.cookTime = JSONUtils.getInt(json, "process_time", 50);
                recipe.experience = JSONUtils.getFloat(json, "xp", 0.0f);
                recipe.ingredient = Ingredient.deserialize(JSONUtils.getJsonObject(json, "ingredient"));
            }
            return recipe;
        }

        @Nullable
        @Override
        public MicroScopeRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            MicroScopeRecipe recipe = new MicroScopeRecipe(recipeId);
            recipe.cookTime = buffer.readVarInt();
            recipe.result = buffer.readItemStack();
            recipe.ingredient = Ingredient.read(buffer);
            return recipe;
        }

        @Override
        public void write(PacketBuffer buffer, MicroScopeRecipe recipe) {
            buffer.writeVarInt(recipe.cookTime);
            buffer.writeItemStack(recipe.result);
            buffer.writeFloat(recipe.experience);
            recipe.ingredient.write(buffer);
            return;
        }
    }
}
