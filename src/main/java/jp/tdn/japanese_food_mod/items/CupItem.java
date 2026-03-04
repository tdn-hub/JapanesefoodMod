package jp.tdn.japanese_food_mod.items;

import jp.tdn.japanese_food_mod.init.JPItems;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.List;

public class CupItem extends SimpleItem {
    public CupItem(){
        super();
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        List<AreaEffectCloud> aecEntityList = level.getEntitiesOfClass(AreaEffectCloud.class, player.getBoundingBox().inflate(2.0D), (entity) -> {
            return entity != null && entity.isAlive() && entity.getOwner() instanceof EnderDragon;
        });
        ItemStack inHand = player.getItemInHand(hand);
        if (!aecEntityList.isEmpty()) {
            AreaEffectCloud aecEntity = aecEntityList.get(0);
            aecEntity.setRadius(aecEntity.getRadius() - 0.5F);
            level.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.BOTTLE_FILL_DRAGONBREATH, SoundSource.NEUTRAL, 1.0F, 1.0F);
            return InteractionResultHolder.success(this.turnBottleIntoItem(inHand, player, new ItemStack(Items.DRAGON_BREATH)));
        } else {
            HitResult rayTrace = getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);
            if (rayTrace.getType() == HitResult.Type.MISS) {
                return InteractionResultHolder.pass(inHand);
            } else {
                if (rayTrace.getType() == HitResult.Type.BLOCK) {
                    BlockPos pos = ((BlockHitResult)rayTrace).getBlockPos();
                    if (!level.mayInteract(player, pos)) {
                        return InteractionResultHolder.pass(inHand);
                    }

                    if (level.getFluidState(pos).is(FluidTags.WATER)) {
                        level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.BOTTLE_FILL, SoundSource.NEUTRAL, 1.0F, 1.0F);
                        return InteractionResultHolder.success(this.turnBottleIntoItem(inHand, player, new ItemStack(JPItems.CUP_WITH_WATER.get())));
                    }
                }

                return InteractionResultHolder.pass(inHand);
            }
        }
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity entity, InteractionHand hand) {
        if(stack.getItem() == JPItems.CUP.get()){
            if(entity.getType() == EntityType.COW){
                turnBottleIntoItem(stack, player, new ItemStack(JPItems.CUP_WITH_MILK.get()));
                player.level().playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.COW_MILK, SoundSource.NEUTRAL, 1.0F, 1.0F);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    protected ItemStack turnBottleIntoItem(ItemStack bottleStack, Player player, ItemStack filledStack) {
        bottleStack.shrink(1);
        player.awardStat(Stats.ITEM_USED.get(this));
        if (bottleStack.isEmpty()) {
            return filledStack;
        } else {
            if (!player.getInventory().add(filledStack)) {
                player.drop(filledStack, false);
            }

            return bottleStack;
        }
    }
}
