package dev.toyu0112.spellbound_nexus.entity.projectile;

import dev.toyu0112.spellbound_nexus.init.ModAttributes;
import dev.toyu0112.spellbound_nexus.init.ModEntities;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class AstrovoidMeteorProjectile extends ThrowableProjectile {
    public AstrovoidMeteorProjectile(EntityType<? extends ThrowableProjectile> type, Level world) {
        super(type, world);
    }

    public AstrovoidMeteorProjectile(Level world, LivingEntity shooter) {
        super(ModEntities.ASTROVOID_METEOR_PROJECTILE.get(), shooter, world);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);

        Entity target = result.getEntity();
        Entity owner = this.getOwner();
        double damage = 5.0D;

        if (owner instanceof LivingEntity livingOwner) {
            damage += livingOwner.getAttributeValue(ModAttributes.ASTROVOID_DAMAGE.get());
        }

        target.hurt(damageSources().thrown(this, owner), (float) damage);

        this.discard();
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        this.discard();
    }

    @Override
    protected boolean canHitEntity(Entity target) {
        return super.canHitEntity(target) && target != this.getOwner();
    }

    @Override
    protected void defineSynchedData() {

    }
}
