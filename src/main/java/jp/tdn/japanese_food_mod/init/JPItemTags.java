package jp.tdn.japanese_food_mod.init;

import jp.tdn.japanese_food_mod.JapaneseFoodMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class JPItemTags {
    public static final TagKey<Item> SALT = TagKey.create(Registries.ITEM,
            ResourceLocation.fromNamespaceAndPath("neoforge", "dusts/salt"));
    public static final TagKey<Item> WATER = TagKey.create(Registries.ITEM,
            ResourceLocation.fromNamespaceAndPath(JapaneseFoodMod.MOD_ID, "water"));
}
