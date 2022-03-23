package jp.tdn.japanese_food_mod.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import jp.tdn.japanese_food_mod.JapaneseFoodMod;
import jp.tdn.japanese_food_mod.blocks.tileentity.FurnaceCauldronTileEntity;
import jp.tdn.japanese_food_mod.container.FurnaceCauldronContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class FurnaceCauldronScreen extends ContainerScreen<FurnaceCauldronContainer> {
    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(JapaneseFoodMod.MOD_ID, "textures/gui/container/furnace_cauldron.png");
    private int textureXSize;
    private int textureYSize;

    public FurnaceCauldronScreen(final FurnaceCauldronContainer container, final PlayerInventory inventory, final ITextComponent title){
        super(container, inventory, title);
        this.xSize = 175;
        this.ySize = 172;
        this.textureXSize = 256;
        this.textureYSize = 256;
    }


    @Override
    public void render(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
        this.renderBackground(p_230430_1_);
        super.render(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
        this.renderHoveredTooltip(p_230430_1_, p_230430_2_, p_230430_3_);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY) {
        this.font.func_238422_b_(matrixStack, this.title.func_241878_f(), (float)(this.xSize / 2) - (float)(this.title.getString().length() / 2) * 5, 6.0f, 4210752);
        this.font.func_238422_b_(matrixStack, this.playerInventory.getDisplayName().func_241878_f(), 8.0F, (float) (this.ySize - 96 + 6), 4210752)    ;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);

        this.minecraft.getTextureManager().bindTexture(BACKGROUND_TEXTURE);

        int startX = this.guiLeft;
        int startY = this.guiTop;

        this.blit(matrixStack , startX, startY, 0, 0, this.xSize, this.ySize);
        final FurnaceCauldronTileEntity tileEntity = container.tileEntity;
        if(tileEntity.heatingTimeLeft > 0){
            int arrowWidth = getIdentifiedTimeScaled();
            this.blit(
                    matrixStack,
                    startX + 79, startY + 34,
                    176, 0,
                    arrowWidth, 16
                    );
        }

        if(tileEntity.waterRemaining > 0){
            int waterRemaining = getWaterRemainingScaled();
            //JapaneseFoodMod.LOGGER.info(waterRemaining);
            this.blit(
                    matrixStack,
                    startX + 14, startY + 32 + (36 - waterRemaining),
                    176, 17,
                    48, waterRemaining
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
