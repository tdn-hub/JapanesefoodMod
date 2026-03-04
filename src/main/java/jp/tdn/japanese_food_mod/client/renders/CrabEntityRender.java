package jp.tdn.japanese_food_mod.client.renders;

import jp.tdn.japanese_food_mod.JapaneseFoodMod;
import jp.tdn.japanese_food_mod.client.models.CrabEntityModel;
import jp.tdn.japanese_food_mod.entities.CrabEntity;
import jp.tdn.japanese_food_mod.init.JPModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CrabEntityRender extends MobRenderer<CrabEntity, CrabEntityModel<CrabEntity>> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(JapaneseFoodMod.MOD_ID, "textures/entity/crab.png");

    public CrabEntityRender(EntityRendererProvider.Context context) {
        super(context, new CrabEntityModel<>(context.bakeLayer(JPModelLayers.CRAB)), 0f);
    }

    @Override
    public ResourceLocation getTextureLocation(CrabEntity entity) {
        return TEXTURE;
    }
}
