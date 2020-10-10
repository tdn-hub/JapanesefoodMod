package jp.tdn.japanese_food_mod.world.gen.feature;

import jp.tdn.japanese_food_mod.JapaneseFoodMod;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.*;

import java.util.function.Supplier;

public class JPFeatures {
    public static final ConfiguredFeature<?, ?> OYSTER = register("oyster", OysterFeature.KELP.withConfiguration(Features.))

    private static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> register(String key, ConfiguredFeature<FC, ?> feature){
        return Registry.register(WorldGenRegistries.field_243653_e, new ResourceLocation(JapaneseFoodMod.MOD_ID, key), feature);
    }
}
