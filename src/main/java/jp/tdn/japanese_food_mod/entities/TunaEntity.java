package jp.tdn.japanese_food_mod.entities;

import jp.tdn.japanese_food_mod.init.JPEntities;
import jp.tdn.japanese_food_mod.init.JPItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.passive.fish.AbstractGroupFishEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class TunaEntity extends AbstractGroupFishEntity {

    public TunaEntity(EntityType<? extends AbstractGroupFishEntity> type, World worldIn){
        super(JPEntities.TUNA, worldIn);
    }

    @Override
    protected ItemStack getFishBucket() {
        return new ItemStack(JPItems.TUNA_BUCKET.get());
    }

    @Override
    protected SoundEvent getFlopSound() {
        return SoundEvents.ENTITY_COD_FLOP;
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 10.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 1.0D);
    }
}
