package jp.tdn.japanese_food_mod.entities;

import jp.tdn.japanese_food_mod.init.JPEntities;
import jp.tdn.japanese_food_mod.init.JPItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.level.Level;

public class ClamEntity extends ShellfishEntity {
    public ClamEntity(EntityType<? extends WaterAnimal> type, Level worldIn){
        super(JPEntities.CLAM.get(), worldIn, JPItems.CLAM.get());
    }

    @Override
    protected void handleAirSupply(int airSupply) {
    }
}
