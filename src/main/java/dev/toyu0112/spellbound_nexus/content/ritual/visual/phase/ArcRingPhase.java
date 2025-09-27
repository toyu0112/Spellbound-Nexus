package dev.toyu0112.spellbound_nexus.content.ritual.visual.phase;

import dev.toyu0112.spellbound_nexus.content.ritual.particle.config.ParticleConfig;
import dev.toyu0112.spellbound_nexus.content.ritual.particle.draw.RitualParticleEffects;
import dev.toyu0112.spellbound_nexus.content.ritual.visual.template.RitualVisualPhase;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ArcRingPhase implements RitualVisualPhase {
    private final Vec3 center;
    private final double radius;
    private final double height;
    private final int count;
    private final int steps;
    private final ParticleConfig config;
    private final int startTick;
    private final int endTick;

    public ArcRingPhase(Vec3 center, double radius, double height, int count, int steps, ParticleConfig config, int startTick, int endTick) {
        this.center = center;
        this.radius = radius;
        this.height = height;
        this.count = count;
        this.steps = steps;
        this.config = config;
        this.startTick = startTick;
        this.endTick = endTick;
    }

    @Override
    public void play(Level level, Vec3 center, int tick) {
        if (!level.isClientSide) return;

        for (int i = 0; i < count; i++) {
            double angle = (2 * Math.PI / count) * i;
            Vec3 start = new Vec3(center.x + radius * Math.cos(angle), center.y, center.z + radius * Math.sin(angle));
            Vec3 end = center.add(0, 2.5, 0);
            RitualParticleEffects.drawArc(level, start, end, center.y + height, steps, config, tick);
        }
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
