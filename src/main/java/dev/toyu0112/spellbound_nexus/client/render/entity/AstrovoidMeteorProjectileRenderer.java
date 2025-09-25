package dev.toyu0112.spellbound_nexus.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.toyu0112.spellbound_nexus.SpellboundNexus;
import dev.toyu0112.spellbound_nexus.client.model.entity.AstrovoidMeteorProjectileModel;
import dev.toyu0112.spellbound_nexus.entity.projectile.AstrovoidMeteorProjectile;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class AstrovoidMeteorProjectileRenderer extends EntityRenderer<AstrovoidMeteorProjectile> {
    private final AstrovoidMeteorProjectileModel<AstrovoidMeteorProjectile> model;

    public static final ModelLayerLocation LAYER =
            new ModelLayerLocation(new ResourceLocation(SpellboundNexus.MOD_ID, "astrovoid_meteor_projectile"), "main");

    public AstrovoidMeteorProjectileRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.model = new AstrovoidMeteorProjectileModel<>(pContext.bakeLayer(LAYER));
    }

    @Override
    public void render(AstrovoidMeteorProjectile entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        poseStack.scale(0.5F, 0.5F, 0.5F);
        model.renderToBuffer(poseStack, buffer.getBuffer(RenderType.entityCutout(getTextureLocation(entity))), packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.popPose();
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(AstrovoidMeteorProjectile pEntity) {
        return new ResourceLocation(SpellboundNexus.MOD_ID, "textures/entity/astrovoid_meteor_projectile.png");
    }
}
