package jp.tdn.japanese_food_mod.init;

import jp.tdn.japanese_food_mod.JapaneseFoodMod;
import jp.tdn.japanese_food_mod.blocks.tileentity.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

public class JPBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, JapaneseFoodMod.MOD_ID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MicroScopeTileEntity>> MICROSCOPE =
            BLOCK_ENTITIES.register("microscope", () ->
                    BlockEntityType.Builder.of(MicroScopeTileEntity::new, JPBlocks.MICRO_SCOPE.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<WoodenBucketTileEntity>> WOODEN_BUCKET =
            BLOCK_ENTITIES.register("wooden_bucket", () ->
                    BlockEntityType.Builder.of(WoodenBucketTileEntity::new, JPBlocks.WOODEN_BUCKET.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<PresserTileEntity>> PRESSER =
            BLOCK_ENTITIES.register("presser", () ->
                    BlockEntityType.Builder.of(PresserTileEntity::new, JPBlocks.PRESSER.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<UnrefinedSoySauceTileEntity>> UNREFINED_SOY_SAUCE =
            BLOCK_ENTITIES.register("unrefined_soy_sauce", () ->
                    BlockEntityType.Builder.of(UnrefinedSoySauceTileEntity::new, JPBlocks.UNREFINED_SOY_SAUCE.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<UnrefinedSakeTileEntity>> UNREFINED_SAKE =
            BLOCK_ENTITIES.register("unrefined_sake", () ->
                    BlockEntityType.Builder.of(UnrefinedSakeTileEntity::new, JPBlocks.UNREFINED_SAKE.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<OysterShellTileEntity>> OYSTER_SHELL =
            BLOCK_ENTITIES.register("oyster_shell", () ->
                    BlockEntityType.Builder.of(OysterShellTileEntity::new, JPBlocks.OYSTER_SHELL.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<UnrefinedMirinTileEntity>> UNREFINED_MIRIN =
            BLOCK_ENTITIES.register("unrefined_mirin", () ->
                    BlockEntityType.Builder.of(UnrefinedMirinTileEntity::new, JPBlocks.UNREFINED_MIRIN.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<FurnaceCauldronTileEntity>> FURNACE_CAULDRON =
            BLOCK_ENTITIES.register("furnace_cauldron", () ->
                    BlockEntityType.Builder.of(FurnaceCauldronTileEntity::new, JPBlocks.FURNACE_CAULDRON.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SoyHayTileEntity>> SOY_HAY =
            BLOCK_ENTITIES.register("soy_hay", () ->
                    BlockEntityType.Builder.of(SoyHayTileEntity::new, JPBlocks.SOY_HAY.get()).build(null));
}
