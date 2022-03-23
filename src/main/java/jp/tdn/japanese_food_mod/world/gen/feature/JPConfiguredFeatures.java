package jp.tdn.japanese_food_mod.world.gen.feature;

import jp.tdn.japanese_food_mod.JapaneseFoodMod;
import jp.tdn.japanese_food_mod.init.JPBlocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class JPConfiguredFeatures {
    public static ConfiguredFeature<?, ?> ROCK_SALT = Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241882_a, JPBlocks.ROCK_SALT_BLOCK.get().getDefaultState(), 8)).withPlacement(Placement.field_242907_l.configure(new TopSolidRangeConfig(20, 0, 63)).func_242728_a().func_242731_b(8));
    public static ConfiguredFeature<?, ?> TRONA_ORE = Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241882_a, JPBlocks.TRONA_ORE.get().getDefaultState(), 8)).withPlacement(Placement.field_242907_l.configure(new TopSolidRangeConfig(4, 0,  32)).func_242731_b(8));
    public static final ConfiguredFeature<?, ?> OYSTER = register("oyster", JPFeatures.OYSTER.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.TOP_SOLID_HEIGHTMAP.configure(new NoPlacementConfig())));
    public static final ConfiguredFeature<?, ?> WAKAME = register("wakame_block", JPFeatures.WAKAME_BLOCK.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.TOP_SOLID_HEIGHTMAP.configure(new NoPlacementConfig())));
    public static final ConfiguredFeature<?, ?> WEED = register("weed", Feature.RANDOM_PATCH.withConfiguration((new net.minecraft.world.gen.feature.BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(JPBlocks.CROP_GRASS.get().getDefaultState()), new SimpleBlockPlacer())).tries(32).build()));

    private static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> register(String key, ConfiguredFeature<FC, ?> feature)
    {
        return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation(JapaneseFoodMod.MOD_ID, key), feature);
    }
}
