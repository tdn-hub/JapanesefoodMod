package jp.tdn.japanese_food_mod.init;

import com.google.common.collect.Lists;
import jp.tdn.japanese_food_mod.JapaneseFoodMod;
import jp.tdn.japanese_food_mod.blocks.RicePlantBlock;
import jp.tdn.japanese_food_mod.blocks.RockSaltBlock;
import jp.tdn.japanese_food_mod.blocks.SoyPlantBlock;
import jp.tdn.japanese_food_mod.blocks.WoodenBucketBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IEnviromentBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

@Mod.EventBusSubscriber(modid = JapaneseFoodMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class JPBlocks {
    public static final List<Block> blocklist = Lists.newArrayList();

    public static final Block SOY_PLANT = register(new SoyPlantBlock(), "soy_plant");
    public static final Block ROCK_SALT_BLOCK = register(new RockSaltBlock(), "rock_salt_block");
    public static final Block RICE_PLANT = register(new RicePlantBlock(), "rice_plant");
    public static final Block WOODEN_BUCKET = register(new WoodenBucketBlock(), "wooden_bucket");

    public static Block register(@Nonnull Block block, @Nonnull String name){
        block.setRegistryName(JapaneseFoodMod.MOD_ID, name);
        blocklist.add(block);
        return block;
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event){
        for(Block block : blocklist){
            event.getRegistry().register(block);
        }
    }
}
