package jp.tdn.japanese_food_mod.init;

import jp.tdn.japanese_food_mod.JapaneseFoodMod;
import net.minecraft.item.Item;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

public class JPItemTags {
    public static final Tag<Item> SALT = new ItemTags.Wrapper(new ResourceLocation("forge", "dusts/salt"));
    public static final Tag<Item> WATER = new ItemTags.Wrapper(new ResourceLocation(JapaneseFoodMod.MOD_ID, "water"));
}
