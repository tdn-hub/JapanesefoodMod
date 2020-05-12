package jp.tdn.japanese_food_mod.client.renders;

import jp.tdn.japanese_food_mod.entities.CrabEntity;
import jp.tdn.japanese_food_mod.entities.EelEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

@OnlyIn(Dist.CLIENT)
public class JPRenderRegistry {
    public static void registryEntityRenders(){
        RenderingRegistry.registerEntityRenderingHandler(EelEntity.class, EelEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(CrabEntity.class, CrabEntityRender::new);
    }
}
