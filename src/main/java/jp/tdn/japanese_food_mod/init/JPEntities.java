package jp.tdn.japanese_food_mod.init;

import jp.tdn.japanese_food_mod.JapaneseFoodMod;
import jp.tdn.japanese_food_mod.entities.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

@EventBusSubscriber(modid = JapaneseFoodMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class JPEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, JapaneseFoodMod.MOD_ID);

    public static final DeferredHolder<EntityType<?>, EntityType<EelEntity>> EEL = ENTITIES.register("eel",
            () -> EntityType.Builder.<EelEntity>of(EelEntity::new, MobCategory.WATER_CREATURE)
                    .sized(0.5f, 0.5f)
                    .build("japanese_food_mod:eel"));

    public static final DeferredHolder<EntityType<?>, EntityType<CrabEntity>> CRAB = ENTITIES.register("crab",
            () -> EntityType.Builder.<CrabEntity>of(CrabEntity::new, MobCategory.WATER_CREATURE)
                    .sized(0.5f, 0.3f)
                    .build("japanese_food_mod:crab"));

    public static final DeferredHolder<EntityType<?>, EntityType<TunaEntity>> TUNA = ENTITIES.register("tuna",
            () -> EntityType.Builder.<TunaEntity>of(TunaEntity::new, MobCategory.WATER_CREATURE)
                    .sized(1.0f, 1.0f)
                    .build("japanese_food_mod:tuna"));

    public static final DeferredHolder<EntityType<?>, EntityType<ClamEntity>> CLAM = ENTITIES.register("clam",
            () -> EntityType.Builder.<ClamEntity>of(ClamEntity::new, MobCategory.WATER_CREATURE)
                    .sized(0.5f, 0.5f)
                    .build("japanese_food_mod:clam"));

    public static final DeferredHolder<EntityType<?>, EntityType<AsariClamEntity>> ASARI_CLAM = ENTITIES.register("asari_clam",
            () -> EntityType.Builder.<AsariClamEntity>of(AsariClamEntity::new, MobCategory.WATER_CREATURE)
                    .sized(0.45f, 0.45f)
                    .build("japanese_food_mod:asari_clam"));

    public static final DeferredHolder<EntityType<?>, EntityType<TurbanShellEntity>> TURBAN_SHELL = ENTITIES.register("turban_shell",
            () -> EntityType.Builder.<TurbanShellEntity>of(TurbanShellEntity::new, MobCategory.WATER_CREATURE)
                    .sized(0.5f, 0.5f)
                    .build("japanese_food_mod:turban_shell"));

    public static final DeferredHolder<EntityType<?>, EntityType<AnglerfishEntity>> ANGLERFISH = ENTITIES.register("anglerfish",
            () -> EntityType.Builder.<AnglerfishEntity>of(AnglerfishEntity::new, MobCategory.MONSTER)
                    .sized(0.75f, 0.4f)
                    .build("japanese_food_mod:anglerfish"));

    @SubscribeEvent
    public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(EEL.get(), EelEntity.createAttributes().build());
        event.put(CRAB.get(), CrabEntity.createAttributes().build());
        event.put(TUNA.get(), TunaEntity.createAttributes().build());
        event.put(CLAM.get(), ClamEntity.createAttributes().build());
        event.put(ASARI_CLAM.get(), AsariClamEntity.createAttributes().build());
        event.put(TURBAN_SHELL.get(), TurbanShellEntity.createAttributes().build());
        event.put(ANGLERFISH.get(), AnglerfishEntity.createAttributes().build());
    }

    @SubscribeEvent
    public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        event.register(EEL.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING,
                WaterAnimal::checkSurfaceWaterAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(CRAB.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.OCEAN_FLOOR_WG,
                CrabEntity::checkSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(TUNA.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING,
                WaterAnimal::checkSurfaceWaterAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(CLAM.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.OCEAN_FLOOR_WG,
                ClamEntity::checkSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(ASARI_CLAM.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.OCEAN_FLOOR_WG,
                AsariClamEntity::checkSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(TURBAN_SHELL.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.OCEAN_FLOOR_WG,
                TurbanShellEntity::checkSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(ANGLERFISH.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.OCEAN_FLOOR_WG,
                AnglerfishEntity::checkAnglerfishSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
    }
}
