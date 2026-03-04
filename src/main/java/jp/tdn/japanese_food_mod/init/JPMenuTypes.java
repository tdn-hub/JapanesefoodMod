package jp.tdn.japanese_food_mod.init;

import jp.tdn.japanese_food_mod.JapaneseFoodMod;
import jp.tdn.japanese_food_mod.container.FurnaceCauldronContainer;
import jp.tdn.japanese_food_mod.container.MicroScopeContainer;
import jp.tdn.japanese_food_mod.container.PresserContainer;
import jp.tdn.japanese_food_mod.container.WoodenBucketContainer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

public class JPMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES =
            DeferredRegister.create(Registries.MENU, JapaneseFoodMod.MOD_ID);

    public static final DeferredHolder<MenuType<?>, MenuType<MicroScopeContainer>> MICROSCOPE =
            MENU_TYPES.register("microscope", () ->
                    IMenuTypeExtension.create(MicroScopeContainer::new));

    public static final DeferredHolder<MenuType<?>, MenuType<WoodenBucketContainer>> WOODEN_BUCKET =
            MENU_TYPES.register("wooden_bucket", () ->
                    IMenuTypeExtension.create(WoodenBucketContainer::new));

    public static final DeferredHolder<MenuType<?>, MenuType<PresserContainer>> PRESSER =
            MENU_TYPES.register("presser", () ->
                    IMenuTypeExtension.create(PresserContainer::new));

    public static final DeferredHolder<MenuType<?>, MenuType<FurnaceCauldronContainer>> FURNACE_CAULDRON =
            MENU_TYPES.register("furnace_cauldron", () ->
                    IMenuTypeExtension.create(FurnaceCauldronContainer::new));
}
