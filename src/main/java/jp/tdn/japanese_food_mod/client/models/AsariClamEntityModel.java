package jp.tdn.japanese_food_mod.client.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;

public class AsariClamEntityModel<T extends LivingEntity> extends EntityModel<T> {
    private final ModelPart body;
    private final ModelPart upper_shell;

    public AsariClamEntityModel(ModelPart root) {
        this.body = root.getChild("body");
        this.upper_shell = body.getChild("upper_shell");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        PartDefinition body = root.addOrReplaceChild("body",
                CubeListBuilder.create(),
                PartPose.offset(0.0F, 24.0F, 0.0F));

        // Lower shell (stays fixed)
        body.addOrReplaceChild("lower_shell",
                CubeListBuilder.create()
                        .texOffs(0, 19).addBox(-1.7F, -1.0F, -1.0F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 13).addBox(-0.7F, -1.0F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        // Upper shell (pivots at hinge/back edge for opening animation)
        body.addOrReplaceChild("upper_shell",
                CubeListBuilder.create()
                        .texOffs(0, 5).addBox(-1.7F, -1.0F, -3.0F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 9).addBox(-0.7F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -2.0F, 2.0F));

        // Extended foot/base
        body.addOrReplaceChild("foot",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2.7F, -1.0F, -5.0F, 5.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -1.0F, 3.0F));

        return LayerDefinition.create(mesh, 32, 32);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        // Periodic shell opening/closing (slightly faster than hamaguri)
        float openAmount = Math.max(0, Mth.sin(ageInTicks * 0.08f));
        upper_shell.xRot = -openAmount * 0.3f;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
        body.render(poseStack, buffer, packedLight, packedOverlay, color);
    }
}
