package jp.tdn.japanese_food_mod.client.models;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.ModelBox;
import net.minecraft.entity.LivingEntity;

public class AsariClamEntityModel<T extends LivingEntity> extends EntityModel<T> {
    private final RendererModel body;
    private final RendererModel bone;
    private final RendererModel bone2;

    public AsariClamEntityModel() {
        textureWidth = 32;
        textureHeight = 32;

        body = new RendererModel(this);
        body.setRotationPoint(0.0F, 24.0F, 0.0F);
        setRotationAngle(body, 0.0F, 3.1416F, 0.0F);


        bone = new RendererModel(this);
        bone.setRotationPoint(0.0F, 0.0F, 0.0F);
        body.addChild(bone);
        bone.cubeList.add(new ModelBox(bone, 0, 19, -1.5F, -1.0F, -1.0F, 3, 1, 3, 0.0F, false));
        bone.cubeList.add(new ModelBox(bone, 0, 5, -1.5F, -3.0F, -1.0F, 3, 1, 3, 0.0F, false));
        bone.cubeList.add(new ModelBox(bone, 0, 9, -0.5F, -2.0F, 2.0F, 1, 1, 1, 0.0F, false));
        bone.cubeList.add(new ModelBox(bone, 0, 13, -0.5F, -1.0F, 2.0F, 1, 1, 1, 0.0F, false));

        bone2 = new RendererModel(this);
        bone2.setRotationPoint(0.0F, -1.0F, 3.0F);
        body.addChild(bone2);
        bone2.cubeList.add(new ModelBox(bone2, 0, 0, -2.5F, -1.0F, -5.0F, 5, 1, 4, 0.0F, false));
    }

    @Override
    public void render(T entity, float f, float f1, float f2, float f3, float f4, float f5) {
        body.render(f5);
    }

    public void setRotationAngle(RendererModel modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
