package dev.toyu0112.spellbound_nexus.content.ritual.visual.template;

import dev.toyu0112.spellbound_nexus.content.ritual.visual.controller.RitualVisualController;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public interface RitualVisualTemplate {
    default void onStart(Level level, BlockPos pos) {}
    RitualVisualController createVisuals(Vec3 center);
    default void onFinish(Level level, BlockPos pos) {}
}