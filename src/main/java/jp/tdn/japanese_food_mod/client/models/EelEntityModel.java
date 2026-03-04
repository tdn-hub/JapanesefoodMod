package jp.tdn.japanese_food_mod.client.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;

public class EelEntityModel<T extends LivingEntity> extends EntityModel<T> {
    private final ModelPart tail;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart body2;
    private final ModelPart hire;
    private final ModelPart hire2;

    public EelEntityModel(ModelPart root) {
        this.tail = root.getChild("tail");
        this.body = root.getChild("body");
        this.head = root.getChild("head");
        this.body2 = root.getChild("body2");
        this.hire = root.getChild("hire");
        this.hire2 = root.getChild("hire2");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        root.addOrReplaceChild("tail",
                CubeListBuilder.create()
                        .texOffs(17, 11).addBox(-0.5F, -1.5F, 0.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(19, 6).addBox(-0.5F, -1.5F, 3.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 23.0F, 12.0F));

        root.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(0, 10).addBox(-1.0F, -3.0F, -4.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 10).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 10).addBox(-1.0F, -3.0F, 2.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 24.0F, 0.0F));

        root.addOrReplaceChild("head",
                CubeListBuilder.create()
                        .texOffs(0, 10).addBox(-1.0F, -3.0F, -7.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 2).addBox(-1.0F, -2.5F, -9.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 0).addBox(-1.0F, -2.5F, -10.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 24.0F, 0.0F));

        root.addOrReplaceChild("body2",
                CubeListBuilder.create()
                        .texOffs(8, 0).addBox(-1.0F, -1.5F, 0.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(7, 5).addBox(-1.0F, -1.5F, 3.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 23.0F, 5.0F));

        root.addOrReplaceChild("hire",
                CubeListBuilder.create()
                        .texOffs(0, 3).addBox(0.0F, -0.5F, 0.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(1.0F, 23.0F, -7.0F, 0.0F, 0.2618F, 0.0F));

        root.addOrReplaceChild("hire2",
                CubeListBuilder.create()
                        .texOffs(0, 3).mirror().addBox(0.0F, -0.5F, 0.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false),
                PartPose.offsetAndRotation(-1.0F, 23.0F, -7.0F, 0.0F, -0.2618F, 0.0F));

        return LayerDefinition.create(mesh, 32, 32);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float move = 1.0f;
        if (!entity.isInWater()) {
            move = 1.5f;
        }
        tail.yRot = Mth.sin(move * 0.1f * ageInTicks) * 0.2f;
        body2.yRot = -(Mth.sin(move * 0.1f * ageInTicks) * 0.2f);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
        tail.render(poseStack, buffer, packedLight, packedOverlay, color);
        body.render(poseStack, buffer, packedLight, packedOverlay, color);
        head.render(poseStack, buffer, packedLight, packedOverlay, color);
        body2.render(poseStack, buffer, packedLight, packedOverlay, color);
        hire.render(poseStack, buffer, packedLight, packedOverlay, color);
        hire2.render(poseStack, buffer, packedLight, packedOverlay, color);
    }
}
