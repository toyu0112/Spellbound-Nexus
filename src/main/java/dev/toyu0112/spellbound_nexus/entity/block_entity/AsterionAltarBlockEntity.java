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
import net.minecraft.world.phys.Vec3;

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
    private static double starRotation;
    private static double spokesRotation;

    private static void spawnRitualParticles(Level level, BlockPos pos, int ticks) {
        double centerX = pos.getX() + 0.5;
        double centerY = pos.getY();
        double centerZ = pos.getZ() + 0.5;
        double slopeX = 0.5;
        double slopeZ = 0;
        int beamHeight = 25;
        boolean isMagicCirclePhase = ticks >= 500 && ticks < 600;

        playRitualSounds(level, pos, ticks);

        if (!isMagicCirclePhase) {
            double radius = 10.0;
            starRotation += 0.3;
            spokesRotation += 0.5;

            // Phase 1
            int trailCount = Math.min(ticks / 2, 100);
            RitualEffects.drawCircle(level, centerX, centerY, centerZ, radius, trailCount, ParticleTypes.GLOW, new Vec3(0, 1, 0),ticks * 0.05);
            RitualEffects.drawCircle(level, centerX, centerY, centerZ, radius - 2, trailCount + 100, ParticleTypes.GLOW, new Vec3(0, 1, 0),ticks * 0.05);
            RitualEffects.drawStar(level, centerX, centerY, centerZ, radius - 2, 20, ParticleTypes.SCULK_SOUL, new Vec3(0, 1, 0), starRotation);
            RitualEffects.drawSpokes(level, centerX, centerY, centerZ, radius, radius - 2, 20, 10, ParticleTypes.GLOW, new Vec3(0, 1, 0), spokesRotation);

            // Phase 2
            if (ticks >= 200) {
                for (int i = 0; i < 20; i++) {
                    double angle = (2 * Math.PI / 20) * i;
                    double ex = centerX + radius * Math.cos(angle);
                    double ez = centerZ + radius * Math.sin(angle);
                    RitualEffects.drawArc(level, ex, centerY, ez, centerX, centerY + 2.0, centerZ, centerY + 2.5, 10, ParticleTypes.SOUL_FIRE_FLAME);
                }
            }
        }

        // Phase 3
        if (ticks >= 400 && ticks < 600) {
            RitualEffects.drawBeam(level, centerX, centerY + 2.0, centerZ, beamHeight, slopeX, slopeZ, ParticleTypes.END_ROD);
        }

        // Phase 4
        if (isMagicCirclePhase) {
            double ringY = centerY + beamHeight + 2;
            double ringX = centerX + slopeX * beamHeight;
            double ringZ = centerZ + slopeZ * beamHeight;

            starRotation += 0.3;
            spokesRotation += 0.5;

            Vec3 laserDirection = new Vec3(slopeX, 1.0, slopeZ).normalize();

            RitualEffects.drawCircle(level, ringX, ringY, ringZ, 20.0, 100, ParticleTypes.GLOW, laserDirection,ticks * 0.05);
            RitualEffects.drawCircle(level, ringX, ringY, ringZ, 17.0, 100, ParticleTypes.GLOW, laserDirection, ticks * 0.05);
            RitualEffects.drawStar(level, ringX, ringY, ringZ, 17.0, 20, ParticleTypes.SOUL_FIRE_FLAME, laserDirection, starRotation);
            RitualEffects.drawSpokes(level, ringX, ringY, ringZ, 17.0, 20, 30, 10, ParticleTypes.GLOW, laserDirection, spokesRotation);
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
