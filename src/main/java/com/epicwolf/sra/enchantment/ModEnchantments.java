package com.epicwolf.sra.enchantment;

import com.epicwolf.sra.Sra;
import com.mojang.serialization.MapCodec;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.registry.*;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;


public class ModEnchantments {

    public static final RegistryKey<Enchantment> FROST_BITE = of("frost_bite");

    public static final RegistryKey<Enchantment> FROZEN = of("frozen");

    public static MapCodec<FrostBiteEnchantmentEffect> FROST_BITE_EFFECT = register("frost_bite", FrostBiteEnchantmentEffect.CODEC);

    public static TagKey<Enchantment> ICE_EXCLUSIVE_SET = TagKey.of(RegistryKeys.ENCHANTMENT, Identifier.of(Sra.MOD_ID,"exclusive_set/ice"));

    private static RegistryKey<Enchantment> of(String path) {
        Identifier id = Identifier.of(Sra.MOD_ID, path);
        return RegistryKey.of(RegistryKeys.ENCHANTMENT, id);
    }

    private static <T extends EnchantmentEntityEffect> MapCodec<T> register(String id, MapCodec<T> codec) {
        return Registry.register(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, Identifier.of(Sra.MOD_ID, id), codec);
    }

    public static void bootstrap() {
//        Sra.LOGGER.info("Registering EnchantmentEffects for" + Sra.MOD_ID);
    }
}

