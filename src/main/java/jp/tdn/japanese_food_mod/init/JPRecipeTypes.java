package jp.tdn.japanese_food_mod.init;

import com.google.common.base.Preconditions;
import jp.tdn.japanese_food_mod.JapaneseFoodMod;
import jp.tdn.japanese_food_mod.recipes.MicroScopeRecipe;
import net.minecraft.client.gui.recipebook.RecipeList;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber(modid = JapaneseFoodMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class JPRecipeTypes {
    @SubscribeEvent
    public static void registryRecipes(RegistryEvent.Register<IRecipeSerializer<?>> event){
        event.getRegistry().registerAll(
                setup(MicroScopeRecipe.SERIALIZER, "microscope")
        );
    }

    @Nonnull
    private static <T extends IForgeRegistryEntry<T>> T setup(@Nonnull final T entry, @Nonnull final String name){
        Preconditions.checkNotNull(name, "Name to assign to entry cannot be null");
        return setup(entry, new ResourceLocation(JapaneseFoodMod.MOD_ID, name));
    }

    @Nonnull
    private static <T extends IForgeRegistryEntry<T>> T setup(@Nonnull final T entry, @Nonnull final ResourceLocation registryName) {
        Preconditions.checkNotNull(entry, "Entry cannot be null!");
        Preconditions.checkNotNull(registryName, "Registry name to assign to entry cannot be null!");
        entry.setRegistryName(registryName);
        return entry;
    }
}
