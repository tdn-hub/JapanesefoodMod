package jp.tdn.japanese_food_mod.client.patchouli.processor;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;
import vazkii.patchouli.api.IComponentProcessor;
import vazkii.patchouli.api.IVariableProvider;
import vazkii.patchouli.api.PatchouliAPI;

public class MicroScopeRecipeProcessor implements IComponentProcessor {
    private IRecipe<?> recipe;

    @Override
    public void setup(IVariableProvider<String> variables) {
        String recipeId = variables.get("recipe");
        RecipeManager manager = Minecraft.getInstance().world.getRecipeManager();
        recipe = manager.getRecipe(new ResourceLocation(recipeId)).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public String process(String key) {
        if(recipe == null){
            return null;
        }else if(key.equals("heading")){
            return I18n.format("jpfood.page.identify.title", recipe.getRecipeOutput().getDisplayName());
        }else if(key.equals("output")) {
            return PatchouliAPI.instance.serializeItemStack(recipe.getRecipeOutput());
        }else if(key.startsWith("input")) {
            return PatchouliAPI.instance.serializeIngredient(recipe.getIngredients().get(0));
        }
        return null;
    }
}
