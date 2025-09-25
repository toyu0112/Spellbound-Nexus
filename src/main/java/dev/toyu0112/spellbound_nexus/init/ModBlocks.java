package dev.toyu0112.spellbound_nexus.init;

import dev.toyu0112.spellbound_nexus.SpellboundNexus;
import dev.toyu0112.spellbound_nexus.block.AsterionAltarBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, SpellboundNexus.MOD_ID);
    public static void register(IEventBus eventBus) { BLOCKS.register(eventBus); }
    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) { return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties())); }
    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) { RegistryObject<T> toReturn = BLOCKS.register(name, block);registerBlockItem(name, toReturn);return toReturn; }

    public static final RegistryObject<Block> ASTERION_ALTAR =
            registerBlock("asterion_altar", () -> new AsterionAltarBlock(BlockBehaviour.Properties.of()));
}
