package jp.tdn.japanese_food_mod.recipes;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import jp.tdn.japanese_food_mod.JapaneseFoodMod;
import jp.tdn.japanese_food_mod.init.JPRecipeSerializers;
import jp.tdn.japanese_food_mod.init.JPRecipeTypes;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
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
import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.List;

public class FermentationRecipe implements IRecipe<IInventory> {
    protected final IRecipeType<FermentationRecipe> type;
    protected final ResourceLocation id;
    private final NonNullList<Ingredient> ingredients;
    private final ItemStack result;
    private final int fermentTime;

    public FermentationRecipe(ResourceLocation id, NonNullList<Ingredient> ingredients, ItemStack output, int fermentTime){
        this.type = JPRecipeTypes.FERMENTATION;
        this.id = id;
        this.ingredients = ingredients;
        this.result = output;
        this.fermentTime = fermentTime;
    }

    @Override
    public boolean matches(IInventory iInventory, World world) {
        List<ItemStack> inputs = Lists.newArrayList();
        for(int i = 0; i < iInventory.getContainerSize(); ++i){
            ItemStack stack = iInventory.getItem(i);
            if(!stack.isEmpty()){
                inputs.add(stack);
            }
        }

        return RecipeMatcher.findMatches(inputs, this.ingredients) != null;
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
    public NonNullList<Ingredient> getIngredients() {
        return this.ingredients;
    }

    @Override
    public ItemStack getResultItem() {
        return this.result;
    }

    public int getFermentTime() {
        return this.fermentTime;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public IRecipeType<FermentationRecipe> getType() {
        return this.type;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return JPRecipeSerializers.FERMENTATION.get();
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<FermentationRecipe> {
        @Override
        public FermentationRecipe fromJson(ResourceLocation recipeId, JsonObject json) {

            ItemStack result = CraftingHelper.getItemStack(JSONUtils.getAsJsonObject(json, "result"), true);
            int cookTime = JSONUtils.getAsInt(json, "process_time");
            NonNullList<Ingredient> ingredients = readIngredients(JSONUtils.getAsJsonArray(json, "ingredients"));
            FermentationRecipe recipe = new FermentationRecipe(recipeId, ingredients, result, cookTime);

            return recipe;
        }

        @Nullable
        @Override
        public FermentationRecipe fromNetwork(ResourceLocation recipeId, PacketBuffer buffer) {
            int size = buffer.readVarInt();
            NonNullList<Ingredient> ingredients = NonNullList.create();
            for (int index = 0; index < size; ++index){
                ingredients.add(Ingredient.fromNetwork(buffer));
            }
            ItemStack result = buffer.readItem();
            int time = buffer.readVarInt();
            return new FermentationRecipe(recipeId, ingredients, result, time);
        }

        @Override
        public void toNetwork(PacketBuffer buffer, FermentationRecipe recipe) {
            buffer.writeVarInt(recipe.ingredients.size());
            for (Ingredient ingredient : recipe.ingredients) {
                ingredient.toNetwork(buffer);
            }
            buffer.writeItem(recipe.result);
            buffer.writeVarInt(recipe.fermentTime);
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
    }
}
