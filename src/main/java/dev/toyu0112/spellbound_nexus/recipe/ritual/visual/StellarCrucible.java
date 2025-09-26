package dev.toyu0112.spellbound_nexus.recipe.ritual.visual;

import dev.toyu0112.spellbound_nexus.client.visual.RitualSounds;
import dev.toyu0112.spellbound_nexus.client.visual.particle.MagicCircle;
import dev.toyu0112.spellbound_nexus.client.visual.particle.RitualParticleEffects;
import dev.toyu0112.spellbound_nexus.recipe.ritual.RitualVisualTemplate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class StellarCrucible implements RitualVisualTemplate {
    @Override
    public void play(Level level, BlockPos pos, int ticks) {
        RitualSounds.StellarCrucible(level, pos, ticks);
        if (!level.isClientSide) return;

        double x = pos.getX() + 0.5;
        double y = pos.getY();
        double z = pos.getZ() + 0.5;
        Vec3 up = new Vec3(0, 1, 0);
        double slopeX = 0.5;
        double slopeZ = 0;
        int beamHeight = 25;

        if (isInPhase(ticks, 0, 400))
            playPhase1(level, x, y, z, ticks, up);

        if (isInPhase(ticks, 200, 400))
            playPhase2(level, x, y, z);

        if (isInPhase(ticks, 400, 600))
            playPhase3(level, x, y, z, beamHeight, slopeX, slopeZ);

        if (isInPhase(ticks, 500, 600))
            playPhase4(level, x, y, z, beamHeight, slopeX, slopeZ, ticks);

        if (ticks == 600)
            RitualParticleEffects.particleExplode(level, x, y + 2.0, z, 200, 1.5, ParticleTypes.FLAME);
    }

    private boolean isInPhase(int ticks, int start, int end) {
        return ticks >= start && ticks < end;
    }

    private void playPhase1(Level level, double x, double y, double z, int ticks, Vec3 direction) {
        MagicCircle.drawMagicCircle(level, x, y, z, 10.0, 8.0, ticks, direction, 0.3, 0.5, ParticleTypes.GLOW, ParticleTypes.SCULK_SOUL);
    }

    private void playPhase2(Level level, double x, double y, double z) {
        double radius = 10.0;
        for (int i = 0; i < 20; i++) {
            double angle = (2 * Math.PI / 20) * i;
            double ex = x + radius * Math.cos(angle);
            double ez = z + radius * Math.sin(angle);
            RitualParticleEffects.drawArc(level, ex, y, ez, x, y + 2.0, z, y + 2.5, 10, ParticleTypes.SOUL_FIRE_FLAME);
        }
    }

    private void playPhase3(Level level, double x, double y, double z, int height, double slopeX, double slopeZ) {
        RitualParticleEffects.drawBeam(level, x, y + 2.0, z, height, slopeX, slopeZ, ParticleTypes.END_ROD);
    }

    private void playPhase4(Level level, double x, double y, double z, int height, double slopeX, double slopeZ, int ticks) {
        double ringY = y + height + 2;
        double ringX = x + slopeX * height;
        double ringZ = z + slopeZ * height;
        Vec3 laserDirection = new Vec3(slopeX, 1.0, slopeZ).normalize();
        MagicCircle.drawMagicCircle(level, ringX, ringY, ringZ, 20.0, 18.0, ticks, laserDirection, 0.3, 0.5, ParticleTypes.GLOW, ParticleTypes.SOUL_FIRE_FLAME);
    }
}
