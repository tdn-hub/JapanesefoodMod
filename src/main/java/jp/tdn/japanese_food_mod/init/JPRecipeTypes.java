package jp.tdn.japanese_food_mod.init;

import jp.tdn.japanese_food_mod.JapaneseFoodMod;
import jp.tdn.japanese_food_mod.recipes.FermentationRecipe;
import jp.tdn.japanese_food_mod.recipes.IdentifiedRecipe;
import jp.tdn.japanese_food_mod.recipes.PresserRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class JPRecipeTypes {


    public static final IRecipeType<FermentationRecipe> FERMENTATION = registerType(new ResourceLocation(JapaneseFoodMod.MOD_ID, "fermentation"));
    public static final IRecipeType<IdentifiedRecipe> IDENTIFIED = registerType(new ResourceLocation(JapaneseFoodMod.MOD_ID, "identified"));
    public static final IRecipeType<PresserRecipe> PRESSER = registerType(new ResourceLocation(JapaneseFoodMod.MOD_ID, "presser"));

    private static <T extends IRecipe<?>> IRecipeType<T> registerType(ResourceLocation type){
        return IRecipeType.register(type.toString());
    }
}
