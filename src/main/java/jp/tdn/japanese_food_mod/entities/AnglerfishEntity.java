package jp.tdn.japanese_food_mod.entities;

import jp.tdn.japanese_food_mod.init.JPEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.passive.fish.AbstractFishEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class AnglerfishEntity extends AbstractFishEntity {

    public AnglerfishEntity(EntityType<? extends AbstractFishEntity> type, World worldIn){
        super(JPEntities.ANGLERFISH, worldIn);
    }

    @Override
    protected ItemStack getFishBucket() {
        return null;
    }

    @Override
    protected SoundEvent getFlopSound() {
        return SoundEvents.ENTITY_COD_FLOP;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        //this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, PlayerEntity.class, true));
    }

    public static AttributeModifierMap.MutableAttribute getAttributeMap() {
        return MobEntity.func_233666_p_().func_233815_a_(Attributes.field_233818_a_, 6.0D).func_233815_a_(Attributes.field_233821_d_, 0.3D);
    }
}
