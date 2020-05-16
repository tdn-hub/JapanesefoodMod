package jp.tdn.japanese_food_mod.client.renders;

import jp.tdn.japanese_food_mod.blocks.tileentity.MicroScopeTileEntity;
import jp.tdn.japanese_food_mod.client.renders.tileentity.MicroScopeTileEntityRenderer;
import jp.tdn.japanese_food_mod.entities.CrabEntity;
import jp.tdn.japanese_food_mod.entities.EelEntity;
import jp.tdn.japanese_food_mod.entities.TunaEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

@OnlyIn(Dist.CLIENT)
public class JPRenderRegistry {
    public static void registryEntityRenders(){
        RenderingRegistry.registerEntityRenderingHandler(EelEntity.class, EelEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(CrabEntity.class, CrabEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(TunaEntity.class, TunaEntityRender::new);
    }

    public static void registryTileEntityRenders(){
        ClientRegistry.bindTileEntitySpecialRenderer(MicroScopeTileEntity.class, new MicroScopeTileEntityRenderer());
    }
}
