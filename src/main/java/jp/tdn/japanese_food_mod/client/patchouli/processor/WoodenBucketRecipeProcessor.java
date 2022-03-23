//package jp.tdn.japanese_food_mod.client.patchouli.processor;
//
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.resources.I18n;
//import net.minecraft.item.crafting.IRecipe;
//import net.minecraft.item.crafting.RecipeManager;
//import net.minecraft.util.ResourceLocation;
//import vazkii.patchouli.api.IComponentProcessor;
//import vazkii.patchouli.api.IVariable;
//import vazkii.patchouli.api.IVariableProvider;
//
//import java.util.Arrays;
//import java.util.stream.Collectors;
//
//public class WoodenBucketRecipeProcessor implements IComponentProcessor {
//    private IRecipe<?> recipe;
//
//    @Override
//    public void setup(IVariableProvider variables) {
//        String recipeId = variables.get("recipe").asString();
//        RecipeManager manager = Minecraft.getInstance().world.getRecipeManager();
//        recipe = manager.getRecipe(new ResourceLocation(recipeId)).orElseThrow(IllegalArgumentException::new);
//    }
//
//    @Override
//    public IVariable process(String key) {
//        if(recipe == null){
//            return null;
//        }else if(key.equals("heading")){
//            return IVariable.wrap(I18n.format("jpfood.page.ferment.title", recipe.getRecipeOutput().getTranslationKey()));
//        }else if(key.equals("output")) {
//            return IVariable.from(recipe.getRecipeOutput());
//        }else if(key.startsWith("input")){
//            int requestedIndex = Integer.parseInt(key.substring(5)) - 1;
//            int indexOffset = (6 - recipe.getIngredients().size()) / 2;
//            int index = requestedIndex - indexOffset;
//
//            if (index < recipe.getIngredients().size() && index >= 0) {
//                return IVariable.wrapList(Arrays.stream(recipe.getIngredients().get(index).getMatchingStacks()).map(IVariable::from).collect(Collectors.toList()));
//            } else {
//                return null;
//            }
//        }else if(key.equals("is_offset")){
//            return IVariable.wrap(recipe.getIngredients().size() % 2 == 0);
//        }
//        return null;
//    }
//}
