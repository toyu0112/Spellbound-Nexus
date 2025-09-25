package dev.toyu0112.spellbound_nexus.spell;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class SpellManager {
    private static final Map<String, Spell> SPELLS = new HashMap<>();

    public static void register(Spell spell) { SPELLS.put(spell.getId(), spell); }
    public static Spell get(String id) { return SPELLS.get(id); }
    public static Collection<Spell> getAll() { return SPELLS.values(); }

    public static void cast(String id, ServerLevel level, LivingEntity caster, SpellTarget target) {
        Spell spell = get(id);
        if (spell == null) return;

        if (target.hasEntities() && !spell.allowsEntityTarget()) return;
        if (target.hasBlockOrPosition() && !spell.allowsBlockTarget()) return;
        if (target.isSelf() && !spell.allowsSelfTarget()) return;
        if (!target.hasEntities() && !target.hasBlockOrPosition() && !target.isSelf() && !spell.allowsAirTarget()) return;

        spell.cast(level, caster, target);
    }

    public static Vec3 safeGetPosition(ServerLevel level, LivingEntity caster, SpellTarget target, double maxDistance) {
        if (target.isSelf()) return caster.position();
        if (target.hasEntities()) return target.getEntities().get(0).position();
        if (target.getBlockPos() != null) return Vec3.atCenterOf(target.getBlockPos());
        if (target.getPosition() != null) return target.getPosition();
        Vec3 eye = caster.getEyePosition(1.0f);
        Vec3 look = caster.getViewVector(1.0f).scale(maxDistance);
        return eye.add(look);
    }
}
