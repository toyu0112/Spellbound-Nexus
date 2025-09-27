package dev.toyu0112.spellbound_nexus.content.ritual.particle.draw;

import dev.toyu0112.spellbound_nexus.content.ritual.particle.config.ParticleConfig;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class RitualParticleEffects {
    public static void drawCircle(Level level, Vec3 center, double radius, int density, Vec3 normal, double rotation, ParticleConfig config, int tick) {
        Vec3[] basis = getCircleBasic(normal);
        for (int i = 0; i < density; i++) {
            double angle = (2 * Math.PI / density) * i + rotation;
            Vec3 pos = getCirclePoint(center, radius, angle, basis[0], basis[1]);
            drawParticle(level, pos, config, tick);
        }
    }

    public static void drawStar(Level level, Vec3 center, double radius, int stepsPerLine, ParticleConfig config, Vec3 normal, double rotation, int tick) {
        Vec3[] basis = getCircleBasic(normal);
        int points = 5;
        Vec3[] vertices = new Vec3[points];

        for (int i = 0; i < points; i++) {
            double angle = (2 * Math.PI / points) * i - Math.PI / 2 + rotation;
            vertices[i] = getCirclePoint(center, radius, angle, basis[0], basis[1]);
        }

        for (int i = 0; i < points; i++) {
            int next = (i + 2) % points;
            drawLine(level, vertices[i], vertices[next], stepsPerLine, config, tick);
        }
    }

    public static void drawArc(Level level, Vec3 start, Vec3 end, double peakHeight, int steps, ParticleConfig config, int tick) {
        for (int i = 0; i <= steps; i++) {
            double t = i / (double) steps;
            double x = start.x + (end.x - start.x) * t;
            double z = start.z + (end.z - start.z) * t;
            double y = start.y + (peakHeight - start.y) * Math.sin(t * Math.PI) * (1 - t) + (end.y - start.y) * t;

            drawParticle(level, new Vec3(x, y, z), config, tick);
        }
    }

    public static void drawSpokes(Level level, Vec3 center, double outerRadius, double innerRadius, int count, int stepsPerLine, ParticleConfig config, Vec3 normal, double rotation, int tick) {
        Vec3[] basis = getCircleBasic(normal);

        for (int i = 0; i < count; i++) {
            double angle = (2 * Math.PI / count) * i + rotation;
            Vec3 from = getCirclePoint(center, outerRadius, angle, basis[0], basis[1]);
            Vec3 to = getCirclePoint(center, innerRadius, angle, basis[0], basis[1]);
            drawLine(level, from, to, stepsPerLine, config, tick);
        }
    }

    public static void drawBeam(Level level,Vec3 start, int height, Vec3 slope, ParticleConfig config, int tick) {
        for (int h = 0; h < height; h++) {
            Vec3 pos = start.add(slope.scale(h));
            drawParticle(level, pos, config, tick);
        }
    }

    public static void particleExplode(Level level, double x, double y, double z, int count, double spread, ParticleOptions particle) {
        for (int i = 0; i < count; i++) {
            double dx = (level.random.nextDouble() - 0.5) * spread;
            double dy = (level.random.nextDouble() - 0.5) * spread;
            double dz = (level.random.nextDouble() - 0.5) * spread;

            level.addParticle(particle, x, y, z, dx, dy, dz);
        }
    }

    public static Vec3[] getCircleBasic(Vec3 normal) {
        Vec3 n = normal.normalize();
        Vec3 tangent1 = n.cross(new Vec3(0, 1, 0)).normalize();
        if (tangent1.lengthSqr() == 0) tangent1 = new Vec3(1, 0, 0);
        Vec3 tangent2 = n.cross(tangent1).normalize();
        return new Vec3[]{tangent1, tangent2};
    }

    public static Vec3 getCirclePoint(Vec3 center, double radius, double angle, Vec3 t1, Vec3 t2) {
        return center.add(t1.scale(Math.cos(angle) * radius)).add(t2.scale(Math.sin(angle) * radius));
    }

    public static void drawLine(Level level, Vec3 from, Vec3 to, int steps, ParticleConfig config, int tick) {
        for (int i = 0; i <= steps; i++) {
            double t = i / (double) steps;
            Vec3 pos = from.lerp(to, t);
            drawParticle(level, pos, config, tick);
        }
    }

    public static void drawParticle(Level level, Vec3 pos, ParticleConfig config, int tick) {
        if (config.shouldDraw(tick))
            level.addParticle(config.type, pos.x, pos.y, pos.z, config.dx, config.dy, config.dz);
    }
}
