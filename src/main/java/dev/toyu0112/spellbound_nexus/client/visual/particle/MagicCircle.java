package dev.toyu0112.spellbound_nexus.client.visual.particle;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class MagicCircle {
    public static void drawMagicCircle(Level level, double x, double y, double z, double innerRadius, double outerRadius, int ticks, Vec3 direction, double rotationSpeed, ParticleOptions circleParticle, ParticleOptions styleParticle) {
        double rotation = ticks * rotationSpeed;
        int trailCount = Math.min(ticks / 2, 200);

        RitualParticleEffects.drawCircle(level, x, y, z, outerRadius, trailCount, circleParticle, direction, rotation);
        RitualParticleEffects.drawCircle(level, x, y, z, innerRadius, trailCount / 2, circleParticle, direction, rotation);
        RitualParticleEffects.drawSpokes(level, x, y, z, innerRadius, outerRadius, 20, 10, circleParticle, direction, rotation);
        RitualParticleEffects.drawStar(level, x, y, z, outerRadius, 30, styleParticle, direction, rotation);
    }
}
