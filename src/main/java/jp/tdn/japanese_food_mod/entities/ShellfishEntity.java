package jp.tdn.japanese_food_mod.entities;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.passive.WaterMobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Random;

public class ShellfishEntity extends WaterMobEntity {
    private static Item interactItem;
    public ShellfishEntity(EntityType<? extends ShellfishEntity> type, World worldIn, Item interact){
        super(type, worldIn);
        interactItem = interact;
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return 8;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new RandomWalkingGoal(this, 1.0D));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 5.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.15D);
    }

    @Override
    @Nonnull
    public ActionResultType func_230254_b_(PlayerEntity player, Hand hand) {
        ItemStack handStack = player.getHeldItem(hand);
        if(handStack.isEmpty()){
            player.setHeldItem(hand, new ItemStack(interactItem));
        }else if(!player.inventory.addItemStackToInventory(new ItemStack(interactItem))){
            player.dropItem(new ItemStack(interactItem), false);
        }
        this.remove();
        return ActionResultType.func_233537_a_(this.world.isRemote);
    }

    public static boolean spawnHandler(EntityType<? extends ShellfishEntity> entityIn, IWorld worldIn, SpawnReason reason, BlockPos pos, Random random){
        return worldIn.getBlockState(pos).getBlock() == Blocks.WATER && pos.getY() < 53;
    }
}
