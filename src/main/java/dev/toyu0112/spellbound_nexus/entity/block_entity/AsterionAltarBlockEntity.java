package dev.toyu0112.spellbound_nexus.entity.block_entity;

import dev.toyu0112.spellbound_nexus.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class AsterionAltarBlockEntity extends BlockEntity {
    private ItemStack spinningItem = ItemStack.EMPTY;

    public AsterionAltarBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.ASTERION_ALTAR.get(), pPos, pBlockState);
    }

    public void setSpinningItem(ItemStack stack) {
        this.spinningItem = stack;
        setChanged();
        if (level != null && !level.isClientSide) {
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
        }
    }

    public ItemStack getSpinningItem() {
        return spinningItem;
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (!spinningItem.isEmpty()) {
            tag.put("SpinningItem", spinningItem.save(new CompoundTag()));
        }
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("SpinningItem")) {
            spinningItem = ItemStack.of(tag.getCompound("SpinningItem"));
        } else {
            spinningItem = ItemStack.EMPTY;
        }
    }

    @Override
    public CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        this.load(tag);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection connection, ClientboundBlockEntityDataPacket packet) {
        this.load(packet.getTag());
    }
}
