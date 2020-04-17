package jp.tdn.japanese_food_mod.init;

import com.google.common.collect.Lists;
import jp.tdn.japanese_food_mod.JapaneseFoodItemGroup;
import jp.tdn.japanese_food_mod.JapaneseFoodMod;
import jp.tdn.japanese_food_mod.items.CockWareItem;
import jp.tdn.japanese_food_mod.items.MisoSoupItem;
import jp.tdn.japanese_food_mod.items.SimpleItem;
import net.minecraft.item.*;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;
import java.util.List;

@Mod.EventBusSubscriber(modid = JapaneseFoodMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class JPItems {
    private static String modId = JapaneseFoodMod.MOD_ID;
    public static final ItemGroup ItemGroup_Japanese = new JapaneseFoodItemGroup();
    public static final List<Item> itemlist = Lists.newArrayList();

    // Misc
    public static  final Item TYAWAN = register(new SimpleItem(), "tyawan");
    public static  final Item POT = register(new CockWareItem(), "pot");
    public static  final Item SALT = register(new SimpleItem(), "salt");
    public static  final Item ROCK_SALT = register(new SimpleItem(), "rock_salt");

    // Food
    public static  final Item BOILED_SOY_BEANS = register(new Item(new Item.Properties().group(ItemGroup_Japanese).food(new Food.Builder().hunger(1).saturation(0.5f).build())), "boiled_soy_beans");
    public static  final Item MISO_SOUP = register(new MisoSoupItem(), "miso_soup");
    public static  final Item RICE = register(new Item(new Item.Properties().group(ItemGroup_Japanese).food(new Food.Builder().hunger(1).saturation(0.5f).build())), "rice");

    // Block Items
    public static  final Item SOY_BEANS = register(new BlockItem(JPBlocks.SOY_PLANT, new Item.Properties().group(ItemGroup_Japanese).food(new Food.Builder().hunger(1).saturation(0.5f).build())), "soy_beans");
    public static  final Item ROCK_SALT_BLOCK = register(new BlockItem(JPBlocks.ROCK_SALT_BLOCK, new Item.Properties().group(ItemGroup_Japanese)), "rock_salt_block");
    public static  final Item RICE_SEEDLING = register(new BlockItem(JPBlocks.RICE_PLANT, new Item.Properties().group(ItemGroup_Japanese)),"rice_seedling");

    public static Item register(@Nonnull Item item, @Nonnull String name){
        item.setRegistryName(modId, name);
        itemlist.add(item);
        return item;
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event){
        JapaneseFoodMod.LOGGER.info("Register items...");
        for(Item item : itemlist){
            JapaneseFoodMod.LOGGER.info(item.getRegistryName());
            event.getRegistry().register(item);
        }
    }
}