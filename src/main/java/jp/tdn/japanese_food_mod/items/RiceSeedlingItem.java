package jp.tdn.japanese_food_mod.items;

import jp.tdn.japanese_food_mod.init.JPBlocks;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.neoforge.common.util.BlockSnapshot;
import net.neoforged.neoforge.event.EventHooks;

import javax.annotation.Nonnull;

public class RiceSeedlingItem extends BlockItem {
    public RiceSeedlingItem(Block block, Properties properties){
        super(block, properties);
    }

    @Nonnull
    @Override
    public InteractionResult useOn(UseOnContext context) {
        return InteractionResult.PASS;
    }

    @Nonnull
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, @Nonnull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        HitResult raytraceresult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);
        if (raytraceresult.getType() == HitResult.Type.MISS) {
            return InteractionResultHolder.pass(itemstack);
        } else {
            if (raytraceresult.getType() == HitResult.Type.BLOCK) {
                BlockHitResult blockraytraceresult = (BlockHitResult)raytraceresult;
                BlockPos blockpos = blockraytraceresult.getBlockPos();
                Direction direction = blockraytraceresult.getDirection();
                if (!level.mayInteract(player, blockpos) || !player.mayUseItemAt(blockpos.relative(direction), direction, itemstack)) {
                    return InteractionResultHolder.fail(itemstack);
                }

                BlockPos blockpos1 = blockpos.above();
                if (level.getFluidState(blockpos).is(FluidTags.WATER) && level.isEmptyBlock(blockpos1)) {
                    BlockSnapshot blocksnapshot = BlockSnapshot.create(level.dimension(), level, blockpos1);
                    level.setBlock(blockpos1, JPBlocks.RICE_PLANT.get().defaultBlockState(), 11);
                    if (EventHooks.onBlockPlace(player, blocksnapshot, Direction.UP)) {
                        blocksnapshot.restore();
                        return InteractionResultHolder.fail(itemstack);
                    }

                    if (player instanceof ServerPlayer serverPlayer) {
                        CriteriaTriggers.PLACED_BLOCK.trigger(serverPlayer, blockpos1, itemstack);
                    }

                    if (!player.getAbilities().instabuild) {
                        itemstack.shrink(1);
                    }

                    player.awardStat(Stats.ITEM_USED.get(this));
                    level.playSound(player, blockpos, SoundEvents.CROP_PLANTED, SoundSource.BLOCKS, 1.0F, 1.0F);
                    return InteractionResultHolder.success(itemstack);
                }
            }

            return InteractionResultHolder.fail(itemstack);
        }
    }
}
