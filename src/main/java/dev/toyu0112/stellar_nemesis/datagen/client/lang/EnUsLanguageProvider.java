package dev.toyu0112.stellar_nemesis.datagen.client.lang;

import dev.toyu0112.stellar_nemesis.StellarNemesis;
import dev.toyu0112.stellar_nemesis.item.SNItems;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

import java.util.Locale;

public class EnUsLanguageProvider extends LanguageProvider {
    public EnUsLanguageProvider(PackOutput output) {
        super(output, StellarNemesis.MOD_ID, Locale.US.toString().toLowerCase());
    }

    @Override
    protected void addTranslations() {
        addItem(SNItems.COMET_FRAGMENT, "Comet Fragment");

        add("creativetab.stellar_nemesis_tab", "Stellar Nemesis");
    }
}
