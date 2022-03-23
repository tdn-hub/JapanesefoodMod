package jp.tdn.japanese_food_mod.world.gen.feature;

import jp.tdn.japanese_food_mod.JapaneseFoodMod;
import jp.tdn.japanese_food_mod.init.JPBlocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class JPFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, JapaneseFoodMod.MOD_ID);

    public static final RegistryObject<Feature<NoFeatureConfig>> OYSTER = register("oyster", () -> new OysterFeature(NoFeatureConfig.field_236558_a_.stable()));
    public static final RegistryObject<Feature<NoFeatureConfig>> WAKAME_BLOCK = register("wakame_block", () -> new WakameFeature(NoFeatureConfig.field_236558_a_.stable()));
    private static <C extends IFeatureConfig, F extends Feature<C>> RegistryObject<F> register(String key, Supplier<F> feature){
        return FEATURES.register(key, feature);
    }
}
