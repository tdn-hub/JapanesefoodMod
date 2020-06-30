package jp.tdn.japanese_food_mod.items;

import jp.tdn.japanese_food_mod.init.JPBlocks;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.event.ForgeEventFactory;

import javax.annotation.Nonnull;

public class RiceSeedlingItem extends BlockItem {
    public RiceSeedlingItem(Block block, Properties properties){
        super(block, properties);
    }

    @Nonnull
    public ActionResultType onItemUse(ItemUseContext itemUseContext) {
        return ActionResultType.PASS;
    }

    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity playerentity, @Nonnull Hand hand) {
        ItemStack itemstack = playerentity.getHeldItem(hand);
        RayTraceResult raytraceresult = rayTrace(world, playerentity, RayTraceContext.FluidMode.SOURCE_ONLY);
        if (raytraceresult.getType() == RayTraceResult.Type.MISS) {
            return new ActionResult<>(ActionResultType.PASS, itemstack);
        } else {
            if (raytraceresult.getType() == RayTraceResult.Type.BLOCK) {
                BlockRayTraceResult blockraytraceresult = (BlockRayTraceResult)raytraceresult;
                BlockPos blockpos = blockraytraceresult.getPos();
                Direction direction = blockraytraceresult.getFace();
                if (!world.isBlockModifiable(playerentity, blockpos) || !playerentity.canPlayerEdit(blockpos.offset(direction), direction, itemstack)) {
                    return new ActionResult<>(ActionResultType.FAIL, itemstack);
                }

                BlockPos blockpos1 = blockpos.up();
                BlockState blockstate = world.getBlockState(blockpos);
                Material material = blockstate.getMaterial();
                FluidState ifluidstate = world.getFluidState(blockpos);
                if ((ifluidstate.getFluid() == Fluids.WATER) && world.isAirBlock(blockpos1)) {
                    BlockSnapshot blocksnapshot = BlockSnapshot.create(world, blockpos1);
                    world.setBlockState(blockpos1, JPBlocks.RICE_PLANT.getDefaultState(), 11);
                    if (ForgeEventFactory.onBlockPlace(playerentity, blocksnapshot, Direction.UP)) {
                        blocksnapshot.restore(true, false);
                        return new ActionResult<>(ActionResultType.FAIL, itemstack);
                    }

                    if (playerentity instanceof ServerPlayerEntity) {
                        CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity)playerentity, blockpos1, itemstack);
                    }

                    if (!playerentity.abilities.isCreativeMode) {
                        itemstack.shrink(1);
                    }

                    playerentity.addStat(Stats.ITEM_USED.get(this));
                    world.playSound(playerentity, blockpos, SoundEvents.ITEM_CROP_PLANT, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
                }
            }

            return new ActionResult<>(ActionResultType.FAIL, itemstack);
        }
    }
}
