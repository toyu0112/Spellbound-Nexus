package dev.toyu0112.spellbound_nexus.datagen.client.lang;

import dev.toyu0112.spellbound_nexus.SpellboundNexus;
import dev.toyu0112.spellbound_nexus.init.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

import java.util.Locale;

public class JaJpLanguageProvider extends LanguageProvider {
    public JaJpLanguageProvider(PackOutput output) {
        super(output, SpellboundNexus.MOD_ID, Locale.JAPAN.toString().toLowerCase());
    }

    @Override
    protected void addTranslations() {
        addItem(ModItems.COMET_FRAGMENT, "彗星のフラグメント");
    }
}
