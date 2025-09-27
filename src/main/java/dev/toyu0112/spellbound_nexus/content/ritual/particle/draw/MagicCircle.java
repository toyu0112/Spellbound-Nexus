package dev.toyu0112.spellbound_nexus.content.ritual.particle.draw;

import dev.toyu0112.spellbound_nexus.content.ritual.particle.config.ParticleConfig;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class MagicCircle {
    public static void drawMagicCircle(Level level, Vec3 center, int ticks, Vec3 direction, double rotationSpeed, ParticleConfig circleConfig, ParticleConfig styleConfig, double outerRadius, double innerRadius) {
        double rotation = ticks * rotationSpeed;
        int trailCount = Math.min(ticks / 2, 200);

        RitualParticleEffects.drawCircle(level, center, innerRadius, trailCount, direction, rotation, circleConfig, ticks);
        RitualParticleEffects.drawCircle(level, center, outerRadius, trailCount / 2, direction, rotation, circleConfig, ticks);
        RitualParticleEffects.drawSpokes(level, center, innerRadius, outerRadius, 20, 10, circleConfig, direction, rotation, ticks);
        RitualParticleEffects.drawStar(level, center, outerRadius, 30, styleConfig, direction, rotation, ticks);
    }
}
