package dev.toyu0112.spellbound_nexus.client.visual;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.Level;

public class RitualEffects {
    public static void drawCircle(Level level, double centerX, double centerY, double centerZ, double radius, int density, ParticleOptions particle, double rotation) {
        for (int i = 0; i < density; i++) {
            double angle = (2 * Math.PI / density) * i + rotation;
            double x = centerX + radius * Math.cos(angle);
            double z = centerZ + radius * Math.sin(angle);

            level.addParticle(particle, x, centerY, z, 0, 0.01, 0);
        }
    }

    public static void drawStar(Level level, double centerX, double centerY, double centerZ, double radius, int stepsPerLine, ParticleOptions particle) {
        int points = 5;
        double[] vertexX = new double[points];
        double[] vertexZ = new double[points];

        for (int i = 0; i < points; i++) {
            double angle = (2 * Math.PI / points) * i - Math.PI / 2;
            vertexX[i] = centerX + radius * Math.cos(angle);
            vertexZ[i] = centerZ + radius * Math.sin(angle);
        }

        for (int i = 0; i < points; i++) {
            int next = (i + 2) % points;

            for (int step = 0; step <= stepsPerLine; step++) {
                double t = step / (double) stepsPerLine;
                double x = vertexX[i] + (vertexX[next] - vertexX[i]) * t;
                double z = vertexZ[i] + (vertexZ[next] - vertexZ[i]) * t;

                level.addParticle(particle, x, centerY, z, 0, 0.01, 0);
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

    public static void drawSpokes(Level level, double centerX, double centerY, double centerZ, double innerRadius, double outerRadius, int count, int stepsPerLine, ParticleOptions particle) {
        for (int i = 0; i < count; i++) {
            double angle = (2 * Math.PI / count) * i;
            double x1 = centerX + innerRadius * Math.cos(angle);
            double z1 = centerZ + innerRadius * Math.sin(angle);
            double x2 = centerX + outerRadius * Math.cos(angle);
            double z2 = centerZ + outerRadius * Math.sin(angle);

            for (int step = 0; step <= stepsPerLine; step++) {
                double t = step / (double) stepsPerLine;
                double x = x1 + (x2 - x1) * t;
                double z = z1 + (z2 - z1) * t;

                level.addParticle(particle, x, centerY, z, 0, 0.01, 0);
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
