package dev.toyu0112.spellbound_nexus.entity.block_entity;

import dev.toyu0112.spellbound_nexus.init.ModBlockEntities;
import dev.toyu0112.spellbound_nexus.client.visual.particle.RitualVisualRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class AsterionAltarBlockEntity extends BlockEntity {
    private ItemStack spinningItem = ItemStack.EMPTY;
    private RitualState ritual = RitualState.inactive();
    private ResourceLocation visualId = null;

    public AsterionAltarBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.ASTERION_ALTAR.get(), pPos, pBlockState);
    }

    private void ritualFinished() {
        summonBoss(level, getBlockPos());
    }

    public void setSpinningItem(ItemStack stack) {
        this.spinningItem = stack;
        setChanged();

        if (stack.isEmpty()) stopRitual();
        notifyBlockUpdate();
    }

    public void notifyBlockUpdate() {
        if (level != null && !level.isClientSide) {
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
        }
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, AsterionAltarBlockEntity altar) {
        if (!altar.ritual.active()) return;
        altar.ritual = altar.ritual.ticked();
        if (altar.ritual.ticks() % 5 == 0) {
            altar.spawnRitualParticles();
        }
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, AsterionAltarBlockEntity altar) {
        if (!altar.ritual.active()) return;
        if (altar.spinningItem.isEmpty()) {
            altar.stopRitual();
            return;
        }

        altar.ritual = altar.ritual.ticked();

        if (altar.ritual.shouldSpawnParticles()) altar.spawnRitualParticles();
        if (altar.ritual.finished()) {
            altar.stopRitual();
            altar.setSpinningItem(ItemStack.EMPTY);
            altar.ritualFinished();
        }
    }

    private void spawnRitualParticles() {
        if (level == null || visualId == null) return;
        if (!RitualVisualRegistry.contains(visualId)) return;
        RitualVisualRegistry.play(visualId, level, getBlockPos(), ritual.ticks());
    }

    private static void summonBoss(Level level, BlockPos pos) {
        Zombie zombie = new Zombie(EntityType.ZOMBIE, level);
        zombie.moveTo(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, 0, 0);
        level.addFreshEntity(zombie);
    }

    public record RitualState(boolean active, int ticks) {
        public RitualState ticked() {
            return active ? new RitualState(true, ticks + 1) : this;
        }

        public boolean shouldSpawnParticles() {
            return active && ticks % 5 == 0;
        }

        public boolean finished() {
            return active && ticks >= 600;
        }

        public static RitualState inactive() { return new RitualState(false, 0); }
        public static RitualState started() { return new RitualState(true, 0); }
    }

    public void startRitual(ResourceLocation visualId) {
        this.visualId = visualId;
        this.ritual = RitualState.started();
    }

    public void stopRitual() {
        ritual = RitualState.inactive();
    }

    public ItemStack getSpinningItem() {
        return spinningItem;
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putBoolean("RitualActive", ritual.active());
        tag.putInt("RitualTicks", ritual.ticks());
        if (visualId != null) tag.putString("VisualId", visualId.toString());
        if (!spinningItem.isEmpty()) tag.put("SpinningItem", spinningItem.save(new CompoundTag()));
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        ritual = new RitualState(tag.getBoolean("RitualActive"), tag.getInt("RitualTicks"));
        if (tag.contains("VisualId")) {
            visualId = new ResourceLocation(tag.getString("VisualId"));
        } else {
            visualId = null;
        }
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
