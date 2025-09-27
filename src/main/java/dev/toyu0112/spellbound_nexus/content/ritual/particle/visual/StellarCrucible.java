package dev.toyu0112.spellbound_nexus.content.ritual.particle.visual;

import dev.toyu0112.spellbound_nexus.content.ritual.particle.config.ParticleConfig;
import dev.toyu0112.spellbound_nexus.content.ritual.visual.builder.RitualVisualBuilder;
import dev.toyu0112.spellbound_nexus.content.ritual.visual.controller.RitualVisualController;
import dev.toyu0112.spellbound_nexus.content.ritual.visual.template.RitualVisualTemplate;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class StellarCrucible implements RitualVisualTemplate {
    private static final Vec3 UP = new Vec3(0, 1, 0);
    private static final Vec3 SLOPE = new Vec3(0.5, 1.0, 0);
    private static final int BEAM_HEIGHT = 25;
    private static final double INNER_RADIUS = 8;
    private static final double OUTER_RADIUS = 10;
    private static final double SKY_INNER_RADIUS = 20;
    private static final double SKY_OUTER_RADIUS = 16;

    // Phase 1: Magic Circle
    private final ParticleConfig phase1_circle = new ParticleConfig(ParticleTypes.GLOW, 0, 0.01, 0, false, 1);
    private final ParticleConfig phase1_style = new ParticleConfig(ParticleTypes.SCULK_SOUL, 0, 0.01, 0, false, 1);

    // Phase 2: Arc Ring
    private final ParticleConfig phase2_arc = new ParticleConfig(ParticleTypes.SOUL_FIRE_FLAME, 0, 0.01, 0, true, 3);

    // Phase 3: Beam
    private final ParticleConfig phase3_beam = new ParticleConfig(ParticleTypes.END_ROD, SLOPE.x * 0.2, 0.2, SLOPE.z * 0.2, false, 1);

    // Phase 4: Sky Magic Circle
    private final ParticleConfig phase4_circle = new ParticleConfig(ParticleTypes.GLOW, 0, 0.01, 0, false, 1);
    private final ParticleConfig phase4_style = new ParticleConfig(ParticleTypes.SOUL_FIRE_FLAME, 0, 0.01, 0, false, 1);

    @Override
    public RitualVisualController createVisuals(Vec3 center) {
        Vec3 beamTip = center.add(SLOPE.scale(BEAM_HEIGHT));
        Vec3 skyCenter = beamTip.add(0, 2, 0);
        Vec3 skyDirection = SLOPE.normalize();

        return new RitualVisualBuilder()
                .addMagicCircle(0, 400, center ,phase1_circle, phase1_style, INNER_RADIUS, OUTER_RADIUS, UP, 0.5)
                .addArcRing(200, 400, center, OUTER_RADIUS, 2, 20, 10, phase2_arc)
                .addBeam(400, 700, center, BEAM_HEIGHT, SLOPE, phase3_beam)
                .addMagicCircle(500, 700, skyCenter ,phase4_circle, phase4_style, SKY_INNER_RADIUS, SKY_OUTER_RADIUS, skyDirection, 0.75)
                .endSequence(center.add(0, 2, 0), ParticleTypes.FLAME, 200, 1.5, EntityType.ZOMBIE, ItemStack.EMPTY)
                .build();
    }
}
