package dev.toyu0112.stellar_nemesis.datagen.client.lang;

import dev.toyu0112.stellar_nemesis.StellarNemesis;
import dev.toyu0112.stellar_nemesis.item.SNItems;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

import java.util.Locale;

public class JaJpLanguageProvider extends LanguageProvider {
    public JaJpLanguageProvider(PackOutput output) {
        super(output, StellarNemesis.MOD_ID, Locale.JAPAN.toString().toLowerCase());
    }

    @Override
    protected void addTranslations() {
        addItem(SNItems.COMET_FRAGMENT, "彗星のフラグメント");
    }
}
