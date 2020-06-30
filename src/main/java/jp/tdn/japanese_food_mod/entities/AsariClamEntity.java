package jp.tdn.japanese_food_mod.entities;

import jp.tdn.japanese_food_mod.init.JPEntities;
import jp.tdn.japanese_food_mod.init.JPItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.passive.WaterMobEntity;
import net.minecraft.world.World;

public class AsariClamEntity extends ShellfishEntity {
    public AsariClamEntity(EntityType<? extends WaterMobEntity> type, World worldIn){
        super(JPEntities.ASARI_CLAM, worldIn, JPItems.ASARI_CLAM);
    }

    protected void updateAir(int p_209207_1_) {
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MobEntity.func_233666_p_().func_233815_a_(Attributes.field_233818_a_, 2.0D).func_233815_a_(Attributes.field_233821_d_, 0.15D);
    }
}
