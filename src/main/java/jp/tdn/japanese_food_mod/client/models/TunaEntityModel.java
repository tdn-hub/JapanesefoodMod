package jp.tdn.japanese_food_mod.client.models;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;

public class TunaEntityModel<T extends LivingEntity> extends EntityModel<T> {
    private final ModelRenderer bone;
    private final ModelRenderer body;
    private final ModelRenderer side3;
    private final ModelRenderer side4;
    private final ModelRenderer side5;
    private final ModelRenderer side6;
    private final ModelRenderer head;
    private final ModelRenderer mouse;
    private final ModelRenderer mouse_under;
    private final ModelRenderer mouse_upper;
    private final ModelRenderer mouse_upper2;
    private final ModelRenderer side;
    private final ModelRenderer side2;
    private final ModelRenderer side7;
    private final ModelRenderer side8;
    private final ModelRenderer side9;
    private final ModelRenderer side10;
    private final ModelRenderer tail;
    private final ModelRenderer tail_side;
    private final ModelRenderer tail_side2;
    private final ModelRenderer tail_side3;
    private final ModelRenderer tail_side4;
    private final ModelRenderer back_tail;
    private final ModelRenderer hire;
    private final ModelRenderer hire2;
    private final ModelRenderer back_tail_side;
    private final ModelRenderer back_tail_side2;
    private final ModelRenderer back_tail_side3;
    private final ModelRenderer back_tail_side4;
    private final ModelRenderer sebire;
    private final ModelRenderer sebire2;
    private final ModelRenderer harabire;
    private final ModelRenderer harabire2;
    private final ModelRenderer hire3;
    private final ModelRenderer hire4;

    public TunaEntityModel() {
        textureWidth = 64;
        textureHeight = 64;

        bone = new ModelRenderer(this);
        bone.setRotationPoint(0.0F, 24.0F, 0.0F);


        body = new ModelRenderer(this);
        body.setRotationPoint(0.0F, 0.0F, -2.0F);
        bone.addChild(body);
        body.setTextureOffset(20, 30).addBox(-0.5F, -8.0F, -4.0F, 1.0F, 7.0F, 11.0F, 0.0F, false);
        body.setTextureOffset(20, 48).addBox(0.5F, -7.0F, -4.0F, 1.0F, 5.0F, 11.0F, 0.0F, false);
        body.setTextureOffset(20, 48).addBox(-1.5F, -7.0F, -4.0F, 1.0F, 5.0F, 11.0F, 0.0F, false);
        body.setTextureOffset(20, 16).addBox(-2.5F, -6.0F, -4.0F, 1.0F, 3.0F, 11.0F, 0.0F, false);
        body.setTextureOffset(20, 16).addBox(1.5F, -6.0F, -4.0F, 1.0F, 3.0F, 11.0F, 0.0F, false);

        side3 = new ModelRenderer(this);
        side3.setRotationPoint(0.0F, -4.0F, 0.0F);
        body.addChild(side3);
        setRotationAngle(side3, 0.0F, 0.0F, 0.4363F);
        side3.setTextureOffset(20, 0).addBox(-2.5F, -3.5F, -4.0F, 1.0F, 4.0F, 11.0F, 0.0F, false);

        side4 = new ModelRenderer(this);
        side4.setRotationPoint(0.0F, -4.0F, 0.0F);
        body.addChild(side4);
        setRotationAngle(side4, 0.0F, 0.0F, -0.4363F);
        side4.setTextureOffset(20, 0).addBox(1.5F, -3.5F, -4.0F, 1.0F, 4.0F, 11.0F, 0.0F, true);

        side5 = new ModelRenderer(this);
        side5.setRotationPoint(0.0F, -4.0F, 0.0F);
        body.addChild(side5);
        setRotationAngle(side5, 0.0F, 0.0F, -0.3491F);
        side5.setTextureOffset(20, 33).addBox(-2.0F, -1.5F, -4.0F, 1.0F, 4.0F, 11.0F, 0.0F, false);

        side6 = new ModelRenderer(this);
        side6.setRotationPoint(0.0F, -4.0F, 0.0F);
        body.addChild(side6);
        setRotationAngle(side6, 0.0F, 0.0F, 0.3491F);
        side6.setTextureOffset(20, 33).addBox(1.1F, -1.5F, -4.0F, 1.0F, 4.0F, 11.0F, 0.0F, true);

        head = new ModelRenderer(this);
        head.setRotationPoint(0.0F, -5.0F, -8.0F);
        bone.addChild(head);
        head.setTextureOffset(0, 29).addBox(-0.5F, -2.0F, 0.0F, 1.0F, 5.0F, 2.0F, 0.0F, false);
        head.setTextureOffset(8, 51).addBox(-1.0F, -1.0F, 0.0F, 1.0F, 2.0F, 3.0F, 0.0F, false);
        head.setTextureOffset(0, 51).addBox(0.0F, -1.0F, 0.0F, 1.0F, 2.0F, 3.0F, 0.0F, true);

        mouse = new ModelRenderer(this);
        mouse.setRotationPoint(0.5F, 0.0F, 0.0F);
        head.addChild(mouse);
        mouse.setTextureOffset(8, 47).addBox(-1.75F, -1.0F, -1.7F, 2.0F, 2.0F, 2.0F, 0.0F, false);

        mouse_under = new ModelRenderer(this);
        mouse_under.setRotationPoint(0.0F, 0.0F, 0.0F);
        mouse.addChild(mouse_under);
        setRotationAngle(mouse_under, -0.0873F, 0.0F, 0.0F);
        mouse_under.setTextureOffset(6, 31).addBox(-1.75F, 1.9128F, -3.0038F, 2.0F, 1.0F, 4.0F, 0.0F, false);

        mouse_upper = new ModelRenderer(this);
        mouse_upper.setRotationPoint(0.0F, 2.0F, -2.0F);
        mouse.addChild(mouse_upper);
        setRotationAngle(mouse_upper, -1.8326F, 0.0F, 0.0F);
        mouse_upper.setTextureOffset(10, 37).addBox(-1.75F, 0.0F, -3.0F, 2.0F, 1.0F, 2.0F, 0.0F, false);

        mouse_upper2 = new ModelRenderer(this);
        mouse_upper2.setRotationPoint(0.0F, 0.0F, -2.0F);
        mouse.addChild(mouse_upper2);
        setRotationAngle(mouse_upper2, -2.8798F, 0.0F, 0.0F);
        mouse_upper2.setTextureOffset(0, 36).addBox(-1.75F, 0.1F, -3.0F, 2.0F, 1.0F, 3.0F, 0.0F, false);

        side = new ModelRenderer(this);
        side.setRotationPoint(0.0F, 0.0F, 0.0F);
        head.addChild(side);
        setRotationAngle(side, 0.0F, -0.3491F, 0.0F);
        side.setTextureOffset(0, 45).addBox(-1.5F, -1.0F, 0.0F, 1.0F, 3.0F, 3.0F, 0.0F, false);

        side2 = new ModelRenderer(this);
        side2.setRotationPoint(0.0F, 0.0F, 0.0F);
        head.addChild(side2);
        setRotationAngle(side2, 0.0F, 0.3491F, 0.0F);
        side2.setTextureOffset(0, 45).addBox(0.5F, -1.0F, 0.0F, 1.0F, 3.0F, 3.0F, 0.0F, true);

        side7 = new ModelRenderer(this);
        side7.setRotationPoint(5.0F, -2.0F, -1.0F);
        head.addChild(side7);
        setRotationAngle(side7, 0.0F, 0.2618F, 0.3491F);
        side7.setTextureOffset(7, 40).addBox(-3.0F, 4.7F, 0.0F, 1.0F, 2.0F, 3.0F, 0.0F, false);

        side8 = new ModelRenderer(this);
        side8.setRotationPoint(1.0F, -1.0F, 0.0F);
        head.addChild(side8);
        setRotationAngle(side8, 0.0F, -0.2618F, 0.6981F);
        side8.setTextureOffset(0, 40).addBox(-2.0F, 0.0F, 0.0F, 1.0F, 2.0F, 3.0F, 0.0F, false);

        side9 = new ModelRenderer(this);
        side9.setRotationPoint(-1.0F, -1.0F, 0.0F);
        head.addChild(side9);
        setRotationAngle(side9, 0.0F, 0.2618F, -0.6981F);
        side9.setTextureOffset(0, 40).addBox(1.0F, 0.0F, 0.0F, 1.0F, 2.0F, 3.0F, 0.0F, true);

        side10 = new ModelRenderer(this);
        side10.setRotationPoint(-5.0F, -2.0F, -1.0F);
        head.addChild(side10);
        setRotationAngle(side10, 0.0F, -0.2618F, -0.3491F);
        side10.setTextureOffset(7, 40).addBox(2.0F, 4.7F, 0.0F, 1.0F, 2.0F, 3.0F, 0.0F, true);

        tail = new ModelRenderer(this);
        tail.setRotationPoint(0.0F, 0.0F, 0.0F);
        bone.addChild(tail);
        tail.setTextureOffset(0, 16).addBox(-0.5F, -7.0F, 5.0F, 1.0F, 5.0F, 3.0F, 0.0F, false);
        tail.setTextureOffset(0, 10).addBox(0.5F, -6.0F, 5.0F, 1.0F, 3.0F, 3.0F, 0.0F, false);
        tail.setTextureOffset(0, 10).addBox(-1.5F, -6.0F, 5.0F, 1.0F, 3.0F, 3.0F, 0.0F, true);

        tail_side = new ModelRenderer(this);
        tail_side.setRotationPoint(1.0F, 0.0F, 0.0F);
        tail.addChild(tail_side);
        setRotationAngle(tail_side, 0.0F, 0.0F, -0.1745F);
        tail_side.setTextureOffset(12, 19).addBox(0.5F, -7.0F, 5.0F, 1.0F, 2.0F, 3.0F, 0.0F, false);

        tail_side2 = new ModelRenderer(this);
        tail_side2.setRotationPoint(-1.0F, 0.0F, 0.0F);
        tail.addChild(tail_side2);
        setRotationAngle(tail_side2, 0.0F, 0.0F, 0.1745F);
        tail_side2.setTextureOffset(12, 19).addBox(-1.5F, -7.0F, 5.0F, 1.0F, 2.0F, 3.0F, 0.0F, true);

        tail_side3 = new ModelRenderer(this);
        tail_side3.setRotationPoint(-1.0F, -9.0F, 0.0F);
        tail.addChild(tail_side3);
        setRotationAngle(tail_side3, 0.0F, 0.0F, -0.1745F);
        tail_side3.setTextureOffset(0, 24).addBox(-1.5F, 5.0F, 5.0F, 1.0F, 2.0F, 3.0F, 0.0F, true);

        tail_side4 = new ModelRenderer(this);
        tail_side4.setRotationPoint(1.0F, -9.0F, 0.0F);
        tail.addChild(tail_side4);
        setRotationAngle(tail_side4, 0.0F, 0.0F, 0.1745F);
        tail_side4.setTextureOffset(0, 24).addBox(0.5F, 5.0F, 5.0F, 1.0F, 2.0F, 3.0F, 0.0F, false);

        back_tail = new ModelRenderer(this);
        back_tail.setRotationPoint(0.0F, -4.0F, 8.0F);
        tail.addChild(back_tail);
        back_tail.setTextureOffset(8, 11).addBox(-0.5F, -2.0F, 0.0F, 1.0F, 3.0F, 2.0F, 0.0F, false);
        back_tail.setTextureOffset(14, 13).addBox(-0.5F, -1.0F, 2.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);

        hire = new ModelRenderer(this);
        hire.setRotationPoint(0.0F, 0.0F, 3.0F);
        back_tail.addChild(hire);
        setRotationAngle(hire, -0.2618F, 0.0F, 0.0F);
        hire.setTextureOffset(8, 19).addBox(-0.5F, -5.0F, -0.2247F, 1.0F, 4.0F, 1.0F, 0.0F, false);

        hire2 = new ModelRenderer(this);
        hire2.setRotationPoint(0.0F, -1.0F, 0.0F);
        hire.addChild(hire2);
        setRotationAngle(hire2, 0.2618F, 0.0F, 0.0F);
        hire2.setTextureOffset(8, 19).addBox(-0.5F, 0.7071F, -0.2247F, 1.0F, 4.0F, 1.0F, 0.0F, false);

        back_tail_side = new ModelRenderer(this);
        back_tail_side.setRotationPoint(0.0F, 0.0F, 0.0F);
        back_tail.addChild(back_tail_side);
        setRotationAngle(back_tail_side, 0.0F, 0.0873F, 0.3491F);
        back_tail_side.setTextureOffset(8, 25).addBox(-1.5F, -2.3F, -0.5F, 1.0F, 2.0F, 2.0F, 0.0F, false);

        back_tail_side2 = new ModelRenderer(this);
        back_tail_side2.setRotationPoint(0.0F, 0.0F, 0.0F);
        back_tail.addChild(back_tail_side2);
        setRotationAngle(back_tail_side2, 0.0F, -0.0873F, -0.3491F);
        back_tail_side2.setTextureOffset(8, 25).addBox(0.5F, -2.3F, -0.5F, 1.0F, 2.0F, 2.0F, 0.0F, true);

        back_tail_side3 = new ModelRenderer(this);
        back_tail_side3.setRotationPoint(0.0F, -1.0F, 0.0F);
        back_tail.addChild(back_tail_side3);
        setRotationAngle(back_tail_side3, 0.0F, -0.0873F, 0.3491F);
        back_tail_side3.setTextureOffset(14, 25).addBox(0.5F, -0.2F, -0.5F, 1.0F, 2.0F, 2.0F, 0.0F, true);

        back_tail_side4 = new ModelRenderer(this);
        back_tail_side4.setRotationPoint(0.0F, -1.0F, 0.0F);
        back_tail.addChild(back_tail_side4);
        setRotationAngle(back_tail_side4, 0.0F, 0.0873F, -0.3491F);
        back_tail_side4.setTextureOffset(14, 25).addBox(-1.5F, -0.2F, -0.5F, 1.0F, 2.0F, 2.0F, 0.0F, false);

        sebire = new ModelRenderer(this);
        sebire.setRotationPoint(0.0F, -8.0F, -3.0F);
        bone.addChild(sebire);
        setRotationAngle(sebire, -1.0472F, 0.0F, 0.0F);
        sebire.setTextureOffset(12, 5).addBox(-0.5F, -2.1F, -1.0F, 1.0F, 3.0F, 2.0F, 0.0F, false);

        sebire2 = new ModelRenderer(this);
        sebire2.setRotationPoint(0.0F, -8.0F, 2.0F);
        bone.addChild(sebire2);
        setRotationAngle(sebire2, -0.8727F, 0.0F, 0.0F);
        sebire2.setTextureOffset(8, 6).addBox(-0.5F, -3.0F, 0.0F, 1.0F, 3.0F, 1.0F, 0.0F, false);
        sebire2.setTextureOffset(4, 5).addBox(-0.5F, -4.0F, -0.9063F, 1.0F, 4.0F, 1.0F, 0.0F, false);

        harabire = new ModelRenderer(this);
        harabire.setRotationPoint(0.0F, -3.0F, 2.0F);
        bone.addChild(harabire);
        setRotationAngle(harabire, 0.8727F, 0.0F, 0.0F);
        harabire.setTextureOffset(0, 5).addBox(-0.5F, -0.5774F, -0.9063F, 1.0F, 4.0F, 1.0F, 0.0F, false);

        harabire2 = new ModelRenderer(this);
        harabire2.setRotationPoint(0.0F, -3.0F, -5.0F);
        bone.addChild(harabire2);
        setRotationAngle(harabire2, 0.8727F, 0.0F, 0.0F);
        harabire2.setTextureOffset(0, 5).addBox(-0.5F, -1.5774F, -0.9063F, 1.0F, 4.0F, 1.0F, 0.0F, false);

        hire3 = new ModelRenderer(this);
        hire3.setRotationPoint(3.0F, -5.0F, -4.0F);
        bone.addChild(hire3);
        setRotationAngle(hire3, 0.0F, 0.2618F, 0.0F);
        hire3.setTextureOffset(0, 0).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 1.0F, 4.0F, 0.0F, false);

        hire4 = new ModelRenderer(this);
        hire4.setRotationPoint(-3.0F, -5.0F, -4.0F);
        bone.addChild(hire4);
        setRotationAngle(hire4, 0.0F, -0.2618F, 0.0F);
        hire4.setTextureOffset(0, 0).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 1.0F, 4.0F, 0.0F, true);
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        bone.render(matrixStack, buffer, packedLight, packedOverlay);
    }
    @Override
    public void setRotationAngles(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
        float move = 1.0f;
        if(!entity.isInWater()){
            move = 1.5f;
        }

        back_tail.rotateAngleY = MathHelper.sin(move * 0.4f * ageInTicks) * 0.4f;
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}

