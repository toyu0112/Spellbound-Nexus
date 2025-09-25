package dev.toyu0112.spellbound_nexus.init;

import dev.toyu0112.spellbound_nexus.SpellboundNexus;
import dev.toyu0112.spellbound_nexus.entity.block_entity.AsterionAltarBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, SpellboundNexus.MOD_ID);
    public static void register(IEventBus eventBus) { BLOCK_ENTITIES.register(eventBus); }

    public static final RegistryObject<BlockEntityType<AsterionAltarBlockEntity>> ASTERION_ALTAR =
            BLOCK_ENTITIES.register("asterion_altar", () -> BlockEntityType.Builder.of(AsterionAltarBlockEntity::new, ModBlocks.ASTERION_ALTAR.get()).build(null));
}
