package jp.tdn.japanese_food_mod.client.renders;

import jp.tdn.japanese_food_mod.JapaneseFoodMod;
import jp.tdn.japanese_food_mod.client.models.AnglerfishEntityModel;
import jp.tdn.japanese_food_mod.entities.AnglerfishEntity;
import jp.tdn.japanese_food_mod.init.JPModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AnglerfishEntityRender extends MobRenderer<AnglerfishEntity, AnglerfishEntityModel<AnglerfishEntity>> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(JapaneseFoodMod.MOD_ID, "textures/entity/anglerfish.png");

    public AnglerfishEntityRender(EntityRendererProvider.Context context) {
        super(context, new AnglerfishEntityModel<>(context.bakeLayer(JPModelLayers.ANGLERFISH)), 0f);
    }

    @Override
    public ResourceLocation getTextureLocation(AnglerfishEntity entity) {
        return TEXTURE;
    }
}
