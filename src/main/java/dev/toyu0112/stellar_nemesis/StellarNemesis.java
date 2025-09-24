package dev.toyu0112.stellar_nemesis;

import dev.toyu0112.stellar_nemesis.item.SNItems;
import dev.toyu0112.stellar_nemesis.item.SNCreativeModeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(StellarNemesis.MOD_ID)
public class StellarNemesis {
    public static final String MOD_ID = "stellar_nemesis";

    public StellarNemesis(FMLJavaModLoadingContext context) {
        SNCreativeModeTabs.register(context.getModEventBus());
        SNItems.register(context.getModEventBus());
    }
}
