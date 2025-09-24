package dev.toyu0112.spellbound_nexus.datagen.client.lang;

import dev.toyu0112.spellbound_nexus.SpellboundNexus;
import dev.toyu0112.spellbound_nexus.init.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

import java.util.Locale;

public class EnUsLanguageProvider extends LanguageProvider {
    public EnUsLanguageProvider(PackOutput output) {
        super(output, SpellboundNexus.MOD_ID, Locale.US.toString().toLowerCase());
    }

    @Override
    protected void addTranslations() {
        addItem(ModItems.COMET_FRAGMENT, "Comet Fragment");

        add("creativetab.spellbound_nexus_item_tab", "Spellbound Nexus");
    }
}
