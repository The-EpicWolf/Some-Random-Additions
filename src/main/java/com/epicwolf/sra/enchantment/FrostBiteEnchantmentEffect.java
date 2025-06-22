package com.epicwolf.sra.enchantment;

import com.epicwolf.sra.config.ModConfigs;
import com.epicwolf.sra.effect.ModEffects;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

public record FrostBiteEnchantmentEffect() implements EnchantmentEntityEffect {
    public static final MapCodec<FrostBiteEnchantmentEffect> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(EnchantmentLevelBasedValue.CODEC.fieldOf("duration").forGetter((frostbite) -> frostbite.duration)).apply(instance, FrostBiteEnchantmentEffect::new));

    public static EnchantmentLevelBasedValue duration;

    public FrostBiteEnchantmentEffect() {
    }

    public FrostBiteEnchantmentEffect(EnchantmentLevelBasedValue duration) {
        this();
    }

    public void apply(ServerWorld world, int level, EnchantmentEffectContext context, Entity user, Vec3d pos) {
        if (user instanceof LivingEntity entity) {
            if (ModConfigs.ENABLE_NEW_POTIONS) {
                entity.addStatusEffect(new StatusEffectInstance(ModEffects.FREEZE, 150 + (50 * (level - 1)), level - 1));
            }
            else {
                entity.setFrozenTicks(Math.min(entity.getFrozenTicks()+150, entity.getMinFreezeDamageTicks()+360));
            }
        }
    }

    public MapCodec<FrostBiteEnchantmentEffect> getCodec() {
        return CODEC;
    }
}
