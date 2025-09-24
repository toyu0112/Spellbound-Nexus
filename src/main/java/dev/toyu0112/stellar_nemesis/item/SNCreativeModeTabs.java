package dev.toyu0112.stellar_nemesis.item;

import dev.toyu0112.stellar_nemesis.StellarNemesis;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class SNCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, StellarNemesis.MOD_ID);

    public static final RegistryObject<CreativeModeTab> ITEM_TAB = CREATIVE_MODE_TABS.register("sn_item_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(SNItems.COMET_FRAGMENT.get()))
                    .title(Component.translatable("creativetab.stellar_nemesis_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(SNItems.COMET_FRAGMENT.get());
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
