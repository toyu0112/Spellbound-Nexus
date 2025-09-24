package dev.toyu0112.spellbound_nexus;

import dev.toyu0112.spellbound_nexus.client.model.entity.AsterionMeteorProjectileModel;
import dev.toyu0112.spellbound_nexus.client.render.entity.AsterionMeteorProjectileRenderer;
import dev.toyu0112.spellbound_nexus.init.ModCreativeModeTabs;
import dev.toyu0112.spellbound_nexus.init.ModEffects;
import dev.toyu0112.spellbound_nexus.init.ModEntities;
import dev.toyu0112.spellbound_nexus.init.ModItems;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(SpellboundNexus.MOD_ID)
public class SpellboundNexus {
    public static final String MOD_ID = "stellar_nemesis";

    public SpellboundNexus() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        ModCreativeModeTabs.register(bus);
        ModItems.register(bus);
        ModEntities.register(bus);
        ModEffects.EFFECTS.register(bus);
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
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
}
