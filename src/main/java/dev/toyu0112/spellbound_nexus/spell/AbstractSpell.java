package dev.toyu0112.spellbound_nexus.spell;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public abstract class AbstractSpell implements Spell {
    private final String id;
    private final int maxRange;
    private boolean allowEntityTarget = true;
    private boolean allowBlockTarget = true;
    private boolean allowSelfTarget = true;
    private boolean allowAirTarget = true;

    public AbstractSpell(String id, int maxRange) {
        this.id = id;
        this.maxRange = maxRange;
    }

    @Override public String getId() { return id; }
    @Override public int getMaxRange() { return maxRange; }
    @Override public boolean allowsEntityTarget() { return allowEntityTarget; }
    @Override public boolean allowsBlockTarget() { return allowBlockTarget; }
    @Override public boolean allowsSelfTarget() { return allowSelfTarget; }
    @Override public boolean allowsAirTarget() { return allowAirTarget; }

    protected void setAllowEntityTarget(boolean b) { allowEntityTarget = b; }
    protected void setAllowBlockTarget(boolean b) { allowBlockTarget = b; }
    protected void setAllowSelfTarget(boolean b) { allowSelfTarget = b; }
    protected void setAllowAirTarget(boolean b) { allowAirTarget = b; }

    protected void preCast(ServerLevel level, LivingEntity caster, SpellTarget target) {}
    protected void postCast(ServerLevel level, LivingEntity caster, SpellTarget target) {}

    protected abstract void castBody(ServerLevel level, LivingEntity caster, SpellTarget target);

    @Override
    public final void cast(ServerLevel level, LivingEntity caster, SpellTarget target) {
        preCast(level, caster, target);
        castBody(level, caster, target);
        postCast(level, caster, target);;
    }

    protected Vec3 randOffSet(double range) {
        return new Vec3((Math.random() - 0.5) * range, 0, (Math.random() - 0.5) * range);
    }

    protected void spawnEntityAt(ServerLevel level, Entity entity, Vec3 pos) {
        entity.moveTo(pos.x, pos.y, pos.z, entity.getYRot(), entity.getXRot());
        level.addFreshEntity(entity);
    }

    protected void spawnParticles(ServerLevel level, Vec3 pos, ParticleOptions particle, int count) {
        for (int i = 0; i < count; i++) {
            level.sendParticles(particle, pos.x, pos.y, pos.z, 1, randOffSet(1.0).x, 0, randOffSet(1.0).z, 0.0);
        }
    }

    protected void playSound(ServerLevel level, Vec3 pos, SoundEvent sound) {
        level.playSound(null, pos.x, pos.y, pos.z, sound, SoundSource.PLAYERS, 1.0f, 1.0f);
    }
}
