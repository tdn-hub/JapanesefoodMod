package jp.tdn.japanese_food_mod.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import jp.tdn.japanese_food_mod.JapaneseFoodMod;
import jp.tdn.japanese_food_mod.blocks.tileentity.MicroScopeTileEntity;
import jp.tdn.japanese_food_mod.container.MicroScopeContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class MicroScopeScreen extends ContainerScreen<MicroScopeContainer> {
    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(JapaneseFoodMod.MOD_ID, "textures/gui/container/microscope.png");

    public MicroScopeScreen(final MicroScopeContainer container, final PlayerInventory inventory, final ITextComponent title){
        super(container, inventory, title);
        this.ySize = 191;
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
        this.font.func_238422_b_(matrixStack, this.playerInventory.getDisplayName().func_241878_f(), 8.0F, (float) (this.ySize - 96 + 4), 4210752);

    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);

        this.minecraft.getTextureManager().bindTexture(BACKGROUND_TEXTURE);

        int startX = this.guiLeft;
        int startY = this.guiTop;

        this.blit(matrixStack , startX, startY, 0, 0, this.xSize, this.ySize);

        final MicroScopeTileEntity tileEntity = container.tileEntity;
        if(tileEntity.identifiedTimeLeft > 0){
            int arrowHeight = getIdentifiedTimeScaled();
            this.blit(
                    matrixStack,
                    startX + 87, startY + 44,
                    176, 0,
                    27, 28 - arrowHeight
                    );
        }else{
            this.blit(
                    matrixStack,
                    startX + 87, startY + 44,
                    176, 0,
                    27, 28);
        }
    }


    private int getIdentifiedTimeScaled(){
        final MicroScopeTileEntity tileEntity = this.container.tileEntity;
        final short identifiedTimeLeft = tileEntity.identifiedTimeLeft;
        final short maxIdentifiedTime = tileEntity.maxIdentifiedTime;
        if(identifiedTimeLeft <= 0 || maxIdentifiedTime <= 0) return 0;
        return (maxIdentifiedTime - identifiedTimeLeft) * 28 / maxIdentifiedTime;
    }
}
