package dev.toyu0112.stellar_nemesis.item;

import dev.toyu0112.stellar_nemesis.StellarNemesis;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SNItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, StellarNemesis.MOD_ID);

    public static final RegistryObject<Item> COMET_FRAGMENT = ITEMS.register("comet_fragment",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
