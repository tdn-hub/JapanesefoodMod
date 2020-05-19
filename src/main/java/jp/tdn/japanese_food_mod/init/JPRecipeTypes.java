package jp.tdn.japanese_food_mod.init;

import jp.tdn.japanese_food_mod.JapaneseFoodMod;
import jp.tdn.japanese_food_mod.recipes.MicroScopeRecipe;
import jp.tdn.japanese_food_mod.recipes.PresserRecipe;
import jp.tdn.japanese_food_mod.recipes.WoodenBucketRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = JapaneseFoodMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class JPRecipeTypes {
    //public static final IRecipeSerializer<MicroScopeRecipe> MICRO_SCOPE_RECIPE = JapaneseFoodUtil._null();

    @SubscribeEvent
    public static void registryRecipes(RegistryEvent.Register<IRecipeSerializer<?>> event){
        event.getRegistry().registerAll(
                MicroScopeRecipe.SERIALIZER,
                WoodenBucketRecipe.SERIALIZER,
                PresserRecipe.SERIALIZER
        );
    }
}
