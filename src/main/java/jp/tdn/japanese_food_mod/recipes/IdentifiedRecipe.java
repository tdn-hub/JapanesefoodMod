package jp.tdn.japanese_food_mod.recipes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import jp.tdn.japanese_food_mod.JapaneseFoodMod;
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

public class IdentifiedRecipe implements IRecipe<IInventory> {
    protected final IRecipeType<IdentifiedRecipe> type;
    protected final ResourceLocation id;
    private final Ingredient ingredient;
    private final ItemStack result;
    private final float experience;
    private final float probability;
    private final int identifyTime;

    public IdentifiedRecipe(ResourceLocation idIn, Ingredient ingredient, ItemStack result, float experience, float probability, int identifyTime){
        this.id = idIn;
        this.type = JPRecipeTypes.IDENTIFIED;
        this.ingredient = ingredient;
        this.result = result;
        this.experience = experience;
        this.probability = probability;
        this.identifyTime = identifyTime;
    }

    @Override
    public boolean matches(IInventory inventory, @Nonnull World worldIn){
        return this.ingredient.test(inventory.getItem(0));
    }

    @Override
    public ItemStack assemble(IInventory iInventory) {
        return this.result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return true;
    }

    @Nonnull
    @Override
    public NonNullList<Ingredient> getIngredients(){
        NonNullList<Ingredient> nonNullList = NonNullList.create();
        nonNullList.add(this.ingredient);
        return nonNullList;
    }

    @Override
    public ItemStack getResultItem() {
        return this.result.copy();
    }

    public float getExperience(){
        return this.experience;
    }

    public float getProbability(){
        return probability;
    }

    public int getIdentifyTime(){
        return this.identifyTime;
    }

    @Nonnull
    @Override
    public ResourceLocation getId(){
        return this.id;
    }

    @Nonnull
    @Override
    public IRecipeSerializer<?> getSerializer() {
        return JPRecipeSerializers.IDENTIFY.get();
    }

    @Nonnull
    @Override
    public IRecipeType<?> getType(){
        return type;
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<IdentifiedRecipe>{
        public Serializer(){
            this.setRegistryName(new ResourceLocation(JapaneseFoodMod.MOD_ID, "identifying"));
        }

        @Nonnull
        @Override
        public IdentifiedRecipe fromJson(@Nonnull ResourceLocation recipeId, @Nonnull JsonObject json) {
            final JsonElement inputElement = JSONUtils.isArrayNode(json, "ingredient") ? JSONUtils.getAsJsonArray(json, "ingredient") : JSONUtils.getAsJsonObject(json, "ingredient");
            ItemStack result = CraftingHelper.getItemStack(JSONUtils.getAsJsonObject(json, "result"), true);
            Ingredient ingredient = CraftingHelper.getIngredient(inputElement);
            int identifyTime = JSONUtils.getAsInt(json, "process_time", 50);
            float experience = JSONUtils.getAsFloat(json, "xp", 0.0f);
            float probability = JSONUtils.getAsFloat(json, "probability");
            //JapaneseFoodMod.LOGGER.info(recipe.ingredient);

            return new IdentifiedRecipe(recipeId, ingredient, result, experience, probability, identifyTime);
        }

        @Nullable
        @Override
        public IdentifiedRecipe fromNetwork(@Nonnull ResourceLocation recipeId, @Nonnull PacketBuffer buffer) {
            Ingredient ingredient = Ingredient.fromNetwork(buffer);
            ItemStack result = buffer.readItem();
            int identifyTime = buffer.readVarInt();
            float experience = buffer.readFloat();
            float probability = buffer.readFloat();

            return new IdentifiedRecipe(recipeId, ingredient, result, experience, probability, identifyTime);
        }

        @Override
        public void toNetwork(@Nonnull PacketBuffer buffer, IdentifiedRecipe recipe) {
            recipe.ingredient.toNetwork(buffer);
            buffer.writeItem(recipe.result);
            buffer.writeVarInt(recipe.identifyTime);
            buffer.writeFloat(recipe.experience);
            buffer.writeFloat(recipe.probability);
        }
    }
}
