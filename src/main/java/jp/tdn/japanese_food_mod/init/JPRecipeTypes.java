package jp.tdn.japanese_food_mod.init;

import jp.tdn.japanese_food_mod.JapaneseFoodMod;
import jp.tdn.japanese_food_mod.recipes.FurnaceCauldronRecipe;
import jp.tdn.japanese_food_mod.recipes.MicroScopeRecipe;
import jp.tdn.japanese_food_mod.recipes.PresserRecipe;
import jp.tdn.japanese_food_mod.recipes.WoodenBucketRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

public class JPRecipeTypes {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, JapaneseFoodMod.MOD_ID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<MicroScopeRecipe>> MICROSCOPE_SERIALIZER =
            RECIPE_SERIALIZERS.register("identifying", () -> MicroScopeRecipe.SERIALIZER);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<WoodenBucketRecipe>> WOODEN_BUCKET_SERIALIZER =
            RECIPE_SERIALIZERS.register("fermentation", () -> WoodenBucketRecipe.SERIALIZER);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<PresserRecipe>> PRESSER_SERIALIZER =
            RECIPE_SERIALIZERS.register("pressing", () -> PresserRecipe.SERIALIZER);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<FurnaceCauldronRecipe>> FURNACE_CAULDRON_SERIALIZER =
            RECIPE_SERIALIZERS.register("salt_making", () -> FurnaceCauldronRecipe.SERIALIZER);
}
