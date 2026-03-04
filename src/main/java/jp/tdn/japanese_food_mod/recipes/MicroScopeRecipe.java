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

public class MicroScopeRecipe implements Recipe<SingleRecipeInput> {
    public static final Serializer SERIALIZER = new Serializer();
    public static final RecipeType<MicroScopeRecipe> RECIPE_TYPE = RecipeType.simple(ResourceLocation.fromNamespaceAndPath(JapaneseFoodMod.MOD_ID, "identifying"));

    protected final ResourceLocation id;
    protected Ingredient ingredient;
    protected ItemStack result;
    protected float experience;
    protected float probability;
    protected int cookTime;

    public MicroScopeRecipe(ResourceLocation idIn, Ingredient ingredient, ItemStack result, float experience, float probability, int cookTime){
        this.id = idIn;
        this.ingredient = ingredient;
        this.result = result;
        this.experience = experience;
        this.probability = probability;
        this.cookTime = cookTime;
    }

    @Override
    public boolean matches(@Nonnull SingleRecipeInput input, @Nonnull Level level){
        return this.ingredient.test(input.item());
    }

    @Nonnull
    @Override
    public ItemStack assemble(@Nonnull SingleRecipeInput input, @Nonnull HolderLookup.Provider registries) {
        return this.result.copy();
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

    public float getExperience(){
        return this.experience;
    }

    public float getProbability(){
        return probability;
    }

    @Nonnull
    @Override
    public ItemStack getResultItem(@Nonnull HolderLookup.Provider registries){
        return this.result;
    }

    public int getCookTime(){
        return this.cookTime;
    }

    @Nonnull
    @Override
    public RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    @Nonnull
    @Override
    public RecipeType<?> getType(){
        return RECIPE_TYPE;
    }

    public static class Serializer implements RecipeSerializer<MicroScopeRecipe> {

        @Nonnull
        @Override
        public MapCodec<MicroScopeRecipe> codec() {
            return RecordCodecBuilder.mapCodec(instance -> instance.group(
                    ResourceLocation.CODEC.fieldOf("id").forGetter(r -> r.id),
                    Ingredient.CODEC.fieldOf("ingredient").forGetter(r -> r.ingredient),
                    ItemStack.CODEC.fieldOf("result").forGetter(r -> r.result),
                    Codec.FLOAT.fieldOf("xp").orElse(0.0f).forGetter(r -> r.experience),
                    Codec.FLOAT.fieldOf("probability").forGetter(r -> r.probability),
                    Codec.INT.fieldOf("process_time").orElse(50).forGetter(r -> r.cookTime)
            ).apply(instance, MicroScopeRecipe::new));
        }

        @Nonnull
        @Override
        public StreamCodec<RegistryFriendlyByteBuf, MicroScopeRecipe> streamCodec() {
            return StreamCodec.of(
                    (buf, recipe) -> {
                        buf.writeResourceLocation(recipe.id);
                        Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.ingredient);
                        ItemStack.STREAM_CODEC.encode(buf, recipe.result);
                        buf.writeVarInt(recipe.cookTime);
                        buf.writeFloat(recipe.experience);
                        buf.writeFloat(recipe.probability);
                    },
                    buf -> {
                        ResourceLocation id = buf.readResourceLocation();
                        Ingredient ingredient = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
                        ItemStack result = ItemStack.STREAM_CODEC.decode(buf);
                        int cookTime = buf.readVarInt();
                        float experience = buf.readFloat();
                        float probability = buf.readFloat();
                        return new MicroScopeRecipe(id, ingredient, result, experience, probability, cookTime);
                    }
            );
        }
    }
}
