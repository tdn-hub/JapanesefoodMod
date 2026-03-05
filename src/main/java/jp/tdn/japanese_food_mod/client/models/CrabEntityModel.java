package jp.tdn.japanese_food_mod.client.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;

public class CrabEntityModel<T extends LivingEntity> extends EntityModel<T> {
    private final ModelPart body;
    private final ModelPart leg;
    private final ModelPart leg2;
    private final ModelPart leg3;
    private final ModelPart leg4;
    private final ModelPart leg5;
    private final ModelPart leg6;
    private final ModelPart arm;
    private final ModelPart arm2;

    public CrabEntityModel(ModelPart root) {
        this.body = root.getChild("body");
        this.leg = body.getChild("leg");
        this.leg2 = body.getChild("leg2");
        this.leg3 = body.getChild("leg3");
        this.leg4 = body.getChild("leg4");
        this.leg5 = body.getChild("leg5");
        this.leg6 = body.getChild("leg6");
        this.arm = body.getChild("arm");
        this.arm2 = body.getChild("arm2");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        PartDefinition body = root.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2.0F, -4.0F, 0.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 23.0F, 0.0F, -0.2618F, 0.0F, 0.0F));

        body.addOrReplaceChild("leg",
                CubeListBuilder.create()
                        .texOffs(17, 0).addBox(-1.0F, 0.0341F, -0.5078F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(3.0F, -3.0341F, 3.2588F, 0.1745F, 0.0F, 0.0F))
                .addOrReplaceChild("leg_sub",
                        CubeListBuilder.create()
                                .texOffs(16, 2).addBox(4.5F, -3.8673F, 2.8325F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
                        PartPose.offsetAndRotation(-2.0F, 6.3197F, -2.3914F, 0.1745F, 0.0F, -0.3491F));

        body.addOrReplaceChild("leg2",
                CubeListBuilder.create()
                        .texOffs(17, 0).mirror().addBox(-1.0F, 0.0341F, -0.5078F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false),
                PartPose.offsetAndRotation(-3.0F, -3.0341F, 3.2588F, 0.1745F, 0.0F, 0.0F))
                .addOrReplaceChild("leg_sub2",
                        CubeListBuilder.create()
                                .texOffs(16, 2).mirror().addBox(-5.5F, -3.8674F, 2.8835F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false),
                        PartPose.offsetAndRotation(2.0F, 6.3197F, -2.3914F, 0.1745F, 0.0F, 0.3491F));

        body.addOrReplaceChild("leg3",
                CubeListBuilder.create()
                        .texOffs(17, 0).addBox(-1.0F, -0.9319F, -0.4755F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(3.0F, -2.0681F, 2.5176F, 0.1745F, 0.2618F, 0.0F))
                .addOrReplaceChild("leg_sub3",
                        CubeListBuilder.create()
                                .texOffs(16, 2).addBox(4.2675F, -8.0362F, 2.851F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
                        PartPose.offsetAndRotation(-0.5811F, 9.3544F, -1.8687F, 0.1745F, 0.0F, -0.3491F));

        body.addOrReplaceChild("leg4",
                CubeListBuilder.create()
                        .texOffs(17, 0).mirror().addBox(-1.0681F, -0.9318F, -0.4754F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false),
                PartPose.offsetAndRotation(-3.0F, -2.0681F, 2.5176F, 0.1745F, -0.2618F, 0.0F))
                .addOrReplaceChild("leg_sub4",
                        CubeListBuilder.create()
                                .texOffs(16, 2).mirror().addBox(-5.5F, -8.0362F, 2.8835F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false),
                        PartPose.offsetAndRotation(0.5811F, 9.3544F, -1.8687F, 0.1745F, 0.0F, 0.3491F));

        body.addOrReplaceChild("leg5",
                CubeListBuilder.create()
                        .texOffs(17, 0).mirror().addBox(-1.3951F, -1.1907F, -0.7171F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false),
                PartPose.offsetAndRotation(-3.0F, -1.8093F, 1.5517F, 0.1745F, -0.2618F, 0.0F))
                .addOrReplaceChild("leg_sub5",
                        CubeListBuilder.create()
                                .texOffs(23, 0).mirror().addBox(-3.3058F, -2.3882F, 0.083F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false),
                        PartPose.offsetAndRotation(0.124F, 2.4742F, -0.6988F, 0.0873F, 0.0F, 0.3491F));

        body.addOrReplaceChild("leg6",
                CubeListBuilder.create()
                        .texOffs(17, 0).addBox(-0.3461F, -1.1907F, -0.6684F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(3.0F, -1.8093F, 1.5517F, 0.1745F, 0.2618F, 0.0F))
                .addOrReplaceChild("leg_sub6",
                        CubeListBuilder.create()
                                .texOffs(23, 0).addBox(2.3058F, -2.3882F, 0.083F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)),
                        PartPose.offsetAndRotation(0.1348F, 2.3064F, -0.65F, 0.0873F, 0.0F, -0.3491F));

        body.addOrReplaceChild("outline",
                CubeListBuilder.create()
                        .texOffs(0, 9).addBox(1.5F, -4.0F, 0.8F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, -1.0F, 0.0F, -0.2618F, 0.0F));

        body.addOrReplaceChild("outline2",
                CubeListBuilder.create()
                        .texOffs(0, 9).mirror().addBox(-3.5F, -4.0F, 0.8F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false),
                PartPose.offsetAndRotation(0.0F, 0.0F, -1.0F, 0.0F, 0.2618F, 0.0F));

        PartDefinition arm = body.addOrReplaceChild("arm",
                CubeListBuilder.create()
                        .texOffs(0, 16).addBox(-3.0F, -2.5F, -1.5176F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.2618F, 0.0F));

        arm.addOrReplaceChild("s",
                CubeListBuilder.create()
                        .texOffs(0, 14).addBox(-2.5479F, -2.5F, -1.9507F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition arm2 = body.addOrReplaceChild("arm2",
                CubeListBuilder.create()
                        .texOffs(10, 12).mirror().addBox(2.0F, -2.5F, -2.5176F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.2618F, 0.0F));

        arm2.addOrReplaceChild("s2",
                CubeListBuilder.create()
                        .texOffs(10, 10).mirror().addBox(-0.7159F, -2.9F, -1.9507F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false),
                PartPose.offset(0.7159F, 0.2588F, -1.1918F));

        return LayerDefinition.create(mesh, 32, 32);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        // Alternating tripod gait (realistic crab locomotion)
        // Tripod A: right-back(leg), left-middle(leg4), right-front(leg6)
        // Tripod B: left-back(leg2), right-middle(leg3), left-front(leg5)
        float legSwing = limbSwing * 2.5f;
        float legAmp = 0.6f * limbSwingAmount;

        // Tripod A
        leg.yRot = Mth.cos(legSwing) * legAmp;
        leg4.yRot = Mth.cos(legSwing) * legAmp;
        leg6.yRot = Mth.cos(legSwing) * legAmp;

        // Tripod B (opposite phase)
        leg2.yRot = -Mth.cos(legSwing) * legAmp;
        leg3.yRot = -Mth.cos(legSwing) * legAmp;
        leg5.yRot = -Mth.cos(legSwing) * legAmp;

        // Claw animation - idle sway + occasional snapping
        float clawIdle = Mth.sin(ageInTicks * 0.15f) * 0.15f;
        boolean snapping = Mth.sin(ageInTicks * 0.03f) > 0.7f;
        float snapAngle = snapping ? Mth.sin(ageInTicks * 3.0f) * 0.4f : 0;
        arm.xRot = clawIdle + snapAngle;
        arm2.xRot = -clawIdle;

        // Subtle body sway while walking
        body.zRot = Mth.sin(limbSwing * 1.0f) * 0.04f * limbSwingAmount;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
        body.render(poseStack, buffer, packedLight, packedOverlay, color);
    }
}
