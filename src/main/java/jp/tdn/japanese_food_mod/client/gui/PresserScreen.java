package jp.tdn.japanese_food_mod.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import jp.tdn.japanese_food_mod.JapaneseFoodMod;
import jp.tdn.japanese_food_mod.blocks.tileentity.PresserTileEntity;
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
        this.xSize = 175;
        this.ySize = 168;
    }

    @Override
    public void func_230430_a_(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.func_230446_a_(matrixStack);
        super.func_230430_a_(matrixStack, mouseX, mouseY, partialTicks);
        this.func_230459_a_(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void func_230451_b_(MatrixStack matrixStack, int mouseX, int mouseY) {
        this.field_230712_o_.func_238422_b_(matrixStack, this.field_230704_d_, (float)(this.xSize / 2), 6.0f, 4210752);
        this.field_230712_o_.func_238422_b_(matrixStack, this.playerInventory.getDisplayName(), 8.0F, (float) (this.ySize - 96 + 2), 4210752);

    }

    @Override
    protected void func_230450_a_(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);

        this.field_230706_i_.getTextureManager().bindTexture(BACKGROUND_TEXTURE);

        int startX = this.guiLeft;
        int startY = this.guiTop;

        this.func_238474_b_(matrixStack , startX, startY, 0, 0, this.xSize, this.ySize);

        final PresserTileEntity tileEntity = container.tileEntity;
        if(tileEntity.pressedTimeLeft > 0){
            int arrowHeight = getPressedTimeScaled();
            this.func_238474_b_(
                    matrixStack,
                    startX + 34, startY + 32,
                    176, 0,
                    16, arrowHeight
                    );
        }

        if(tileEntity.oilRemaining > 0){
            int oilRemaining = getOilRemainingScaled();
            this.func_238474_b_(
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
