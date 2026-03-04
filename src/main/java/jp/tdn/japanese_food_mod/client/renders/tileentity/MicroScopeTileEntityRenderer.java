package jp.tdn.japanese_food_mod.client.renders.tileentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import jp.tdn.japanese_food_mod.blocks.MicroScopeBlock;
import jp.tdn.japanese_food_mod.blocks.tileentity.MicroScopeTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class MicroScopeTileEntityRenderer implements BlockEntityRenderer<MicroScopeTileEntity> {
    public MicroScopeTileEntityRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(MicroScopeTileEntity tileEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        net.minecraft.core.Direction direction = tileEntity.getBlockState().getValue(MicroScopeBlock.DIRECTION);
        ItemStack item = tileEntity.getInventory();

        if (!item.isEmpty()) {
            poseStack.pushPose();
            poseStack.translate(0.5f, 0.5f, 0.5f);
            poseStack.mulPose(Axis.YP.rotationDegrees(-direction.toYRot()));
            poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
            poseStack.translate(0f, 0f, 0.1f);
            poseStack.scale(0.3f, 0.3f, 0.3f);
            ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
            itemRenderer.renderStatic(item, ItemDisplayContext.FIXED, packedLight, packedOverlay, poseStack, bufferSource, tileEntity.getLevel(), 0);
            poseStack.popPose();
        }
    }
}
