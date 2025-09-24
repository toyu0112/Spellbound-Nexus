package dev.toyu0112.stellar_nemesis.attribute;

import dev.toyu0112.stellar_nemesis.StellarNemesis;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SNAttributes {
    public static final DeferredRegister<Attribute> ATTRIBUTES =
            DeferredRegister.create(ForgeRegistries.ATTRIBUTES, StellarNemesis.MOD_ID);

    public static final RegistryObject<Attribute> ASTROVOID_POWER =
            ATTRIBUTES.register("astrovoid_power",
                    () -> new RangedAttribute("attribute.name." + StellarNemesis.MOD_ID + ".astrovoid_power",
                            0.0D, 0.0D, 1024.0D).setSyncable(true));

    public static void register(IEventBus eventBus) {
        ATTRIBUTES.register(eventBus);
    }
}
