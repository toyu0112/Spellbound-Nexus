package dev.toyu0112.stellar_nemesis.entity.asterion;// Made with Blockbench 4.12.6
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class AsterionMeteorProjectileModel<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "asterionmeteorprojectilemodel"), "main");
	private final ModelPart adventitia;
	private final ModelPart bone;

	public AsterionMeteorProjectileModel(ModelPart root) {
		this.adventitia = root.getChild("adventitia");
		this.bone = this.adventitia.getChild("bone");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition adventitia = partdefinition.addOrReplaceChild("adventitia", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -9.5F, -2.5F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(5.0F, -9.5F, -2.5F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(4.5F, -12.0F, -2.5F, 1.0F, 2.5F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-5.5F, -12.0F, -2.5F, 1.0F, 2.5F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-5.5F, -5.5F, -2.5F, 1.0F, 2.5F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(4.5F, -5.5F, -2.5F, 1.0F, 2.5F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(4.0F, -12.5F, -2.5F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(4.0F, -3.5F, -2.5F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-5.0F, -12.5F, -2.5F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-5.0F, -3.5F, -2.5F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(4.5F, -9.5F, -5.0F, 1.0F, 4.0F, 2.5F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(4.0F, -12.0F, -5.0F, 1.0F, 2.5F, 2.5F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(4.0F, -12.0F, 1.5F, 1.0F, 2.5F, 2.5F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-5.0F, -12.0F, 1.5F, 1.0F, 2.5F, 2.5F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-5.0F, -12.0F, -5.0F, 1.0F, 2.5F, 2.5F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-5.0F, -5.5F, -5.0F, 1.0F, 2.5F, 2.5F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-5.0F, -5.5F, 1.5F, 1.0F, 2.5F, 2.5F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(4.0F, -5.5F, 1.5F, 1.0F, 2.5F, 2.5F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(4.0F, -5.5F, -5.0F, 1.0F, 2.5F, 2.5F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-5.5F, -9.5F, -5.0F, 1.0F, 4.0F, 2.5F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(4.5F, -9.5F, 1.5F, 1.0F, 4.0F, 2.5F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-5.5F, -9.5F, 1.5F, 1.0F, 4.0F, 2.5F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-5.0F, -9.5F, -5.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-5.0F, -9.5F, 3.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(4.0F, -9.5F, -5.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(4.0F, -9.5F, 3.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-4.0F, -9.5F, -6.0F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-4.0F, -9.5F, 4.0F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(2.0F, -9.5F, -6.0F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(2.0F, -12.0F, -5.5F, 2.0F, 2.5F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(2.0F, -12.0F, 3.5F, 2.0F, 2.5F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-4.0F, -12.0F, 3.5F, 2.0F, 2.5F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-4.0F, -12.0F, -5.5F, 2.0F, 2.5F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-4.0F, -5.5F, -5.5F, 2.0F, 2.5F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-4.0F, -5.5F, 3.5F, 2.0F, 2.5F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(2.0F, -5.5F, 3.5F, 2.0F, 2.5F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(2.0F, -5.5F, -5.5F, 2.0F, 2.5F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(2.0F, -9.5F, 4.0F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-2.0F, -13.0F, 1.5F, 4.0F, 1.0F, 2.5F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-2.0F, -13.0F, -4.5F, 4.0F, 1.0F, 2.5F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(2.0F, -12.5F, -4.5F, 2.0F, 0.5F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(2.0F, -12.5F, 1.5F, 2.0F, 0.5F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-4.0F, -12.5F, 1.5F, 2.0F, 0.5F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-4.0F, -12.5F, -4.5F, 2.0F, 0.5F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-4.0F, -3.0F, -4.5F, 2.0F, 0.5F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-4.0F, -3.0F, 1.5F, 2.0F, 0.5F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(2.0F, -3.0F, 1.5F, 2.0F, 0.5F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(2.0F, -3.0F, -4.5F, 2.0F, 0.5F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-2.0F, -3.0F, -4.5F, 4.0F, 1.0F, 2.5F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-2.0F, -3.0F, 1.5F, 4.0F, 1.0F, 2.5F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition cube_r1 = adventitia.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -1.5F, 0.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, 5.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition cube_r2 = adventitia.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -1.5F, 0.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, -4.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition cube_r3 = adventitia.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -1.5F, 0.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -11.0F, -4.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition cube_r4 = adventitia.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -1.5F, 0.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -11.0F, 5.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition cube_r5 = adventitia.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -2.0F, -1.0F, 4.0F, 1.0F, 2.5F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.0F, 6.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition cube_r6 = adventitia.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -2.0F, -1.0F, 4.0F, 1.0F, 2.5F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.0F, -4.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition cube_r7 = adventitia.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -2.0F, -0.5F, 4.0F, 1.0F, 2.5F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -10.0F, -4.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition cube_r8 = adventitia.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -2.0F, -0.5F, 4.0F, 1.0F, 2.5F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -10.0F, 6.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition cube_r9 = adventitia.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -3.0F, -3.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -6.5F, 5.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r10 = adventitia.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -3.0F, -3.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -6.5F, -6.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r11 = adventitia.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -2.5F, -2.5F, 1.0F, 2.5F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -3.5F, 0.0F, 0.0F, 0.0F, -1.5708F));

		PartDefinition cube_r12 = adventitia.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -2.5F, -2.5F, 1.0F, 2.5F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -3.5F, 0.0F, 0.0F, 0.0F, -1.5708F));

		PartDefinition cube_r13 = adventitia.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -2.5F, -2.5F, 1.0F, 2.5F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -13.5F, 0.0F, 0.0F, 0.0F, -1.5708F));

		PartDefinition cube_r14 = adventitia.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -2.0F, -2.5F, 1.0F, 2.5F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -13.5F, 0.0F, 0.0F, 0.0F, -1.5708F));

		PartDefinition cube_r15 = adventitia.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -3.0F, -2.5F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -13.5F, 0.0F, 0.0F, 0.0F, -1.5708F));

		PartDefinition cube_r16 = adventitia.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -3.0F, -2.5F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -2.5F, 0.0F, 0.0F, 0.0F, -1.5708F));

		PartDefinition bone = adventitia.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offset(-1.0F, -6.5F, 5.0F));

		return LayerDefinition.create(meshdefinition, 16, 16);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		adventitia.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}