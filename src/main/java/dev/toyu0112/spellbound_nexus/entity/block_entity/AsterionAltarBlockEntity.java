package dev.toyu0112.spellbound_nexus.entity.block_entity;

import dev.toyu0112.spellbound_nexus.client.visual.RitualEffects;
import dev.toyu0112.spellbound_nexus.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class AsterionAltarBlockEntity extends BlockEntity {
    private ItemStack spinningItem = ItemStack.EMPTY;
    private RitualState ritual = RitualState.inactive();

    public AsterionAltarBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.ASTERION_ALTAR.get(), pPos, pBlockState);
    }

    public void setSpinningItem(ItemStack stack) {
        this.spinningItem = stack;
        setChanged();

        if (stack.isEmpty()) stopRitual();

        if (level != null && !level.isClientSide) {
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
        }
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, AsterionAltarBlockEntity altar) {
        if (!altar.ritual.active()) return;
        altar.ritual = altar.ritual.ticked();
        if (altar.ritual.ticks() % 5 == 0) {
            spawnRitualParticles(level, pos, altar.ritual.ticks());
        }
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, AsterionAltarBlockEntity altar) {
        if (!altar.ritual.active()) return;

        if (altar.getSpinningItem().isEmpty()) {
            altar.stopRitual();
            return;
        }

        altar.ritual = altar.ritual.ticked();

        if (altar.ritual.ticks() % 5 == 0) {
            spawnRitualParticles(level, pos, altar.ritual.ticks());
        }

        if (altar.ritual.ticks() >= 600) {
            altar.stopRitual();
            altar.setSpinningItem(ItemStack.EMPTY);
            summonBoss(level, pos);
        }
    }

    private static void spawnRitualParticles(Level level, BlockPos pos, int ticks) {
        double centerX = pos.getX() + 0.5;
        double centerY = pos.getY();
        double centerZ = pos.getZ() + 0.5;
        boolean isMagicCirclePhase = ticks >= 500 && ticks < 600;

        playRitualSounds(level, pos, ticks);

        if (!isMagicCirclePhase) {
            int count = 20;
            double radius = 7.5;

            // Phase 1
            int trailCount = Math.min(ticks / 2, count);
            RitualEffects.drawCircle(level, centerX, centerY, centerZ, radius, trailCount, ParticleTypes.WITCH, 0);

            // Phase 2
            if (ticks >= 200) {
                for (int i = 0; i < count; i++) {
                    double angle = (2 * Math.PI / count) * i;
                    double ex = centerX + radius * Math.cos(angle);
                    double ez = centerZ + radius * Math.sin(angle);
                    RitualEffects.drawArc(level, ex, centerY, ez, centerX, centerY + 2.0, centerZ, centerY + 1.5, 10, ParticleTypes.SOUL_FIRE_FLAME);
                }
            }
        }

        // Phase 3
        if (ticks >= 400 && ticks < 500) {
            RitualEffects.drawBeam(level, centerX, centerY + 2.0, centerZ, 25, 0.05, 0.03, ParticleTypes.END_ROD);
        }

        // Phase5
        if (isMagicCirclePhase) {
            double ringY = centerY + 27.0;
            RitualEffects.drawCircle(level, centerX, ringY, centerZ, 20.0, 100, ParticleTypes.GLOW, ticks * 0.05);
            RitualEffects.drawCircle(level, centerX, ringY, centerZ, 17.0, 100, ParticleTypes.GLOW, ticks * 0.05);
            RitualEffects.drawStar(level, centerX, ringY, centerZ, 17.0, 20, ParticleTypes.SOUL_FIRE_FLAME);
            RitualEffects.drawSpokes(level, centerX, ringY, centerZ, 17.0, 20, 30, 10, ParticleTypes.GLOW);
        }

        // Finish
        if (ticks == 600) {
            RitualEffects.particleExplode(level, centerX, centerY + 2.0, centerZ, 200, 1.5, ParticleTypes.FLAME);
        }
    }

    private static void summonBoss(Level level, BlockPos pos) {
        Zombie zombie = new Zombie(EntityType.ZOMBIE, level);
        zombie.moveTo(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, 0, 0);
        level.addFreshEntity(zombie);
    }

    private static void playRitualSounds(Level level, BlockPos pos, int ticks) {
        if (ticks == 1)
            level.playSound(null, pos, SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.BLOCKS, 1.0f, 0.8f);
        else if (ticks == 200)
            level.playSound(null, pos, SoundEvents.BEACON_POWER_SELECT, SoundSource.BLOCKS, 1.0f, 1.2f);
        else if (ticks == 400)
            level.playSound(null, pos, SoundEvents.BEACON_ACTIVATE, SoundSource.BLOCKS, 1.0f, 1.0f);
        else if (ticks == 500) {
            level.playSound(null, pos, SoundEvents.BEACON_AMBIENT, SoundSource.BLOCKS, 1.0f, 1.0f);
            level.playSound(null, pos, SoundEvents.ENDER_DRAGON_GROWL, SoundSource.BLOCKS, 1.0f, 0.6f);
        } else if (ticks == 600) {
            level.playSound(null, pos, SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 1.0f, 1.0f);
            level.playSound(null, pos, SoundEvents.ENDER_DRAGON_GROWL, SoundSource.BLOCKS, 1.0f, 0.6f);
            level.playSound(null, pos, SoundEvents.BEACON_DEACTIVATE, SoundSource.BLOCKS, 0.8f, 1.2f);
            level.playSound(null, pos, SoundEvents.CONDUIT_ACTIVATE, SoundSource.BLOCKS, 0.6f, 1.0f);
        }

        if (ticks % 40 == 0 && ticks < 400)
            level.playSound(null, pos, SoundEvents.AMBIENT_CAVE.get(), SoundSource.BLOCKS, 0.5f, 1.0f);

        if (ticks % 20 == 0 && ticks >= 500 && ticks < 600)
            level.playSound(null, pos, SoundEvents.BEACON_AMBIENT, SoundSource.BLOCKS, 0.8f, 1.0f);
    }

    public record RitualState(boolean active, int ticks) {
        public RitualState ticked() {
            return new RitualState(active, ticks + 1);
        }

        public static RitualState inactive() {
            return new RitualState(false, 0);
        }

        public static RitualState started() {
            return new RitualState(true, 0);
        }
    }

    public void startRitual() {
        ritual = RitualState.started();
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
        if (!spinningItem.isEmpty()) {
            tag.put("SpinningItem", spinningItem.save(new CompoundTag()));
        }
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        ritual = new RitualState(tag.getBoolean("RitualActive"), tag.getInt("RitualTicks"));
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
