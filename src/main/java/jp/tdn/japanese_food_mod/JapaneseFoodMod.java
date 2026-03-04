package jp.tdn.japanese_food_mod;

import jp.tdn.japanese_food_mod.config.Config;
import jp.tdn.japanese_food_mod.events.LootLoadEventHandler;
import jp.tdn.japanese_food_mod.init.*;
import jp.tdn.japanese_food_mod.world.gen.feature.JPFeatures;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import com.mojang.serialization.MapCodec;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;

@Mod(JapaneseFoodMod.MOD_ID)
public class JapaneseFoodMod {
    public static final String MOD_ID = "japanese_food_mod";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> LOOT_MODIFIERS =
            DeferredRegister.create(NeoForgeRegistries.GLOBAL_LOOT_MODIFIER_SERIALIZERS, MOD_ID);

    static {
        LOOT_MODIFIERS.register("inject_vanilla_loot", () -> LootLoadEventHandler.CODEC);
    }

    public JapaneseFoodMod(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::setup);

        JPBlocks.BLOCKS.register(modEventBus);
        JPItems.ITEMS.register(modEventBus);
        JPItems.CREATIVE_MODE_TABS.register(modEventBus);
        JPEntities.ENTITIES.register(modEventBus);
        JPBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        JPMenuTypes.MENU_TYPES.register(modEventBus);
        JPRecipeTypes.RECIPE_SERIALIZERS.register(modEventBus);
        JPFeatures.FEATURES.register(modEventBus);
        LOOT_MODIFIERS.register(modEventBus);

        Config.loadConfig(modContainer);
    }

    private void setup(final FMLCommonSetupEvent event) {
        LOGGER.info("JapaneseFoodMod setup");
    }
}
