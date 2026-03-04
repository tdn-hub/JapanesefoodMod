package jp.tdn.japanese_food_mod.client.gui;

import jp.tdn.japanese_food_mod.JapaneseFoodMod;
import jp.tdn.japanese_food_mod.blocks.tileentity.MicroScopeTileEntity;
import jp.tdn.japanese_food_mod.container.MicroScopeContainer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class MicroScopeScreen extends AbstractContainerScreen<MicroScopeContainer> {
    private static final ResourceLocation BACKGROUND_TEXTURE = ResourceLocation.fromNamespaceAndPath(JapaneseFoodMod.MOD_ID, "textures/gui/container/microscope.png");

    public MicroScopeScreen(final MicroScopeContainer container, final Inventory inventory, final Component title){
        super(container, inventory, title);
        this.imageHeight = 191;
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
        graphics.drawString(this.font, this.playerInventoryTitle, 8, this.imageHeight - 96 + 4, 4210752, false);
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        int startX = this.leftPos;
        int startY = this.topPos;

        graphics.blit(BACKGROUND_TEXTURE, startX, startY, 0, 0, this.imageWidth, this.imageHeight);

        final MicroScopeTileEntity tileEntity = menu.tileEntity;
        if(tileEntity.identifiedTimeLeft > 0){
            int arrowHeight = getIdentifiedTimeScaled();
            graphics.blit(
                    BACKGROUND_TEXTURE,
                    startX + 87, startY + 44,
                    176, 0,
                    27, 28 - arrowHeight
                    );
        }else{
            graphics.blit(
                    BACKGROUND_TEXTURE,
                    startX + 87, startY + 44,
                    176, 0,
                    27, 28);
        }
    }

    private int getIdentifiedTimeScaled(){
        final MicroScopeTileEntity tileEntity = this.menu.tileEntity;
        final short identifiedTimeLeft = tileEntity.identifiedTimeLeft;
        final short maxIdentifiedTime = tileEntity.maxIdentifiedTime;
        if(identifiedTimeLeft <= 0 || maxIdentifiedTime <= 0) return 0;
        return (maxIdentifiedTime - identifiedTimeLeft) * 28 / maxIdentifiedTime;
    }
}
