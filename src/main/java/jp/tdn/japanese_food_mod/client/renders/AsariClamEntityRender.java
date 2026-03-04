package jp.tdn.japanese_food_mod.client.renders;

import jp.tdn.japanese_food_mod.JapaneseFoodMod;
import jp.tdn.japanese_food_mod.client.models.AsariClamEntityModel;
import jp.tdn.japanese_food_mod.entities.AsariClamEntity;
import jp.tdn.japanese_food_mod.init.JPModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AsariClamEntityRender extends MobRenderer<AsariClamEntity, AsariClamEntityModel<AsariClamEntity>> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(JapaneseFoodMod.MOD_ID, "textures/entities/asari_clam.png");

    public AsariClamEntityRender(EntityRendererProvider.Context context) {
        super(context, new AsariClamEntityModel<>(context.bakeLayer(JPModelLayers.ASARI_CLAM)), 0f);
    }

    @Override
    public ResourceLocation getTextureLocation(AsariClamEntity entity) {
        return TEXTURE;
    }
}
