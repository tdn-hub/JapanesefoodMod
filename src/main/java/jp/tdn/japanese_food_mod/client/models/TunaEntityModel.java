package jp.tdn.japanese_food_mod.client.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;

public class TunaEntityModel<T extends LivingEntity> extends EntityModel<T> {
    private final ModelPart bone;
    private final ModelPart back_tail;

    public TunaEntityModel(ModelPart root) {
        this.bone = root.getChild("bone");
        ModelPart tail = bone.getChild("tail");
        this.back_tail = tail.getChild("back_tail");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        PartDefinition bone = root.addOrReplaceChild("bone",
                CubeListBuilder.create(),
                PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body = bone.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(20, 30).addBox(-0.5F, -8.0F, -4.0F, 1.0F, 7.0F, 11.0F, new CubeDeformation(0.0F))
                        .texOffs(20, 48).addBox(0.5F, -7.0F, -4.0F, 1.0F, 5.0F, 11.0F, new CubeDeformation(0.0F))
                        .texOffs(20, 48).addBox(-1.5F, -7.0F, -4.0F, 1.0F, 5.0F, 11.0F, new CubeDeformation(0.0F))
                        .texOffs(20, 16).addBox(-2.5F, -6.0F, -4.0F, 1.0F, 3.0F, 11.0F, new CubeDeformation(0.0F))
                        .texOffs(20, 16).addBox(1.5F, -6.0F, -4.0F, 1.0F, 3.0F, 11.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, -2.0F));

        body.addOrReplaceChild("side3",
                CubeListBuilder.create()
                        .texOffs(20, 0).addBox(-2.5F, -3.5F, -4.0F, 1.0F, 4.0F, 11.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -4.0F, 0.0F, 0.0F, 0.0F, 0.4363F));

        body.addOrReplaceChild("side4",
                CubeListBuilder.create()
                        .texOffs(20, 0).mirror().addBox(1.5F, -3.5F, -4.0F, 1.0F, 4.0F, 11.0F, new CubeDeformation(0.0F)).mirror(false),
                PartPose.offsetAndRotation(0.0F, -4.0F, 0.0F, 0.0F, 0.0F, -0.4363F));

        body.addOrReplaceChild("side5",
                CubeListBuilder.create()
                        .texOffs(20, 33).addBox(-2.0F, -1.5F, -4.0F, 1.0F, 4.0F, 11.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -4.0F, 0.0F, 0.0F, 0.0F, -0.3491F));

        body.addOrReplaceChild("side6",
                CubeListBuilder.create()
                        .texOffs(20, 33).mirror().addBox(1.1F, -1.5F, -4.0F, 1.0F, 4.0F, 11.0F, new CubeDeformation(0.0F)).mirror(false),
                PartPose.offsetAndRotation(0.0F, -4.0F, 0.0F, 0.0F, 0.0F, 0.3491F));

        PartDefinition head = bone.addOrReplaceChild("head",
                CubeListBuilder.create()
                        .texOffs(0, 29).addBox(-0.5F, -2.0F, 0.0F, 1.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(8, 51).addBox(-1.0F, -1.0F, 0.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 51).addBox(0.0F, -1.0F, 0.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -5.0F, -8.0F));

        PartDefinition mouse = head.addOrReplaceChild("mouse",
                CubeListBuilder.create()
                        .texOffs(8, 47).addBox(-1.75F, -1.0F, -1.7F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.5F, 0.0F, 0.0F));

        mouse.addOrReplaceChild("mouse_under",
                CubeListBuilder.create()
                        .texOffs(6, 31).addBox(-1.75F, 1.9128F, -3.0038F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.0873F, 0.0F, 0.0F));

        mouse.addOrReplaceChild("mouse_upper",
                CubeListBuilder.create()
                        .texOffs(10, 37).addBox(-1.75F, 0.0F, -3.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 2.0F, -2.0F, -1.8326F, 0.0F, 0.0F));

        mouse.addOrReplaceChild("mouse_upper2",
                CubeListBuilder.create()
                        .texOffs(0, 36).addBox(-1.75F, 0.1F, -3.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, -2.0F, -2.8798F, 0.0F, 0.0F));

        head.addOrReplaceChild("side",
                CubeListBuilder.create()
                        .texOffs(0, 45).addBox(-1.5F, -1.0F, 0.0F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.3491F, 0.0F));

        head.addOrReplaceChild("side2",
                CubeListBuilder.create()
                        .texOffs(0, 45).mirror().addBox(0.5F, -1.0F, 0.0F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.3491F, 0.0F));

        head.addOrReplaceChild("side7",
                CubeListBuilder.create()
                        .texOffs(7, 40).addBox(-3.0F, 4.7F, 0.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(5.0F, -2.0F, -1.0F, 0.0F, 0.2618F, 0.3491F));

        head.addOrReplaceChild("side8",
                CubeListBuilder.create()
                        .texOffs(0, 40).addBox(-2.0F, 0.0F, 0.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(1.0F, -1.0F, 0.0F, 0.0F, -0.2618F, 0.6981F));

        head.addOrReplaceChild("side9",
                CubeListBuilder.create()
                        .texOffs(0, 40).mirror().addBox(1.0F, 0.0F, 0.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false),
                PartPose.offsetAndRotation(-1.0F, -1.0F, 0.0F, 0.0F, 0.2618F, -0.6981F));

        head.addOrReplaceChild("side10",
                CubeListBuilder.create()
                        .texOffs(7, 40).mirror().addBox(2.0F, 4.7F, 0.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false),
                PartPose.offsetAndRotation(-5.0F, -2.0F, -1.0F, 0.0F, -0.2618F, -0.3491F));

        PartDefinition tail = bone.addOrReplaceChild("tail",
                CubeListBuilder.create()
                        .texOffs(0, 16).addBox(-0.5F, -7.0F, 5.0F, 1.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 10).addBox(0.5F, -6.0F, 5.0F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 10).addBox(-1.5F, -6.0F, 5.0F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        tail.addOrReplaceChild("tail_side",
                CubeListBuilder.create()
                        .texOffs(12, 19).addBox(0.5F, -7.0F, 5.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(1.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.1745F));

        tail.addOrReplaceChild("tail_side2",
                CubeListBuilder.create()
                        .texOffs(12, 19).mirror().addBox(-1.5F, -7.0F, 5.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false),
                PartPose.offsetAndRotation(-1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.1745F));

        tail.addOrReplaceChild("tail_side3",
                CubeListBuilder.create()
                        .texOffs(0, 24).mirror().addBox(-1.5F, 5.0F, 5.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false),
                PartPose.offsetAndRotation(-1.0F, -9.0F, 0.0F, 0.0F, 0.0F, -0.1745F));

        tail.addOrReplaceChild("tail_side4",
                CubeListBuilder.create()
                        .texOffs(0, 24).addBox(0.5F, 5.0F, 5.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(1.0F, -9.0F, 0.0F, 0.0F, 0.0F, 0.1745F));

        PartDefinition back_tail = tail.addOrReplaceChild("back_tail",
                CubeListBuilder.create()
                        .texOffs(8, 11).addBox(-0.5F, -2.0F, 0.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(14, 13).addBox(-0.5F, -1.0F, 2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -4.0F, 8.0F));

        PartDefinition hire = back_tail.addOrReplaceChild("hire",
                CubeListBuilder.create()
                        .texOffs(8, 19).addBox(-0.5F, -5.0F, -0.2247F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 3.0F, -0.2618F, 0.0F, 0.0F));

        hire.addOrReplaceChild("hire2",
                CubeListBuilder.create()
                        .texOffs(8, 19).addBox(-0.5F, 0.7071F, -0.2247F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, 0.2618F, 0.0F, 0.0F));

        back_tail.addOrReplaceChild("back_tail_side",
                CubeListBuilder.create()
                        .texOffs(8, 25).addBox(-1.5F, -2.3F, -0.5F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0873F, 0.3491F));

        back_tail.addOrReplaceChild("back_tail_side2",
                CubeListBuilder.create()
                        .texOffs(8, 25).mirror().addBox(0.5F, -2.3F, -0.5F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.0873F, -0.3491F));

        back_tail.addOrReplaceChild("back_tail_side3",
                CubeListBuilder.create()
                        .texOffs(14, 25).mirror().addBox(0.5F, -0.2F, -0.5F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false),
                PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, 0.0F, -0.0873F, 0.3491F));

        back_tail.addOrReplaceChild("back_tail_side4",
                CubeListBuilder.create()
                        .texOffs(14, 25).addBox(-1.5F, -0.2F, -0.5F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, 0.0F, 0.0873F, -0.3491F));

        bone.addOrReplaceChild("sebire",
                CubeListBuilder.create()
                        .texOffs(12, 5).addBox(-0.5F, -2.1F, -1.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -8.0F, -3.0F, -1.0472F, 0.0F, 0.0F));

        bone.addOrReplaceChild("sebire2",
                CubeListBuilder.create()
                        .texOffs(8, 6).addBox(-0.5F, -3.0F, 0.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(4, 5).addBox(-0.5F, -4.0F, -0.9063F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -8.0F, 2.0F, -0.8727F, 0.0F, 0.0F));

        bone.addOrReplaceChild("harabire",
                CubeListBuilder.create()
                        .texOffs(0, 5).addBox(-0.5F, -0.5774F, -0.9063F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -3.0F, 2.0F, 0.8727F, 0.0F, 0.0F));

        bone.addOrReplaceChild("harabire2",
                CubeListBuilder.create()
                        .texOffs(0, 5).addBox(-0.5F, -1.5774F, -0.9063F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -3.0F, -5.0F, 0.8727F, 0.0F, 0.0F));

        bone.addOrReplaceChild("hire3",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(3.0F, -5.0F, -4.0F, 0.0F, 0.2618F, 0.0F));

        bone.addOrReplaceChild("hire4",
                CubeListBuilder.create()
                        .texOffs(0, 0).mirror().addBox(-0.5F, 0.0F, -1.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false),
                PartPose.offsetAndRotation(-3.0F, -5.0F, -4.0F, 0.0F, -0.2618F, 0.0F));

        return LayerDefinition.create(mesh, 64, 64);
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
        back_tail.yRot = Mth.sin(move * 0.4f * ageInTicks) * 0.4f;
    }
}
