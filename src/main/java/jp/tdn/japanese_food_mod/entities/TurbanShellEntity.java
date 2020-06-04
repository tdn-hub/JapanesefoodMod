package jp.tdn.japanese_food_mod.entities;

import jp.tdn.japanese_food_mod.init.JPEntities;
import jp.tdn.japanese_food_mod.init.JPItems;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.passive.WaterMobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import java.util.Random;

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
