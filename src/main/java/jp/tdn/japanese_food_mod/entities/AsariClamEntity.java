package jp.tdn.japanese_food_mod.entities;

import jp.tdn.japanese_food_mod.init.JPEntities;
import jp.tdn.japanese_food_mod.init.JPItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.level.Level;

public class AsariClamEntity extends ShellfishEntity {
    public AsariClamEntity(EntityType<? extends WaterAnimal> type, Level worldIn){
        super(JPEntities.ASARI_CLAM.get(), worldIn, JPItems.ASARI_CLAM.get());
    }

    @Override
    protected void handleAirSupply(int airSupply) {
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 2.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.15D);
    }
}
