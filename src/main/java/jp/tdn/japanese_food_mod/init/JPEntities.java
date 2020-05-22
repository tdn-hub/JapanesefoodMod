package jp.tdn.japanese_food_mod.init;

import jp.tdn.japanese_food_mod.JapaneseFoodMod;
import jp.tdn.japanese_food_mod.entities.CrabEntity;
import jp.tdn.japanese_food_mod.entities.EelEntity;
import jp.tdn.japanese_food_mod.entities.TunaEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = JapaneseFoodMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class JPEntities {
    public static EntityType<EelEntity> EEL = createEntity(EelEntity::new, EntityClassification.WATER_CREATURE, "eel", 0.5f, 0.5f);
    public static EntityType<CrabEntity> CRAB = createEntity(CrabEntity::new, EntityClassification.WATER_CREATURE, "crab", 0.5f, 0.3f);
    public static EntityType<TunaEntity> TUNA = createEntity(TunaEntity::new, EntityClassification.WATER_CREATURE, "tuna", 1.0f, 1.0f);

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
                TUNA
        );

        EntitySpawnPlacementRegistry.register(EEL, EntitySpawnPlacementRegistry.PlacementType.IN_WATER, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, EelEntity::func_223363_b);
        EntitySpawnPlacementRegistry.register(CRAB, EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, CrabEntity::spawnHandler);
        EntitySpawnPlacementRegistry.register(TUNA, EntitySpawnPlacementRegistry.PlacementType.IN_WATER, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, TunaEntity::func_223363_b);
    }

    public static void registerEntityWorldSpawns(){
        registerEntityWorldSpawn(EEL, 10, 1, 5, Biomes.RIVER, Biomes.OCEAN, Biomes.LUKEWARM_OCEAN, Biomes.WARM_OCEAN);
        registerEntityWorldSpawn(CRAB, 50, 1, 10, Biomes.OCEAN, Biomes.LUKEWARM_OCEAN, Biomes.WARM_OCEAN, Biomes.COLD_OCEAN, Biomes.DEEP_COLD_OCEAN, Biomes.DEEP_OCEAN, Biomes.DEEP_FROZEN_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN, Biomes.DEEP_WARM_OCEAN, Biomes.BEACH);
        registerEntityWorldSpawn(TUNA, 5, 1, 10, Biomes.OCEAN, Biomes.LUKEWARM_OCEAN, Biomes.WARM_OCEAN, Biomes.DEEP_OCEAN, Biomes.DEEP_WARM_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN);
    }

    public static void registerEntityWorldSpawn(EntityType<?> entity, int weight, int min, int max, Biome... biomes){
        for(Biome biome : biomes){
            Biome inBiome = ForgeRegistries.BIOMES.getValue(biome.getRegistryName());
            if(inBiome != null){
               inBiome.getSpawns(entity.getClassification()).add(new Biome.SpawnListEntry(entity, weight, min, max));
            }
        }
    }
}
