package jp.tdn.japanese_food_mod.client.models;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.ModelBox;
import net.minecraft.entity.LivingEntity;

public class TurbanShellEntityModel<T extends LivingEntity> extends EntityModel<T> {
    private final RendererModel bone;
    private final RendererModel hole;
    private final RendererModel back;

    public TurbanShellEntityModel() {
        textureWidth = 32;
        textureHeight = 32;

        bone = new RendererModel(this);
        bone.setRotationPoint(0.0F, 24.0F, 0.0F);


        hole = new RendererModel(this);
        hole.setRotationPoint(0.0F, 0.0F, 0.0F);
        bone.addChild(hole);
        hole.cubeList.add(new ModelBox(hole, 0, 12, 1.0F, -2.12F, -1.0F, 1, 2, 2, 0.0F, false));
        hole.cubeList.add(new ModelBox(hole, 0, 7, 0.5F, -2.1F, 0.5F, 1, 2, 1, 0.0F, false));
        hole.cubeList.add(new ModelBox(hole, 0, 7, 0.5F, -2.1F, -1.5F, 1, 2, 1, 0.0F, false));
        hole.cubeList.add(new ModelBox(hole, 7, 7, 0.5F, -2.5F, -1.5F, 1, 1, 3, 0.0F, false));
        hole.cubeList.add(new ModelBox(hole, 0, 0, -2.0F, -3.0F, -2.0F, 3, 3, 4, 0.0F, false));
        hole.cubeList.add(new ModelBox(hole, 0, 21, -1.0F, -1.8F, -3.2F, 1, 1, 2, 0.0F, false));
        hole.cubeList.add(new ModelBox(hole, 0, 16, -1.0F, -3.6F, -3.0F, 1, 1, 1, 0.0F, false));
        hole.cubeList.add(new ModelBox(hole, 4, 16, -1.0F, -4.0F, -1.5F, 1, 1, 1, 0.0F, false));
        hole.cubeList.add(new ModelBox(hole, 13, 16, -1.0F, -3.6F, 2.0F, 1, 1, 1, 0.0F, false));
        hole.cubeList.add(new ModelBox(hole, 0, 21, -1.0F, -1.8F, 1.2F, 1, 1, 2, 0.0F, false));
        hole.cubeList.add(new ModelBox(hole, 9, 16, -1.0F, -4.0F, 0.5F, 1, 1, 1, 0.0F, false));

        back = new RendererModel(this);
        back.setRotationPoint(-2.0F, -1.0F, 0.0F);
        bone.addChild(back);
        setRotationAngle(back, 0.0F, 0.0F, 0.1745F);
        back.cubeList.add(new ModelBox(back, 9, 18, -0.8F, -2.0F, -2.0F, 1, 3, 4, 0.0F, false));
        back.cubeList.add(new ModelBox(back, 16, 0, -2.0F, -3.0F, -1.0F, 2, 3, 3, 0.0F, false));
        back.cubeList.add(new ModelBox(back, 16, 6, -3.0F, -2.8F, 0.0F, 1, 2, 2, 0.0F, false));
        back.cubeList.add(new ModelBox(back, 26, 0, -4.0F, -2.7F, 1.0F, 1, 1, 1, 0.0F, false));
    }

    @Override
    public void render(T entity, float f, float f1, float f2, float f3, float f4, float f5) {
        bone.render(f5);
    }

    public void setRotationAngle(RendererModel modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
