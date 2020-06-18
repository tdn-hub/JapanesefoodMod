package jp.tdn.japanese_food_mod.init;

import com.google.common.collect.Lists;
import jdk.nashorn.internal.ir.Block;
import jp.tdn.japanese_food_mod.JapaneseFoodItemGroup;
import jp.tdn.japanese_food_mod.JapaneseFoodMod;
import jp.tdn.japanese_food_mod.items.*;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
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

    //Tool
    public static final Item POT = register(new CookWareItem(), "pot");
    public static final Item GRATER = register(new CookWareItem(), "grater");
    public static final Item JAPANESE_KNIFE = register(new CookWareItem(), "japanese_knife");
    public static final Item ROLLING_PIN = register(new CookWareItem(), "rolling_pin");

    // Misc
    public static final Item TYAWAN = register(new SimpleItem(), "tyawan");
    public static final Item JAPANESE_BOWL = register(new SimpleItem(), "japanese_bowl");
    public static final Item CLAY_POT_BEFORE_HEATING = register(new SimpleItem(), "clay_pot_before_heating");
    public static final Item CLAY_POT = register(new SimpleItem(), "clay_pot");
    public static final Item CUP = register(new CupItem(), "cup");
    public static final Item CUP_WITH_WATER = register(new DrinkItem(new Item.Properties().group(ItemGroup_Japanese).containerItem(CUP).food(new Food.Builder().saturation(0f).hunger(0).build())), "cup_with_water");
    public static final Item BREAD_CRUMBS = register(new SimpleItem(), "bread_crumbs");
    public static final Item WHEAT_FLOUR = register(new SimpleItem(), "wheat_flour");
    public static final Item POTATO_STARCH = register(new SimpleItem(), "potato_starch");
    public static final Item SALT = register(new SimpleItem(), "salt");
    public static final Item ROCK_SALT = register(new SimpleItem(), "rock_salt");
    public static final Item BAKING_SODA = register(new SimpleItem(), "baking_soda");
    public static final Item COOKING_OIL = register(new SimpleItem(), "cooking_oil");
    public static final Item MISO = register(new Item(new Item.Properties().group(ItemGroup_Japanese).food(new Food.Builder().hunger(1).saturation(0.5f).build())), "miso");
    public static final Item WAKAME = register(new Item(new Item.Properties().group(ItemGroup_Japanese).food(new Food.Builder().hunger(1).saturation(0.5f).fastToEat().build())), "wakame");
    public static final Item SOY_SAUCE = register(new DrinkItem(new Item.Properties().group(ItemGroup_Japanese).food(new Food.Builder().hunger(2).saturation(0.0f).effect(new EffectInstance(Effects.POISON, 100, 2), 1.0f).effect(new EffectInstance(Effects.NAUSEA, 100, 1), 1.0f).build())), "soy_sauce");
    public static final Item VINEGAR = register(new DrinkItem(1, 0f), "vinegar");
    public static final Item MIRIN = register(new DrinkItem(1, 0f), "mirin");
    public static final Item BATTER = register(new SimpleItem(), "batter");
    public static final Item TARE_SAUCE = register(new SimpleItem(), "tare_sauce");
    public static final Item MISO_SAUCE = register(new SimpleItem(), "miso_sauce");
    public static final Item BROTH = register(new SimpleItem(), "broth");
    public static final Item YEAST_CELL = register(new YeastCellItem(Items.GLASS_BOTTLE), "yeast_cell");
    public static final Item ACETIC_ACID_BACTERIA = register(new YeastCellItem(Items.GLASS_BOTTLE), "acetic_acid_bacteria");
    public static final Item PORK_BONE = register(new SimpleItem(), "pork_bone");

    //Seed
    public static final Item SOY_BEANS = register(new BlockItem(JPBlocks.SOY_PLANT, new Item.Properties().group(ItemGroup_Japanese).food(new Food.Builder().hunger(1).saturation(0.5f).build())), "soy_beans");
    public static final Item RICE_SEEDLING = register(new RiceSeedlingItem(JPBlocks.RICE_PLANT, new Item.Properties().group(ItemGroup_Japanese)),"rice_seedling");
    public static final Item RADISH_SPROUT_SEED = register(new BlockItem(JPBlocks.RADISH_SPROUT_PLANT, new Item.Properties().group(ItemGroup_Japanese)), "radish_sprout_seed");
    public static final Item LEEK_SEED = register(new BlockItem(JPBlocks.LEEK_PLANT, new Item.Properties().group(ItemGroup_Japanese)),"leek_seed");

    // Food
    public static final Item EDAMAME = register(new FoodItem(1, 0f), "edamame");
    public static final Item RADISH_SPROUT = register(new FoodItem(1, 0.5f), "radish_sprout");
    public static final Item RICE = register(new Item(new Item.Properties().group(ItemGroup_Japanese).food(new Food.Builder().hunger(1).saturation(0.5f).build())), "rice");
    public static final Item LEEK = register(new FoodItem(1, 0.5f), "leek");
    public static final Item BOILED_SOY_BEANS = register(new Item(new Item.Properties().group(ItemGroup_Japanese).food(new Food.Builder().hunger(1).saturation(0.5f).build())), "boiled_soy_beans");
    public static final Item BOILED_WAKAME = register(new FoodItem(2, 1.0f), "boiled_wakame");
    public static final Item MISO_SOUP = register(new MisoSoupItem(3, 2.5f), "miso_soup");
    public static final Item OMELET = register(new Item(new Item.Properties().group(ItemGroup_Japanese).food(new Food.Builder().hunger(4).saturation(2.5f).build())), "omelet");
    public static final Item ONIGIRI = register(new Item(new Item.Properties().group(ItemGroup_Japanese).food(new Food.Builder().hunger(3).saturation(4.0f).build())), "onigiri");
    public static final Item SALMON_ONIGIRI  = register(new Item(new Item.Properties().group(ItemGroup_Japanese).food(new Food.Builder().hunger(4).saturation(4.5f).build())), "salmon_onigiri");
    public static final Item NIKUJAGA = register(new FoodItem(7, 2.5f), "nikujaga");
    public static final Item AMBERJACK_RADISHES = register(new FoodItem(7, 2.5f), "amberjack_radishes");
    public static final Item PICKLED_RADISH = register(new Item(new Item.Properties().group(ItemGroup_Japanese).food(new Food.Builder().hunger(2).saturation(1.5f).build())), "pickled_radish");
    public static final Item FRIED_CHICKEN = register(new Item(new Item.Properties().group(ItemGroup_Japanese).food(new Food.Builder().hunger(5).saturation(4.0f).build())), "fried_chicken");
    public static final Item SKEWERED_CHICKEN = register(new Item(new Item.Properties().group(ItemGroup_Japanese).food(new Food.Builder().hunger(1).saturation(0.5f).effect(new EffectInstance(Effects.HUNGER, 100, 1), 0.3f).build())), "skewered_chicken");
    public static final Item YAKITORI = register(new FoodItem(3, 2.0f), "yakitori");
    public static final Item RAW_NEGIMA = register(new Item(new Item.Properties().group(ItemGroup_Japanese).food(new Food.Builder().hunger(1).saturation(0.5f).effect(new EffectInstance(Effects.HUNGER, 100, 1), 0.3f).build())), "raw_negima");
    public static final Item NEGIMA = register(new FoodItem(3, 2.3f), "negima");
    public static final Item SQUID_RING = register(new FoodItem(3, 2.0f), "squid_ring");
    public static final Item SURUME = register(new FoodItem(3, 2.0f), "surume");
    public static final Item SAKE = register(new SakeItem(2, 1.0f), "sake");
    public static final Item TEMPURA_SHRIMP = register(new FoodItem(3, 2.5f), "tempura_shrimp");
    public static final Item FUGU_SASHIMI = register(new FoodItem(4, 4.0f), "fugu_sashimi");
    public static final Item BOILED_EGG = register(new FoodItem(3, 3.0f), "boiled_egg");
    public static final Item GRATED_DAIKON = register(new FoodItem(3, 1.5f),"grated_daikon");
    public static final Item NORI = register(new FoodItem(1, 0f), "nori");
    public static final Item VINEGARED_RICE = register(new FoodItem(3, 2.5f), "vinegared_rice");
    public static final Item TUNA_SASHIMI = register(new FoodItem(4, 3.5f), "tuna_sashimi");
    public static final Item TUNA_SUSHI = register(new FoodItem(5, 3.5f), "tuna_sushi");
    public static final Item SALMON_SUSHI = register(new FoodItem(5, 3.5f), "salmon_sushi");
    public static final Item SALMON_ROE = register(new FoodItem(5, 2.5f), "salmon_roe");
    public static final Item SHRIMP_SUSHI = register(new FoodItem(4,2.0f), "shrimp_sushi");
    public static final Item SQUID_SUSHI = register(new FoodItem(4, 2.0f), "squid_sushi");
    public static final Item SEA_BREAM_SUSHI = register(new FoodItem(5, 2.0f), "sea_bream_sushi");
    public static final Item TONKATSU = register(new FoodItem(4, 3.0f), "tonkatsu");
    public static final Item MISO_TONKATSU = register(new FoodItem(5, 3.0f), "miso_tonkatsu");
    public static final Item GRILLED_TURBAN_SHELL = register(new FoodItem(4, 1.5f), "grilled_turban_shell");
    public static final Item BAMBOO_SHOOT = register(new FoodItem(2, 0.1f), "bamboo_shoot");
    public static final Item GLAZE_GRILLED_EEL = register(new FoodItem(5, 2.5f), "glaze_grilled_eel");
    public static final Item EEL_RICE_BOX = register(new FoodItem(7, 3.0f), "eel_rice_box");
    public static final Item FISH_PASTE = register(new FoodItem(1, 0.5f), "fish_paste");
    public static final Item KAMABOKO = register(new FoodItem(3, 1.0f), "kamaboko");
    public static final Item CHIKUWA = register(new FoodItem(3, 1.0f), "chikuwa");
    public static final Item HANPEN = register(new FoodItem(3, 1.0f), "hanpen");
    public static final Item TSUMIRE = register(new FoodItem(2, 0.5f), "tsumire");
    public static final Item NOODLES = register(new FoodItem(2, 0.5f), "noodles");
    public static final Item SOY_SAUCE_RAMEN_SOUP = register(new DrinkItem(2, 0.5f), "soy_sauce_ramen_soup");
    public static final Item PORK_BONE_RAMEN_SOUP = register(new DrinkItem(2, 0.5f), "pork_bone_ramen_soup");
    public static final Item SOY_SAUCE_RAMEN = register(new DrinkItem(8, 5.0f), "soy_sauce_ramen");
    public static final Item PORK_BONE_RAMEN = register(new DrinkItem(8, 5.0f), "pork_bone_ramen");

    // Fish
    public static final Item SQUID = register(new FoodItem(2, 1.5f), "squid");
    public static final Item SHRIMP = register(new FoodItem(2, 1.5f), "shrimp");
    public static final Item JAPANESE_PUFFER_FISH = register(new Item(new Item.Properties().group(ItemGroup_Japanese).food(new Food.Builder().hunger(2).saturation(1.5f).effect(new EffectInstance(Effects.POISON, 1000, 3), 1.0f).build())), "japanese_puffer_fish");
    public static final Item EEL = register(new Item(new Item.Properties().group(ItemGroup_Japanese).food(new Food.Builder().hunger(2).saturation(1.5f).effect(new EffectInstance(Effects.POISON, 500, 2), 0.2f).build())), "eel");
    public static final Item CRAB = register(new FoodItem(2, 1.5f), "crab");
    public static final Item TUNA = register(new FoodItem(3, 2.0f), "tuna");
    public static final Item SARDINE = register(new FoodItem(1, 0.5f), "sardine");
    public static final Item AMBERJACK = register(new FoodItem(2, 1.5f), "amberjack");
    public static final Item ANGLERFISN = register(new FoodItem(2, 1.0f), "anglerfish");
    public static final Item SEA_BREAM = register(new FoodItem(2, 0.5f), "sea_bream");
    public static final Item MACKEREL = register(new FoodItem(1, 0.5f), "mackerel");
    public static final Item EEL_BUCKET = register(new FishBucketItem(JPEntities.EEL, Fluids.WATER, new Item.Properties().maxStackSize(1).group(ItemGroup_Japanese)), "eel_bucket");
    public static final Item TUNA_BUCKET = register(new FishBucketItem(JPEntities.TUNA, Fluids.WATER, new Item.Properties().maxStackSize(1).group(ItemGroup_Japanese)), "tuna_bucket");
    public static final Item CLAM = register(new FoodItem(1, 0.5f), "clam");
    public static final Item ASARI_CLAM = register(new FoodItem(1, 0.1f), "asari_clam");
    public static final Item TURBAN_SHELL = register(new FoodItem(2, 0.5f), "turban_shell");

    // Block Items
    public static final Item ROCK_SALT_BLOCK = register(new BlockItem(JPBlocks.ROCK_SALT_BLOCK, new Item.Properties().group(ItemGroup_Japanese)), "rock_salt_block");
    public static final Item TRONA_ORE = register(new BlockItem(JPBlocks.TRONA_ORE, new Item.Properties().group(ItemGroup_Japanese)), "trona_ore");
    public static final Item UNREFINED_SAKE = register(new BlockItem(JPBlocks.UNREFINED_SAKE, new Item.Properties().group(ItemGroup_Japanese)), "unrefined_sake");
    public static final Item UNREFINED_SOY_SAUCE = register(new BlockItem(JPBlocks.UNREFINED_SOY_SAUCE, new Item.Properties().group(ItemGroup_Japanese)), "unrefined_soy_sauce");
    public static final Item UNREFINED_MIRIN = register(new BlockItem(JPBlocks.UNREFINED_MIRIN, new Item.Properties().group(ItemGroup_Japanese)), "unrefined_mirin");
    public static final Item WOODEN_BUCKET = register(new BlockItem(JPBlocks.WOODEN_BUCKET, new Item.Properties().group(ItemGroup_Japanese)), "wooden_bucket");
    public static final Item MICROSCOPE = register(new BlockItem(JPBlocks.MICRO_SCOPE, new Item.Properties().group(ItemGroup_Japanese)), "microscope");
    public static final Item PRESSER = register(new BlockItem(JPBlocks.PRESSER, new Item.Properties().group(ItemGroup_Japanese)), "presser");
    public static final Item OYSTER_SHELL = register(new BlockItem(JPBlocks.OYSTER_SHELL, new Item.Properties().group(ItemGroup_Japanese)), "oyster_shell");

    // Spawn Eggs
    public static final Item EEL_ENTITY_EGG = register(new SpawnEggItem(JPEntities.EEL, 0x00c3ff, 0x694b3a, new Item.Properties().group(ItemGroup_Japanese)), "eel_spawn_egg");
    public static final Item CRAB_ENTITY_EGG = register(new SpawnEggItem(JPEntities.CRAB, 0x00c3ff, 0xba2d20, new Item.Properties().group(ItemGroup_Japanese)), "crab_spawn_egg");
    public static final Item TUNA_ENTITY_EGG = register(new SpawnEggItem(JPEntities.TUNA, 0x00c3ff, 0x0000ff, new Item.Properties().group(ItemGroup_Japanese)), "tuna_spawn_egg");
    public static final Item CLAM_ENTITY_EGG = register(new SpawnEggItem(JPEntities.CLAM, 0x00c3ff, 0x996136, new Item.Properties().group(ItemGroup_Japanese)), "clam_spawn_egg");
    public static final Item ASARI_CLAM_ENTITY_EGG = register(new SpawnEggItem(JPEntities.ASARI_CLAM, 0x00c3ff, 0x827a51, new Item.Properties().group(ItemGroup_Japanese)), "asari_clam_spawn_egg");
    public static final Item TURBAN_SHELL_ENTITY_EGG = register(new SpawnEggItem(JPEntities.TURBAN_SHELL, 0x00c3ff, 0xfca503, new Item.Properties().group(ItemGroup_Japanese)), "turban_shell_spawn_egg");
    public static final Item ANGLERFISH_ENTITY_EGG = register(new SpawnEggItem(JPEntities.ANGLERFISH, 0x00c3ff, 0xfca000, new Item.Properties().group(ItemGroup_Japanese)), "anglerfish_spawn_egg");

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