package jp.tdn.japanese_food_mod.init;

import com.google.common.base.Preconditions;
import jp.tdn.japanese_food_mod.JapaneseFoodMod;
import jp.tdn.japanese_food_mod.recipes.MicroScopeRecipe;
import jp.tdn.japanese_food_mod.recipes.PresserRecipe;
import jp.tdn.japanese_food_mod.recipes.WoodenBucketRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
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
                setup(new MicroScopeRecipe.Serializer(), "identifying"),
                setup(new WoodenBucketRecipe.Serializer(), "fermentation"),
                setup(new PresserRecipe.Serializer(), "pressing")
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
