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
        ItemStack held = player.getItemInHand(hand);
        BlockEntity be = level.getBlockEntity(pos);
        if (!(be instanceof AsterionAltarBlockEntity altar)) return InteractionResult.PASS;

        ItemStack altarItem = altar.getSpinningItem();

        if (!altarItem.isEmpty()) {
            if (held.isEmpty()) {
                if (!level.isClientSide) {
                    player.setItemInHand(hand, altarItem);
                }
                altar.setSpinningItem(ItemStack.EMPTY);
                return InteractionResult.sidedSuccess(level.isClientSide);
            }

            if (ItemStack.isSameItemSameTags(held, altarItem)) {
                return InteractionResult.sidedSuccess(level.isClientSide);
            }

            if (!level.isClientSide) {
                ItemStack copy = held.copyWithCount(1);
                held.shrink(1);
                altar.setSpinningItem(copy);
                player.addItem(altarItem);

                if (altar.getSpinningItem().is(ModItems.COMET_FRAGMENT.get())) {
                    altar.startRitual();
                }
            } else {
                altar.setSpinningItem(held.copyWithCount(1));
            }

            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        if (!held.isEmpty()) {
            if (!level.isClientSide) {
                ItemStack copy = held.copyWithCount(1);
                altar.setSpinningItem(copy);
                if (copy.is(ModItems.COMET_FRAGMENT.get())) {
                    altar.startRitual();
                }
            } else {
                altar.setSpinningItem(held.copyWithCount(1));
                if (held.is(ModItems.COMET_FRAGMENT.get())) {
                    altar.startRitual();
                }
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
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

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.box(0.0, 0.0, 0.0, 1.0, 0.75, 1.0); // 中央に小さめの台座
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return getShape(state, level, pos, context);
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        List<ItemStack> drops = super.getDrops(state, builder);

        BlockEntity be = builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        if (be instanceof AsterionAltarBlockEntity altar) {
            ItemStack offering = altar.getSpinningItem();
            if (!offering.isEmpty()) {
                drops.add(offering);
            }
        }

        return drops;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (type != ModBlockEntities.ASTERION_ALTAR.get()) return null;

        return level.isClientSide
                ? (lvl, pos, st, be) -> {
            if (be instanceof AsterionAltarBlockEntity altar) {
                AsterionAltarBlockEntity.clientTick(lvl, pos, st, altar);
            }
        }
                : (lvl, pos, st, be) -> {
            if (be instanceof AsterionAltarBlockEntity altar) {
                AsterionAltarBlockEntity.serverTick(lvl, pos, st, altar);
            }
        };
    }
}
