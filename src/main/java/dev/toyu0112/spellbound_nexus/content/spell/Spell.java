package dev.toyu0112.spellbound_nexus.content.spell;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;

public interface Spell {
    String getId();
    int getMaxRange();

    boolean allowsEntityTarget();
    boolean allowsBlockTarget();
    boolean allowsSelfTarget();
    boolean allowsAirTarget();

    void cast(ServerLevel level, LivingEntity caster, SpellTarget target);
}
