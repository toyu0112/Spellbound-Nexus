package dev.toyu0112.spellbound_nexus.content.ritual.visual.builder;

import dev.toyu0112.spellbound_nexus.content.ritual.particle.config.ParticleConfig;
import dev.toyu0112.spellbound_nexus.content.ritual.particle.draw.RitualParticleEffects;
import dev.toyu0112.spellbound_nexus.content.ritual.visual.controller.RitualVisualController;
import dev.toyu0112.spellbound_nexus.content.ritual.visual.phase.ArcRingPhase;
import dev.toyu0112.spellbound_nexus.content.ritual.visual.phase.BeamPhase;
import dev.toyu0112.spellbound_nexus.content.ritual.visual.phase.MagicCirclePhase;
import dev.toyu0112.spellbound_nexus.content.ritual.visual.template.RitualVisualPhase;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class RitualVisualBuilder {
    private final List<RitualVisualPhase> phases = new ArrayList<>();
    private final List<Consumer<Level>> endActions = new ArrayList<>();
    private Runnable onEnd = () -> {};

    public RitualVisualBuilder endSequence(Vec3 position, ParticleOptions explosionParticle, int explosionCount, double explosionSpread, EntityType<?> bossType, ItemStack rewardItem) {
        this.endActions.add(level -> {
            if (level.isClientSide) {
                RitualParticleEffects.particleExplode(level, position.x, position.y, position.z, explosionCount, explosionSpread, explosionParticle);
                return;
            }

            if (bossType != null) {
                Entity boss = bossType.create(level);
                if (boss != null) {
                    boss.moveTo(position.x, position.y, position.z, 0, 0);
                    level.addFreshEntity(boss);
                }
            }

            if (rewardItem != null && !rewardItem.isEmpty()) {
                ItemEntity item = new ItemEntity(level, position.x, position.y, position.z, rewardItem.copy());
                item.setDefaultPickUpDelay();
                level.addFreshEntity(item);
            }
        });
        return this;
    }

    public RitualVisualBuilder addMagicCircle(int startTick, int endTick, Vec3 center ,ParticleConfig circleConfig, ParticleConfig styleConfig, double outerRadius, double innerRadius, Vec3 direction, double rotationSpeed) {
        phases.add(new MagicCirclePhase(circleConfig, styleConfig, innerRadius, outerRadius, center, direction, rotationSpeed, startTick, endTick));
        return this;
    }

    public RitualVisualBuilder addArcRing(int startTick, int endTick, Vec3 center,double radius, double height, int count, int steps, ParticleConfig config) {
        phases.add(new ArcRingPhase(center, radius, height, count, steps, config, startTick, endTick));
        return this;
    }

    public RitualVisualBuilder addBeam(int startTick, int endTick, Vec3 center, int height, Vec3 direction, ParticleConfig config) {
        phases.add(new BeamPhase(center, height, direction, config, startTick, endTick));
        return this;
    }

    public RitualVisualController build() {
        RitualVisualController controller = new RitualVisualController(phases);
        for (Consumer<Level> action : endActions) {
            controller.addEndAction(action);
        }
        return controller;
    }
}
