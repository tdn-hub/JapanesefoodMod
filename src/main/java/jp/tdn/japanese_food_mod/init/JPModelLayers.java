package jp.tdn.japanese_food_mod.init;

import jp.tdn.japanese_food_mod.JapaneseFoodMod;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class JPModelLayers {
    public static final ModelLayerLocation EEL =
            new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(JapaneseFoodMod.MOD_ID, "eel"), "main");

    public static final ModelLayerLocation CRAB =
            new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(JapaneseFoodMod.MOD_ID, "crab"), "main");

    public static final ModelLayerLocation TUNA =
            new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(JapaneseFoodMod.MOD_ID, "tuna"), "main");

    public static final ModelLayerLocation CLAM =
            new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(JapaneseFoodMod.MOD_ID, "clam"), "main");

    public static final ModelLayerLocation ASARI_CLAM =
            new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(JapaneseFoodMod.MOD_ID, "asari_clam"), "main");

    public static final ModelLayerLocation TURBAN_SHELL =
            new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(JapaneseFoodMod.MOD_ID, "turban_shell"), "main");

    public static final ModelLayerLocation ANGLERFISH =
            new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(JapaneseFoodMod.MOD_ID, "anglerfish"), "main");
}
