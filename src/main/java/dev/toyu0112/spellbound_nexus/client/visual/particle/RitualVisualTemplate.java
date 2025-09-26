package dev.toyu0112.spellbound_nexus.client.visual.particle;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public interface RitualVisualTemplate {
    default void onStart(Level level, BlockPos pos) {}
    void play(Level level, BlockPos pos, int ticks);
    default void onFinish(Level level, BlockPos pos) {}
}