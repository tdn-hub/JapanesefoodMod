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
    private static String modId = JapaneseFoodMod.MOD_ID;
    public static final ItemGroup ItemGroup_Japanese = new JapaneseFoodItemGroup();
    public static final Item TYAWAN = new Item(new Item.Properties().group(ItemGroup_Japanese))
            .setRegistryName(modId, "tyawan");

    public static final Item SOY_SEEDS = new BlockItem(BlockList.SOY, new Item.Properties().group((ItemGroup_Japanese)))
            .setRegistryName(modId, "soy_seeds");

    public static final Item SALT = new Item(new Item.Properties().group(ItemGroup_Japanese))
            .setRegistryName(modId, "salt");

    public static final Item ROCK_SALT_BLOCK = new BlockItem(BlockList.ROCK_SALT_BLOCK, new Item.Properties().group(ItemGroup_Japanese))
            .setRegistryName(BlockList.ROCK_SALT_BLOCK.getRegistryName());

    public static final Item ROCK_SALT = new Item(new Item.Properties().group(ItemGroup_Japanese))
            .setRegistryName(modId, "rock_salt");

    public static final Item MISO_SOUP = new MisoSoupItem();

}