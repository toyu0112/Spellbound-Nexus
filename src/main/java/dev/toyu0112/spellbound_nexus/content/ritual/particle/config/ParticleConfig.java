package dev.toyu0112.spellbound_nexus.content.ritual.particle.config;

import net.minecraft.core.particles.ParticleOptions;

public class ParticleConfig {
    public final ParticleOptions type;
    public final double dx, dy, dz;
    public final boolean conditional;
    public final int interval;

    public ParticleConfig(ParticleOptions type, double dx, double dy, double dz, boolean conditional, int interval) {
        this.type = type;
        this.dx = dx;
        this.dy = dy;
        this.dz = dz;
        this.conditional = conditional;
        this.interval = interval;
    }

    public boolean shouldDraw(int tick) {
        return !conditional || tick % interval == 0;
    }
}
