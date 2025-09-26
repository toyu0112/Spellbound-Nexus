package dev.toyu0112.spellbound_nexus.client.visual;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class RitualEffects {
    public static void drawCircle(Level level, double centerX, double centerY, double centerZ, double radius, int density, ParticleOptions particle, Vec3 normal, double rotation) {
        Vec3 n = normal.normalize();
        Vec3 tangent1 = n.cross(new Vec3(0, 1, 0)).normalize();
        if (tangent1.lengthSqr() == 0) tangent1 = new Vec3(1, 0, 0);
        Vec3 tangent2 = n.cross(tangent1).normalize();

        for (int i = 0; i < density; i++) {
            double angle = (2 * Math.PI / density) * i + rotation;
            Vec3 offset = tangent1.scale(Math.cos(angle) * radius).add(tangent2.scale(Math.sin(angle) * radius));
            double x = centerX + offset.x;
            double y = centerY + offset.y;
            double z = centerZ + offset.z;

            level.addParticle(particle, x, y, z, 0, 0.01, 0);
        }
    }

    public static void drawStar(Level level, double centerX, double centerY, double centerZ, double radius, int stepsPerLine, ParticleOptions particle, Vec3 normal, double rotation) {
        Vec3 n = normal.normalize();
        Vec3 tangent1 = n.cross(new Vec3(0, 1, 0)).normalize();
        if (tangent1.lengthSqr() == 0) tangent1 = new Vec3(1, 0, 0);
        Vec3 tangent2 = n.cross(tangent1).normalize();

        int points = 5;
        Vec3[] vertices = new Vec3[points];
        for (int i = 0; i < points; i++) {
            double angle = (2 * Math.PI / points) * i - Math.PI / 2 + rotation;
            Vec3 offset = tangent1.scale(Math.cos(angle) * radius)
                    .add(tangent2.scale(Math.sin(angle) * radius));
            vertices[i] = new Vec3(centerX + offset.x, centerY + offset.y, centerZ + offset.z);
        }

        for (int i = 0; i < points; i++) {
            int next = (i + 2) % points;
            Vec3 from = vertices[i];
            Vec3 to = vertices[next];

            for (int step = 0; step <= stepsPerLine; step++) {
                double t = step / (double) stepsPerLine;
                double x = from.x + (to.x - from.x) * t;
                double y = from.y + (to.y - from.y) * t;
                double z = from.z + (to.z - from.z) * t;

                level.addParticle(particle, x, y, z, 0, 0.01, 0);
            }
        }
    }

    public static void drawArc(Level level, double x1, double y1, double z1, double x2, double y2, double z2, double peakHeight, int steps, ParticleOptions particle) {
        for (int step = 0; step <= steps; step++) {
            double t = step / (double) steps;
            double x = x1 + (x2 - x1) * t;
            double z = z1 + (z2 - z1) * t;
            double y = y1 + (peakHeight - y1) * Math.sin(t * Math.PI) * (1 - t) + (y2 - y1) * t;

            level.addParticle(particle, x, y, z, 0, 0.01, 0);
        }
    }

    public static void drawSpokes(Level level, double centerX, double centerY, double centerZ, double innerRadius, double outerRadius, int count, int stepsPerLine, ParticleOptions particle, Vec3 normal, double rotation) {
        Vec3 n = normal.normalize();
        Vec3 tangent1 = n.cross(new Vec3(0, 1, 0)).normalize();
        if (tangent1.lengthSqr() == 0) tangent1 = new Vec3(1, 0, 0);
        Vec3 tangent2 = n.cross(tangent1).normalize();

        for (int i = 0; i < count; i++) {
            double angle = (2 * Math.PI / count) * i + rotation;

            Vec3 fromOffset = tangent1.scale(Math.cos(angle) * innerRadius) .add(tangent2.scale(Math.sin(angle) * innerRadius));
            Vec3 toOffset = tangent1.scale(Math.cos(angle) * outerRadius) .add(tangent2.scale(Math.sin(angle) * outerRadius));

            Vec3 from = new Vec3(centerX + fromOffset.x, centerY + fromOffset.y, centerZ + fromOffset.z);
            Vec3 to = new Vec3(centerX + toOffset.x, centerY + toOffset.y, centerZ + toOffset.z);

            for (int step = 0; step <= stepsPerLine; step++) {
                double t = step / (double) stepsPerLine;
                double x = from.x + (to.x - from.x) * t;
                double y = from.y + (to.y - from.y) * t;
                double z = from.z + (to.z - from.z) * t;

                level.addParticle(particle, x, y, z, 0, 0.01, 0);
            }
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
}
