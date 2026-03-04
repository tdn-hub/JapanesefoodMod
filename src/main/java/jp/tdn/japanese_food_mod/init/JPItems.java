package jp.tdn.japanese_food_mod.init;

import jp.tdn.japanese_food_mod.JapaneseFoodMod;
import jp.tdn.japanese_food_mod.items.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class JPItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, JapaneseFoodMod.MOD_ID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, JapaneseFoodMod.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> JP_FOOD_TAB = CREATIVE_MODE_TABS.register("japanese_food_tab", () ->
            CreativeModeTab.builder()
                    .icon(() -> new ItemStack(Items.COOKED_BEEF))
                    .title(Component.translatable("itemGroup.japanese_food_mod"))
                    .displayItems((params, output) ->
                            ITEMS.getEntries().forEach(item -> output.accept(item.get()))
                    )
                    .build()
    );

    // Tool
    public static final DeferredHolder<Item, Item> POT = register(CookWareItem::new, "pot");
    public static final DeferredHolder<Item, Item> GRATER = register(CookWareItem::new, "grater");
    public static final DeferredHolder<Item, Item> JAPANESE_KNIFE = register(CookWareItem::new, "japanese_knife");
    public static final DeferredHolder<Item, Item> ROLLING_PIN = register(CookWareItem::new, "rolling_pin");
    public static final DeferredHolder<Item, Item> PESTLE = register(CookWareItem::new, "pestle");

    // Misc
    public static final DeferredHolder<Item, Item> TYAWAN = register(SimpleItem::new, "tyawan");
    public static final DeferredHolder<Item, Item> JAPANESE_BOWL = register(SimpleItem::new, "japanese_bowl");
    public static final DeferredHolder<Item, Item> CLAY_POT_BEFORE_HEATING = register(SimpleItem::new, "clay_pot_before_heating");
    public static final DeferredHolder<Item, Item> CLAY_POT = register(SimpleItem::new, "clay_pot");
    public static final DeferredHolder<Item, Item> CUP = register(CupItem::new, "cup");
    public static final DeferredHolder<Item, Item> CUP_WITH_WATER = register(() -> new DrinkItem(
            new Item.Properties().craftRemainder(CUP.get())
                    .food(new FoodProperties.Builder().nutrition(0).saturationModifier(0f).build())), "cup_with_water");
    public static final DeferredHolder<Item, Item> CUP_WITH_MILK = register(() -> new DrinkItem(
            new Item.Properties().craftRemainder(CUP.get())
                    .food(new FoodProperties.Builder().nutrition(0).saturationModifier(0f).build())), "cup_with_milk");
    public static final DeferredHolder<Item, Item> BREAD_CRUMBS = register(SimpleItem::new, "bread_crumbs");
    public static final DeferredHolder<Item, Item> WHEAT_FLOUR = register(SimpleItem::new, "wheat_flour");
    public static final DeferredHolder<Item, Item> POTATO_STARCH = register(SimpleItem::new, "potato_starch");
    public static final DeferredHolder<Item, Item> RICE_FLOUR = register(SimpleItem::new, "rice_flour");
    public static final DeferredHolder<Item, Item> SALT = register(SimpleItem::new, "salt");
    public static final DeferredHolder<Item, Item> ROCK_SALT = register(SimpleItem::new, "rock_salt");
    public static final DeferredHolder<Item, Item> BAKING_SODA = register(SimpleItem::new, "baking_soda");
    public static final DeferredHolder<Item, Item> COOKING_OIL = register(SimpleItem::new, "cooking_oil");
    public static final DeferredHolder<Item, Item> MISO = register(() -> new Item(
            new Item.Properties().food(new FoodProperties.Builder().nutrition(1).saturationModifier(0.5f).build())), "miso");
    public static final DeferredHolder<Item, Item> WAKAME = register(() -> new Item(
            new Item.Properties().food(new FoodProperties.Builder().nutrition(1).saturationModifier(0.5f).fast().build())), "wakame");
    public static final DeferredHolder<Item, Item> SOY_SAUCE = register(() -> new DrinkItem(
            new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.0f)
                    .effect(() -> new MobEffectInstance(MobEffects.POISON, 100, 2), 1.0f)
                    .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 100, 1), 1.0f)
                    .build())), "soy_sauce");
    public static final DeferredHolder<Item, Item> VINEGAR = register(() -> new DrinkItem(1, 0f), "vinegar");
    public static final DeferredHolder<Item, Item> MIRIN = register(() -> new DrinkItem(1, 0f), "mirin");
    public static final DeferredHolder<Item, Item> BATTER = register(SimpleItem::new, "batter");
    public static final DeferredHolder<Item, Item> TARE_SAUCE = register(SimpleItem::new, "tare_sauce");
    public static final DeferredHolder<Item, Item> MISO_SAUCE = register(SimpleItem::new, "miso_sauce");
    public static final DeferredHolder<Item, Item> BROTH = register(SimpleItem::new, "broth");
    public static final DeferredHolder<Item, Item> YEAST_CELL = register(() -> new ContainerItem(Items.GLASS_BOTTLE), "yeast_cell");
    public static final DeferredHolder<Item, Item> ACETIC_ACID_BACTERIA = register(() -> new ContainerItem(Items.GLASS_BOTTLE), "acetic_acid_bacteria");
    public static final DeferredHolder<Item, Item> PORK_BONE = register(SimpleItem::new, "pork_bone");
    public static final DeferredHolder<Item, Item> BITTERN = register(SimpleItem::new, "bittern");
    public static final DeferredHolder<Item, Item> SOY_MILK = register(() -> new DrinkItem(
            new Item.Properties().craftRemainder(Items.GLASS_BOTTLE)
                    .food(new FoodProperties.Builder().nutrition(3).saturationModifier(0.5f).build())), "soy_milk");

    // Seed
    public static final DeferredHolder<Item, Item> SOY_BEANS = register(() -> new BlockItem(
            JPBlocks.SOY_PLANT.get(),
            new Item.Properties().food(new FoodProperties.Builder().nutrition(1).saturationModifier(0.5f).build())), "soy_beans");
    public static final DeferredHolder<Item, Item> AZUKI_BEANS = register(() -> new BlockItem(
            JPBlocks.AZUKI_PLANT.get(), new Item.Properties()), "azuki_beans");
    public static final DeferredHolder<Item, Item> RICE_SEEDLING = register(() -> new RiceSeedlingItem(
            JPBlocks.RICE_PLANT.get(), new Item.Properties()), "rice_seedling");
    public static final DeferredHolder<Item, Item> RADISH_SPROUT_SEED = register(() -> new BlockItem(
            JPBlocks.RADISH_SPROUT_PLANT.get(), new Item.Properties()), "radish_sprout_seed");
    public static final DeferredHolder<Item, Item> LEEK_SEED = register(() -> new BlockItem(
            JPBlocks.LEEK_PLANT.get(), new Item.Properties()), "leek_seed");

    // Food
    public static final DeferredHolder<Item, Item> EDAMAME = register(() -> new FoodItem(1, 0f), "edamame");
    public static final DeferredHolder<Item, Item> RADISH_SPROUT = register(() -> new FoodItem(1, 0.5f), "radish_sprout");
    public static final DeferredHolder<Item, Item> RICE = register(() -> new Item(
            new Item.Properties().food(new FoodProperties.Builder().nutrition(1).saturationModifier(0.5f).build())), "rice");
    public static final DeferredHolder<Item, Item> LEEK = register(() -> new FoodItem(1, 0.5f), "leek");
    public static final DeferredHolder<Item, Item> BOILED_SOY_BEANS = register(() -> new Item(
            new Item.Properties().food(new FoodProperties.Builder().nutrition(1).saturationModifier(0.5f).build())), "boiled_soy_beans");
    public static final DeferredHolder<Item, Item> BOILED_AZUKI_BEANS = register(() -> new FoodItem(1, 0.5f), "boiled_azuki_beans");
    public static final DeferredHolder<Item, Item> BOILED_WAKAME = register(() -> new FoodItem(2, 1.0f), "boiled_wakame");
    public static final DeferredHolder<Item, Item> MISO_SOUP = register(() -> new MisoSoupItem(3, 2.5f), "miso_soup");
    public static final DeferredHolder<Item, Item> OMELET = register(() -> new Item(
            new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationModifier(2.5f).build())), "omelet");
    public static final DeferredHolder<Item, Item> ONIGIRI = register(() -> new Item(
            new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationModifier(4.0f).build())), "onigiri");
    public static final DeferredHolder<Item, Item> SALMON_ONIGIRI = register(() -> new Item(
            new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationModifier(4.5f).build())), "salmon_onigiri");
    public static final DeferredHolder<Item, Item> NIKUJAGA = register(() -> new FoodItem(7, 2.5f), "nikujaga");
    public static final DeferredHolder<Item, Item> AMBERJACK_RADISHES = register(() -> new FoodItem(7, 2.5f), "amberjack_radishes");
    public static final DeferredHolder<Item, Item> PICKLED_RADISH = register(() -> new Item(
            new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(1.5f).build())), "pickled_radish");
    public static final DeferredHolder<Item, Item> FRIED_CHICKEN = register(() -> new Item(
            new Item.Properties().food(new FoodProperties.Builder().nutrition(5).saturationModifier(4.0f).build())), "fried_chicken");
    public static final DeferredHolder<Item, Item> SKEWERED_CHICKEN = register(() -> new Item(
            new Item.Properties().food(new FoodProperties.Builder().nutrition(1).saturationModifier(0.5f)
                    .effect(() -> new MobEffectInstance(MobEffects.HUNGER, 100, 1), 0.3f).build())), "skewered_chicken");
    public static final DeferredHolder<Item, Item> YAKITORI = register(() -> new FoodItem(3, 2.0f), "yakitori");
    public static final DeferredHolder<Item, Item> RAW_NEGIMA = register(() -> new Item(
            new Item.Properties().food(new FoodProperties.Builder().nutrition(1).saturationModifier(0.5f)
                    .effect(() -> new MobEffectInstance(MobEffects.HUNGER, 100, 1), 0.3f).build())), "raw_negima");
    public static final DeferredHolder<Item, Item> NEGIMA = register(() -> new FoodItem(3, 2.3f), "negima");
    public static final DeferredHolder<Item, Item> SQUID_RING = register(() -> new FoodItem(3, 2.0f), "squid_ring");
    public static final DeferredHolder<Item, Item> SURUME = register(() -> new FoodItem(3, 2.0f), "surume");
    public static final DeferredHolder<Item, Item> SAKE = register(() -> new SakeItem(2, 1.0f), "sake");
    public static final DeferredHolder<Item, Item> TEMPURA_SHRIMP = register(() -> new FoodItem(3, 2.5f), "tempura_shrimp");
    public static final DeferredHolder<Item, Item> FUGU_SASHIMI = register(() -> new FoodItem(4, 4.0f), "fugu_sashimi");
    public static final DeferredHolder<Item, Item> BOILED_EGG = register(() -> new FoodItem(3, 3.0f), "boiled_egg");
    public static final DeferredHolder<Item, Item> GRATED_DAIKON = register(() -> new FoodItem(3, 1.5f), "grated_daikon");
    public static final DeferredHolder<Item, Item> NORI = register(() -> new FoodItem(1, 0f), "nori");
    public static final DeferredHolder<Item, Item> VINEGARED_RICE = register(() -> new FoodItem(3, 2.5f), "vinegared_rice");
    public static final DeferredHolder<Item, Item> TUNA_SASHIMI = register(() -> new FoodItem(4, 3.5f), "tuna_sashimi");
    public static final DeferredHolder<Item, Item> TUNA_SUSHI = register(() -> new FoodItem(5, 3.5f), "tuna_sushi");
    public static final DeferredHolder<Item, Item> SALMON_SUSHI = register(() -> new FoodItem(5, 3.5f), "salmon_sushi");
    public static final DeferredHolder<Item, Item> SALMON_ROE = register(() -> new FoodItem(5, 2.5f), "salmon_roe");
    public static final DeferredHolder<Item, Item> SHRIMP_SUSHI = register(() -> new FoodItem(4, 2.0f), "shrimp_sushi");
    public static final DeferredHolder<Item, Item> SQUID_SUSHI = register(() -> new FoodItem(4, 2.0f), "squid_sushi");
    public static final DeferredHolder<Item, Item> SEA_BREAM_SUSHI = register(() -> new FoodItem(5, 2.0f), "sea_bream_sushi");
    public static final DeferredHolder<Item, Item> TONKATSU = register(() -> new FoodItem(4, 3.0f), "tonkatsu");
    public static final DeferredHolder<Item, Item> MISO_TONKATSU = register(() -> new FoodItem(5, 3.0f), "miso_tonkatsu");
    public static final DeferredHolder<Item, Item> GRILLED_TURBAN_SHELL = register(() -> new FoodItem(4, 1.5f), "grilled_turban_shell");
    public static final DeferredHolder<Item, Item> BAMBOO_SHOOT = register(() -> new FoodItem(2, 0.1f), "bamboo_shoot");
    public static final DeferredHolder<Item, Item> GLAZE_GRILLED_EEL = register(() -> new FoodItem(5, 2.5f), "glaze_grilled_eel");
    public static final DeferredHolder<Item, Item> EEL_RICE_BOX = register(() -> new FoodItem(7, 3.0f), "eel_rice_box");
    public static final DeferredHolder<Item, Item> FISH_PASTE = register(() -> new FoodItem(1, 0.5f), "fish_paste");
    public static final DeferredHolder<Item, Item> KAMABOKO = register(() -> new FoodItem(3, 1.0f), "kamaboko");
    public static final DeferredHolder<Item, Item> CHIKUWA = register(() -> new FoodItem(3, 1.0f), "chikuwa");
    public static final DeferredHolder<Item, Item> HANPEN = register(() -> new FoodItem(3, 1.0f), "hanpen");
    public static final DeferredHolder<Item, Item> TSUMIRE = register(() -> new FoodItem(2, 0.5f), "tsumire");
    public static final DeferredHolder<Item, Item> MENMA = register(() -> new FoodItem(2, 0.5f), "menma");
    public static final DeferredHolder<Item, Item> NOODLES = register(() -> new FoodItem(2, 0.5f), "noodles");
    public static final DeferredHolder<Item, Item> SOY_SAUCE_RAMEN_SOUP = register(() -> new DrinkItem(2, 0.5f), "soy_sauce_ramen_soup");
    public static final DeferredHolder<Item, Item> PORK_BONE_RAMEN_SOUP = register(() -> new DrinkItem(2, 0.5f), "pork_bone_ramen_soup");
    public static final DeferredHolder<Item, Item> SALT_RAMEN_SOUP = register(() -> new DrinkItem(2, 0.5f), "salt_ramen_soup");
    public static final DeferredHolder<Item, Item> MISO_RAMEN_SOUP = register(() -> new DrinkItem(2, 0.5f), "miso_ramen_soup");
    public static final DeferredHolder<Item, Item> SOY_SAUCE_RAMEN = register(() -> new DrinkItem(8, 5.0f), "soy_sauce_ramen");
    public static final DeferredHolder<Item, Item> PORK_BONE_RAMEN = register(() -> new DrinkItem(8, 5.0f), "pork_bone_ramen");
    public static final DeferredHolder<Item, Item> SALT_RAMEN = register(() -> new DrinkItem(8, 5.0f), "salt_ramen");
    public static final DeferredHolder<Item, Item> MISO_RAMEN = register(() -> new DrinkItem(8, 5.0f), "miso_ramen");
    public static final DeferredHolder<Item, Item> TOFU = register(() -> new FoodItem(4, 2.5f), "tofu");
    public static final DeferredHolder<Item, Item> NATTO = register(() -> new FoodItem(5, 3.0f), "natto");
    public static final DeferredHolder<Item, Item> FRIED_TOFU = register(() -> new FoodItem(2, 1.0f), "fried_tofu");
    public static final DeferredHolder<Item, Item> TSUBU_AN = register(() -> new FoodItem(3, 0.5f), "mashed_sweet_bean_paste");
    public static final DeferredHolder<Item, Item> IMAGAWA_YAKI = register(() -> new FoodItem(5, 1.0f), "imagawa_yaki");
    public static final DeferredHolder<Item, Item> RICE_CAKE = register(() -> new FoodItem(4, 2.5f), "rice_cake");
    public static final DeferredHolder<Item, Item> ROASTED_RICE_CAKE = register(() -> new FoodItem(6, 3.5f), "roasted_rice_cake");
    public static final DeferredHolder<Item, Item> MANJU = register(() -> new FoodItem(4, 1.5f), "manju");
    public static final DeferredHolder<Item, Item> OSHIRUKO = register(() -> new DrinkItem(7, 3.5f), "oshiruko");
    public static final DeferredHolder<Item, Item> RICE_CRACKER_DOUGH = register(SimpleItem::new, "rice_cracker_dough");
    public static final DeferredHolder<Item, Item> RICE_CRACKER = register(() -> new FoodItem(
            new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationModifier(0.5f).fast().build())), "rice_cracker");
    public static final DeferredHolder<Item, Item> SOY_SAUCE_RICE_CRACKER = register(() -> new FoodItem(
            new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationModifier(1.0f).fast().build())), "soy_sauce_rice_cracker");
    public static final DeferredHolder<Item, Item> JAPANESE_SHAVED_ICE = register(() -> new FoodItem(3, 0.2f), "japanese_shaved_ice");
    public static final DeferredHolder<Item, Item> SWEET_BEAN_BUN = register(() -> new FoodItem(6, 2.5f), "sweet_bean_bun");
    public static final DeferredHolder<Item, Item> STICKY_RICE_DUMPLING = register(() -> new FoodItem(2, 0.1f), "sticky_rice_dumpling");

    // Fish
    public static final DeferredHolder<Item, Item> SQUID = register(() -> new FoodItem(2, 1.5f), "squid");
    public static final DeferredHolder<Item, Item> SHRIMP = register(() -> new FoodItem(2, 1.5f), "shrimp");
    public static final DeferredHolder<Item, Item> JAPANESE_PUFFER_FISH = register(() -> new Item(
            new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(1.5f)
                    .effect(() -> new MobEffectInstance(MobEffects.POISON, 1000, 3), 1.0f).build())), "japanese_puffer_fish");
    public static final DeferredHolder<Item, Item> EEL = register(() -> new Item(
            new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(1.5f)
                    .effect(() -> new MobEffectInstance(MobEffects.POISON, 500, 2), 0.2f).build())), "eel");
    public static final DeferredHolder<Item, Item> CRAB = register(() -> new FoodItem(2, 1.5f), "crab");
    public static final DeferredHolder<Item, Item> TUNA = register(() -> new FoodItem(3, 2.0f), "tuna");
    public static final DeferredHolder<Item, Item> SARDINE = register(() -> new FoodItem(1, 0.5f), "sardine");
    public static final DeferredHolder<Item, Item> AMBERJACK = register(() -> new FoodItem(2, 1.5f), "amberjack");
    public static final DeferredHolder<Item, Item> ANGLERFISN = register(() -> new FoodItem(2, 1.0f), "anglerfish");
    public static final DeferredHolder<Item, Item> SEA_BREAM = register(() -> new FoodItem(2, 0.5f), "sea_bream");
    public static final DeferredHolder<Item, Item> MACKEREL = register(() -> new FoodItem(1, 0.5f), "mackerel");
    public static final DeferredHolder<Item, Item> EEL_BUCKET = register(() -> new MobBucketItem(
            JPEntities.EEL.get(), Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH,
            new Item.Properties().stacksTo(1)), "eel_bucket");
    public static final DeferredHolder<Item, Item> TUNA_BUCKET = register(() -> new MobBucketItem(
            JPEntities.TUNA.get(), Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH,
            new Item.Properties().stacksTo(1)), "tuna_bucket");
    public static final DeferredHolder<Item, Item> CLAM = register(() -> new FoodItem(1, 0.5f), "clam");
    public static final DeferredHolder<Item, Item> ASARI_CLAM = register(() -> new FoodItem(1, 0.1f), "asari_clam");
    public static final DeferredHolder<Item, Item> TURBAN_SHELL = register(() -> new FoodItem(2, 0.5f), "turban_shell");

    // Block Items
    public static final DeferredHolder<Item, Item> CROP_GRASS = register(() -> new BlockItem(
            JPBlocks.CROP_GRASS.get(), new Item.Properties()), "crop_grass");
    public static final DeferredHolder<Item, Item> WAKAME_BLOCK = register(() -> new BlockItem(
            JPBlocks.WAKAME_BLOCK.get(), new Item.Properties()), "wakame_block");
    public static final DeferredHolder<Item, Item> ROCK_SALT_BLOCK = register(() -> new BlockItem(
            JPBlocks.ROCK_SALT_BLOCK.get(), new Item.Properties()), "rock_salt_block");
    public static final DeferredHolder<Item, Item> TRONA_ORE = register(() -> new BlockItem(
            JPBlocks.TRONA_ORE.get(), new Item.Properties()), "trona_ore");
    public static final DeferredHolder<Item, Item> UNREFINED_SAKE = register(() -> new BlockItem(
            JPBlocks.UNREFINED_SAKE.get(), new Item.Properties()), "unrefined_sake");
    public static final DeferredHolder<Item, Item> UNREFINED_SOY_SAUCE = register(() -> new BlockItem(
            JPBlocks.UNREFINED_SOY_SAUCE.get(), new Item.Properties()), "unrefined_soy_sauce");
    public static final DeferredHolder<Item, Item> UNREFINED_MIRIN = register(() -> new BlockItem(
            JPBlocks.UNREFINED_MIRIN.get(), new Item.Properties()), "unrefined_mirin");
    public static final DeferredHolder<Item, Item> WOODEN_BUCKET = register(() -> new BlockItem(
            JPBlocks.WOODEN_BUCKET.get(), new Item.Properties()), "wooden_bucket");
    public static final DeferredHolder<Item, Item> MICROSCOPE = register(() -> new BlockItem(
            JPBlocks.MICRO_SCOPE.get(), new Item.Properties()), "microscope");
    public static final DeferredHolder<Item, Item> PRESSER = register(() -> new BlockItem(
            JPBlocks.PRESSER.get(), new Item.Properties()), "presser");
    public static final DeferredHolder<Item, Item> FURNACE_CAULDRON = register(() -> new BlockItem(
            JPBlocks.FURNACE_CAULDRON.get(), new Item.Properties()), "furnace_cauldron");
    public static final DeferredHolder<Item, Item> OYSTER_SHELL = register(() -> new BlockItem(
            JPBlocks.OYSTER_SHELL.get(), new Item.Properties()), "oyster_shell");
    public static final DeferredHolder<Item, Item> SOY_HAY = register(() -> new BlockItem(
            JPBlocks.SOY_HAY.get(), new Item.Properties()), "soy_hay");

    // Spawn Eggs
    public static final DeferredHolder<Item, Item> EEL_ENTITY_EGG = register(() ->
            new DeferredSpawnEggItem(JPEntities.EEL, 0x00c3ff, 0x694b3a, new Item.Properties()), "eel_spawn_egg");
    public static final DeferredHolder<Item, Item> CRAB_ENTITY_EGG = register(() ->
            new DeferredSpawnEggItem(JPEntities.CRAB, 0x00c3ff, 0xba2d20, new Item.Properties()), "crab_spawn_egg");
    public static final DeferredHolder<Item, Item> TUNA_ENTITY_EGG = register(() ->
            new DeferredSpawnEggItem(JPEntities.TUNA, 0x00c3ff, 0x0000ff, new Item.Properties()), "tuna_spawn_egg");
    public static final DeferredHolder<Item, Item> CLAM_ENTITY_EGG = register(() ->
            new DeferredSpawnEggItem(JPEntities.CLAM, 0x00c3ff, 0x996136, new Item.Properties()), "clam_spawn_egg");
    public static final DeferredHolder<Item, Item> ASARI_CLAM_ENTITY_EGG = register(() ->
            new DeferredSpawnEggItem(JPEntities.ASARI_CLAM, 0x00c3ff, 0x827a51, new Item.Properties()), "asari_clam_spawn_egg");
    public static final DeferredHolder<Item, Item> TURBAN_SHELL_ENTITY_EGG = register(() ->
            new DeferredSpawnEggItem(JPEntities.TURBAN_SHELL, 0x00c3ff, 0xfca503, new Item.Properties()), "turban_shell_spawn_egg");
    public static final DeferredHolder<Item, Item> ANGLERFISH_ENTITY_EGG = register(() ->
            new DeferredSpawnEggItem(JPEntities.ANGLERFISH, 0x00c3ff, 0xfca000, new Item.Properties()), "anglerfish_spawn_egg");

    public static DeferredHolder<Item, Item> register(@Nonnull Supplier<Item> item, @Nonnull String name) {
        return ITEMS.register(name, item);
    }
}
