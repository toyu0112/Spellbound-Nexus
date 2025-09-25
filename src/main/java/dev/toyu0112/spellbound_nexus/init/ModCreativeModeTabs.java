package dev.toyu0112.spellbound_nexus.init;

import dev.toyu0112.spellbound_nexus.SpellboundNexus;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, SpellboundNexus.MOD_ID);
    public static void register(IEventBus eventBus) { CREATIVE_MODE_TABS.register(eventBus); }

    public static final RegistryObject<CreativeModeTab> ITEM_TAB =
            CREATIVE_MODE_TABS.register("spellbound_nexus_item_tab", () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.COMET_FRAGMENT.get()))
                    .title(Component.translatable("creativetab.spellbound_nexus_item_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.COMET_FRAGMENT.get());

                        pOutput.accept(ModBlocks.ASTERION_ALTAR.get());
                    })
                    .build());
}
