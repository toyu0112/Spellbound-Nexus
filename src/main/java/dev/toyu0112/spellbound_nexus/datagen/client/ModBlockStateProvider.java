package dev.toyu0112.spellbound_nexus.datagen.client;

import dev.toyu0112.spellbound_nexus.SpellboundNexus;
import dev.toyu0112.spellbound_nexus.init.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, SpellboundNexus.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.ASTERION_ALTAR);
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), new ModelFile.UncheckedModelFile(modLoc("block/" + blockRegistryObject.getId().getPath())));
    }
}
