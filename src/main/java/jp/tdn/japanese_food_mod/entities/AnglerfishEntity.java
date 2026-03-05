package jp.tdn.japanese_food_mod.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;

public class AnglerfishEntity extends Monster {

    private static final EntityDataAccessor<Boolean> DATA_AGGRO =
            SynchedEntityData.defineId(AnglerfishEntity.class, EntityDataSerializers.BOOLEAN);

    public AnglerfishEntity(EntityType<? extends AnglerfishEntity> type, Level level) {
        super(type, level);
        this.xpReward = 5;
        this.setPathfindingMalus(PathType.WATER, 0.0F);
        this.moveControl = new AnglerfishMoveControl(this);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_AGGRO, false);
    }

    public boolean isAggro() {
        return this.entityData.get(DATA_AGGRO);
    }

    private void setAggro(boolean aggro) {
        this.entityData.set(DATA_AGGRO, aggro);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2D, true));
        this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, 1.0, 40));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.ATTACK_DAMAGE, 3.0D)
                .add(Attributes.FOLLOW_RANGE, 16.0D);
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        return new WaterBoundPathNavigation(this, level);
    }

    @Override
    public void travel(Vec3 travelVector) {
        if (this.isControlledByLocalInstance() && this.isInWater()) {
            this.moveRelative(0.1F, travelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9));
            if (this.getTarget() == null) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.005, 0.0));
            }
        } else {
            super.travel(travelVector);
        }
    }

    @Override
    public void aiStep() {
        if (this.isAlive()) {
            // Server: update aggro state and apply glowing
            if (!this.level().isClientSide) {
                boolean hasTarget = this.getTarget() != null;
                if (hasTarget != this.isAggro()) {
                    this.setAggro(hasTarget);
                }
                if (hasTarget && this.getTarget() instanceof Player target) {
                    if (this.distanceToSqr(target) < 64.0 && this.tickCount % 40 == 0) {
                        target.addEffect(new MobEffectInstance(MobEffects.GLOWING, 60, 0, false, false));
                    }
                }
            }

            // Client: particle effects
            if (this.level().isClientSide) {
                // Lantern glow particle (always)
                if (this.random.nextInt(5) == 0) {
                    this.level().addParticle(
                            ParticleTypes.END_ROD,
                            this.getX(), this.getY() + this.getBbHeight() + 0.2, this.getZ(),
                            (this.random.nextDouble() - 0.5) * 0.02, 0.02, (this.random.nextDouble() - 0.5) * 0.02
                    );
                }
                // Aggro glow particles
                if (this.isAggro()) {
                    for (int i = 0; i < 2; i++) {
                        this.level().addParticle(
                                ParticleTypes.GLOW,
                                this.getRandomX(0.5), this.getRandomY(), this.getRandomZ(0.5),
                                0.0, 0.0, 0.0
                        );
                    }
                }
            }

            // Water breathing
            if (this.isInWaterOrBubble()) {
                this.setAirSupply(300);
            } else if (this.onGround()) {
                // Flop on land
                this.setDeltaMovement(
                        this.getDeltaMovement().add(
                                (this.random.nextFloat() * 2.0F - 1.0F) * 0.4F,
                                0.5,
                                (this.random.nextFloat() * 2.0F - 1.0F) * 0.4F
                        )
                );
                this.setYRot(this.random.nextFloat() * 360.0F);
                this.setOnGround(false);
                this.hasImpulse = true;
                this.makeSound(SoundEvents.COD_FLOP);
            }
        }

        super.aiStep();
    }

    @Override
    public float getWalkTargetValue(BlockPos pos, LevelReader level) {
        return level.getFluidState(pos).is(FluidTags.WATER)
                ? 10.0F + level.getPathfindingCostFromLightLevels(pos)
                : super.getWalkTargetValue(pos, level);
    }

    @Override
    public boolean checkSpawnObstruction(LevelReader level) {
        return level.isUnobstructed(this);
    }

    @Override
    public int getMaxHeadXRot() {
        return 180;
    }

    @Override
    public SoundSource getSoundSource() {
        return SoundSource.HOSTILE;
    }

    public static boolean checkAnglerfishSpawnRules(
            EntityType<? extends AnglerfishEntity> type, LevelAccessor level,
            MobSpawnType spawnType, BlockPos pos, RandomSource random
    ) {
        return level.getDifficulty() != Difficulty.PEACEFUL
                && (MobSpawnType.isSpawner(spawnType) || level.getFluidState(pos).is(FluidTags.WATER))
                && level.getFluidState(pos.below()).is(FluidTags.WATER);
    }

    static class AnglerfishMoveControl extends MoveControl {
        private final AnglerfishEntity anglerfish;

        AnglerfishMoveControl(AnglerfishEntity mob) {
            super(mob);
            this.anglerfish = mob;
        }

        @Override
        public void tick() {
            if (this.anglerfish.isEyeInFluidType(net.neoforged.neoforge.common.NeoForgeMod.WATER_TYPE.value())) {
                this.anglerfish.setDeltaMovement(this.anglerfish.getDeltaMovement().add(0.0, 0.005, 0.0));
            }

            if (this.operation == MoveControl.Operation.MOVE_TO && !this.anglerfish.getNavigation().isDone()) {
                float f = (float) (this.speedModifier * this.anglerfish.getAttributeValue(Attributes.MOVEMENT_SPEED));
                this.anglerfish.setSpeed(Mth.lerp(0.125F, this.anglerfish.getSpeed(), f));
                double dx = this.wantedX - this.anglerfish.getX();
                double dy = this.wantedY - this.anglerfish.getY();
                double dz = this.wantedZ - this.anglerfish.getZ();
                if (dy != 0.0) {
                    double dist = Math.sqrt(dx * dx + dy * dy + dz * dz);
                    this.anglerfish.setDeltaMovement(
                            this.anglerfish.getDeltaMovement().add(0.0, this.anglerfish.getSpeed() * (dy / dist) * 0.1, 0.0)
                    );
                }
                if (dx != 0.0 || dz != 0.0) {
                    float yRot = (float) (Mth.atan2(dz, dx) * 180.0F / (float) Math.PI) - 90.0F;
                    this.anglerfish.setYRot(this.rotlerp(this.anglerfish.getYRot(), yRot, 90.0F));
                    this.anglerfish.yBodyRot = this.anglerfish.getYRot();
                }
            } else {
                this.anglerfish.setSpeed(0.0F);
            }
        }
    }
}
