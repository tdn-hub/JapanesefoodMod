package jp.tdn.japanese_food_mod.world.gen.feature;

import com.mojang.datafixers.Dynamic;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;
import java.util.function.Function;

public class CropGrassFeature extends Feature<NoFeatureConfig> {
    public CropGrassFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> deserializer){
        super(deserializer);
    }

    @Override
    public boolean place(IWorld iWorld, ChunkGenerator<? extends GenerationSettings> chunkGenerator, Random random, BlockPos blockPos, NoFeatureConfig noFeatureConfig) {

        return false;
    }
}
