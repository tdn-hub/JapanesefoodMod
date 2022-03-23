package jp.tdn.japanese_food_mod.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import jp.tdn.japanese_food_mod.JapaneseFoodMod;
import jp.tdn.japanese_food_mod.blocks.tileentity.PresserTileEntity;
import jp.tdn.japanese_food_mod.container.PresserContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class PresserScreen extends ContainerScreen<PresserContainer> {
    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(JapaneseFoodMod.MOD_ID, "textures/gui/container/presser.png");

    public PresserScreen(final PresserContainer container, final PlayerInventory inventory, final ITextComponent title){
        super(container, inventory, title);
        this.xSize = 175;
        this.ySize = 168;
    }

    @Override
    public void render(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
        this.renderBackground(p_230430_1_);
        super.render(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
        this.renderHoveredTooltip(p_230430_1_, p_230430_2_, p_230430_3_);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY) {
        this.font.func_238422_b_(matrixStack, this.title.func_241878_f(), (float)(this.xSize / 2), 6.0f, 4210752);
        this.font.func_238422_b_(matrixStack, this.playerInventory.getDisplayName().func_241878_f(), 8.0F, (float) (this.ySize - 96 + 5), 4210752);

    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);

        this.minecraft.getTextureManager().bindTexture(BACKGROUND_TEXTURE);

        int startX = this.guiLeft;
        int startY = this.guiTop;

        this.blit(matrixStack , startX, startY, 0, 0, this.xSize, this.ySize);

        final PresserTileEntity tileEntity = container.tileEntity;
        if(tileEntity.pressedTimeLeft > 0){
            int arrowHeight = getPressedTimeScaled();
            this.blit(
                    matrixStack,
                    startX + 34, startY + 32,
                    176, 0,
                    16, arrowHeight
                    );
        }

        if(tileEntity.oilRemaining > 0){
            int oilRemaining = getOilRemainingScaled();
            this.blit(
                    matrixStack,
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
