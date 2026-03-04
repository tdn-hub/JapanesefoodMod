package jp.tdn.japanese_food_mod.client.gui;

import jp.tdn.japanese_food_mod.JapaneseFoodMod;
import jp.tdn.japanese_food_mod.blocks.tileentity.PresserTileEntity;
import jp.tdn.japanese_food_mod.container.PresserContainer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class PresserScreen extends AbstractContainerScreen<PresserContainer> {
    private static final ResourceLocation BACKGROUND_TEXTURE = ResourceLocation.fromNamespaceAndPath(JapaneseFoodMod.MOD_ID, "textures/gui/container/presser.png");

    public PresserScreen(final PresserContainer container, final Inventory inventory, final Component title){
        super(container, inventory, title);
        this.imageWidth = 175;
        this.imageHeight = 168;
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
        graphics.drawString(this.font, this.playerInventoryTitle, 8, this.imageHeight - 96 + 5, 4210752, false);
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        int startX = this.leftPos;
        int startY = this.topPos;

        graphics.blit(BACKGROUND_TEXTURE, startX, startY, 0, 0, this.imageWidth, this.imageHeight);

        final PresserTileEntity tileEntity = menu.tileEntity;
        if(tileEntity.pressedTimeLeft > 0){
            int arrowHeight = getPressedTimeScaled();
            graphics.blit(
                    BACKGROUND_TEXTURE,
                    startX + 34, startY + 32,
                    176, 0,
                    16, arrowHeight
                    );
        }

        if(tileEntity.oilRemaining > 0){
            int oilRemaining = getOilRemainingScaled();
            graphics.blit(
                    BACKGROUND_TEXTURE,
                    startX + 22, startY + 59 + 13 - oilRemaining,
                    176, 22,
                    40, oilRemaining
            );
        }
    }

    private int getPressedTimeScaled(){
        final PresserTileEntity tileEntity = this.menu.tileEntity;
        final short pressedTimeLeft = tileEntity.pressedTimeLeft;
        final short maxPressedTime = tileEntity.maxPressedTime;
        if(pressedTimeLeft <= 0 || maxPressedTime <= 0) return 0;
        return (maxPressedTime - pressedTimeLeft) * 20 / maxPressedTime;
    }

    private int getOilRemainingScaled(){
        final PresserTileEntity tileEntity = this.menu.tileEntity;
        final short oilRemaining = tileEntity.oilRemaining;
        if(oilRemaining <= 0) return 0;
        return Math.round((float)oilRemaining / tileEntity.maxOilRemaining * 13);
    }
}
