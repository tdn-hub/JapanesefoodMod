package jp.tdn.japanese_food_mod.client.models;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.ModelBox;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;

public class EelEntityModel<T extends LivingEntity> extends EntityModel<T> {
    private final RendererModel tail;
    private final RendererModel body;
    private final RendererModel head;
    private final RendererModel body2;

    public EelEntityModel() {
        textureWidth = 32;
        textureHeight = 32;

        tail = new RendererModel(this);
        tail.setRotationPoint(0.0F, 23.0F, 12.0F);
        tail.cubeList.add(new ModelBox(tail, 17, 11, -0.5F, -1.5F, 0.0F, 1, 2, 3, 0.0F, false));
        tail.cubeList.add(new ModelBox(tail, 19, 6, -0.5F, -1.5F, 3.0F, 1, 2, 3, 0.0F, false));

        body = new RendererModel(this);
        body.setRotationPoint(0.0F, 24.0F, 0.0F);
        body.cubeList.add(new ModelBox(body, 0, 10, -1.0F, -3.0F, -4.0F, 2, 3, 3, 0.0F, false));
        body.cubeList.add(new ModelBox(body, 0, 10, -1.0F, -3.0F, -1.0F, 2, 3, 3, 0.0F, false));
        body.cubeList.add(new ModelBox(body, 0, 10, -1.0F, -3.0F, 2.0F, 2, 3, 3, 0.0F, false));

        head = new RendererModel(this);
        head.setRotationPoint(0.0F, 24.0F, 0.0F);
        head.cubeList.add(new ModelBox(head, 0, 10, -1.0F, -3.0F, -7.0F, 2, 3, 3, 0.0F, false));
        head.cubeList.add(new ModelBox(head, 0, 2, -1.0F, -2.5F, -9.0F, 2, 2, 2, 0.0F, false));
        head.cubeList.add(new ModelBox(head, 0, 0, -1.0F, -2.5F, -10.0F, 2, 1, 1, 0.0F, false));

        body2 = new RendererModel(this);
        body2.setRotationPoint(0.0F, 23.0F, 5.0F);
        body2.cubeList.add(new ModelBox(body2, 8, 0, -1.0F, -1.5F, 0.0F, 2, 2, 3, 0.0F, false));
        body2.cubeList.add(new ModelBox(body2, 7, 5, -1.0F, -1.5F, 3.0F, 2, 2, 4, 0.0F, false));
    }

    @Override
    public void render(T entity, float f, float f1, float f2, float f3, float f4, float f5) {
        tail.render(f5);
        body.render(f5);
        head.render(f5);
        body2.render(f5);
    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
        float move = 1.0f;
        if(!entityIn.isInWater()){
            move = 1.5f;
        }

        tail.rotateAngleY = MathHelper.sin(move * 0.1f * ageInTicks) * 0.2f;
        body2.rotateAngleY = -(MathHelper.sin(move * 0.1f * ageInTicks) * 0.2f);

        super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
    }

    public void setRotationAngle(RendererModel modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}

