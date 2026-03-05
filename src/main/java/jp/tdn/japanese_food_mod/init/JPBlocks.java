package jp.tdn.japanese_food_mod.init;

import jp.tdn.japanese_food_mod.JapaneseFoodMod;
import jp.tdn.japanese_food_mod.blocks.*;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.minecraft.core.registries.Registries;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class JPBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, JapaneseFoodMod.MOD_ID);

    public static final DeferredHolder<Block, Block> SOY_PLANT = register(() -> new SoyPlantBlock(
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT)
                    .noCollission()
                    .randomTicks()
                    .instabreak()
                    .sound(SoundType.CROP)
    ), "soy_plant");

    public static final DeferredHolder<Block, Block> ROCK_SALT_BLOCK = register(() -> new RockSaltBlock(
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_PINK)
                    .requiresCorrectToolForDrops()
                    .strength(2.5f)
    ), "rock_salt_block");

    public static final DeferredHolder<Block, Block> RICE_PLANT = register(RicePlantBlock::new, "rice_plant");
    public static final DeferredHolder<Block, Block> WOODEN_BUCKET = register(WoodenBucketBlock::new, "wooden_bucket");
    public static final DeferredHolder<Block, Block> MICRO_SCOPE = register(MicroScopeBlock::new, "microscope");
    public static final DeferredHolder<Block, Block> PRESSER = register(PresserBlock::new, "presser");
    public static final DeferredHolder<Block, Block> UNREFINED_SOY_SAUCE = register(UnrefinedSoySauceBlock::new, "unrefined_soy_sauce");

    public static final DeferredHolder<Block, Block> RADISH_SPROUT_PLANT = register(RadishSproutPlantBlock::new, "radish_sprout_plant");

    public static final DeferredHolder<Block, Block> UNREFINED_SAKE = register(UnrefinedSakeBlock::new, "unrefined_sake");
    public static final DeferredHolder<Block, Block> CROP_GRASS = register(CropGrassBlock::new, "crop_grass");

    public static final DeferredHolder<Block, Block> LEEK_PLANT = register(LeekPlantBlock::new, "leek_plant");

    public static final DeferredHolder<Block, Block> OYSTER_SHELL = register(OysterShellBlock::new, "oyster_shell");
    public static final DeferredHolder<Block, Block> UNREFINED_MIRIN = register(UnrefinedMirinBlock::new, "unrefined_mirin");

    public static final DeferredHolder<Block, Block> TRONA_ORE = register(() -> new DropExperienceBlock(
            ConstantInt.of(0),
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.STONE)
                    .requiresCorrectToolForDrops()
                    .strength(3.0F, 3.0F)
    ), "trona_ore");

    public static final DeferredHolder<Block, Block> FURNACE_CAULDRON = register(FurnaceCauldronBlock::new, "furnace_cauldron");
    public static final DeferredHolder<Block, Block> MORTAR = register(MortarBlock::new, "mortar");
    public static final DeferredHolder<Block, Block> SOY_HAY = register(SoyHayBlock::new, "soy_hay");

    public static final DeferredHolder<Block, Block> AZUKI_PLANT = register(() -> new SoyPlantBlock(
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT)
                    .noCollission()
                    .randomTicks()
                    .instabreak()
                    .sound(SoundType.CROP)
    ), "azuki_plant");

    public static final DeferredHolder<Block, Block> WAKAME_BLOCK = register(() -> new TallSeagrassBlock(
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BROWN)
                    .noCollission()
                    .instabreak()
                    .sound(SoundType.WET_GRASS)
    ), "wakame_block");

    public static DeferredHolder<Block, Block> register(@Nonnull Supplier<Block> block, @Nonnull String name) {
        return BLOCKS.register(name, block);
    }
}
