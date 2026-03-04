package jp.tdn.japanese_food_mod.client.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.LivingEntity;

public class TurbanShellEntityModel<T extends LivingEntity> extends EntityModel<T> {
    private final ModelPart bone;

    public TurbanShellEntityModel(ModelPart root) {
        this.bone = root.getChild("bone");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        PartDefinition bone = root.addOrReplaceChild("bone",
                CubeListBuilder.create(),
                PartPose.offset(0.0F, 24.0F, 0.0F));

        bone.addOrReplaceChild("hole",
                CubeListBuilder.create()
                        .texOffs(0, 12).addBox(1.0F, -2.12F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 7).addBox(0.5F, -2.1F, 0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 7).addBox(0.5F, -2.1F, -1.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(7, 7).addBox(0.5F, -2.5F, -1.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 0).addBox(-2.0F, -3.0F, -2.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 21).addBox(-1.0F, -1.8F, -3.2F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 16).addBox(-1.0F, -3.6F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(4, 16).addBox(-1.0F, -4.0F, -1.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(13, 16).addBox(-1.0F, -3.6F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 21).addBox(-1.0F, -1.8F, 1.2F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(9, 16).addBox(-1.0F, -4.0F, 0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        bone.addOrReplaceChild("back",
                CubeListBuilder.create()
                        .texOffs(9, 18).addBox(-0.8F, -2.0F, -2.0F, 1.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
                        .texOffs(16, 0).addBox(-2.0F, -3.0F, -1.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(16, 6).addBox(-3.0F, -2.8F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(26, 0).addBox(-4.0F, -2.7F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-2.0F, -1.0F, 0.0F, 0.0F, 0.0F, 0.1745F));

        return LayerDefinition.create(mesh, 32, 32);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        // no animation
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
        bone.render(poseStack, buffer, packedLight, packedOverlay, color);
    }
}
