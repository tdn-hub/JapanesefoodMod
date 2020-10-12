package jp.tdn.japanese_food_mod.init;

import com.google.common.collect.Lists;
import jp.tdn.japanese_food_mod.JapaneseFoodMod;
import jp.tdn.japanese_food_mod.blocks.*;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.Supplier;

public class JPBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, JapaneseFoodMod.MOD_ID);

    public static final RegistryObject<Block> SOY_PLANT = register(() -> new SoyPlantBlock(AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().sound(SoundType.CROP)), "soy_plant");
    public static final RegistryObject<Block> ROCK_SALT_BLOCK = register(() -> new RockSaltBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.PINK).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2.5f)), "rock_salt_block");
    public static final RegistryObject<Block> RICE_PLANT = register(() -> new RicePlantBlock(), "rice_plant");
    public static final RegistryObject<Block> WOODEN_BUCKET = register(() -> new WoodenBucketBlock(), "wooden_bucket");
    public static final RegistryObject<Block> MICRO_SCOPE = register(() -> new MicroScopeBlock(), "microscope");
    public static final RegistryObject<Block> PRESSER = register(() -> new PresserBlock(), "presser");
    public static final RegistryObject<Block> UNREFINED_SOY_SAUCE = register(() -> new UnrefinedSoySauceBlock(), "unrefined_soy_sauce");
    public static final RegistryObject<Block> RADISH_SPROUT_PLANT = register(() -> new RadishSproutPlantBlock(), "radish_sprout_plant");
    public static final RegistryObject<Block> UNREFINED_SAKE = register(() -> new UnrefinedSakeBlock(), "unrefined_sake");
    public static final RegistryObject<Block> CROP_GRASS = register(() -> new CropGrassBlock(), "crop_grass");
    public static final RegistryObject<Block> LEEK_PLANT = register(() -> new LeekPlantBlock(), "leek_plant");
    public static final RegistryObject<Block> OYSTER_SHELL = register(() -> new OysterShellBlock(), "oyster_shell");
    public static final RegistryObject<Block> UNREFINED_MIRIN = register(() -> new UnrefinedMirinBlock(), "unrefined_mirin");
    public static final RegistryObject<Block> TRONA_ORE = register(() -> new OreBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.0F, 3.0F)), "trona_ore");
    public static final RegistryObject<Block> FURNACE_CAULDRON = register(() -> new FurnaceCauldronBlock(), "furnace_cauldron");
    public static final RegistryObject<Block> SOY_HAY = register(() -> new SoyHayBlock(), "soy_hay");
    public static final RegistryObject<Block> AZUKI_PLANT = register(() -> new SoyPlantBlock(AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().sound(SoundType.CROP)), "azuki_plant");
    public static final RegistryObject<Block> WAKAME_BLOCK = register(() -> new TallSeaGrassBlock(AbstractBlock.Properties.create(Material.SEA_GRASS, MaterialColor.BROWN).zeroHardnessAndResistance().doesNotBlockMovement().sound(SoundType.WET_GRASS)), "wakame_block");

    public static RegistryObject<Block> register(@Nonnull Supplier<Block> block, @Nonnull String name){
        return BLOCKS.register(name, block);
    }
}
