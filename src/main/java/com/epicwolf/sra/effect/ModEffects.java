package com.epicwolf.sra.effect;

import com.epicwolf.sra.Sra;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class ModEffects {
    public static final RegistryEntry<StatusEffect> FREEZE =
            Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(Sra.MOD_ID, "freeze"), new FreezeEffect());

    public static void initialize() {

    }
}
