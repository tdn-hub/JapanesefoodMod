package jp.tdn.japanese_food_mod.init;

import com.google.common.base.Preconditions;
import jp.tdn.japanese_food_mod.JapaneseFoodMod;
import jp.tdn.japanese_food_mod.JapaneseFoodUtil;
import jp.tdn.japanese_food_mod.blocks.tileentity.MicroScopeTileEntity;
import jp.tdn.japanese_food_mod.blocks.tileentity.PresserTileEntity;
import jp.tdn.japanese_food_mod.blocks.tileentity.WoodenBucketTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.ObjectHolder;

import javax.annotation.Nonnull;

@ObjectHolder(JapaneseFoodMod.MOD_ID)
@Mod.EventBusSubscriber(modid = JapaneseFoodMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class JPTileEntities {
     public static final TileEntityType<MicroScopeTileEntity> MICROSCOPE = JapaneseFoodUtil._null();
     public static final TileEntityType<WoodenBucketTileEntity> WOODEN_BUCKET = JapaneseFoodUtil._null();
     public static final TileEntityType<PresserTileEntity> PRESSER = JapaneseFoodUtil._null();

     @SubscribeEvent
     public static void registerTileEntityTypes(@Nonnull final RegistryEvent.Register<TileEntityType<?>> event){
          event.getRegistry().registerAll(
                  setup(TileEntityType.Builder.create(MicroScopeTileEntity::new, JPBlocks.MICRO_SCOPE).build(null), "microscope"),
                  setup(TileEntityType.Builder.create(WoodenBucketTileEntity::new, JPBlocks.WOODEN_BUCKET).build(null), "wooden_bucket"),
                  setup(TileEntityType.Builder.create(PresserTileEntity::new, JPBlocks.PRESSER).build(null), "presser")
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
