package jp.tdn.japanese_food_mod.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import jp.tdn.japanese_food_mod.JapaneseFoodMod;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;

public class FurnaceCauldronRecipe implements Recipe<SingleRecipeInput> {
    public static final Serializer SERIALIZER = new Serializer();
    public static final RecipeType<FurnaceCauldronRecipe> RECIPE_TYPE = RecipeType.simple(ResourceLocation.fromNamespaceAndPath(JapaneseFoodMod.MOD_ID, "salt_making"));

    protected final ResourceLocation id;
    protected Ingredient ingredient;
    protected ItemStack result;
    protected int cookTime;

    public FurnaceCauldronRecipe(ResourceLocation idIn, Ingredient ingredient, ItemStack result, int cookTime){
        this.id = idIn;
        this.ingredient = ingredient;
        this.result = result;
        this.cookTime = cookTime;
    }

    @Override
    public boolean matches(@Nonnull SingleRecipeInput input, @Nonnull Level level){
        ItemStack stack = input.item();
        return ingredient.test(stack);
    }

    @Override
    @Nonnull
    public ItemStack assemble(@Nonnull SingleRecipeInput input, @Nonnull HolderLookup.Provider registries) {
        return result.copy();
    }

    @Override
    @Nonnull
    public ItemStack getResultItem(@Nonnull HolderLookup.Provider registries) {
        return result;
    }

    public ItemStack getResult(){
        return result;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height){
        return true;
    }

    @Nonnull
    @Override
    public NonNullList<Ingredient> getIngredients(){
        NonNullList<Ingredient> ingredients = NonNullList.create();
        ingredients.add(ingredient);
        return ingredients;
    }

    public int getCookTime(){
        return cookTime;
    }

    @Override
    @Nonnull
    public RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    @Nonnull
    @Override
    public RecipeType<?> getType(){
        return RECIPE_TYPE;
    }

    public static class Serializer implements RecipeSerializer<FurnaceCauldronRecipe> {

        @Override
        @Nonnull
        public MapCodec<FurnaceCauldronRecipe> codec() {
            return RecordCodecBuilder.mapCodec(instance -> instance.group(
                    ResourceLocation.CODEC.fieldOf("id").forGetter(r -> r.id),
                    Ingredient.CODEC.fieldOf("ingredient").forGetter(r -> r.ingredient),
                    ItemStack.CODEC.fieldOf("result").forGetter(r -> r.result),
                    Codec.INT.fieldOf("process_time").orElse(50).forGetter(r -> r.cookTime)
            ).apply(instance, FurnaceCauldronRecipe::new));
        }

        @Override
        @Nonnull
        public StreamCodec<RegistryFriendlyByteBuf, FurnaceCauldronRecipe> streamCodec() {
            return StreamCodec.of(
                    (buf, recipe) -> {
                        buf.writeResourceLocation(recipe.id);
                        buf.writeVarInt(recipe.cookTime);
                        ItemStack.STREAM_CODEC.encode(buf, recipe.result);
                        Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.ingredient);
                    },
                    buf -> {
                        ResourceLocation id = buf.readResourceLocation();
                        int cookTime = buf.readVarInt();
                        ItemStack result = ItemStack.STREAM_CODEC.decode(buf);
                        Ingredient ingredient = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
                        return new FurnaceCauldronRecipe(id, ingredient, result, cookTime);
                    }
            );
        }
    }
}
