package dev.toyu0112.spellbound_nexus.block;

import dev.toyu0112.spellbound_nexus.entity.block_entity.AsterionAltarBlockEntity;
import dev.toyu0112.spellbound_nexus.init.ModBlockEntities;
import dev.toyu0112.spellbound_nexus.init.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;

public class AsterionAltarBlock extends Block implements EntityBlock {

    public AsterionAltarBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (level.isClientSide) return InteractionResult.SUCCESS;

        BlockEntity be = level.getBlockEntity(pos);
        if (!(be instanceof AsterionAltarBlockEntity altar)) return InteractionResult.PASS;

        ItemStack held = player.getItemInHand(hand);
        ItemStack altarItem = altar.getSpinningItem();

        if (!altarItem.isEmpty() && held.isEmpty()) {
            player.setItemInHand(hand, altarItem);
            altar.setSpinningItem(ItemStack.EMPTY);
            return InteractionResult.SUCCESS;
        }

        if (!altarItem.isEmpty() && ItemStack.isSameItemSameTags(held, altarItem)) {
            return InteractionResult.SUCCESS;
        }

        if (!altarItem.isEmpty()) {
            ItemStack copy = held.copyWithCount(1);
            held.shrink(1);
            altar.setSpinningItem(copy);
            player.addItem(altarItem);
            if (copy.is(ModItems.COMET_FRAGMENT.get())) altar.startRitual();
            return InteractionResult.SUCCESS;
        }

        if (!held.isEmpty()) {
            ItemStack copy = held.copyWithCount(1);
            held.shrink(1);
            altar.setSpinningItem(copy);
            if (copy.is(ModItems.COMET_FRAGMENT.get())) altar.startRitual();
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new AsterionAltarBlockEntity(pPos, pState);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    private static final VoxelShape SHAPE = Shapes.box(0.0, 0.0, 0.0, 1.0, 0.75, 1.0);

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        List<ItemStack> drops = super.getDrops(state, builder);
        BlockEntity be = builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        if (be instanceof AsterionAltarBlockEntity altar && !altar.getSpinningItem().isEmpty()) {
            drops.add(altar.getSpinningItem());
        }
        return drops;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (type != ModBlockEntities.ASTERION_ALTAR.get()) return null;

        return level.isClientSide
                ? createTicker(AsterionAltarBlockEntity::clientTick)
                : createTicker(AsterionAltarBlockEntity::serverTick);
    }

    private <T extends BlockEntity> BlockEntityTicker<T> createTicker(BlockEntityTicker<? super AsterionAltarBlockEntity> ticker) {
        return (lvl, pos, st, be) -> {
            if (be instanceof AsterionAltarBlockEntity altar) {
                ticker.tick(lvl, pos, st, altar);
            }
        };
    }
}
