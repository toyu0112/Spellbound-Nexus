package dev.toyu0112.spellbound_nexus.content.spell;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class SpellTarget {
    private final BlockPos blockPos;
    private final Vec3 position;
    private final List<? extends Entity> entities;
    private final boolean self;

    private SpellTarget(BlockPos blockPos, Vec3 position, List<? extends Entity> entities, boolean self) {
        this.blockPos = blockPos;
        this.position = position;
        this.entities = entities;
        this.self = self;
    }

    public static SpellTarget ofBlock(BlockPos pos) { return new SpellTarget(pos, null, null, false); }
    public static SpellTarget ofPosition(Vec3 pos) { return new SpellTarget(null, pos, null, false); }
    public static SpellTarget ofEntities(List<? extends Entity> entities) { return new SpellTarget(null, null, entities, false); }
    public static SpellTarget self() { return new SpellTarget(null, null, null, true); }

    public boolean hasEntities() { return entities != null && !entities.isEmpty(); }
    public boolean hasBlockOrPosition() { return blockPos != null || position != null; }
    public boolean isSelf() { return  self; }

    public BlockPos getBlockPos() { return blockPos; }
    public Vec3 getPosition() { return position; }
    public List<? extends Entity> getEntities() { return entities; }
}
