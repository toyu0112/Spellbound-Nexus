package dev.toyu0112.spellbound_nexus.init;

import dev.toyu0112.spellbound_nexus.SpellboundNexus;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModAttributes {
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, SpellboundNexus.MOD_ID);
    public static void register(IEventBus eventBus) { ATTRIBUTES.register(eventBus); }

    public static final RegistryObject<Attribute> ASTROVOID_DAMAGE =
            ATTRIBUTES.register("astrovoid_damage", () -> new RangedAttribute("attribute." + SpellboundNexus.MOD_ID + "astrovoid_damage", 1.0, 0.0 , 1024.0));
}
