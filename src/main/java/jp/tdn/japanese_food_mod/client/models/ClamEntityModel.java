package jp.tdn.japanese_food_mod.client.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.LivingEntity;

public class ClamEntityModel<T extends LivingEntity> extends EntityModel<T> {
    private final ModelPart body;

    public ClamEntityModel(ModelPart root) {
        this.body = root.getChild("body");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        PartDefinition body = root.addOrReplaceChild("body",
                CubeListBuilder.create(),
                PartPose.offset(0.0F, 24.0F, 0.0F));

        body.addOrReplaceChild("bone",
                CubeListBuilder.create()
                        .texOffs(0, 16).addBox(-2.7F, -1.0F, -2.0F, 5.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 11).addBox(-2.7F, -3.0F, -2.0F, 5.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 6).addBox(0.3F, -4.0F, 0.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 9).addBox(-2.7F, -2.0F, 2.0F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        body.addOrReplaceChild("bone2",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-4.7F, -1.0F, -6.0F, 8.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -1.0F, 3.0F));

        return LayerDefinition.create(mesh, 32, 32);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        // no animation
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
        body.render(poseStack, buffer, packedLight, packedOverlay, color);
    }
}
