package jp.tdn.japanese_food_mod.init;

import com.google.common.base.Preconditions;
import jp.tdn.japanese_food_mod.JapaneseFoodMod;
import jp.tdn.japanese_food_mod.JapaneseFoodUtil;
import jp.tdn.japanese_food_mod.container.FurnaceCauldronContainer;
import jp.tdn.japanese_food_mod.container.MicroScopeContainer;
import jp.tdn.japanese_food_mod.container.PresserContainer;
import jp.tdn.japanese_food_mod.container.WoodenBucketContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.ObjectHolder;

import javax.annotation.Nonnull;

@ObjectHolder(JapaneseFoodMod.MOD_ID)
@Mod.EventBusSubscriber(modid = JapaneseFoodMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class JPContainerTypes {
    public static final ContainerType<MicroScopeContainer> MICROSCOPE = JapaneseFoodUtil._null();
    public static final ContainerType<WoodenBucketContainer> WOODEN_BUCKET = JapaneseFoodUtil._null();
    public static final ContainerType<PresserContainer> PRESSER = JapaneseFoodUtil._null();
    public static final ContainerType<FurnaceCauldronContainer> FURNACE_CAULDRON = JapaneseFoodUtil._null();

    @SubscribeEvent
    public static void registerContainerTypes(@Nonnull final RegistryEvent.Register<ContainerType<?>> event){
        event.getRegistry().registerAll(
                setup(IForgeContainerType.create(MicroScopeContainer::new), "microscope"),
                setup(IForgeContainerType.create(WoodenBucketContainer::new), "wooden_bucket"),
                setup(IForgeContainerType.create(PresserContainer::new), "presser"),
                setup(IForgeContainerType.create(FurnaceCauldronContainer::new), "furnace_cauldron")
        );
    }

    @Nonnull
    private static <T extends IForgeRegistryEntry<T>> T setup(@Nonnull final T entry, @Nonnull final String name){
        Preconditions.checkNotNull(name, "Name to assign to entry cannot be null");
        return setup(entry, new ResourceLocation(JapaneseFoodMod.MOD_ID, name));
    }

    @Nonnull
    private static <T extends IForgeRegistryEntry<T>> T setup(@Nonnull final T entry, @Nonnull final ResourceLocation registryName) {
        Preconditions.checkNotNull(entry, "Entry cannot be null!");
        Preconditions.checkNotNull(registryName, "Registry name to assign to entry cannot be null!");
        entry.setRegistryName(registryName);
        return entry;
    }
}
