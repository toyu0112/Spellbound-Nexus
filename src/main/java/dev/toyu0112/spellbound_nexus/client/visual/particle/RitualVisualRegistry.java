package dev.toyu0112.spellbound_nexus.client.visual.particle;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Map;

public class RitualVisualRegistry {
    private static final Map<ResourceLocation, RitualVisualTemplate> templates = new HashMap<>();
    public static void register(ResourceLocation id, RitualVisualTemplate template) { templates.put(id, template); }

    public static void play(ResourceLocation id, Level level, BlockPos pos, int ticks) {
        RitualVisualTemplate template = templates.get(id);
        if (template != null) template.play(level, pos, ticks);
    }

    public static boolean contains(ResourceLocation id) {
        return templates.containsKey(id);
    }
}
