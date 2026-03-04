package jp.tdn.japanese_food_mod.client;

import jp.tdn.japanese_food_mod.JapaneseFoodMod;
import jp.tdn.japanese_food_mod.client.gui.FurnaceCauldronScreen;
import jp.tdn.japanese_food_mod.client.gui.MicroScopeScreen;
import jp.tdn.japanese_food_mod.client.gui.PresserScreen;
import jp.tdn.japanese_food_mod.client.gui.WoodenBucketScreen;
import jp.tdn.japanese_food_mod.client.renders.AnglerfishEntityRender;
import jp.tdn.japanese_food_mod.client.renders.AsariClamEntityRender;
import jp.tdn.japanese_food_mod.client.renders.ClamEntityRender;
import jp.tdn.japanese_food_mod.client.renders.CrabEntityRender;
import jp.tdn.japanese_food_mod.client.renders.EelEntityRender;
import jp.tdn.japanese_food_mod.client.renders.TunaEntityRender;
import jp.tdn.japanese_food_mod.client.renders.TurbanShellEntityRender;
import jp.tdn.japanese_food_mod.client.renders.tileentity.MicroScopeTileEntityRenderer;
import jp.tdn.japanese_food_mod.client.models.*;
import jp.tdn.japanese_food_mod.init.JPBlockEntities;
import jp.tdn.japanese_food_mod.init.JPEntities;
import jp.tdn.japanese_food_mod.init.JPMenuTypes;
import jp.tdn.japanese_food_mod.init.JPModelLayers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@EventBusSubscriber(modid = JapaneseFoodMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEventSubscriber {

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        // エンティティレンダラー登録
        event.registerEntityRenderer(JPEntities.EEL.get(), EelEntityRender::new);
        event.registerEntityRenderer(JPEntities.CRAB.get(), CrabEntityRender::new);
        event.registerEntityRenderer(JPEntities.TUNA.get(), TunaEntityRender::new);
        event.registerEntityRenderer(JPEntities.CLAM.get(), ClamEntityRender::new);
        event.registerEntityRenderer(JPEntities.ASARI_CLAM.get(), AsariClamEntityRender::new);
        event.registerEntityRenderer(JPEntities.TURBAN_SHELL.get(), TurbanShellEntityRender::new);
        event.registerEntityRenderer(JPEntities.ANGLERFISH.get(), AnglerfishEntityRender::new);
        JapaneseFoodMod.LOGGER.debug("Registered EntityRenderers");

        // BlockEntityレンダラー登録
        event.registerBlockEntityRenderer(JPBlockEntities.MICROSCOPE.get(), MicroScopeTileEntityRenderer::new);
        JapaneseFoodMod.LOGGER.debug("Registered BlockEntityRenderers");
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(JPModelLayers.EEL, EelEntityModel::createBodyLayer);
        event.registerLayerDefinition(JPModelLayers.CRAB, CrabEntityModel::createBodyLayer);
        event.registerLayerDefinition(JPModelLayers.TUNA, TunaEntityModel::createBodyLayer);
        event.registerLayerDefinition(JPModelLayers.CLAM, ClamEntityModel::createBodyLayer);
        event.registerLayerDefinition(JPModelLayers.ASARI_CLAM, AsariClamEntityModel::createBodyLayer);
        event.registerLayerDefinition(JPModelLayers.TURBAN_SHELL, TurbanShellEntityModel::createBodyLayer);
        event.registerLayerDefinition(JPModelLayers.ANGLERFISH, AnglerfishEntityModel::createBodyLayer);
        JapaneseFoodMod.LOGGER.debug("Registered LayerDefinitions");
    }

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(JPMenuTypes.MICROSCOPE.get(), MicroScopeScreen::new);
        event.register(JPMenuTypes.WOODEN_BUCKET.get(), WoodenBucketScreen::new);
        event.register(JPMenuTypes.PRESSER.get(), PresserScreen::new);
        event.register(JPMenuTypes.FURNACE_CAULDRON.get(), FurnaceCauldronScreen::new);
        JapaneseFoodMod.LOGGER.debug("Registered MenuScreens");
    }

}
