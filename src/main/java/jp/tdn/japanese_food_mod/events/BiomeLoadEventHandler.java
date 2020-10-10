package jp.tdn.japanese_food_mod.events;

import jp.tdn.japanese_food_mod.JapaneseFoodMod;
import jp.tdn.japanese_food_mod.init.JPBlocks;
import jp.tdn.japanese_food_mod.world.gen.feature.JPFeatures;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = JapaneseFoodMod.MOD_ID)
public class BiomeLoadEventHandler {
    public void onBiomeLoadEvent(BiomeLoadingEvent event){
        event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).add(JPFeatures.OYSTER::new);

        ResourceLocation name = event.getName();
        if(name.equals(new ResourceLocation("minecraft", "ocean"))){

        }
    }
}
