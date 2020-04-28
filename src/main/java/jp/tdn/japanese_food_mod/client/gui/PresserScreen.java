package jp.tdn.japanese_food_mod.client.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import jp.tdn.japanese_food_mod.JapaneseFoodMod;
import jp.tdn.japanese_food_mod.blocks.tileentity.MicroScopeTileEntity;
import jp.tdn.japanese_food_mod.blocks.tileentity.PresserTileEntity;
import jp.tdn.japanese_food_mod.container.MicroScopeContainer;
import jp.tdn.japanese_food_mod.container.PresserContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class PresserScreen extends ContainerScreen<PresserContainer> {
    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(JapaneseFoodMod.MOD_ID, "textures/gui/container/presser.png");

    public PresserScreen(final PresserContainer container, final PlayerInventory inventory, final ITextComponent title){
        super(container, inventory, title);
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        this.renderBackground();
        super.render(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);

        int relMouseX = mouseX - this.guiLeft;
        int relMouseY = mouseY - this.guiTop;
        final PresserTileEntity tileEntity = this.container.tileEntity;
        boolean arrowHovered = relMouseX > 34 && relMouseX < 50 && relMouseY > 32 && relMouseY < 52;
        if(arrowHovered && tileEntity.maxPressedTime > 0 && tileEntity.oilRemaining < 500){
            String tooltip = new TranslationTextComponent(
                    "gui." + JapaneseFoodMod.MOD_ID + ".pressingTimeProgress",
                    (short)(((float)(tileEntity.maxPressedTime - tileEntity.pressedTimeLeft) / (float)tileEntity.maxPressedTime) * 100),
                    "%"
            ).getFormattedText();
            this.renderTooltip(tooltip, mouseX, mouseY);
        }

        arrowHovered = relMouseX > 22 && relMouseX < 62 && relMouseY > 59 && relMouseY < 74;
        if(arrowHovered){
            String tooltip = new TranslationTextComponent(
                    "gui." + JapaneseFoodMod.MOD_ID + ".oilRemaining",
                    tileEntity.oilRemaining, tileEntity.maxOilRemaining
            ).getFormattedText();
            this.renderTooltip(tooltip, mouseX, mouseY);
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX_, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX_, mouseY);
        String s = this.title.getFormattedText();
        this.font.drawString(s, (float)(this.xSize / 1.5 - this.font.getStringWidth(s) / 2), 6.0F, 0x404040);
        //this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8.0F, (float)(this.ySize - 96 + 2), 0x404040);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        getMinecraft().getTextureManager().bindTexture(BACKGROUND_TEXTURE);
        int startX = this.guiLeft;
        int startY = this.guiTop;

        this.blit(startX, startY, 0, 0, this.xSize, this.ySize);

        final PresserTileEntity tileEntity = container.tileEntity;
        if(tileEntity.pressedTimeLeft > 0){
            int arrowHeight = getPressedTimeScaled();
            this.blit(
                    startX + 34, startY + 32,
                    176, 0,
                    16, arrowHeight
                    );
        }

        if(tileEntity.oilRemaining > 0){
            int oilRemaining = getOilRemainingScaled();
            this.blit(
                    startX + 22, startY + 59 + 13 - oilRemaining,
                    176, 22,
                    40, oilRemaining
            );
        }
    }

    private int getPressedTimeScaled(){
        final PresserTileEntity tileEntity = this.container.tileEntity;
        final short pressedTimeLeft = tileEntity.pressedTimeLeft;
        final short maxPressedTime = tileEntity.maxPressedTime;
        if(pressedTimeLeft <= 0 || maxPressedTime <= 0) return 0;
        return (maxPressedTime - pressedTimeLeft) * 20 / maxPressedTime;
    }

    private int getOilRemainingScaled(){
        final PresserTileEntity tileEntity = this.container.tileEntity;
        final short oilRemaining = tileEntity.oilRemaining;
        if(oilRemaining <= 0) return 0;
        return Math.round((float)oilRemaining / tileEntity.maxOilRemaining * 13);
    }
}
