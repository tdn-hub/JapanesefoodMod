package jp.tdn.japanese_food_mod.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import jp.tdn.japanese_food_mod.JapaneseFoodMod;
import jp.tdn.japanese_food_mod.blocks.tileentity.WoodenBucketTileEntity;
import jp.tdn.japanese_food_mod.container.WoodenBucketContainer;
import net.minecraft.client.gui.IHasContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class WoodenBucketScreen extends ContainerScreen<WoodenBucketContainer> implements IHasContainer<WoodenBucketContainer> {
    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(JapaneseFoodMod.MOD_ID, "textures/gui/container/wooden_bucket.png");
    private int textureXSize;
    private int textureYSize;

    public WoodenBucketScreen(final WoodenBucketContainer container, final PlayerInventory inventory, final ITextComponent title){
        super(container, inventory, title);
        this.imageHeight = 172;
        this.imageWidth = 175;
        this.textureXSize = 256;
        this.textureYSize = 256;
    }

    @Override
    public void render(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
        this.renderBackground(p_230430_1_);
        super.render(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
    }

    @Override
    protected void renderBg(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);

        this.minecraft.getTextureManager().bind(BACKGROUND_TEXTURE);

        int startX = this.leftPos;
        int startY = this.topPos;

        this.blit(matrixStack , startX, startY, 0, 0, this.imageWidth, this.imageHeight);

        final WoodenBucketTileEntity tileEntity = this.menu.tileEntity;
        if(tileEntity.fermentationTimeLeft > 0){
            int arrowWidth = getIdentifiedTimeScaled();
            this.blit(
                    matrixStack,
                    startX + 79, startY + 34,
                    176, 0,
                    arrowWidth, 16
            );
        }
    }

    private int getIdentifiedTimeScaled(){
        final WoodenBucketTileEntity tileEntity = this.menu.tileEntity;
        final short fermentationTimeLeft = tileEntity.fermentationTimeLeft;
        final short maxFermentationTime = tileEntity.maxFermentationTime;
        if(fermentationTimeLeft <= 0 || maxFermentationTime <= 0) return 0;
        return (maxFermentationTime - fermentationTimeLeft) * 24 / maxFermentationTime;
    }
}
