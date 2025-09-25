package dev.toyu0112.spellbound_nexus.init;

import dev.toyu0112.spellbound_nexus.SpellboundNexus;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;

public class ModDamageTypes {
    public static final ResourceKey<DamageType> SHOCK_WAVE = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(SpellboundNexus.MOD_ID, "shock_wave"));
}
