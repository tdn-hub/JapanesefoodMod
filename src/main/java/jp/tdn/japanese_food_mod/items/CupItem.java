package jp.tdn.japanese_food_mod.items;

import jp.tdn.japanese_food_mod.init.JPItems;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import java.util.List;

public class CupItem extends SimpleItem {
    public CupItem(){
        super();
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity player, Hand hand) {
        List<AreaEffectCloudEntity> AECEntityList = worldIn.getEntitiesWithinAABB(AreaEffectCloudEntity.class, player.getBoundingBox().grow(2.0D), (p_210311_0_) -> {
            return p_210311_0_ != null && p_210311_0_.isAlive() && p_210311_0_.getOwner() instanceof EnderDragonEntity;
        });
        ItemStack inHand = player.getHeldItem(hand);
        if (!AECEntityList.isEmpty()) {
            AreaEffectCloudEntity AECEntity = (AreaEffectCloudEntity)AECEntityList.get(0);
            AECEntity.setRadius(AECEntity.getRadius() - 0.5F);
            worldIn.playSound((PlayerEntity)null, player.posX, player.posY, player.posZ, SoundEvents.ITEM_BOTTLE_FILL_DRAGONBREATH, SoundCategory.NEUTRAL, 1.0F, 1.0F);
            return new ActionResult(ActionResultType.SUCCESS, this.turnBottleIntoItem(inHand, player, new ItemStack(Items.DRAGON_BREATH)));
        } else {
            RayTraceResult rayTrace = rayTrace(worldIn, player, RayTraceContext.FluidMode.SOURCE_ONLY);
            if (rayTrace.getType() == RayTraceResult.Type.MISS) {
                return new ActionResult(ActionResultType.PASS, inHand);
            } else {
                if (rayTrace.getType() == RayTraceResult.Type.BLOCK) {
                    BlockPos pos = ((BlockRayTraceResult)rayTrace).getPos();
                    if (!worldIn.isBlockModifiable(player, pos)) {
                        return new ActionResult(ActionResultType.PASS, inHand);
                    }

                    if (worldIn.getFluidState(pos).isTagged(FluidTags.WATER)) {
                        worldIn.playSound(player, player.posX, player.posY, player.posZ, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.NEUTRAL, 1.0F, 1.0F);
                        return new ActionResult(ActionResultType.SUCCESS, this.turnBottleIntoItem(inHand, player, new ItemStack(JPItems.CUP_WITH_WATER)));
                    }
                }

                return new ActionResult(ActionResultType.PASS, inHand);
            }
        }
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, PlayerEntity player, LivingEntity entity, Hand hand) {
//        ItemStack inHand = player.getHeldItem(hand);
//        JapaneseFoodMod.LOGGER.info(stack);
//        JapaneseFoodMod.LOGGER.info(entity.getEntityString());
        if(stack.getItem() == JPItems.CUP){
            if(entity.getEntityString().equals("minecraft:cow")){
                turnBottleIntoItem(stack, player, new ItemStack(JPItems.CUP_WITH_MILK));
                player.getEntityWorld().playSound(player, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_COW_MILK, SoundCategory.NEUTRAL, 1.0F, 1.0F);
            }
        }
        return false;
    }

    protected ItemStack turnBottleIntoItem(ItemStack p_185061_1_, PlayerEntity p_185061_2_, ItemStack p_185061_3_) {
        p_185061_1_.shrink(1);
        p_185061_2_.addStat(Stats.ITEM_USED.get(this));
        if (p_185061_1_.isEmpty()) {
            return p_185061_3_;
        } else {
            if (!p_185061_2_.inventory.addItemStackToInventory(p_185061_3_)) {
                p_185061_2_.dropItem(p_185061_3_, false);
            }

            return p_185061_1_;
        }
    }
}
