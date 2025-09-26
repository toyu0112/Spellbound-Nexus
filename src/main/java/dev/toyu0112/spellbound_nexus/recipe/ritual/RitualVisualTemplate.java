package dev.toyu0112.spellbound_nexus.recipe.ritual;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public interface RitualVisualTemplate {
    void play(Level level, BlockPos pos, int ticks);
}
