package jp.tdn.japanese_food_mod.client.models;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.ModelBox;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;

public class CrabEntityModel<T extends LivingEntity> extends EntityModel<T> {
    private final RendererModel body;
    private final RendererModel leg;
    private final RendererModel leg_sub;
    private final RendererModel leg2;
    private final RendererModel leg_sub2;
    private final RendererModel leg3;
    private final RendererModel leg_sub3;
    private final RendererModel leg4;
    private final RendererModel leg_sub4;
    private final RendererModel leg5;
    private final RendererModel leg_sub5;
    private final RendererModel leg6;
    private final RendererModel leg_sub6;
    private final RendererModel outline;
    private final RendererModel outline2;
    private final RendererModel arm;
    private final RendererModel s;
    private final RendererModel arm2;
    private final RendererModel s2;

    public CrabEntityModel() {
        textureWidth = 32;
        textureHeight = 32;

        body = new RendererModel(this);
        body.setRotationPoint(0.0F, 23.0F, 0.0F);
        setRotationAngle(body, -0.2618F, 0.0F, 0.0F);
        body.cubeList.add(new ModelBox(body, 0, 0, -2.0F, -4.0F, 0.0F, 4, 2, 4, 0.0F, false));

        leg = new RendererModel(this);
        leg.setRotationPoint(3.0F, -3.0341F, 3.2588F);
        setRotationAngle(leg, 0.1745F, 0.0F, 0.0F);
        body.addChild(leg);
        leg.cubeList.add(new ModelBox(leg, 17, 0, -1.0F, 0.0341F, -0.5078F, 2, 1, 1, 0.0F, false));

        leg_sub = new RendererModel(this);
        leg_sub.setRotationPoint(-2.0F, 6.3197F, -2.3914F);
        setRotationAngle(leg_sub, 0.1745F, 0.0F, -0.3491F);
        leg.addChild(leg_sub);
        leg_sub.cubeList.add(new ModelBox(leg_sub, 16, 2, 4.5F, -3.8673F, 2.8325F, 1, 2, 1, 0.0F, false));

        leg2 = new RendererModel(this);
        leg2.setRotationPoint(-3.0F, -3.0341F, 3.2588F);
        setRotationAngle(leg2, 0.1745F, 0.0F, 0.0F);
        body.addChild(leg2);
        leg2.cubeList.add(new ModelBox(leg2, 17, 0, -1.0F, 0.0341F, -0.5078F, 2, 1, 1, 0.0F, true));

        leg_sub2 = new RendererModel(this);
        leg_sub2.setRotationPoint(2.0F, 6.3197F, -2.3914F);
        setRotationAngle(leg_sub2, 0.1745F, 0.0F, 0.3491F);
        leg2.addChild(leg_sub2);
        leg_sub2.cubeList.add(new ModelBox(leg_sub2, 16, 2, -5.5F, -3.8674F, 2.8835F, 1, 2, 1, 0.0F, true));

        leg3 = new RendererModel(this);
        leg3.setRotationPoint(3.0F, -2.0681F, 2.5176F);
        setRotationAngle(leg3, 0.1745F, 0.2618F, 0.0F);
        body.addChild(leg3);
        leg3.cubeList.add(new ModelBox(leg3, 17, 0, -1.0F, -0.8079F, -0.4755F, 2, 1, 1, 0.0F, false));

        leg_sub3 = new RendererModel(this);
        leg_sub3.setRotationPoint(-0.5811F, 9.3544F, -1.8687F);
        setRotationAngle(leg_sub3, 0.1745F, 0.0F, -0.3491F);
        leg3.addChild(leg_sub3);
        leg_sub3.cubeList.add(new ModelBox(leg_sub3, 16, 2, 4.2675F, -8.0362F, 2.851F, 1, 2, 1, 0.0F, false));

        leg4 = new RendererModel(this);
        leg4.setRotationPoint(-3.0F, -2.0681F, 2.5176F);
        setRotationAngle(leg4, 0.1745F, -0.2618F, 0.0F);
        body.addChild(leg4);
        leg4.cubeList.add(new ModelBox(leg4, 17, 0, -1.0681F, -0.8079F, -0.4754F, 2, 1, 1, 0.0F, true));

        leg_sub4 = new RendererModel(this);
        leg_sub4.setRotationPoint(0.5811F, 9.3544F, -1.8687F);
        setRotationAngle(leg_sub4, 0.1745F, 0.0F, 0.3491F);
        leg4.addChild(leg_sub4);
        leg_sub4.cubeList.add(new ModelBox(leg_sub4, 16, 2, -5.5F, -8.0362F, 2.8835F, 1, 2, 1, 0.0F, true));

        leg5 = new RendererModel(this);
        leg5.setRotationPoint(-3.0F, -1.8093F, 1.5517F);
        setRotationAngle(leg5, 0.1745F, -0.2618F, 0.0F);
        body.addChild(leg5);
        leg5.cubeList.add(new ModelBox(leg5, 17, 0, -1.3951F, -1.1907F, -0.7171F, 2, 1, 1, 0.0F, true));

        leg_sub5 = new RendererModel(this);
        leg_sub5.setRotationPoint(0.124F, 2.4742F, -0.6988F);
        setRotationAngle(leg_sub5, 0.0873F, 0.0F, 0.3491F);
        leg5.addChild(leg_sub5);
        leg_sub5.cubeList.add(new ModelBox(leg_sub5, 23, 0, -3.3058F, -2.3882F, 0.083F, 1, 3, 1, 0.0F, true));

        leg6 = new RendererModel(this);
        leg6.setRotationPoint(3.0F, -1.8093F, 1.5517F);
        setRotationAngle(leg6, 0.1745F, 0.2618F, 0.0F);
        body.addChild(leg6);
        leg6.cubeList.add(new ModelBox(leg6, 17, 0, -0.3461F, -1.1907F, -0.6684F, 2, 1, 1, 0.0F, false));

        leg_sub6 = new RendererModel(this);
        leg_sub6.setRotationPoint(0.1348F, 2.3064F, -0.65F);
        setRotationAngle(leg_sub6, 0.0873F, 0.0F, -0.3491F);
        leg6.addChild(leg_sub6);
        leg_sub6.cubeList.add(new ModelBox(leg_sub6, 23, 0, 2.3058F, -2.3882F, 0.083F, 1, 3, 1, 0.0F, false));

        outline = new RendererModel(this);
        outline.setRotationPoint(0.0F, 0.0F, -1.0F);
        setRotationAngle(outline, 0.0F, -0.2618F, 0.0F);
        body.addChild(outline);
        outline.cubeList.add(new ModelBox(outline, 0, 9, 1.5F, -4.0F, 0.8F, 2, 2, 3, 0.0F, false));

        outline2 = new RendererModel(this);
        outline2.setRotationPoint(0.0F, 0.0F, -1.0F);
        setRotationAngle(outline2, 0.0F, 0.2618F, 0.0F);
        body.addChild(outline2);
        outline2.cubeList.add(new ModelBox(outline2, 0, 9, -3.5F, -4.0F, 0.8F, 2, 2, 3, 0.0F, true));

        arm = new RendererModel(this);
        arm.setRotationPoint(0.0F, 0.0F, 0.0F);
        setRotationAngle(arm, 0.0F, 0.2618F, 0.0F);
        body.addChild(arm);
        arm.cubeList.add(new ModelBox(arm, 0, 16, -3.0F, -2.5F, -1.5176F, 1, 1, 2, 0.0F, false));

        s = new RendererModel(this);
        s.setRotationPoint(0.0F, 0.0F, 0.0F);
        arm.addChild(s);
        s.cubeList.add(new ModelBox(s, 0, 14, -2.5479F, -2.5F, -1.9507F, 3, 1, 1, 0.0F, false));

        arm2 = new RendererModel(this);
        arm2.setRotationPoint(0.0F, 0.0F, 0.0F);
        setRotationAngle(arm2, 0.0F, -0.2618F, 0.0F);
        body.addChild(arm2);
        arm2.cubeList.add(new ModelBox(arm2, 10, 12, 2.0F, -2.5F, -2.5176F, 1, 1, 3, 0.0F, true));

        s2 = new RendererModel(this);
        s2.setRotationPoint(0.7159F, 0.2588F, -1.1918F);
        arm2.addChild(s2);
        s2.cubeList.add(new ModelBox(s2, 10, 10, -0.7159F, -2.9F, -1.9507F, 3, 1, 1, 0.0F, true));
    }

    @Override
    public void render(T entity, float f, float f1, float f2, float f3, float f4, float f5) {
        body.render(f5);
    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
        leg.rotateAngleY = MathHelper.sin(limbSwing * 1.5f) * 1.4f * limbSwingAmount;
        leg2.rotateAngleY = MathHelper.sin(limbSwing * 1.4f) * 1.4f * limbSwingAmount;
        leg3.rotateAngleY = MathHelper.sin(limbSwing * 1.35f) * 1.4f * limbSwingAmount;
        leg4.rotateAngleY = MathHelper.sin(limbSwing * 1.3f) * 1.4f * limbSwingAmount;
        leg5.rotateAngleY = MathHelper.sin(limbSwing * 1.2f) * 1.4f * limbSwingAmount;
        leg6.rotateAngleY = MathHelper.sin(limbSwing * 1.15f) * 1.4f * limbSwingAmount;

        arm.rotateAngleX = MathHelper.sin(ageInTicks * 0.3f) * 0.3f;
        arm2.rotateAngleX = -MathHelper.sin(ageInTicks * 0.2f) * 0.3f;

        super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
    }

    public void setRotationAngle(RendererModel modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
