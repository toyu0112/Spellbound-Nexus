package dev.toyu0112.spellbound_nexus.content.ritual.visual.template;

import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public interface RitualVisualPhase {
    void play(Level level, Vec3 center, int tick);
    int startTick();
    int endTick();
}
