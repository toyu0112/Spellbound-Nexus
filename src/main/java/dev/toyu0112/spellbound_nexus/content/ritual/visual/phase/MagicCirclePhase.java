package dev.toyu0112.spellbound_nexus.content.ritual.visual.phase;

import dev.toyu0112.spellbound_nexus.content.ritual.particle.draw.MagicCircle;
import dev.toyu0112.spellbound_nexus.content.ritual.particle.config.ParticleConfig;
import dev.toyu0112.spellbound_nexus.content.ritual.visual.template.RitualVisualPhase;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class MagicCirclePhase implements RitualVisualPhase {
    private final ParticleConfig circleConfig;
    private final ParticleConfig styleConfig;
    private final double outerRadius;
    private final double innerRadius;
    private final Vec3 center;
    private final Vec3 direction;
    private final double rotationSpeed;
    private final int startTick;
    private final int endTick;

    public MagicCirclePhase(
            ParticleConfig circleConfig,
            ParticleConfig styleConfig,
            double outerRadius,
            double innerRadius,
            Vec3 center,
            Vec3 direction,
            double rotationSpeed,
            int startTick,
            int endTick
    ) {
        this.circleConfig = circleConfig;
        this.styleConfig = styleConfig;
        this.outerRadius = outerRadius;
        this.innerRadius = innerRadius;
        this.center = center;
        this.direction = direction;
        this.rotationSpeed = rotationSpeed;
        this.startTick = startTick;
        this.endTick = endTick;
    }

    @Override
    public void play(Level level, Vec3 ignored, int tick) {
        MagicCircle.drawMagicCircle(level, center, tick, direction, rotationSpeed, circleConfig, styleConfig, outerRadius, innerRadius);
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
