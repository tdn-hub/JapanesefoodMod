package jp.tdn.japanese_food_mod.lists;

import jp.tdn.japanese_food_mod.JapaneseFoodItemGroup;
import jp.tdn.japanese_food_mod.JapaneseFoodMod;
import jp.tdn.japanese_food_mod.items.BoiledSoyBeans;
import jp.tdn.japanese_food_mod.items.RiceItem;
import jp.tdn.japanese_food_mod.items.MisoSoupItem;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class ItemList{
    private static String modId = JapaneseFoodMod.MOD_ID;
    public static final ItemGroup ItemGroup_Japanese = new JapaneseFoodItemGroup();

    public static final Item TYAWAN = setItemProperties("tyawan");
    public static final Item POT = setItemProperties("pot");
    public static final Item SOY_BEANS = setBlockItemProperties(BlockList.SOY,"soy_beans");
    public static final Item SALT = setItemProperties("salt");
    public static final Item ROCK_SALT_BLOCK = setBlockItemProperties(BlockList.ROCK_SALT_BLOCK, "rock_salt_block");
    public static final Item ROCK_SALT = setItemProperties("rock_salt");
    public static final Item BOILED_SOY_BEANS = new BoiledSoyBeans();
    public static final Item MISO_SOUP = new MisoSoupItem();
    public static final Item RICE_SEEDLING = setBlockItemProperties(BlockList.RICE_PLANT, "rice_seedling");
    public static final Item RICE = new RiceItem();

    // For non advanced item
    private static Item setItemProperties(String name){
        return new Item(new Item.Properties().group(ItemGroup_Japanese)).setRegistryName(modId, name);
    }

    private static Item setBlockItemProperties(Block block, String name){
        return new BlockItem(block, new Item.Properties().group(ItemGroup_Japanese)).setRegistryName(modId, name);
    }
}