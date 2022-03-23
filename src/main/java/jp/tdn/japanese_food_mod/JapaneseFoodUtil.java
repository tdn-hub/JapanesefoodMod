package jp.tdn.japanese_food_mod;

import com.google.common.collect.Lists;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

public class JapaneseFoodUtil {
    public static final List<Direction> directions = Lists.newArrayList(Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST);
    public static final List<Rotation> rotations = Lists.newArrayList(Rotation.NONE, Rotation.CLOCKWISE_90, Rotation.CLOCKWISE_180, Rotation.COUNTERCLOCKWISE_90);
    public static Random rand = new Random();

    @Nonnull
    @SuppressWarnings("ConstantConditions")
    public static <T>  T _null(){
        return null;
    }

    public static Biome getBiome(String name){
        return ForgeRegistries.BIOMES.getValue(new ResourceLocation("minecraft", name));
    }
}
