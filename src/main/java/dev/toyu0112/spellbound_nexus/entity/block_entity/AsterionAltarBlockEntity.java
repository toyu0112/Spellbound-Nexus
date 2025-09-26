package dev.toyu0112.spellbound_nexus.entity.block_entity;

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
        tag.putBoolean("RitualActive", ritualActive);
        tag.putInt("RitualTicks", ritualTicks);
        if (!spinningItem.isEmpty()) {
            tag.put("SpinningItem", spinningItem.save(new CompoundTag()));
        }
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        ritualActive = tag.getBoolean("RitualActive");
        ritualTicks = tag.getInt("RitualTicks");
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

    public static void clientTick(Level level, BlockPos pos, BlockState state, AsterionAltarBlockEntity altar) {
        if (!altar.ritualActive) return;

        altar.ritualTicks++;

        if (altar.ritualTicks % 5 == 0) {
            spawnRitualParticles(level, pos, altar.ritualTicks);
        }
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, AsterionAltarBlockEntity altar) {
        if (!altar.ritualActive) return;

        if (altar.getSpinningItem().isEmpty()) {
            altar.stopRitual();
            return;
        }

        altar.ritualTicks++;

        if (altar.ritualTicks % 5 == 0) {
            spawnRitualParticles(level, pos, altar.ritualTicks);
        }

        if (altar.ritualTicks >= 600) {
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

        if (ticks == 1) {
            level.playSound(null, pos, SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.BLOCKS, 1.0f, 0.8f);
        }

        if (ticks == 200) {
            level.playSound(null, pos, SoundEvents.BEACON_POWER_SELECT, SoundSource.BLOCKS, 1.0f, 1.2f);
        }

        if (ticks == 400) {
            level.playSound(null, pos, SoundEvents.BEACON_ACTIVATE, SoundSource.BLOCKS, 1.0f, 1.0f);
        }

        if (ticks == 500) {
            level.playSound(null, pos, SoundEvents.BEACON_AMBIENT, SoundSource.BLOCKS, 1.0f, 1.0f);
            level.playSound(null, pos, SoundEvents.ENDER_DRAGON_GROWL, SoundSource.BLOCKS, 1.0f, 0.6f);
        }


        if (ticks % 40 == 0 && ticks < 400) {
            level.playSound(null, pos, SoundEvents.AMBIENT_CAVE.get(), SoundSource.BLOCKS, 0.5f, 1.0f);
        }

        if (ticks >= 500 && ticks < 600 && ticks % 20 == 0) {
            level.playSound(null, pos, SoundEvents.BEACON_AMBIENT, SoundSource.BLOCKS, 0.8f, 1.0f);
        }

        int count = 20;
        double radius = 7.5;

        int trailCount = Math.min(ticks / 2, count); // tickに応じて描く数を増やす
        if (!isMagicCirclePhase) {
            // Phase 1: 円描画（軌跡）
            for (int i = 0; i < trailCount; i++) {
                double angle = (2 * Math.PI / count) * i;
                double x = centerX + radius * Math.cos(angle);
                double z = centerZ + radius * Math.sin(angle);
                double y = centerY;

                level.addParticle(ParticleTypes.WITCH, x, y, z, 0, 0.01, 0);
            }

            // Phase 2: 弧描画（途中も残す）
            if (ticks >= 200) {
                int arcSteps = 10;
                double peakY = pos.getY() + 3.0;   // 弧の最大高さ
                double endY = pos.getY() + 2.0;    // 集束点

                for (int i = 0; i < count; i++) {
                    double angle = (2 * Math.PI / count) * i;
                    double edgeX = centerX + radius * Math.cos(angle);
                    double edgeZ = centerZ + radius * Math.sin(angle);
                    double edgeY = centerY;

                    for (int step = 0; step <= arcSteps; step++) {
                        double arcProgress = step / (double) arcSteps;

                        double arcX = edgeX + (centerX - edgeX) * arcProgress;
                        double arcZ = edgeZ + (centerZ - edgeZ) * arcProgress;

                        // 高さは sin による弧 + 終点への収束
                        double arcY = edgeY
                                + (peakY - edgeY) * Math.sin(arcProgress * Math.PI) * (1 - arcProgress)
                                + (endY - edgeY) * arcProgress;

                        level.addParticle(ParticleTypes.SOUL_FIRE_FLAME, arcX, arcY, arcZ, 0, 0.01, 0);
                    }
                }
            }
        }

        // Phase 3: 中心上空から40ブロック上にビーム（瞬間的に伸びる）
        if (ticks >= 400 && ticks < 500) {
            double dx = 0.05; // 横方向の傾き
            double dz = 0.03; // 奥方向の傾き

            for (int h = 0; h < 25; h++) {
                double beamX = centerX + dx * h;
                double beamY = centerY + 2.0 + h;
                double beamZ = centerZ + dz * h;

                level.addParticle(ParticleTypes.END_ROD, beamX, beamY, beamZ, dx * 0.2, 0.2, dz * 0.2);
            }
        }

        if (isMagicCirclePhase && ticks == 500) {
            // 円と弧の残骸が散るように爆ぜる
            for (int i = 0; i < 100; i++) {
                double dx = (level.random.nextDouble() - 0.5) * 1.5;
                double dy = (level.random.nextDouble() - 0.5) * 1.5;
                double dz = (level.random.nextDouble() - 0.5) * 1.5;

                level.addParticle(ParticleTypes.ASH, centerX, centerY + 1.0, centerZ, dx, dy, dz);
            }
        }

        if (isMagicCirclePhase) {
            {
                double ringY = pos.getY() + 27.0;
                double outerRadius = 20.0;
                double innerRadius = 17.0;
                int circlePoints = 100;

                // 外円
                for (int i = 0; i < circlePoints; i++) {
                    double angle = (2 * Math.PI / circlePoints) * i;
                    double x = centerX + outerRadius * Math.cos(angle);
                    double z = centerZ + outerRadius * Math.sin(angle);
                    level.addParticle(ParticleTypes.GLOW, x, ringY, z, 0, 0.01, 0);
                }

                // 内円
                for (int i = 0; i < circlePoints; i++) {
                    double angle = (2 * Math.PI / circlePoints) * i;
                    double x = centerX + innerRadius * Math.cos(angle);
                    double z = centerZ + innerRadius * Math.sin(angle);
                    level.addParticle(ParticleTypes.GLOW, x, ringY, z, 0, 0.01, 0);
                }

                // 星形（五芒星）
                double starRadius = innerRadius;
                int starPoints = 5;
                double[] starX = new double[starPoints];
                double[] starZ = new double[starPoints];

                for (int i = 0; i < starPoints; i++) {
                    double angle = (2 * Math.PI / starPoints) * i - Math.PI / 2;
                    starX[i] = centerX + starRadius * Math.cos(angle);
                    starZ[i] = centerZ + starRadius * Math.sin(angle);
                }

                // 星の線分（2つ飛ばしで結ぶ）
                for (int i = 0; i < starPoints; i++) {
                    int next = (i + 2) % starPoints;
                    for (int step = 0; step <= 20; step++) {
                        double t = step / 20.0;
                        double x = starX[i] + (starX[next] - starX[i]) * t;
                        double z = starZ[i] + (starZ[next] - starZ[i]) * t;
                        level.addParticle(ParticleTypes.SOUL_FIRE_FLAME, x, ringY, z, 0, 0.01, 0);
                    }
                }
                // 放射状の線（外円と内円を結ぶ）
                int spokeCount = 30;
                for (int i = 0; i < spokeCount; i++) {
                    double angle = (2 * Math.PI / spokeCount) * i;
                    double x1 = centerX + innerRadius * Math.cos(angle);
                    double z1 = centerZ + innerRadius * Math.sin(angle);
                    double x2 = centerX + outerRadius * Math.cos(angle);
                    double z2 = centerZ + outerRadius * Math.sin(angle);

                    for (int step = 0; step <= 10; step++) {
                        double t = step / 10.0;
                        double x = x1 + (x2 - x1) * t;
                        double z = z1 + (z2 - z1) * t;
                        level.addParticle(ParticleTypes.GLOW, x, ringY, z, 0, 0.01, 0);
                    }
                }
            }
        }

        // Finish: 儀式終了時に全パーティクルが爆ぜて散る
        if (ticks == 600) {
            level.playSound(null, pos, SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 1.0f, 1.0f);
            level.playSound(null, pos, SoundEvents.ENDER_DRAGON_GROWL, SoundSource.BLOCKS, 1.0f, 0.6f);
            level.playSound(null, pos, SoundEvents.BEACON_DEACTIVATE, SoundSource.BLOCKS, 0.8f, 1.2f);
            level.playSound(null, pos, SoundEvents.CONDUIT_ACTIVATE, SoundSource.BLOCKS, 0.6f, 1.0f);
            for (int i = 0; i < 200; i++) {
                double dx = (level.random.nextDouble() - 0.5) * 1.5;
                double dy = (level.random.nextDouble() - 0.5) * 1.5;
                double dz = (level.random.nextDouble() - 0.5) * 1.5;

                level.addParticle(ParticleTypes.FLAME, centerX, centerY + 2.0, centerZ, dx, dy, dz);
            }
        }
    }

    private static void summonBoss(Level level, BlockPos pos) {
        Zombie zombie = new Zombie(EntityType.ZOMBIE, level);
        zombie.moveTo(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, 0, 0);
        level.addFreshEntity(zombie);
    }

    private boolean ritualActive = false;
    private int ritualTicks = 0;

    public void startRitual() {
        ritualActive = true;
        ritualTicks = 0;
    }

    public void stopRitual() {
        ritualActive = false;
        ritualTicks = 0;
    }
}
