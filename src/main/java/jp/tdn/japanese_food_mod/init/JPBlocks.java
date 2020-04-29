package jp.tdn.japanese_food_mod.init;

import com.google.common.collect.Lists;
import jp.tdn.japanese_food_mod.JapaneseFoodMod;
import jp.tdn.japanese_food_mod.blocks.*;
import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;
import java.util.List;

@Mod.EventBusSubscriber(modid = JapaneseFoodMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class JPBlocks {
    public static final List<Block> blocklist = Lists.newArrayList();

    public static final Block SOY_PLANT = register(new SoyPlantBlock(), "soy_plant");
    public static final Block ROCK_SALT_BLOCK = register(new RockSaltBlock(), "rock_salt_block");
    public static final Block RICE_PLANT = register(new RicePlantBlock(), "rice_plant");
    public static final Block WOODEN_BUCKET = register(new WoodenBucketBlock(), "wooden_bucket");
    public static final Block MICRO_SCOPE = register(new MicroScopeBlock(), "microscope");
    public static final Block PRESSER = register(new PresserBlock(), "presser");
    public static final Block UNREFINED = register(new UnrefinedBlock(), "unrefined");

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
