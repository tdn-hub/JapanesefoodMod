package jp.tdn.japanese_food_mod.client.renders.tileentity;

import com.mojang.blaze3d.platform.GlStateManager;
import jp.tdn.japanese_food_mod.blocks.MicroScopeBlock;
import jp.tdn.japanese_food_mod.blocks.tileentity.MicroScopeTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;

public class MicroScopeTileEntityRenderer extends TileEntityRenderer<MicroScopeTileEntity> {
    public MicroScopeTileEntityRenderer(){}

    @Override
    public void render(MicroScopeTileEntity tileEntity, double x, double y, double z, float partialTicks, int destroyStage) {
        Direction direction = tileEntity.getBlockState().get(MicroScopeBlock.DIRECTION);
        ItemStack item = tileEntity.getInventory();

        if(!item.isEmpty()){
            GlStateManager.pushMatrix();
            GlStateManager.translatef((float) x + 0.5f, (float) y + 0.5f, (float) z + 0.5f);
            GlStateManager.rotatef(-direction.getHorizontalAngle(), 0f,1.0f,0f);
            GlStateManager.rotatef(90.0f, 1.0f, 0f, 0f);
            GlStateManager.translatef(0f, 0f, 0.1f);
            GlStateManager.scalef(0.3f, 0.3f, 0.3f);
            Minecraft.getInstance().getItemRenderer().renderItem(item, ItemCameraTransforms.TransformType.FIXED);
            GlStateManager.popMatrix();
        }
    }
}
