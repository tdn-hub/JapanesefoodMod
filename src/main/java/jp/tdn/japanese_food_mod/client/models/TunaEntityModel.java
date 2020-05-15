package jp.tdn.japanese_food_mod.client.models;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.ModelBox;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;

public class TunaEntityModel<T extends LivingEntity> extends EntityModel<T> {
    private final RendererModel bone;
    private final RendererModel body;
    private final RendererModel side3;
    private final RendererModel side4;
    private final RendererModel side5;
    private final RendererModel side6;
    private final RendererModel head;
    private final RendererModel mouse;
    private final RendererModel mouse_under;
    private final RendererModel mouse_upper;
    private final RendererModel mouse_upper2;
    private final RendererModel side;
    private final RendererModel side2;
    private final RendererModel side7;
    private final RendererModel side8;
    private final RendererModel side9;
    private final RendererModel side10;
    private final RendererModel tail;
    private final RendererModel tail_side;
    private final RendererModel tail_side2;
    private final RendererModel tail_side3;
    private final RendererModel tail_side4;
    private final RendererModel back_tail;
    private final RendererModel hire;
    private final RendererModel hire2;
    private final RendererModel back_tail_side;
    private final RendererModel back_tail_side2;
    private final RendererModel back_tail_side3;
    private final RendererModel back_tail_side4;
    private final RendererModel sebire;
    private final RendererModel sebire2;
    private final RendererModel harabire;
    private final RendererModel harabire2;
    private final RendererModel hire3;
    private final RendererModel hire4;

    public TunaEntityModel() {
        textureWidth = 64;
        textureHeight = 64;

        bone = new RendererModel(this);
        bone.setRotationPoint(0.0F, 24.0F, 0.0F);

        body = new RendererModel(this);
        body.setRotationPoint(0.0F, 0.0F, -2.0F);
        bone.addChild(body);
        body.cubeList.add(new ModelBox(body, 20, 30, -0.5F, -8.0F, -4.0F, 1, 7, 11, 0.0F, false));
        body.cubeList.add(new ModelBox(body, 20, 48, 0.5F, -7.0F, -4.0F, 1, 5, 11, 0.0F, false));
        body.cubeList.add(new ModelBox(body, 20, 48, -1.5F, -7.0F, -4.0F, 1, 5, 11, 0.0F, false));
        body.cubeList.add(new ModelBox(body, 20, 16, -2.5F, -6.0F, -4.0F, 1, 3, 11, 0.0F, false));
        body.cubeList.add(new ModelBox(body, 20, 16, 1.5F, -6.0F, -4.0F, 1, 3, 11, 0.0F, false));

        side3 = new RendererModel(this);
        side3.setRotationPoint(0.0F, -4.0F, 0.0F);
        setRotationAngle(side3, 0.0F, 0.0F, 0.4363F);
        body.addChild(side3);
        side3.cubeList.add(new ModelBox(side3, 20, 0, -2.5F, -3.5F, -4.0F, 1, 4, 11, 0.0F, false));

        side4 = new RendererModel(this);
        side4.setRotationPoint(0.0F, -4.0F, 0.0F);
        setRotationAngle(side4, 0.0F, 0.0F, -0.4363F);
        body.addChild(side4);
        side4.cubeList.add(new ModelBox(side4, 20, 0, 1.5F, -3.5F, -4.0F, 1, 4, 11, 0.0F, true));

        side5 = new RendererModel(this);
        side5.setRotationPoint(0.0F, -4.0F, 0.0F);
        setRotationAngle(side5, 0.0F, 0.0F, -0.3491F);
        body.addChild(side5);
        side5.cubeList.add(new ModelBox(side5, 20, 33, -2.0F, -1.5F, -4.0F, 1, 4, 11, 0.0F, false));

        side6 = new RendererModel(this);
        side6.setRotationPoint(0.0F, -4.0F, 0.0F);
        setRotationAngle(side6, 0.0F, 0.0F, 0.3491F);
        body.addChild(side6);
        side6.cubeList.add(new ModelBox(side6, 20, 33, 1.1F, -1.5F, -4.0F, 1, 4, 11, 0.0F, true));

        head = new RendererModel(this);
        head.setRotationPoint(0.0F, -5.0F, -8.0F);
        bone.addChild(head);
        head.cubeList.add(new ModelBox(head, 0, 29, -0.5F, -2.0F, 0.0F, 1, 5, 2, 0.0F, false));
        head.cubeList.add(new ModelBox(head, 8, 51, -1.0F, -1.0F, 0.0F, 1, 2, 3, 0.0F, false));
        head.cubeList.add(new ModelBox(head, 0, 51, 0.0F, -1.0F, 0.0F, 1, 2, 3, 0.0F, true));

        mouse = new RendererModel(this);
        mouse.setRotationPoint(0.5F, 0.0F, 0.0F);
        head.addChild(mouse);
        mouse.cubeList.add(new ModelBox(mouse, 8, 47, -1.75F, -1.0F, -1.7F, 2, 2, 2, 0.0F, false));

        mouse_under = new RendererModel(this);
        mouse_under.setRotationPoint(0.0F, 0.0F, 0.0F);
        setRotationAngle(mouse_under, -0.0873F, 0.0F, 0.0F);
        mouse.addChild(mouse_under);
        mouse_under.cubeList.add(new ModelBox(mouse_under, 6, 31, -1.75F, 1.9128F, -3.0038F, 2, 1, 4, 0.0F, false));

        mouse_upper = new RendererModel(this);
        mouse_upper.setRotationPoint(0.0F, 2.0F, -2.0F);
        setRotationAngle(mouse_upper, -1.8326F, 0.0F, 0.0F);
        mouse.addChild(mouse_upper);
        mouse_upper.cubeList.add(new ModelBox(mouse_upper, 10, 37, -1.75F, 0.0F, -3.0F, 2, 1, 2, 0.0F, false));

        mouse_upper2 = new RendererModel(this);
        mouse_upper2.setRotationPoint(0.0F, 0.0F, -2.0F);
        setRotationAngle(mouse_upper2, -2.8798F, 0.0F, 0.0F);
        mouse.addChild(mouse_upper2);
        mouse_upper2.cubeList.add(new ModelBox(mouse_upper2, 0, 36, -1.75F, 0.1F, -3.0F, 2, 1, 3, 0.0F, false));

        side = new RendererModel(this);
        side.setRotationPoint(0.0F, 0.0F, 0.0F);
        setRotationAngle(side, 0.0F, -0.3491F, 0.0F);
        head.addChild(side);
        side.cubeList.add(new ModelBox(side, 0, 45, -1.5F, -1.0F, 0.0F, 1, 3, 3, 0.0F, false));

        side2 = new RendererModel(this);
        side2.setRotationPoint(0.0F, 0.0F, 0.0F);
        setRotationAngle(side2, 0.0F, 0.3491F, 0.0F);
        head.addChild(side2);
        side2.cubeList.add(new ModelBox(side2, 0, 45, 0.5F, -1.0F, 0.0F, 1, 3, 3, 0.0F, true));

        side7 = new RendererModel(this);
        side7.setRotationPoint(5.0F, -2.0F, -1.0F);
        setRotationAngle(side7, 0.0F, 0.2618F, 0.3491F);
        head.addChild(side7);
        side7.cubeList.add(new ModelBox(side7, 7, 40, -3.0F, 4.7F, 0.0F, 1, 2, 3, 0.0F, false));

        side8 = new RendererModel(this);
        side8.setRotationPoint(1.0F, -1.0F, 0.0F);
        setRotationAngle(side8, 0.0F, -0.2618F, 0.6981F);
        head.addChild(side8);
        side8.cubeList.add(new ModelBox(side8, 0, 40, -2.0F, 0.0F, 0.0F, 1, 2, 3, 0.0F, false));

        side9 = new RendererModel(this);
        side9.setRotationPoint(-1.0F, -1.0F, 0.0F);
        setRotationAngle(side9, 0.0F, 0.2618F, -0.6981F);
        head.addChild(side9);
        side9.cubeList.add(new ModelBox(side9, 0, 40, 1.0F, 0.0F, 0.0F, 1, 2, 3, 0.0F, true));

        side10 = new RendererModel(this);
        side10.setRotationPoint(-5.0F, -2.0F, -1.0F);
        setRotationAngle(side10, 0.0F, -0.2618F, -0.3491F);
        head.addChild(side10);
        side10.cubeList.add(new ModelBox(side10, 7, 40, 2.0F, 4.7F, 0.0F, 1, 2, 3, 0.0F, true));

        tail = new RendererModel(this);
        tail.setRotationPoint(0.0F, 0.0F, 0.0F);
        bone.addChild(tail);
        tail.cubeList.add(new ModelBox(tail, 0, 16, -0.5F, -7.0F, 5.0F, 1, 5, 3, 0.0F, false));
        tail.cubeList.add(new ModelBox(tail, 0, 10, 0.5F, -6.0F, 5.0F, 1, 3, 3, 0.0F, false));
        tail.cubeList.add(new ModelBox(tail, 0, 10, -1.5F, -6.0F, 5.0F, 1, 3, 3, 0.0F, true));

        tail_side = new RendererModel(this);
        tail_side.setRotationPoint(1.0F, 0.0F, 0.0F);
        setRotationAngle(tail_side, 0.0F, 0.0F, -0.1745F);
        tail.addChild(tail_side);
        tail_side.cubeList.add(new ModelBox(tail_side, 12, 19, 0.5F, -7.0F, 5.0F, 1, 2, 3, 0.0F, false));

        tail_side2 = new RendererModel(this);
        tail_side2.setRotationPoint(-1.0F, 0.0F, 0.0F);
        setRotationAngle(tail_side2, 0.0F, 0.0F, 0.1745F);
        tail.addChild(tail_side2);
        tail_side2.cubeList.add(new ModelBox(tail_side2, 12, 19, -1.5F, -7.0F, 5.0F, 1, 2, 3, 0.0F, true));

        tail_side3 = new RendererModel(this);
        tail_side3.setRotationPoint(-1.0F, -9.0F, 0.0F);
        setRotationAngle(tail_side3, 0.0F, 0.0F, -0.1745F);
        tail.addChild(tail_side3);
        tail_side3.cubeList.add(new ModelBox(tail_side3, 0, 24, -1.5F, 5.0F, 5.0F, 1, 2, 3, 0.0F, true));

        tail_side4 = new RendererModel(this);
        tail_side4.setRotationPoint(1.0F, -9.0F, 0.0F);
        setRotationAngle(tail_side4, 0.0F, 0.0F, 0.1745F);
        tail.addChild(tail_side4);
        tail_side4.cubeList.add(new ModelBox(tail_side4, 0, 24, 0.5F, 5.0F, 5.0F, 1, 2, 3, 0.0F, false));

        back_tail = new RendererModel(this);
        back_tail.setRotationPoint(0.0F, -4.0F, 8.0F);
        tail.addChild(back_tail);
        back_tail.cubeList.add(new ModelBox(back_tail, 8, 11, -0.5F, -2.0F, 0.0F, 1, 3, 2, 0.0F, false));
        back_tail.cubeList.add(new ModelBox(back_tail, 14, 13, -0.5F, -1.0F, 2.0F, 1, 1, 2, 0.0F, false));

        hire = new RendererModel(this);
        hire.setRotationPoint(0.0F, 0.0F, 3.0F);
        setRotationAngle(hire, -0.2618F, 0.0F, 0.0F);
        back_tail.addChild(hire);
        hire.cubeList.add(new ModelBox(hire, 8, 19, -0.5F, -4.7071F, -0.2247F, 1, 4, 1, 0.0F, false));

        hire2 = new RendererModel(this);
        hire2.setRotationPoint(0.0F, -1.0F, 0.0F);
        setRotationAngle(hire2, 0.2618F, 0.0F, 0.0F);
        hire.addChild(hire2);
        hire2.cubeList.add(new ModelBox(hire2, 8, 19, -0.5F, 0.7071F, -0.2247F, 1, 4, 1, 0.0F, false));

        back_tail_side = new RendererModel(this);
        back_tail_side.setRotationPoint(0.0F, 0.0F, 0.0F);
        setRotationAngle(back_tail_side, 0.0F, 0.0873F, 0.3491F);
        back_tail.addChild(back_tail_side);
        back_tail_side.cubeList.add(new ModelBox(back_tail_side, 8, 25, -1.5F, -2.3F, -0.5F, 1, 2, 2, 0.0F, false));

        back_tail_side2 = new RendererModel(this);
        back_tail_side2.setRotationPoint(0.0F, 0.0F, 0.0F);
        setRotationAngle(back_tail_side2, 0.0F, -0.0873F, -0.3491F);
        back_tail.addChild(back_tail_side2);
        back_tail_side2.cubeList.add(new ModelBox(back_tail_side2, 8, 25, 0.5F, -2.3F, -0.5F, 1, 2, 2, 0.0F, true));

        back_tail_side3 = new RendererModel(this);
        back_tail_side3.setRotationPoint(0.0F, -1.0F, 0.0F);
        setRotationAngle(back_tail_side3, 0.0F, -0.0873F, 0.3491F);
        back_tail.addChild(back_tail_side3);
        back_tail_side3.cubeList.add(new ModelBox(back_tail_side3, 14, 25, 0.5F, -0.2F, -0.5F, 1, 2, 2, 0.0F, true));

        back_tail_side4 = new RendererModel(this);
        back_tail_side4.setRotationPoint(0.0F, -1.0F, 0.0F);
        setRotationAngle(back_tail_side4, 0.0F, 0.0873F, -0.3491F);
        back_tail.addChild(back_tail_side4);
        back_tail_side4.cubeList.add(new ModelBox(back_tail_side4, 14, 25, -1.5F, -0.2F, -0.5F, 1, 2, 2, 0.0F, false));

        sebire = new RendererModel(this);
        sebire.setRotationPoint(0.0F, -8.0F, -3.0F);
        setRotationAngle(sebire, -1.0472F, 0.0F, 0.0F);
        bone.addChild(sebire);
        sebire.cubeList.add(new ModelBox(sebire, 12, 5, -0.5F, -2.1F, -1.0F, 1, 3, 2, 0.0F, false));

        sebire2 = new RendererModel(this);
        sebire2.setRotationPoint(0.0F, -8.0F, 2.0F);
        setRotationAngle(sebire2, -0.8727F, 0.0F, 0.0F);
        bone.addChild(sebire2);
        sebire2.cubeList.add(new ModelBox(sebire2, 8, 6, -0.5F, -2.4F, 0.0F, 1, 3, 1, 0.0F, false));
        sebire2.cubeList.add(new ModelBox(sebire2, 4, 5, -0.5F, -3.4226F, -0.9063F, 1, 4, 1, 0.0F, false));

        harabire = new RendererModel(this);
        harabire.setRotationPoint(0.0F, -3.0F, 2.0F);
        setRotationAngle(harabire, 0.8727F, 0.0F, 0.0F);
        bone.addChild(harabire);
        harabire.cubeList.add(new ModelBox(harabire, 0, 5, -0.5F, 0.0F, -0.9063F, 1, 4, 1, 0.0F, false));

        harabire2 = new RendererModel(this);
        harabire2.setRotationPoint(0.0F, -3.0F, -5.0F);
        setRotationAngle(harabire2, 0.8727F, 0.0F, 0.0F);
        bone.addChild(harabire2);
        harabire2.cubeList.add(new ModelBox(harabire2, 0, 5, -0.5F, -1.0F, -0.9063F, 1, 4, 1, 0.0F, false));

        hire3 = new RendererModel(this);
        hire3.setRotationPoint(3.0F, -5.0F, -4.0F);
        setRotationAngle(hire3, 0.0F, 0.2618F, 0.0F);
        bone.addChild(hire3);
        hire3.cubeList.add(new ModelBox(hire3, 0, 0, -0.5F, 0.0F, -1.0F, 1, 1, 4, 0.0F, false));

        hire4 = new RendererModel(this);
        hire4.setRotationPoint(-3.0F, -5.0F, -4.0F);
        setRotationAngle(hire4, 0.0F, -0.2618F, 0.0F);
        bone.addChild(hire4);
        hire4.cubeList.add(new ModelBox(hire4, 0, 0, -0.5F, 0.0F, -1.0F, 1, 1, 4, 0.0F, true));
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

        back_tail.rotateAngleY = MathHelper.sin(move * 0.4f * ageInTicks) * 0.4f;

        super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
    }

    public void setRotationAngle(RendererModel modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}

