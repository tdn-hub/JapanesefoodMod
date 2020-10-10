package jp.tdn.japanese_food_mod.init;

import jp.tdn.japanese_food_mod.JapaneseFoodMod;
import jp.tdn.japanese_food_mod.entities.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

import static jp.tdn.japanese_food_mod.JapaneseFoodUtil.getBiome;

@Mod.EventBusSubscriber(modid = JapaneseFoodMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class JPEntities {
    public static EntityType<EelEntity> EEL = createEntity(EelEntity::new, EntityClassification.WATER_CREATURE, "eel", 0.5f, 0.5f);
    public static EntityType<CrabEntity> CRAB = createEntity(CrabEntity::new, EntityClassification.WATER_CREATURE, "crab", 0.5f, 0.3f);
    public static EntityType<TunaEntity> TUNA = createEntity(TunaEntity::new, EntityClassification.WATER_CREATURE, "tuna", 1.0f, 1.0f);
    public static EntityType<ClamEntity> CLAM = createEntity(ClamEntity::new, EntityClassification.WATER_CREATURE, "clam", 0.5f, 0.5f);
    public static EntityType<AsariClamEntity> ASARI_CLAM = createEntity(AsariClamEntity::new, EntityClassification.WATER_CREATURE, "asari_clam", 0.45f, 0.45f);
    public static EntityType<TurbanShellEntity> TURBAN_SHELL = createEntity(TurbanShellEntity::new, EntityClassification.WATER_CREATURE, "turban_shell", 0.5f, 0.5f);
    public static EntityType<AnglerfishEntity> ANGLERFISH = createEntity(AnglerfishEntity::new, EntityClassification.WATER_CREATURE, "anglerfish", 0.75f, 0.4f);

    private static <T extends Entity> EntityType<T> createEntity(EntityType.IFactory<T> factory, EntityClassification entityClassification, String name, float width, float height) {
        ResourceLocation location = new ResourceLocation(JapaneseFoodMod.MOD_ID + ":" + name);

        EntityType<T> entity = EntityType.Builder.create(factory, entityClassification).size(width, height).build(location.toString());
        entity.setRegistryName(location);
        return entity;
    }

    @SubscribeEvent
    public static void registerEntities(final RegistryEvent.Register<EntityType<?>> event){
        event.getRegistry().registerAll(
                EEL,
                CRAB,
                TUNA,
                CLAM,
                ASARI_CLAM,
                TURBAN_SHELL,
                ANGLERFISH
        );

        GlobalEntityTypeAttributes.put(EEL, EelEntity.registerAttributes().func_233813_a_());
        GlobalEntityTypeAttributes.put(CRAB, CrabEntity.registerAttributes().func_233813_a_());
        GlobalEntityTypeAttributes.put(TUNA, TunaEntity.registerAttributes().func_233813_a_());
        GlobalEntityTypeAttributes.put(CLAM, ClamEntity.registerAttributes().func_233813_a_());
        GlobalEntityTypeAttributes.put(ASARI_CLAM, AsariClamEntity.registerAttributes().func_233813_a_());
        GlobalEntityTypeAttributes.put(TURBAN_SHELL, TurbanShellEntity.registerAttributes().func_233813_a_());
        GlobalEntityTypeAttributes.put(ANGLERFISH, AnglerfishEntity.getAttributeMap().func_233813_a_());

        EntitySpawnPlacementRegistry.register(EEL, EntitySpawnPlacementRegistry.PlacementType.IN_WATER, Heightmap.Type.MOTION_BLOCKING, EelEntity::func_223363_b);
        EntitySpawnPlacementRegistry.register(CRAB, EntitySpawnPlacementRegistry.PlacementType.IN_WATER, Heightmap.Type.OCEAN_FLOOR_WG, CrabEntity::spawnHandler);
        EntitySpawnPlacementRegistry.register(TUNA, EntitySpawnPlacementRegistry.PlacementType.IN_WATER, Heightmap.Type.MOTION_BLOCKING, TunaEntity::func_223363_b);
        EntitySpawnPlacementRegistry.register(CLAM, EntitySpawnPlacementRegistry.PlacementType.IN_WATER, Heightmap.Type.OCEAN_FLOOR_WG, ClamEntity::spawnHandler);
        EntitySpawnPlacementRegistry.register(ASARI_CLAM, EntitySpawnPlacementRegistry.PlacementType.IN_WATER, Heightmap.Type.OCEAN_FLOOR_WG, AsariClamEntity::spawnHandler);
        EntitySpawnPlacementRegistry.register(TURBAN_SHELL, EntitySpawnPlacementRegistry.PlacementType.IN_WATER, Heightmap.Type.OCEAN_FLOOR_WG, TurbanShellEntity::spawnHandler);
        EntitySpawnPlacementRegistry.register(ANGLERFISH, EntitySpawnPlacementRegistry.PlacementType.IN_WATER, Heightmap.Type.OCEAN_FLOOR_WG, AnglerfishEntity::func_223363_b);
    }

    public static void registerEntityWorldSpawns(){
        registerEntityWorldSpawn(EEL, 10, 1, 5, getBiome("river"), getBiome("ocean"), getBiome("lukewarm_ocean"), getBiome("warm_ocean"));
        registerEntityWorldSpawn(CRAB, 25, 1, 10, getBiome("ocean"), getBiome("lukewarm_ocean"), getBiome("warm_ocean"), Biomes.COLD_OCEAN, Biomes.DEEP_COLD_OCEAN, Biomes.DEEP_OCEAN, Biomes.DEEP_FROZEN_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN, Biomes.DEEP_WARM_OCEAN, Biomes.BEACH);
        registerEntityWorldSpawn(TUNA, 2, 1, 5, Biomes.OCEAN, Biomes.LUKEWARM_OCEAN, Biomes.WARM_OCEAN, Biomes.DEEP_OCEAN, Biomes.DEEP_WARM_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN);
        registerEntityWorldSpawn(CLAM, 40, 1, 3, Biomes.LUKEWARM_OCEAN, Biomes.WARM_OCEAN, Biomes.OCEAN,Biomes.BEACH);
        registerEntityWorldSpawn(ASARI_CLAM, 40, 1, 5, Biomes.LUKEWARM_OCEAN, Biomes.WARM_OCEAN, Biomes.OCEAN, Biomes.BEACH);
        registerEntityWorldSpawn(TURBAN_SHELL, 5, 1, 2, Biomes.OCEAN, Biomes.LUKEWARM_OCEAN, Biomes.WARM_OCEAN);
        registerEntityWorldSpawn(ANGLERFISH, 1, 1, 2, Biomes.DEEP_OCEAN, Biomes.DEEP_COLD_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN, Biomes.DEEP_WARM_OCEAN);
    }

    public static void registerEntityWorldSpawn(EntityType<?> entity, int weight, int min, int max, Biome... biomes){
        for(Biome biome : biomes){
            Biome inBiome = ForgeRegistries.BIOMES.getValue(biome.getRegistryName());
            if(inBiome != null){
               inBiome.func_242440_e().
            }
        }
    }
}
