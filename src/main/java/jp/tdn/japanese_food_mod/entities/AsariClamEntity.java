package jp.tdn.japanese_food_mod.entities;

import jp.tdn.japanese_food_mod.init.JPEntities;
import jp.tdn.japanese_food_mod.init.JPItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.WaterMobEntity;
import net.minecraft.world.World;

public class AsariClamEntity extends ShellfishEntity {
    public AsariClamEntity(EntityType<? extends WaterMobEntity> type, World worldIn){
        super(JPEntities.ASARI_CLAM, worldIn, JPItems.ASARI_CLAM);
    }

    protected void updateAir(int p_209207_1_) {
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(2.0d);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.15d);

    }
}
