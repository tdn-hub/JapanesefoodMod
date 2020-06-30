package jp.tdn.japanese_food_mod.init;

import jp.tdn.japanese_food_mod.JapaneseFoodMod;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

public class JPItemTags {
    public static final ITag.INamedTag<Item> SALT = ItemTags.makeWrapperTag("forge:dusts/salt");
    public static final ITag.INamedTag<Item> WATER = ItemTags.makeWrapperTag("japanese_food_mod:water");
}
