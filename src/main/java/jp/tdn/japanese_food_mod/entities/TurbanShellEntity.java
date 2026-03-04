package jp.tdn.japanese_food_mod.entities;

import jp.tdn.japanese_food_mod.init.JPEntities;
import jp.tdn.japanese_food_mod.init.JPItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.level.Level;

public class TurbanShellEntity extends ShellfishEntity {
    public TurbanShellEntity(EntityType<? extends WaterAnimal> type, Level worldIn){
        super(JPEntities.TURBAN_SHELL.get(), worldIn, JPItems.TURBAN_SHELL.get());
    }

    @Override
    protected void handleAirSupply(int airSupply) {
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 8.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.15D);
    }
}
