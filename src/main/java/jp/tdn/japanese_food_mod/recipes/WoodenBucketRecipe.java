package jp.tdn.japanese_food_mod.recipes;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import jp.tdn.japanese_food_mod.JapaneseFoodMod;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.util.RecipeMatcher;

import javax.annotation.Nonnull;
import java.util.List;

public class WoodenBucketRecipe implements Recipe<RecipeInput> {
    public static final Serializer SERIALIZER = new Serializer();
    public static final RecipeType<WoodenBucketRecipe> RECIPE_TYPE = RecipeType.simple(ResourceLocation.fromNamespaceAndPath(JapaneseFoodMod.MOD_ID, "fermentation"));

    protected final ResourceLocation id;
    protected NonNullList<Ingredient> ingredients;
    protected ItemStack result;
    protected int cookTime;

    public WoodenBucketRecipe(ResourceLocation idIn, NonNullList<Ingredient> ingredients, ItemStack result, int cookTime){
        this.id = idIn;
        this.ingredients = ingredients;
        this.result = result;
        this.cookTime = cookTime;
    }

    @Override
    public boolean matches(@Nonnull RecipeInput input, @Nonnull Level level){
        List<ItemStack> inputs = Lists.newArrayList();
        for(int i = 0; i < input.size(); ++i){
            ItemStack stack = input.getItem(i);
            if(!stack.isEmpty()){
                inputs.add(stack);
            }
        }
        return RecipeMatcher.findMatches(inputs, this.ingredients) != null;
    }

    @Override
    @Nonnull
    public ItemStack assemble(@Nonnull RecipeInput input, @Nonnull HolderLookup.Provider registries) {
        return this.result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height){
        return true;
    }

    @Nonnull
    @Override
    public NonNullList<Ingredient> getIngredients(){
        return this.ingredients;
    }

    @Nonnull
    @Override
    public ItemStack getResultItem(@Nonnull HolderLookup.Provider registries){
        return this.result;
    }

    @Nonnull
    public ResourceLocation getId(){
        return id;
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

    public static class Serializer implements RecipeSerializer<WoodenBucketRecipe> {

        @Override
        @Nonnull
        public MapCodec<WoodenBucketRecipe> codec() {
            return RecordCodecBuilder.mapCodec(instance -> instance.group(
                    ResourceLocation.CODEC.fieldOf("id").forGetter(r -> r.id),
                    Ingredient.CODEC.listOf().fieldOf("ingredients").xmap(
                            list -> {
                                NonNullList<Ingredient> nonnull = NonNullList.create();
                                nonnull.addAll(list);
                                return nonnull;
                            },
                            list -> list
                    ).forGetter(r -> r.ingredients),
                    ItemStack.CODEC.fieldOf("result").forGetter(r -> r.result),
                    Codec.INT.fieldOf("process_time").forGetter(r -> r.cookTime)
            ).apply(instance, WoodenBucketRecipe::new));
        }

        @Override
        @Nonnull
        public StreamCodec<RegistryFriendlyByteBuf, WoodenBucketRecipe> streamCodec() {
            return StreamCodec.of(
                    (buf, recipe) -> {
                        buf.writeResourceLocation(recipe.id);
                        buf.writeVarInt(recipe.ingredients.size());
                        for (Ingredient ingredient : recipe.ingredients) {
                            Ingredient.CONTENTS_STREAM_CODEC.encode(buf, ingredient);
                        }
                        ItemStack.STREAM_CODEC.encode(buf, recipe.result);
                        buf.writeVarInt(recipe.cookTime);
                    },
                    buf -> {
                        ResourceLocation id = buf.readResourceLocation();
                        int size = buf.readVarInt();
                        NonNullList<Ingredient> ingredients = NonNullList.withSize(size, Ingredient.EMPTY);
                        for(int j = 0; j < size; ++j) {
                            ingredients.set(j, Ingredient.CONTENTS_STREAM_CODEC.decode(buf));
                        }
                        ItemStack result = ItemStack.STREAM_CODEC.decode(buf);
                        int cookTime = buf.readVarInt();
                        return new WoodenBucketRecipe(id, ingredients, result, cookTime);
                    }
            );
        }
    }
}
