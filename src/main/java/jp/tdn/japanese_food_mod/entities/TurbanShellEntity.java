package jp.tdn.japanese_food_mod.entities;

import jp.tdn.japanese_food_mod.init.JPEntities;
import jp.tdn.japanese_food_mod.init.JPItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.WaterMobEntity;
import net.minecraft.world.World;

public class TurbanShellEntity extends ShellfishEntity {
    public TurbanShellEntity(EntityType<? extends WaterMobEntity> type, World worldIn){
        super(JPEntities.TURBAN_SHELL, worldIn, JPItems.TURBAN_SHELL);
    }

    protected void updateAir(int p_209207_1_) {
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0d);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.15d);
    }
}
