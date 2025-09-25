package dev.toyu0112.spellbound_nexus.client.render.block_entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.toyu0112.spellbound_nexus.entity.block_entity.AsterionAltarBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class AsterionAltarRenderer implements BlockEntityRenderer<AsterionAltarBlockEntity> {
    @Override
    public void render(AsterionAltarBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        ItemStack stack = pBlockEntity.getSpinningItem();
        if (stack.isEmpty()) return;
        pPoseStack.pushPose();
        double time = (System.currentTimeMillis() / 50.0) % 360;
        pPoseStack.translate(0.5, 0.9, 0.5);
        pPoseStack.mulPose(Axis.YP.rotationDegrees((float) time));
        Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.GROUND, pPackedLight, pPackedOverlay, pPoseStack, pBuffer, null, 0);
        pPoseStack.popPose();
    }
}
