package jp.tdn.japanese_food_mod.init;

import jp.tdn.japanese_food_mod.JapaneseFoodMod;
import jp.tdn.japanese_food_mod.recipes.FermentationRecipe;
import jp.tdn.japanese_food_mod.recipes.IdentifiedRecipe;
import jp.tdn.japanese_food_mod.recipes.PresserRecipe;
import net.minecraft.block.Block;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class JPRecipeSerializers {
    public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, JapaneseFoodMod.MOD_ID);

    public static final RegistryObject<IRecipeSerializer<FermentationRecipe>> FERMENTATION = RECIPE_SERIALIZERS.register("fermentation", FermentationRecipe.Serializer::new);
    public static final RegistryObject<IRecipeSerializer<IdentifiedRecipe>> IDENTIFY = RECIPE_SERIALIZERS.register("identify", IdentifiedRecipe.Serializer::new);
    public static final RegistryObject<IRecipeSerializer<PresserRecipe>> PRESSER = RECIPE_SERIALIZERS.register("presser", PresserRecipe.Serializer::new);
}
