package dev.toyu0112.stellar_nemesis.entity;

import dev.toyu0112.stellar_nemesis.StellarNemesis;
import dev.toyu0112.stellar_nemesis.entity.asterion.AsterionMeteorProjectile;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SNEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, StellarNemesis.MOD_ID);

    public static final RegistryObject<EntityType<AsterionMeteorProjectile>> ASTERION_METEOR_PROJECTILE =
            ENTITIES.register("asterion_meteor_projectile", () ->
                    EntityType.Builder.<AsterionMeteorProjectile>of(AsterionMeteorProjectile::new, MobCategory.MISC)
                            .sized(0.25F, 0.25F)
                            .clientTrackingRange(4)
                            .updateInterval(10)
                            .build("asterion_meteor_projectile"));

    public static void register(IEventBus eventBus) {
        ENTITIES.register(eventBus);
    }
}
