package dev.toyu0112.stellar_nemesis.datagen.client;

import dev.toyu0112.stellar_nemesis.StellarNemesis;
import dev.toyu0112.stellar_nemesis.item.SNItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class SNItemModelProvider extends ItemModelProvider {
    public SNItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, StellarNemesis.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(SNItems.COMET_FRAGMENT);
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(StellarNemesis.MOD_ID, "item/" + item.getId().getPath()));
    }
}
