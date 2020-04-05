package jp.tdn.japanese_food_mod.lists;

import jp.tdn.japanese_food_mod.JapaneseFoodItemGroup;
import jp.tdn.japanese_food_mod.JapaneseFoodMod;
import jp.tdn.japanese_food_mod.items.MisoSoupItem;
import net.minecraft.block.Block;
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

    public static final Item TYAWAN = setItemProperties("tyawan");
    public static final Item POT = setItemProperties("pot");
    public static final Item SOY_SEEDS = setBlockItemProperties(BlockList.SOY,"soy_seeds");
    public static final Item SALT = setItemProperties("salt");
    public static final Item ROCK_SALT_BLOCK = setBlockItemProperties(BlockList.ROCK_SALT_BLOCK, "rock_salt_block");
    public static final Item ROCK_SALT = setItemProperties("rock_salt");
    public static final Item MISO_SOUP = new MisoSoupItem();

    // For non advanced item
    private static Item setItemProperties(String name){
        return new Item(new Item.Properties().group(ItemGroup_Japanese)).setRegistryName(modId, name);
    }

    private static Item setBlockItemProperties(Block block, String name){
        return new BlockItem(block, new Item.Properties().group(ItemGroup_Japanese)).setRegistryName(modId, name);
    }
}