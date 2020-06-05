package jp.tdn.japanese_food_mod.client.models;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.ModelBox;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;

public class AnglerfishEntityModel<T extends LivingEntity> extends EntityModel<T> {
    private final RendererModel bone;
    private final RendererModel body;
    private final RendererModel eye;
    private final RendererModel eye2;
    private final RendererModel tail;
    private final RendererModel side;
    private final RendererModel side2;
    private final RendererModel hire;
    private final RendererModel hire2;
    private final RendererModel hire3;
    private final RendererModel upside;
    private final RendererModel mouse;
    private final RendererModel side_hire;
    private final RendererModel side_hire2;

    public AnglerfishEntityModel() {
        textureWidth = 32;
        textureHeight = 32;

        bone = new RendererModel(this);
        bone.setRotationPoint(0.0F, 24.0F, 0.0F);


        body = new RendererModel(this);
        body.setRotationPoint(0.0F, 0.0F, 0.0F);
        bone.addChild(body);
        body.cubeList.add(new ModelBox(body, 0, 0, -3.5F, -1.0F, -4.0F, 7, 1, 7, 0.0F, false));
        body.cubeList.add(new ModelBox(body, 0, 8, -2.5F, -1.6F, -2.0F, 5, 1, 5, 0.0F, false));
        body.cubeList.add(new ModelBox(body, 0, 14, -0.5F, -1.4F, -4.0F, 1, 1, 2, 0.0F, false));

        eye = new RendererModel(this);
        eye.setRotationPoint(0.0F, 0.0F, 0.0F);
        body.addChild(eye);
        setRotationAngle(eye, 0.0F, 0.0F, -0.0873F);
        eye.cubeList.add(new ModelBox(eye, 0, 0, 0.0F, -1.35F, -2.75F, 1, 1, 1, 0.0F, false));

        eye2 = new RendererModel(this);
        eye2.setRotationPoint(0.0F, 0.0F, 0.0F);
        body.addChild(eye2);
        setRotationAngle(eye2, 0.0F, 0.0F, 0.0873F);
        eye2.cubeList.add(new ModelBox(eye2, 0, 0, -1.0F, -1.35F, -2.75F, 1, 1, 1, 0.0F, true));

        tail = new RendererModel(this);
        tail.setRotationPoint(0.0F, 0.0F, 0.0F);
        bone.addChild(tail);
        tail.cubeList.add(new ModelBox(tail, 22, 4, -1.9786F, -1.0F, 3.0F, 4, 1, 1, 0.0F, false));
        tail.cubeList.add(new ModelBox(tail, 18, 18, -0.9786F, -1.0F, 4.0F, 2, 1, 5, 0.0F, false));

        side = new RendererModel(this);
        side.setRotationPoint(-1.0F, 0.0F, 2.0F);
        tail.addChild(side);
        setRotationAngle(side, 0.0F, 0.1745F, 0.0F);
        side.cubeList.add(new ModelBox(side, 18, 25, -1.3F, -1.0F, 0.8F, 1, 1, 6, 0.0F, false));

        side2 = new RendererModel(this);
        side2.setRotationPoint(1.0F, 0.0F, 3.0F);
        tail.addChild(side2);
        setRotationAngle(side2, 0.0F, -0.1745F, 0.0F);
        side2.cubeList.add(new ModelBox(side2, 18, 25, 0.2214F, -1.0F, -0.2F, 1, 1, 6, 0.0F, true));

        hire = new RendererModel(this);
        hire.setRotationPoint(0.0F, 0.0F, 0.0F);
        tail.addChild(hire);
        hire.cubeList.add(new ModelBox(hire, 0, 29, -0.9786F, -1.0F, 9.0F, 2, 1, 2, 0.0F, false));

        hire2 = new RendererModel(this);
        hire2.setRotationPoint(0.0F, 0.0F, 8.0F);
        tail.addChild(hire2);
        setRotationAngle(hire2, 0.0F, 0.2618F, 0.0F);
        hire2.cubeList.add(new ModelBox(hire2, 8, 29, 0.0214F, -1.1F, 1.0F, 1, 1, 2, 0.0F, false));

        hire3 = new RendererModel(this);
        hire3.setRotationPoint(0.0F, 0.0F, 8.0F);
        tail.addChild(hire3);
        setRotationAngle(hire3, 0.0F, -0.2618F, 0.0F);
        hire3.cubeList.add(new ModelBox(hire3, 8, 29, -1.0214F, -1.1F, 1.0F, 1, 1, 2, 0.0F, true));

        upside = new RendererModel(this);
        upside.setRotationPoint(0.0F, 0.0F, 0.0F);
        tail.addChild(upside);
        upside.cubeList.add(new ModelBox(upside, 26, 0, -1.0F, -1.7F, 3.0F, 2, 1, 1, 0.0F, false));
        upside.cubeList.add(new ModelBox(upside, 0, 23, -0.5F, -1.7F, 4.0F, 1, 1, 5, 0.0F, false));

        mouse = new RendererModel(this);
        mouse.setRotationPoint(0.0F, 0.0F, 0.0F);
        bone.addChild(mouse);
        mouse.cubeList.add(new ModelBox(mouse, 0, 17, -2.5F, -1.0F, -4.75F, 5, 1, 1, 0.0F, false));
        mouse.cubeList.add(new ModelBox(mouse, 0, 19, -2.5F, 0.0F, -5.25F, 5, 0, 1, 0.0F, false));

        side_hire = new RendererModel(this);
        side_hire.setRotationPoint(2.0F, 0.0F, 2.0F);
        bone.addChild(side_hire);
        setRotationAngle(side_hire, 0.0F, -0.6109F, 0.0F);
        side_hire.cubeList.add(new ModelBox(side_hire, 0, 20, 0.5F, -0.8F, -1.0F, 3, 0, 2, 0.0F, false));

        side_hire2 = new RendererModel(this);
        side_hire2.setRotationPoint(-2.0F, 0.0F, 2.0F);
        bone.addChild(side_hire2);
        setRotationAngle(side_hire2, 0.0F, 0.6109F, 0.0F);
        side_hire2.cubeList.add(new ModelBox(side_hire2, 0, 20, -3.5F, -0.8F, -1.0F, 3, 0, 2, 0.0F, true));
    }

    @Override
    public void render(T entity, float f, float f1, float f2, float f3, float f4, float f5) {
        bone.render(f5);
    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
        float move = 1.0f;
        if(!entityIn.isInWater()){
            move = 1.5f;
        }

        tail.rotateAngleX = (MathHelper.sin(ageInTicks * 0.2f) * move * 0.3f) * 0.3f;
    }

    public void setRotationAngle(RendererModel modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
