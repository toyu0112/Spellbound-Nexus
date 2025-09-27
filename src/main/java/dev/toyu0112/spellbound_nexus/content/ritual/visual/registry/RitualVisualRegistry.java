package dev.toyu0112.spellbound_nexus.content.ritual.visual.registry;

import dev.toyu0112.spellbound_nexus.content.ritual.visual.controller.RitualVisualController;
import dev.toyu0112.spellbound_nexus.content.ritual.visual.template.RitualVisualTemplate;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.Map;

public class RitualVisualRegistry {
    private static final Map<ResourceLocation, RitualVisualTemplate> templates = new HashMap<>();
    public static void register(ResourceLocation id, RitualVisualTemplate template) { templates.put(id, template); }

    public static void play(ResourceLocation id, Level level, BlockPos pos, int ticks) {
        RitualVisualTemplate template = templates.get(id);
        if (template == null || !level.isClientSide) return;


        Vec3 center = new Vec3(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
        RitualVisualController controller = template.createVisuals(center);
        controller.play(level, center, ticks);
    }

    public static RitualVisualController getController(ResourceLocation id, Vec3 center) {
        RitualVisualTemplate template = templates.get(id);
        return template != null ? template.createVisuals(center) : null;
    }

    public static boolean contains(ResourceLocation id) {
        return templates.containsKey(id);
    }
}
