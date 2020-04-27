package jp.tdn.japanese_food_mod.client;

import jp.tdn.japanese_food_mod.JapaneseFoodMod;
import jp.tdn.japanese_food_mod.client.gui.MicroScopeScreen;
import jp.tdn.japanese_food_mod.client.gui.PresserScreen;
import jp.tdn.japanese_food_mod.client.gui.WoodenBucketScreen;
import jp.tdn.japanese_food_mod.init.JPContainerTypes;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = JapaneseFoodMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientModEventSubscriber {
    @SubscribeEvent
    public static void onFMLClientSetupEvent(final FMLClientSetupEvent event){
        ScreenManager.registerFactory(JPContainerTypes.MICROSCOPE, MicroScopeScreen::new);
        ScreenManager.registerFactory(JPContainerTypes.WOODEN_BUCKET, WoodenBucketScreen::new);
        ScreenManager.registerFactory(JPContainerTypes.PRESSER, PresserScreen::new);
        JapaneseFoodMod.LOGGER.debug("Registered ContainerTypeScreens");
    }
}
