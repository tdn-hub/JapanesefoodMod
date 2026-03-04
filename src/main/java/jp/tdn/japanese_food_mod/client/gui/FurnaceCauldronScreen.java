package jp.tdn.japanese_food_mod.client.gui;

import jp.tdn.japanese_food_mod.JapaneseFoodMod;
import jp.tdn.japanese_food_mod.blocks.tileentity.FurnaceCauldronTileEntity;
import jp.tdn.japanese_food_mod.container.FurnaceCauldronContainer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class FurnaceCauldronScreen extends AbstractContainerScreen<FurnaceCauldronContainer> {
    private static final ResourceLocation BACKGROUND_TEXTURE = ResourceLocation.fromNamespaceAndPath(JapaneseFoodMod.MOD_ID, "textures/gui/container/furnace_cauldron.png");

    public FurnaceCauldronScreen(final FurnaceCauldronContainer container, final Inventory inventory, final Component title){
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
        graphics.drawString(this.font, this.title, (this.imageWidth / 2) - (this.font.width(this.title) / 2), 6, 4210752, false);
        graphics.drawString(this.font, this.playerInventoryTitle, 8, this.imageHeight - 96 + 6, 4210752, false);
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        int startX = this.leftPos;
        int startY = this.topPos;

        graphics.blit(BACKGROUND_TEXTURE, startX, startY, 0, 0, this.imageWidth, this.imageHeight);

        final FurnaceCauldronTileEntity tileEntity = menu.tileEntity;
        if(tileEntity.heatingTimeLeft > 0){
            int arrowWidth = getIdentifiedTimeScaled();
            graphics.blit(
                    BACKGROUND_TEXTURE,
                    startX + 79, startY + 34,
                    176, 0,
                    arrowWidth, 16
                    );
        }

        if(tileEntity.waterRemaining > 0){
            int waterRemaining = getWaterRemainingScaled();
            graphics.blit(
                    BACKGROUND_TEXTURE,
                    startX + 14, startY + 32 + (36 - waterRemaining),
                    176, 17,
                    48, waterRemaining
            );
        }
    }

    private int getIdentifiedTimeScaled(){
        final FurnaceCauldronTileEntity tileEntity = this.menu.tileEntity;
        final int heatingTimeLeft = tileEntity.heatingTimeLeft;
        final int maxHeatingTime = tileEntity.maxHeatingTime;
        if(heatingTimeLeft <= 0 || maxHeatingTime <= 0) return 0;
        return (maxHeatingTime - heatingTimeLeft) * 24 / maxHeatingTime;
    }

    private int getWaterRemainingScaled(){
        final FurnaceCauldronTileEntity tileEntity = this.menu.tileEntity;
        final int waterRemaining = tileEntity.waterRemaining;
        if(waterRemaining <= 0) return 0;
        return Math.round((float)waterRemaining / tileEntity.maxWater * 36);
    }
}
