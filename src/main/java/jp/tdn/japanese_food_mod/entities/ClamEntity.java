package jp.tdn.japanese_food_mod.entities;

import jp.tdn.japanese_food_mod.JapaneseFoodMod;
import jp.tdn.japanese_food_mod.init.JPEntities;
import jp.tdn.japanese_food_mod.init.JPItems;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.FindWaterGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.passive.WaterMobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import java.util.Random;

public class ClamEntity extends WaterMobEntity {
    public ClamEntity(EntityType<? extends WaterMobEntity> type, World worldIn){
        super((EntityType<? extends WaterMobEntity>) JPEntities.CLAM, worldIn);
    }

    protected void updateAir(int p_209207_1_) {
//        if (this.isAlive() && !this.isInWaterOrBubbleColumn()) {
//            this.setAir(p_209207_1_ - 1);
//            if (this.getAir() == -20) {
//                this.setAir(0);
//                this.attackEntityFrom(DamageSource.DROWN, 2.0F);
//            }
//        } else {
//            this.setAir(20000);
//        }
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return 32;
    }

    @Override
    protected void registerGoals() {
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(5.0d);
    }

    @Override
    protected boolean processInteract(PlayerEntity player, Hand hand) {
        ItemStack handStack = player.getHeldItem(hand);
        if(handStack.isEmpty()){
            player.setHeldItem(hand, new ItemStack(JPItems.CLAM));
        }else if(!player.inventory.addItemStackToInventory(new ItemStack(JPItems.CLAM))){
            player.dropItem(new ItemStack(JPItems.CLAM), false);
        }
        this.remove();
        return true;
    }

    public static boolean spawnHandler(EntityType<? extends ClamEntity> entityIn, IWorld worldIn, SpawnReason reason, BlockPos pos, Random random){
        return (worldIn.getBlockState(pos.down()).getBlock() != Blocks.WATER && worldIn.getFluidState(pos).isTagged(FluidTags.WATER)) || (worldIn.getBlockState(pos).getBlock() == Blocks.AIR && worldIn.getBlockState(pos.down()).getBlock() == Blocks.SAND);
    }
}
