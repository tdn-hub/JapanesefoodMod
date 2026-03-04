package jp.tdn.japanese_food_mod.client.renders;

import jp.tdn.japanese_food_mod.JapaneseFoodMod;
import jp.tdn.japanese_food_mod.client.models.ClamEntityModel;
import jp.tdn.japanese_food_mod.entities.ClamEntity;
import jp.tdn.japanese_food_mod.init.JPModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ClamEntityRender extends MobRenderer<ClamEntity, ClamEntityModel<ClamEntity>> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(JapaneseFoodMod.MOD_ID, "textures/entity/clam.png");

    public ClamEntityRender(EntityRendererProvider.Context context) {
        super(context, new ClamEntityModel<>(context.bakeLayer(JPModelLayers.CLAM)), 0f);
    }

    @Override
    public ResourceLocation getTextureLocation(ClamEntity entity) {
        return TEXTURE;
    }
}
