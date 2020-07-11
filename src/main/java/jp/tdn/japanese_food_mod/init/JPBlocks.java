package jp.tdn.japanese_food_mod.init;

import com.google.common.collect.Lists;
import jp.tdn.japanese_food_mod.JapaneseFoodMod;
import jp.tdn.japanese_food_mod.blocks.*;
import net.minecraft.block.Block;
import net.minecraft.block.OreBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;
import java.util.List;

@Mod.EventBusSubscriber(modid = JapaneseFoodMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class JPBlocks {
    public static final List<Block> blocklist = Lists.newArrayList();

    public static final Block SOY_PLANT = register(new SoyPlantBlock(), "soy_plant");
    public static final Block ROCK_SALT_BLOCK = register(new Block(Block.Properties.create(Material.ROCK, MaterialColor.PINK).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2.5f)), "rock_salt_block");
    public static final Block RICE_PLANT = register(new RicePlantBlock(), "rice_plant");
    public static final Block WOODEN_BUCKET = register(new WoodenBucketBlock(), "wooden_bucket");
    public static final Block MICRO_SCOPE = register(new MicroScopeBlock(), "microscope");
    public static final Block PRESSER = register(new PresserBlock(), "presser");
    public static final Block UNREFINED_SOY_SAUCE = register(new UnrefinedSoySauceBlock(), "unrefined_soy_sauce");
    public static final Block RADISH_SPROUT_PLANT = register(new RadishSproutPlantBlock(), "radish_sprout_plant");
    public static final Block UNREFINED_SAKE = register(new UnrefinedSakeBlock(), "unrefined_sake");
    public static final Block CROP_GRASS = register(new CropGrassBlock(), "crop_grass");
    public static final Block LEEK_PLANT = register(new LeekPlantBlock(), "leek_plant");
    public static final Block OYSTER_SHELL = register(new OysterShellBlock(), "oyster_shell");
    public static final Block UNREFINED_MIRIN = register(new UnrefinedMirinBlock(), "unrefined_mirin");
    public static final Block TRONA_ORE = register(new OreBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.0F, 3.0F)), "trona_ore");
    public static final Block FURNACE_CAULDRON = register(new FurnaceCauldronBlock(), "furnace_cauldron");
    public static final Block SOY_HAY = register(new SoyHayBlock(), "soy_hay");
    public static final Block AZUKI_PLANT = register(new AzukiPlantBlock(), "azuki_plant");

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
