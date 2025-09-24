package dev.toyu0112.stellar_nemesis.entity.asterion;

import dev.toyu0112.stellar_nemesis.entity.SNEntities;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class AsterionMeteorProjectile extends ThrowableProjectile {
    public AsterionMeteorProjectile(EntityType<? extends ThrowableProjectile> type, Level world) {
        super(type, world);
    }

    public AsterionMeteorProjectile(Level world, LivingEntity shooter) {
        super(SNEntities.ASTERION_METEOR_PROJECTILE.get(), shooter, world);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        result.getEntity().hurt(damageSources().thrown(this, this.getOwner()), 5.0F);
        this.discard();
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        this.level().explode(this, this.getX(), this.getY(), this.getZ(), 4.0F, Level.ExplosionInteraction.NONE);
        this.discard();
    }

    @Override
    protected void defineSynchedData() {

    }
}
