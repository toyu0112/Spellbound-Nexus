package dev.toyu0112.spellbound_nexus.content.ritual.visual.phase;

import dev.toyu0112.spellbound_nexus.content.ritual.particle.config.ParticleConfig;
import dev.toyu0112.spellbound_nexus.content.ritual.particle.draw.RitualParticleEffects;
import dev.toyu0112.spellbound_nexus.content.ritual.visual.template.RitualVisualPhase;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class BeamPhase implements RitualVisualPhase {
    private final Vec3 center;
    private final int height;
    private final Vec3 direction;
    private final ParticleConfig config;
    private final int startTick;
    private final int endTick;

    public BeamPhase(Vec3 center, int height, Vec3 direction, ParticleConfig config, int startTick, int endTick) {
        this.center = center;
        this.height = height;
        this.direction = direction;
        this.config = config;
        this.startTick = startTick;
        this.endTick = endTick;
    }

    @Override
    public void play(Level level, Vec3 center, int tick) {
        if (!level.isClientSide) return;
        RitualParticleEffects.drawBeam(level, center, height, direction, config, tick);
    }

    @Override
    public int startTick() {
        return startTick;
    }

    @Override
    public int endTick() {
        return endTick;
    }
}
