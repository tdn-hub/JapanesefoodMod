package jp.tdn.japanese_food_mod.client.gui;

import jp.tdn.japanese_food_mod.JapaneseFoodMod;
import jp.tdn.japanese_food_mod.blocks.tileentity.WoodenBucketTileEntity;
import jp.tdn.japanese_food_mod.container.WoodenBucketContainer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class WoodenBucketScreen extends AbstractContainerScreen<WoodenBucketContainer> {
    private static final ResourceLocation BACKGROUND_TEXTURE = ResourceLocation.fromNamespaceAndPath(JapaneseFoodMod.MOD_ID, "textures/gui/container/wooden_bucket.png");

    public WoodenBucketScreen(final WoodenBucketContainer container, final Inventory inventory, final Component title){
        super(container, inventory, title);
        this.imageWidth = 175;
        this.imageHeight = 172;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics, mouseX, mouseY, partialTick);
        super.render(graphics, mouseX, mouseY, partialTick);
        this.renderTooltip(graphics, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
        graphics.drawCenteredString(this.font, this.title, this.imageWidth / 2, 6, 4210752);
        graphics.drawString(this.font, this.playerInventoryTitle, 8, this.imageHeight - 96 + 6, 4210752, false);
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        int startX = this.leftPos;
        int startY = this.topPos;

        graphics.blit(BACKGROUND_TEXTURE, startX, startY, 0, 0, this.imageWidth, this.imageHeight);

        final WoodenBucketTileEntity tileEntity = menu.tileEntity;
        if(tileEntity.fermentationTimeLeft > 0){
            int arrowWidth = getIdentifiedTimeScaled();
            graphics.blit(
                    BACKGROUND_TEXTURE,
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
