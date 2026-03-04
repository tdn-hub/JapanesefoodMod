package jp.tdn.japanese_food_mod.client.renders;

import jp.tdn.japanese_food_mod.JapaneseFoodMod;
import jp.tdn.japanese_food_mod.client.models.TunaEntityModel;
import jp.tdn.japanese_food_mod.entities.TunaEntity;
import jp.tdn.japanese_food_mod.init.JPModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TunaEntityRender extends MobRenderer<TunaEntity, TunaEntityModel<TunaEntity>> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(JapaneseFoodMod.MOD_ID, "textures/entity/tuna.png");

    public TunaEntityRender(EntityRendererProvider.Context context) {
        super(context, new TunaEntityModel<>(context.bakeLayer(JPModelLayers.TUNA)), 0f);
    }

    @Override
    public ResourceLocation getTextureLocation(TunaEntity entity) {
        return TEXTURE;
    }
}
