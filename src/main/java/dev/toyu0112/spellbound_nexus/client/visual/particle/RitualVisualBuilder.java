package dev.toyu0112.spellbound_nexus.client.visual.particle;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class RitualVisualBuilder {
    private final List<RitualVisualPhase> phases = new ArrayList<>();
    private Consumer<Level> startAction = l -> {};
    private Consumer<Level> finishAction = l -> {};

    public RitualVisualBuilder onStart(BiConsumer<Level, BlockPos> action) {
        this.startAction = level -> action.accept(level, null);
        return this;
    }

    public RitualVisualBuilder onFinish(BiConsumer<Level, BlockPos> action) {
        this.finishAction = level -> action.accept(level, null);
        return this;
    }

    public RitualVisualBuilder addPhase(int start, int end, RitualVisualPhase phase) {
        phases.add(new WrappedPhase(start, end, phase));
        return this;
    }

    public RitualVisualTemplate build() {
        return new RitualVisualTemplate() {
            @Override
            public void onStart(Level level, BlockPos pos) {
                startAction.accept(level);
            }

            @Override
            public void play(Level level, BlockPos pos, int ticks) {
                for (RitualVisualPhase phase : phases)
                    if (phase.isActive(ticks)) phase.play(level, pos, ticks);
            }

            @Override
            public void onFinish(Level level, BlockPos pos) {
                finishAction.accept(level);
            }
        };
    }

    private static class WrappedPhase implements RitualVisualPhase {
        private final int start, end;
        private final RitualVisualPhase delegate;

        WrappedPhase(int start, int end, RitualVisualPhase delegate) {
            this.start = start;
            this.end = end;
            this.delegate = delegate;
        }

        @Override
        public boolean isActive(int ticks) {
            return ticks >= start && ticks < end;
        }

        @Override
        public void play(Level level, BlockPos pos, int ticks) {
            delegate.play(level, pos, ticks);
        }
    }
}
