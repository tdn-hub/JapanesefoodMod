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
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart body2;
    private final ModelPart tail;
    private final ModelPart hire;
    private final ModelPart hire2;

    public EelEntityModel(ModelPart root) {
        this.body = root.getChild("body");
        this.head = body.getChild("head");
        this.body2 = body.getChild("body2");
        this.tail = body2.getChild("tail");
        this.hire = body.getChild("hire");
        this.hire2 = body.getChild("hire2");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        // Main body - pivot at center (chain root for undulation)
        PartDefinition body = root.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(0, 10).addBox(-1.0F, -3.0F, -4.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 10).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 10).addBox(-1.0F, -3.0F, 2.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 24.0F, 0.0F));

        // Head - child of body for chain animation
        body.addOrReplaceChild("head",
                CubeListBuilder.create()
                        .texOffs(0, 10).addBox(-1.0F, -3.0F, -3.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 2).addBox(-1.0F, -2.5F, -5.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 0).addBox(-1.0F, -2.5F, -6.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, -4.0F));

        // Pectoral fins - children of body
        body.addOrReplaceChild("hire",
                CubeListBuilder.create()
                        .texOffs(0, 3).addBox(0.0F, -0.5F, 0.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(1.0F, -1.0F, -7.0F, 0.0F, 0.2618F, 0.0F));

        body.addOrReplaceChild("hire2",
                CubeListBuilder.create()
                        .texOffs(0, 3).mirror().addBox(0.0F, -0.5F, 0.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false),
                PartPose.offsetAndRotation(-1.0F, -1.0F, -7.0F, 0.0F, -0.2618F, 0.0F));

        // Dorsal fin running along back
        body.addOrReplaceChild("dorsal_fin",
                CubeListBuilder.create()
                        .texOffs(0, 3).addBox(0.0F, -1.0F, -3.0F, 0.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -3.0F, 0.0F));

        // Rear body - child of body (chained for serpentine wave)
        PartDefinition body2 = body.addOrReplaceChild("body2",
                CubeListBuilder.create()
                        .texOffs(8, 0).addBox(-1.0F, -1.5F, 0.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(7, 5).addBox(-1.0F, -1.5F, 3.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -1.0F, 5.0F));

        // Tail - child of body2 (chain continues, wave amplifies)
        body2.addOrReplaceChild("tail",
                CubeListBuilder.create()
                        .texOffs(17, 11).addBox(-0.5F, -1.5F, 0.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(19, 6).addBox(-0.5F, -1.5F, 3.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 7.0F));

        return LayerDefinition.create(mesh, 32, 32);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float speed = entity.isInWater() ? 0.3f : 0.7f;
        float amplitude = entity.isInWater() ? 0.2f : 0.6f;
        float t = ageInTicks * speed;
        float phase = 1.2f;

        // Serpentine wave: head stays stable, wave amplifies toward tail
        // Chain hierarchy accumulates rotations naturally
        head.yRot = Mth.sin(t) * amplitude * 0.3f;
        body.yRot = Mth.sin(t - phase) * amplitude * 0.4f;
        body2.yRot = Mth.sin(t - phase * 2) * amplitude * 0.6f;
        tail.yRot = Mth.sin(t - phase * 3) * amplitude * 0.8f;

        // Pectoral fin flutter
        hire.yRot = 0.2618F + Mth.sin(ageInTicks * 0.5f) * 0.15f;
        hire2.yRot = -0.2618F - Mth.sin(ageInTicks * 0.5f) * 0.15f;

        // On land: exaggerated flopping
        if (!entity.isInWater()) {
            body.zRot = Mth.sin(ageInTicks * 0.6f) * 0.4f;
            head.xRot = Mth.sin(ageInTicks * 0.3f) * 0.2f;
        } else {
            body.zRot = 0.0f;
            head.xRot = 0.0f;
        }
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
        body.render(poseStack, buffer, packedLight, packedOverlay, color);
    }
}
