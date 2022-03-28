package jp.tdn.japanese_food_mod.client.models;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;

public class AnglerfishEntityModel<T extends LivingEntity> extends EntityModel<T> {
    private final ModelRenderer bone;
    private final ModelRenderer body;
    private final ModelRenderer eye;
    private final ModelRenderer eye2;
    private final ModelRenderer tail;
    private final ModelRenderer side;
    private final ModelRenderer side2;
    private final ModelRenderer hire;
    private final ModelRenderer hire2;
    private final ModelRenderer hire3;
    private final ModelRenderer upside;
    private final ModelRenderer mouse;
    private final ModelRenderer side_hire;
    private final ModelRenderer side_hire2;

    public AnglerfishEntityModel() {
        this.texWidth = 32;
        this.texHeight = 32;

        bone = new ModelRenderer(this);
        bone.setRotationPoint(0.0F, 24.0F, 0.0F);


        body = new ModelRenderer(this);
        body.setRotationPoint(0.0F, 0.0F, 0.0F);
        bone.addChild(body);
        body.setTextureOffset(0, 0).addBox(-3.5F, -1.0F, -4.0F, 7.0F, 1.0F, 7.0F, 0.0F, false);
        body.setTextureOffset(0, 8).addBox(-2.5F, -1.6F, -2.0F, 5.0F, 1.0F, 5.0F, 0.0F, false);
        body.setTextureOffset(0, 14).addBox(-0.5F, -1.4F, -4.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);

        eye = new ModelRenderer(this);
        eye.setRotationPoint(0.0F, 0.0F, 0.0F);
        body.addChild(eye);
        setRotationAngle(eye, 0.0F, 0.0F, -0.0873F);
        eye.setTextureOffset(0, 0).addBox(0.0F, -1.35F, -2.75F, 1.0F, 1.0F, 1.0F, 0.0F, false);

        eye2 = new ModelRenderer(this);
        eye2.setRotationPoint(0.0F, 0.0F, 0.0F);
        body.addChild(eye2);
        setRotationAngle(eye2, 0.0F, 0.0F, 0.0873F);
        eye2.setTextureOffset(0, 0).addBox(-1.0F, -1.35F, -2.75F, 1.0F, 1.0F, 1.0F, 0.0F, true);

        tail = new ModelRenderer(this);
        tail.setRotationPoint(0.0F, 0.0F, 0.0F);
        bone.addChild(tail);
        tail.setTextureOffset(22, 4).addBox(-1.9786F, -1.0F, 3.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
        tail.setTextureOffset(18, 18).addBox(-0.9786F, -1.0F, 4.0F, 2.0F, 1.0F, 5.0F, 0.0F, false);

        side = new ModelRenderer(this);
        side.setRotationPoint(-1.0F, 0.0F, 2.0F);
        tail.addChild(side);
        setRotationAngle(side, 0.0F, 0.1745F, 0.0F);
        side.setTextureOffset(18, 25).addBox(-1.3F, -1.0F, 0.8F, 1.0F, 1.0F, 6.0F, 0.0F, false);

        side2 = new ModelRenderer(this);
        side2.setRotationPoint(1.0F, 0.0F, 3.0F);
        tail.addChild(side2);
        setRotationAngle(side2, 0.0F, -0.1745F, 0.0F);
        side2.setTextureOffset(18, 25).addBox(0.2214F, -1.0F, -0.2F, 1.0F, 1.0F, 6.0F, 0.0F, true);

        hire = new ModelRenderer(this);
        hire.setRotationPoint(0.0F, 0.0F, 0.0F);
        tail.addChild(hire);
        hire.setTextureOffset(0, 29).addBox(-0.9786F, -1.0F, 9.0F, 2.0F, 1.0F, 2.0F, 0.0F, false);

        hire2 = new ModelRenderer(this);
        hire2.setRotationPoint(0.0F, 0.0F, 8.0F);
        tail.addChild(hire2);
        setRotationAngle(hire2, 0.0F, 0.2618F, 0.0F);
        hire2.setTextureOffset(8, 29).addBox(0.0214F, -1.1F, 1.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);

        hire3 = new ModelRenderer(this);
        hire3.setRotationPoint(0.0F, 0.0F, 8.0F);
        tail.addChild(hire3);
        setRotationAngle(hire3, 0.0F, -0.2618F, 0.0F);
        hire3.setTextureOffset(8, 29).addBox(-1.0214F, -1.1F, 1.0F, 1.0F, 1.0F, 2.0F, 0.0F, true);

        upside = new ModelRenderer(this);
        upside.setRotationPoint(0.0F, 0.0F, 0.0F);
        tail.addChild(upside);
        upside.setTextureOffset(26, 0).addBox(-1.0F, -1.7F, 3.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        upside.setTextureOffset(0, 23).addBox(-0.5F, -1.7F, 4.0F, 1.0F, 1.0F, 5.0F, 0.0F, false);

        mouse = new ModelRenderer(this);
        mouse.setRotationPoint(0.0F, 0.0F, 0.0F);
        bone.addChild(mouse);
        mouse.setTextureOffset(0, 17).addBox(-2.5F, -1.0F, -4.75F, 5.0F, 1.0F, 1.0F, 0.0F, false);
        mouse.setTextureOffset(0, 19).addBox(-2.5F, 0.0F, -5.25F, 5.0F, 0.0F, 1.0F, 0.0F, false);

        side_hire = new ModelRenderer(this);
        side_hire.setRotationPoint(2.0F, 0.0F, 2.0F);
        bone.addChild(side_hire);
        setRotationAngle(side_hire, 0.0F, -0.6109F, 0.0F);
        side_hire.setTextureOffset(0, 20).addBox(0.5F, -0.8F, -1.0F, 3.0F, 0.0F, 2.0F, 0.0F, false);

        side_hire2 = new ModelRenderer(this);
        side_hire2.setRotationPoint(-2.0F, 0.0F, 2.0F);
        bone.addChild(side_hire2);
        setRotationAngle(side_hire2, 0.0F, 0.6109F, 0.0F);
        side_hire2.setTextureOffset(0, 20).addBox(-3.5F, -0.8F, -1.0F, 3.0F, 0.0F, 2.0F, 0.0F, true);
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

        tail.rotateAngleX = (MathHelper.sin(ageInTicks * 0.2f) * move * 0.3f) * 0.3f;
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
