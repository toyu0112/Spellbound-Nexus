package dev.toyu0112.spellbound_nexus.client.visual.particle;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public interface RitualVisualPhase {
    void play(Level level, BlockPos pos, int ticks);

    boolean isActive(int ticks);
}
