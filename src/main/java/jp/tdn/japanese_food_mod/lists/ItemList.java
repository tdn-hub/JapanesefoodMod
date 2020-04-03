package jp.tdn.japanese_food_mod.lists;

import jp.tdn.japanese_food_mod.JapaneseFoodItemGroup;
import jp.tdn.japanese_food_mod.JapaneseFoodMod;
import jp.tdn.japanese_food_mod.items.MisoSoupItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.system.CallbackI;

import java.util.Objects;

public class ItemList{
    public static final ItemGroup ItemGroup_Japanese = new JapaneseFoodItemGroup();
    public static final Item TYAWAN = new Item(new Item.Properties().group(ItemGroup_Japanese))
            .setRegistryName(new ResourceLocation(JapaneseFoodMod.MOD_ID, "tyawan"));

    public static final Item SOY = new BlockItem(BlockList.SOY, new Item.Properties().group((ItemGroup_Japanese)))
            .setRegistryName(JapaneseFoodMod.MOD_ID, "soy");

    public static final Item MISO_SOUP = new MisoSoupItem();

}