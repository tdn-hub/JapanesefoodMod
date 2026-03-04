package jp.tdn.japanese_food_mod.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;

import javax.annotation.Nonnull;

public class ShellfishEntity extends WaterAnimal {
    private static Item interactItem;
    public ShellfishEntity(EntityType<? extends ShellfishEntity> type, Level worldIn, Item interact){
        super(type, worldIn);
        interactItem = interact;
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 8;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new RandomStrollGoal(this, 1.0D));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 5.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.15D);
    }

    @Override
    @Nonnull
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack handStack = player.getItemInHand(hand);
        if (handStack.isEmpty()) {
            player.setItemInHand(hand, new ItemStack(interactItem));
        } else if (!player.getInventory().add(new ItemStack(interactItem))) {
            player.drop(new ItemStack(interactItem), false);
        }
        this.discard();
        return InteractionResult.sidedSuccess(this.level().isClientSide);
    }

    public static boolean checkSpawnRules(EntityType<? extends ShellfishEntity> entityIn, ServerLevelAccessor worldIn, MobSpawnType reason, BlockPos pos, RandomSource random) {
        return worldIn.getBlockState(pos).getBlock() == Blocks.WATER && pos.getY() < 53;
    }
}
