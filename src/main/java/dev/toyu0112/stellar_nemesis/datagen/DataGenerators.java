package dev.toyu0112.stellar_nemesis.datagen;

import dev.toyu0112.stellar_nemesis.StellarNemesis;
import dev.toyu0112.stellar_nemesis.datagen.client.SNItemModelProvider;
import dev.toyu0112.stellar_nemesis.datagen.client.lang.EnUsLanguageProvider;
import dev.toyu0112.stellar_nemesis.datagen.client.lang.JaJpLanguageProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = StellarNemesis.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        generator.addProvider(event.includeClient(), new SNItemModelProvider(packOutput, existingFileHelper));

        generator.addProvider(event.includeClient(), new EnUsLanguageProvider(packOutput));
        generator.addProvider(event.includeClient(), new JaJpLanguageProvider(packOutput));
    }
}
