package jp.tdn.japanese_food_mod.client.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import jp.tdn.japanese_food_mod.JapaneseFoodMod;
import jp.tdn.japanese_food_mod.blocks.tileentity.FurnaceCauldronTileEntity;
import jp.tdn.japanese_food_mod.container.FurnaceCauldronContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class FurnaceCauldronScreen extends ContainerScreen<FurnaceCauldronContainer> {
    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(JapaneseFoodMod.MOD_ID, "textures/gui/container/furnace_cauldron.png");

    public FurnaceCauldronScreen(final FurnaceCauldronContainer container, final PlayerInventory inventory, final ITextComponent title){
        super(container, inventory, title);
        this.ySize = 172;
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        this.renderBackground();
        super.render(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);

        int relMouseX = mouseX - this.guiLeft;
        int relMouseY = mouseY - this.guiTop;
        final FurnaceCauldronTileEntity tileEntity = this.container.tileEntity;
        boolean arrowHovered = relMouseX > 79 && relMouseX < 104 && relMouseY > 34 && relMouseY < 50;
        if(arrowHovered && tileEntity.heatingTimeLeft > 0){
            String tooltip = new TranslationTextComponent(
                    "gui." + JapaneseFoodMod.MOD_ID + ".heatingTimeProgress",
                    (short)(((float)(tileEntity.maxHeatingTime - tileEntity.heatingTimeLeft) / (float)tileEntity.maxHeatingTime) * 100),
                    "%"
            ).getFormattedText();
            this.renderTooltip(tooltip, mouseX, mouseY);
        }

        arrowHovered = relMouseX > 11 && relMouseX < 64 && relMouseY > 31 && relMouseY < 67;
        if(arrowHovered){
            String tooltip = new TranslationTextComponent(
                    "gui." + JapaneseFoodMod.MOD_ID + ".waterRemaining",
                    tileEntity.waterRemaining, tileEntity.maxWater
            ).getFormattedText();
            this.renderTooltip(tooltip, mouseX, mouseY);
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX_, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX_, mouseY);
        String s = this.title.getFormattedText();
        this.font.drawString(s, (float)(this.xSize / 2 - this.font.getStringWidth(s) / 2), 6.0F, 0x404040);
        this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8.0F, (float)(this.ySize - 94 + 2), 0x404040);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        getMinecraft().getTextureManager().bindTexture(BACKGROUND_TEXTURE);
        int startX = this.guiLeft;
        int startY = this.guiTop;

        this.blit(startX, startY, 0, 0, this.xSize, this.ySize);

        final FurnaceCauldronTileEntity tileEntity = container.tileEntity;
        if(tileEntity.heatingTimeLeft > 0){
            int arrowWidth = getIdentifiedTimeScaled();
            this.blit(
                    startX + 79, startY + 34,
                    176, 0,
                    arrowWidth, 16
                    );
        }

        if(tileEntity.waterRemaining > 0){
            int waterRemaining = getWaterRemainingScaled();
            //JapaneseFoodMod.LOGGER.info(waterRemaining);
            this.blit(
                    startX + 14, startY + 31 + (36 - waterRemaining),
                    176, 17,
                    47, waterRemaining
            );
        }
    }

    private int getIdentifiedTimeScaled(){
        final FurnaceCauldronTileEntity tileEntity = this.container.tileEntity;
        final int heatingTimeLeft = tileEntity.heatingTimeLeft;
        final int maxHeatingTime = tileEntity.maxHeatingTime;
        if(heatingTimeLeft <= 0 || maxHeatingTime <= 0) return 0;
        return (maxHeatingTime - heatingTimeLeft) * 24 / maxHeatingTime;
    }

    private int getWaterRemainingScaled(){
        final FurnaceCauldronTileEntity tileEntity = this.container.tileEntity;
        final int waterRemaining = tileEntity.waterRemaining;
        if(waterRemaining <= 0) return 0;
        return Math.round((float)waterRemaining / tileEntity.maxWater * 36);
    }
}
