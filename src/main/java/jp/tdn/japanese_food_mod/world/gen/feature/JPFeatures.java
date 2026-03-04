package jp.tdn.japanese_food_mod.world.gen.feature;

import jp.tdn.japanese_food_mod.JapaneseFoodMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

public class JPFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES =
            DeferredRegister.create(Registries.FEATURE, JapaneseFoodMod.MOD_ID);

    public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> OYSTER =
            FEATURES.register("oyster", () -> new OysterFeature(NoneFeatureConfiguration.CODEC));

    public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> WAKAME =
            FEATURES.register("wakame", () -> new WakameFeature(NoneFeatureConfiguration.CODEC));
}
