package jp.tdn.japanese_food_mod.client.renders;

import jp.tdn.japanese_food_mod.JapaneseFoodMod;
import jp.tdn.japanese_food_mod.client.models.TurbanShellEntityModel;
import jp.tdn.japanese_food_mod.entities.TurbanShellEntity;
import jp.tdn.japanese_food_mod.init.JPModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TurbanShellEntityRender extends MobRenderer<TurbanShellEntity, TurbanShellEntityModel<TurbanShellEntity>> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(JapaneseFoodMod.MOD_ID, "textures/entities/turban_shell.png");

    public TurbanShellEntityRender(EntityRendererProvider.Context context) {
        super(context, new TurbanShellEntityModel<>(context.bakeLayer(JPModelLayers.TURBAN_SHELL)), 0f);
    }

    @Override
    public ResourceLocation getTextureLocation(TurbanShellEntity entity) {
        return TEXTURE;
    }
}
