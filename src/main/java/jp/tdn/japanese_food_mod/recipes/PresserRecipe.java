package jp.tdn.japanese_food_mod.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import jp.tdn.japanese_food_mod.JapaneseFoodMod;
import jp.tdn.japanese_food_mod.init.JPItems;
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

public class PresserRecipe implements Recipe<SingleRecipeInput> {
    public static final Serializer SERIALIZER = new Serializer();
    public static final RecipeType<PresserRecipe> RECIPE_TYPE = RecipeType.simple(ResourceLocation.fromNamespaceAndPath(JapaneseFoodMod.MOD_ID, "pressing"));

    protected final ResourceLocation id;
    protected Ingredient ingredient;
    protected int result;
    protected int cookTime;

    public PresserRecipe(ResourceLocation idIn, Ingredient ingredient, int result, int cookTime){
        this.id = idIn;
        this.ingredient = ingredient;
        this.result = result;
        this.cookTime = cookTime;
    }

    @Override
    public boolean matches(@Nonnull SingleRecipeInput input, @Nonnull Level level){
        return this.ingredient.test(input.item());
    }

    @Override
    @Nonnull
    public ItemStack assemble(@Nonnull SingleRecipeInput input, @Nonnull HolderLookup.Provider registries) {
        return new ItemStack(JPItems.COOKING_OIL.get());
    }

    @Override
    @Nonnull
    public ItemStack getResultItem(@Nonnull HolderLookup.Provider registries) {
        return new ItemStack(JPItems.COOKING_OIL.get());
    }

    public int getResult(){
        return result;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height){
        return true;
    }

    @Nonnull
    @Override
    public NonNullList<Ingredient> getIngredients(){
        NonNullList<Ingredient> nonNullList = NonNullList.create();
        nonNullList.add(this.ingredient);
        return nonNullList;
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

    public static class Serializer implements RecipeSerializer<PresserRecipe> {

        @Override
        @Nonnull
        public MapCodec<PresserRecipe> codec() {
            return RecordCodecBuilder.mapCodec(instance -> instance.group(
                    ResourceLocation.CODEC.fieldOf("id").forGetter(r -> r.id),
                    Ingredient.CODEC.fieldOf("ingredient").forGetter(r -> r.ingredient),
                    Codec.INT.fieldOf("result").forGetter(r -> r.result),
                    Codec.INT.fieldOf("process_time").orElse(50).forGetter(r -> r.cookTime)
            ).apply(instance, PresserRecipe::new));
        }

        @Override
        @Nonnull
        public StreamCodec<RegistryFriendlyByteBuf, PresserRecipe> streamCodec() {
            return StreamCodec.of(
                    (buf, recipe) -> {
                        buf.writeResourceLocation(recipe.id);
                        buf.writeVarInt(recipe.cookTime);
                        buf.writeVarInt(recipe.result);
                        Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.ingredient);
                    },
                    buf -> {
                        ResourceLocation id = buf.readResourceLocation();
                        int cookTime = buf.readVarInt();
                        int result = buf.readVarInt();
                        Ingredient ingredient = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
                        return new PresserRecipe(id, ingredient, result, cookTime);
                    }
            );
        }
    }
}
