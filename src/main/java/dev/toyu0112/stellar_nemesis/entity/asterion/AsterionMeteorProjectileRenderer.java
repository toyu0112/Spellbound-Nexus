package dev.toyu0112.stellar_nemesis.entity.asterion;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.toyu0112.stellar_nemesis.StellarNemesis;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class AsterionMeteorProjectileRenderer extends EntityRenderer<AsterionMeteorProjectile> {
    private final AsterionMeteorProjectileModel<AsterionMeteorProjectile> model;

    public static final ModelLayerLocation LAYER =
            new ModelLayerLocation(new ResourceLocation(StellarNemesis.MOD_ID, "asterion_meteor_projectile"), "main");

    public AsterionMeteorProjectileRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.model = new AsterionMeteorProjectileModel<>(pContext.bakeLayer(LAYER));
    }

    @Override
    public void render(AsterionMeteorProjectile entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        poseStack.scale(0.5F, 0.5F, 0.5F);
        model.renderToBuffer(poseStack, buffer.getBuffer(RenderType.entityCutout(getTextureLocation(entity))), packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.popPose();
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(AsterionMeteorProjectile pEntity) {
        return new ResourceLocation(StellarNemesis.MOD_ID, "textures/entity/asterion_meteor_projectile.png");
    }
}
