package dev.toyu0112.spellbound_nexus.client.visual.particle;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class MagicCircle {
    private static double starRotation = 0;
    private static double spokesRotation = 0;

    public static void drawMagicCircle(Level level, double x, double y, double z, double innerRadius, double outerRadius, int ticks, Vec3 direction, double starRotationSpeed, double spokesRotationSpeed, ParticleOptions circle_particle, ParticleOptions star_particle) {
        starRotation += starRotationSpeed;
        spokesRotation += spokesRotationSpeed;

        int trailCount = Math.min(ticks / 2, 100);
        RitualParticleEffects.drawCircle(level, x, y, z, outerRadius, trailCount, ParticleTypes.GLOW, direction, ticks * 0.05);
        RitualParticleEffects.drawCircle(level, x, y, z, innerRadius, trailCount * 2, circle_particle, direction, ticks * 0.05);
        RitualParticleEffects.drawStar(level, x, y, z, innerRadius, 20, star_particle, direction, starRotation);
        RitualParticleEffects.drawSpokes(level, x, y, z, innerRadius, outerRadius, 20, 10, circle_particle, direction, spokesRotation);
    }
}
