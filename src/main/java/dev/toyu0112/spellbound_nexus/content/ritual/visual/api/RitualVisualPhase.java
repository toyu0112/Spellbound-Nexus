package dev.toyu0112.spellbound_nexus.content.ritual.visual.api;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public interface RitualVisualPhase {
    void play(Level level, BlockPos pos, int ticks);
    int startTick();
    int endTick();
    boolean isActive(int ticks);
}
