package jp.tdn.japanese_food_mod.client.patchouli.processor;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;
//import vazkii.patchouli.api.IComponentProcessor;
//import vazkii.patchouli.api.IVariable;
//import vazkii.patchouli.api.IVariableProvider;
//import vazkii.patchouli.api.PatchouliAPI;

public abstract class MicroScopeRecipeProcessor{// implements IComponentProcessor {
//    private IRecipe<?> recipe;
//
//    @Override
//    public void setup(IVariableProvider variables) {
//        IVariable recipeId = variables.get("recipe");
//        RecipeManager manager = Minecraft.getInstance().world.getRecipeManager();
//        recipe = manager.getRecipe(new ResourceLocation(recipeId.asString())).orElseThrow(IllegalArgumentException::new);
//    }
//
//    @Override
//    public IVariable process(String key) {
//        if(recipe == null){
//            return null;
//        }else if(key.equals("heading")){
//            return I18n.format("jpfood.page.identify.title", recipe.getRecipeOutput().getTranslationKey());
//        }else if(key.equals("output")) {
//            return PatchouliAPI.instance.serializeItemStack(recipe.getRecipeOutput());
//        }else if(key.startsWith("input")) {
//            return PatchouliAPI.instance.serializeIngredient(recipe.getIngredients().get(0));
//        }
//        return null;
//    }
}
