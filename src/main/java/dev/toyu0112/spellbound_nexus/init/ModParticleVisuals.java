package dev.toyu0112.spellbound_nexus.init;

import dev.toyu0112.spellbound_nexus.SpellboundNexus;
import dev.toyu0112.spellbound_nexus.client.visual.particle.RitualVisualTemplate;
import dev.toyu0112.spellbound_nexus.client.visual.particle.RitualVisualRegistry;
import dev.toyu0112.spellbound_nexus.client.visual.particle.StellarCrucible;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class ModParticleVisuals {
    public static final Map<ResourceLocation, RitualVisualTemplate> PARTICLE_VISUALS = new HashMap<>();
    public static void registerAll() {
        for (Map.Entry<ResourceLocation, RitualVisualTemplate> entry : PARTICLE_VISUALS.entrySet()) RitualVisualRegistry.register(entry.getKey(), entry.getValue());
    }

    static {
        PARTICLE_VISUALS.put(new ResourceLocation(SpellboundNexus.MOD_ID, "stellar_crucible"), new StellarCrucible());
    }
}