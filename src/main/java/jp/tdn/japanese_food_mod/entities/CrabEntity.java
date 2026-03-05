package jp.tdn.japanese_food_mod.entities;

import jp.tdn.japanese_food_mod.init.JPEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.level.Level;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.tags.FluidTags;

public class CrabEntity extends WaterAnimal {
    public CrabEntity(EntityType<? extends WaterAnimal> type, Level worldIn){
        super(JPEntities.CRAB.get(), worldIn);
    }

    @Override
    protected void handleAirSupply(int airSupply) {
//        if (this.isAlive() && !this.isInWaterOrBubbleColumn()) {
//            this.setAirSupply(airSupply - 1);
//            if (this.getAirSupply() == -20) {
//                this.setAirSupply(0);
//                this.hurt(this.level().damageSources().drown(), 2.0F);
//            }
//        } else {
//            this.setAirSupply(20000);
//        }
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 32;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(1, new RandomStrollGoal(this, 1.0d));
        this.goalSelector.addGoal(2, new net.minecraft.world.entity.ai.goal.TryFindWaterGoal(this));
        this.goalSelector.addGoal(3, new PanicGoal(this, 1.5d));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 5.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D);
    }

    public static boolean checkSpawnRules(EntityType<? extends CrabEntity> entityIn, ServerLevelAccessor worldIn, MobSpawnType reason, BlockPos pos, RandomSource random) {
        return worldIn.getBlockState(pos.below()).getBlock() != Blocks.WATER && worldIn.getFluidState(pos).is(FluidTags.WATER);
    }
}
