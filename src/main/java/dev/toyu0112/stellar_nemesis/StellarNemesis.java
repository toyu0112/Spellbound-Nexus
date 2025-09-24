package dev.toyu0112.stellar_nemesis;

import dev.toyu0112.stellar_nemesis.entity.SNEntities;
import dev.toyu0112.stellar_nemesis.entity.asterion.AsterionMeteorProjectileModel;
import dev.toyu0112.stellar_nemesis.entity.asterion.AsterionMeteorProjectileRenderer;
import dev.toyu0112.stellar_nemesis.item.SNCreativeModeTabs;
import dev.toyu0112.stellar_nemesis.item.SNItems;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(StellarNemesis.MOD_ID)
public class StellarNemesis {
    public static final String MOD_ID = "stellar_nemesis";

    public StellarNemesis() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        SNCreativeModeTabs.register(modEventBus);
        SNItems.register(modEventBus);
        SNEntities.register(modEventBus);
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
            event.registerEntityRenderer(SNEntities.ASTERION_METEOR_PROJECTILE.get(), AsterionMeteorProjectileRenderer::new);
        }
    }
}
