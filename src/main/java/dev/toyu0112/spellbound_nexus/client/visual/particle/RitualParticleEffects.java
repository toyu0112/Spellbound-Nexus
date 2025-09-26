package dev.toyu0112.spellbound_nexus.client.visual.particle;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class RitualParticleEffects {
    public static void drawCircle(Level level, double centerX, double centerY, double centerZ, double radius, int density, ParticleOptions particle, Vec3 normal, double rotation) {
        Vec3 center = new Vec3(centerX, centerY, centerZ);
        Vec3[] basis = getCircleBasic(normal);
        for (int i = 0; i < density; i++) {
            double angle = (2 * Math.PI / density) * i + rotation;
            Vec3 pos = getCirclePoint(center, radius, angle, basis[0], basis[1]);
            level.addParticle(particle, pos.x, pos.y, pos.z, 0, 0.01, 0);
        }
    }

    public static void drawStar(Level level, double centerX, double centerY, double centerZ, double radius, int stepsPerLine, ParticleOptions particle, Vec3 normal, double rotation) {
        Vec3 center = new Vec3(centerX, centerY, centerZ);
        Vec3[] basis = getCircleBasic(normal);
        int points = 5;
        Vec3[] vertices = new Vec3[points];

        for (int i = 0; i < points; i++) {
            double angle = (2 * Math.PI / points) * i - Math.PI / 2 + rotation;
            vertices[i] = getCirclePoint(center, radius, angle, basis[0], basis[1]);
        }

        for (int i = 0; i < points; i++) {
            int next = (i + 2) % points;
            drawLine(level, vertices[i], vertices[next], stepsPerLine, particle);
        }
    }

    public static void drawArc(Level level, double x1, double y1, double z1, double x2, double y2, double z2, double peakHeight, int steps, ParticleOptions particle) {
        for (int i = 0; i <= steps; i++) {
            double t = i / (double) steps;
            double x = x1 + (x2 - x1) * t;
            double z = z1 + (z2 - z1) * t;
            double y = y1 + (peakHeight - y1) * Math.sin(t * Math.PI) * (1 - t) + (y2 - y1) * t;

            level.addParticle(particle, x, y, z, 0, 0.01, 0);
        }
    }

    public static void drawSpokes(Level level, double centerX, double centerY, double centerZ, double innerRadius, double outerRadius, int count, int stepsPerLine, ParticleOptions particle, Vec3 normal, double rotation) {
        Vec3 center = new Vec3(centerX, centerY, centerZ);
        Vec3[] basis = getCircleBasic(normal);

        for (int i = 0; i < count; i++) {
            double angle = (2 * Math.PI / count) * i + rotation;
            Vec3 from = getCirclePoint(center, innerRadius, angle, basis[0], basis[1]);
            Vec3 to = getCirclePoint(center, outerRadius, angle, basis[0], basis[1]);
            drawLine(level, from, to, stepsPerLine, particle);
        }
    }

    public static void drawBeam(Level level, double x, double y, double z, int height, double slopeX, double slopeZ, ParticleOptions particle) {
        for (int h = 0; h < height; h++) {
            double bx = x + slopeX * h;
            double by = y + h;
            double bz = z + slopeZ * h;

            level.addParticle(particle, bx, by, bz, slopeX * 0.2, 0.2, slopeZ * 0.2);
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

    public static void drawLine(Level level, Vec3 from, Vec3 to, int steps, ParticleOptions particle) {
        for (int i = 0; i <= steps; i++) {
            double t = i / (double) steps;
            Vec3 pos = from.lerp(to, t);
            level.addParticle(particle, pos.x, pos.y, pos.z, 0, 0.01, 0);
        }
    }
}
