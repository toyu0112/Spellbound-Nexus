package dev.toyu0112.spellbound_nexus.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class LowGravityEffect extends MobEffect {
    public LowGravityEffect() {
        super(MobEffectCategory.NEUTRAL, 16262179);
        this.addAttributeModifier(Attributes.JUMP_STRENGTH, "53f5317d-218c-4b84-94f3-e61d243f554e", +0.5D, AttributeModifier.Operation.ADDITION);
    }

    public void applyEffectTick(LivingEntity entity, int amplifier) {

    }

    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration > 0;
    }
}
