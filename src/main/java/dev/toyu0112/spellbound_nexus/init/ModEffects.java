package dev.toyu0112.spellbound_nexus.init;

import dev.toyu0112.spellbound_nexus.SpellboundNexus;
import dev.toyu0112.spellbound_nexus.effects.LowGravityEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEffects {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, SpellboundNexus.MOD_ID);
    public static void register(IEventBus eventBus) { EFFECTS.register(eventBus); }

    public static final RegistryObject<MobEffect> EFFECT_LOW_GRAVITY =
            EFFECTS.register("low_gravity", LowGravityEffect::new);
}
