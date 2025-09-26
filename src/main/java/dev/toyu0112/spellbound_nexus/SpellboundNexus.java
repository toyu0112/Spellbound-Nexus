package dev.toyu0112.spellbound_nexus;

import dev.toyu0112.spellbound_nexus.init.*;
import dev.toyu0112.spellbound_nexus.content.spell.SpellRegister;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(SpellboundNexus.MOD_ID)
public class SpellboundNexus {
    public static final String MOD_ID = "spellbound_nexus";

    public SpellboundNexus() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        ModAttributes.ATTRIBUTES.register(bus);
        ModCreativeModeTabs.CREATIVE_MODE_TABS.register(bus);
        ModItems.register(bus);
        ModEntities.register(bus);
        ModEffects.EFFECTS.register(bus);
        SpellRegister.spellRegister();
        ModBlocks.register(bus);
        ModBlockEntities.register(bus);
        ModParticleVisuals.registerAll();
    }
}
