package dev.toyu0112.spellbound_nexus.client.visual.particle;

import dev.toyu0112.spellbound_nexus.client.visual.RitualSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class StellarCrucible implements RitualVisualTemplate {
    private static final Vec3 UP = new Vec3(0, 1, 0);
    private static final Vec3 SLOPE = new Vec3(0.5, 1.0, 0);
    private static final int BEAM_HEIGHT = 25;

    @Override
    public void play(Level level, BlockPos pos, int ticks) {
        RitualSounds.StellarCrucible(level, pos, ticks);
        if (!level.isClientSide) return;

        Vec3 center = new Vec3(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
        Vec3 beamTip = center.add(SLOPE.scale(BEAM_HEIGHT));

        if (ticks == 600) {
            RitualParticleEffects.particleExplode(level, center.x, center.y + 2.0, center.z, 200, 1.5, ParticleTypes.FLAME);
            return;
        }

        if (ticks < 400) playPhase1(level, center, ticks);
        if (ticks >= 200 && ticks < 400) playPhase2(level, center);
        if (ticks >= 400 && ticks < 600) playPhase3(level, center);
        if (ticks >= 500 && ticks < 600) playPhase4(level, beamTip, ticks);
    }

    private void playPhase1(Level level, Vec3 center, int ticks) {
        MagicCircle.drawMagicCircle(level, center.x, center.y, center.z, 10.0, 8.0, ticks, UP, 0.5, ParticleTypes.GLOW, ParticleTypes.SCULK_SOUL);
    }

    private void playPhase2(Level level, Vec3 center) {
        double radius = 10.0;
        for (int i = 0; i < 20; i++) {
            double angle = (2 * Math.PI / 20) * i;
            double ex = center.x + radius * Math.cos(angle);
            double ez = center.z + radius * Math.sin(angle);
            RitualParticleEffects.drawArc(level, ex, center.y, ez, center.x, center.y + 2.0, center.z, center.y + 2.5, 10, ParticleTypes.SOUL_FIRE_FLAME);
        }
    }

    private void playPhase3(Level level, Vec3 center) {
        RitualParticleEffects.drawBeam(level, center.x, center.y + 2.0, center.z, BEAM_HEIGHT, SLOPE.x, SLOPE.z, ParticleTypes.END_ROD);
    }

    private void playPhase4(Level level, Vec3 beamTip, int ticks) {
        Vec3 direction = SLOPE.normalize();
        MagicCircle.drawMagicCircle(level, beamTip.x, beamTip.y + 2, beamTip.z, 20.0, 18.0, ticks, direction, 0.5,  ParticleTypes.GLOW, ParticleTypes.SOUL_FIRE_FLAME);
    }
}
