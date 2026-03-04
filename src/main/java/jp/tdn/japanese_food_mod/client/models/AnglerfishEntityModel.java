package jp.tdn.japanese_food_mod.client.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;

public class AnglerfishEntityModel<T extends LivingEntity> extends EntityModel<T> {
    private final ModelPart bone;
    private final ModelPart tail;

    public AnglerfishEntityModel(ModelPart root) {
        this.bone = root.getChild("bone");
        this.tail = this.bone.getChild("tail");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        PartDefinition bone = root.addOrReplaceChild("bone",
                CubeListBuilder.create(),
                PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body = bone.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-3.5F, -1.0F, -4.0F, 7.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 8).addBox(-2.5F, -1.6F, -2.0F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 14).addBox(-0.5F, -1.4F, -4.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        body.addOrReplaceChild("eye",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(0.0F, -1.35F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.0873F));

        body.addOrReplaceChild("eye2",
                CubeListBuilder.create()
                        .texOffs(0, 0).mirror().addBox(-1.0F, -1.35F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0873F));

        PartDefinition tail = bone.addOrReplaceChild("tail",
                CubeListBuilder.create()
                        .texOffs(22, 4).addBox(-1.9786F, -1.0F, 3.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(18, 18).addBox(-0.9786F, -1.0F, 4.0F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        tail.addOrReplaceChild("side",
                CubeListBuilder.create()
                        .texOffs(18, 25).addBox(-1.3F, -1.0F, 0.8F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-1.0F, 0.0F, 2.0F, 0.0F, 0.1745F, 0.0F));

        tail.addOrReplaceChild("side2",
                CubeListBuilder.create()
                        .texOffs(18, 25).mirror().addBox(0.2214F, -1.0F, -0.2F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false),
                PartPose.offsetAndRotation(1.0F, 0.0F, 3.0F, 0.0F, -0.1745F, 0.0F));

        tail.addOrReplaceChild("hire",
                CubeListBuilder.create()
                        .texOffs(0, 29).addBox(-0.9786F, -1.0F, 9.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        tail.addOrReplaceChild("hire2",
                CubeListBuilder.create()
                        .texOffs(8, 29).addBox(0.0214F, -1.1F, 1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 8.0F, 0.0F, 0.2618F, 0.0F));

        tail.addOrReplaceChild("hire3",
                CubeListBuilder.create()
                        .texOffs(8, 29).mirror().addBox(-1.0214F, -1.1F, 1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false),
                PartPose.offsetAndRotation(0.0F, 0.0F, 8.0F, 0.0F, -0.2618F, 0.0F));

        tail.addOrReplaceChild("upside",
                CubeListBuilder.create()
                        .texOffs(26, 0).addBox(-1.0F, -1.7F, 3.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 23).addBox(-0.5F, -1.7F, 4.0F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        bone.addOrReplaceChild("mouse",
                CubeListBuilder.create()
                        .texOffs(0, 17).addBox(-2.5F, -1.0F, -4.75F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 19).addBox(-2.5F, 0.0F, -5.25F, 5.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        bone.addOrReplaceChild("side_hire",
                CubeListBuilder.create()
                        .texOffs(0, 20).addBox(0.5F, -0.8F, -1.0F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(2.0F, 0.0F, 2.0F, 0.0F, -0.6109F, 0.0F));

        bone.addOrReplaceChild("side_hire2",
                CubeListBuilder.create()
                        .texOffs(0, 20).mirror().addBox(-3.5F, -0.8F, -1.0F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false),
                PartPose.offsetAndRotation(-2.0F, 0.0F, 2.0F, 0.0F, 0.6109F, 0.0F));

        return LayerDefinition.create(mesh, 32, 32);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
        bone.render(poseStack, buffer, packedLight, packedOverlay, color);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float move = 1.0f;
        if (!entity.isInWater()) {
            move = 1.5f;
        }
        tail.xRot = (Mth.sin(ageInTicks * 0.2f) * move * 0.3f) * 0.3f;
    }
}
