package com.epicwolf.sra.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.server.world.ServerWorld;

public class FreezeEffect extends StatusEffect {
    protected FreezeEffect() {
        super(StatusEffectCategory.HARMFUL, 0x96c8dc);
    }

    // Called every tick to check if the effect can be applied or not
    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean applyUpdateEffect(ServerWorld world, LivingEntity entity, int amplifier) {
        entity.setFrozenTicks(Math.min(entity.getFrozenTicks()+(4+(2*amplifier)), entity.getMinFreezeDamageTicks()+60));
        return super.applyUpdateEffect(world, entity, amplifier);
    }
}
