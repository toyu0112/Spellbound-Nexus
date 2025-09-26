package dev.toyu0112.spellbound_nexus.content.spell.astrovoid;

import dev.toyu0112.spellbound_nexus.entity.projectile.AstrovoidMeteorProjectile;
import dev.toyu0112.spellbound_nexus.content.spell.AbstractSpell;
import dev.toyu0112.spellbound_nexus.content.spell.SpellTarget;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Random;

import static dev.toyu0112.spellbound_nexus.content.spell.SpellManager.safeGetPosition;

public class AstrovoidMeteorSpell extends AbstractSpell {
    public AstrovoidMeteorSpell() {
        super("meteor_rain", 60);
        setAllowEntityTarget(true);
        setAllowBlockTarget(true);
        setAllowSelfTarget(false);
        setAllowAirTarget(true); // ★ 空中に撃てる
    }

    @Override
    protected void castBody(ServerLevel level, LivingEntity caster, SpellTarget target) {
        Vec3 pos = safeGetPosition(level, caster, target, getMaxRange());
        Random random = new Random();

        if (target.hasEntities()) {
            List<? extends Entity> entities = target.getEntities();
            for (Entity e : entities) {
                spawnMeteor(level, caster, e.position(), random);
            }
        } else {
            for (int i = 0; i < 10; i++) {
                Vec3 offset = pos.add(randomOffset(random, 3.0));
                spawnMeteor(level, caster, offset, random);
            }
        }
    }

    private void spawnMeteor(ServerLevel level, LivingEntity owner, Vec3 target, Random random) {
        double spawnX = target.x + random.nextGaussian() * 3.0;
        double spawnZ = target.z + random.nextGaussian() * 3.0;
        double spawnY = target.y + 20.0 + random.nextInt(6) - 3;

        AstrovoidMeteorProjectile meteorProjectile = new AstrovoidMeteorProjectile(level, owner);
        meteorProjectile.moveTo(spawnX, spawnY, spawnZ, 0, 0);

        double velX = (target.x - spawnX) * 0.05 + random.nextGaussian() * 0.05;
        double velZ = (target.z - spawnZ) * 0.05 + random.nextGaussian() * 0.05;
        double velY = -0.5 - random.nextDouble() * 0.3;

        meteorProjectile.setDeltaMovement(velX, velY, velZ);
        level.addFreshEntity(meteorProjectile);
    }

    private Vec3 randomOffset(Random rand, double radius) {
        return new Vec3(rand.nextGaussian() * radius, 0, rand.nextGaussian() * radius);
    }
}