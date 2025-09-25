package dev.toyu0112.spellbound_nexus.client.event;

import dev.toyu0112.spellbound_nexus.SpellboundNexus;
import dev.toyu0112.spellbound_nexus.client.model.entity.AsterionMeteorProjectileModel;
import dev.toyu0112.spellbound_nexus.client.render.entity.AsterionMeteorProjectileRenderer;
import dev.toyu0112.spellbound_nexus.init.ModEntities;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = SpellboundNexus.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
    }
    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(AsterionMeteorProjectileRenderer.LAYER, AsterionMeteorProjectileModel::createBodyLayer);
    }
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.ASTERION_METEOR_PROJECTILE.get(), AsterionMeteorProjectileRenderer::new);
    }
}