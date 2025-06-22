package com.epicwolf.sra.potion;

import com.epicwolf.sra.Sra;
import com.epicwolf.sra.effect.ModEffects;
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class ModPotions {



    public static Potion FREEZE = Registry.register(Registries.POTION, Identifier.of(Sra.MOD_ID, "freeze"), new Potion("freeze", new StatusEffectInstance(ModEffects.FREEZE, 1800, 0)));

    public static Potion LONG_FREEZE = Registry.register(Registries.POTION, Identifier.of(Sra.MOD_ID, "long_freeze"), new Potion("freeze", new StatusEffectInstance(ModEffects.FREEZE, 3600, 0)));

    public static Potion STRONG_FREEZE = Registry.register(Registries.POTION, Identifier.of(Sra.MOD_ID, "strong_freeze"), new Potion("freeze", new StatusEffectInstance(ModEffects.FREEZE, 600, 1)));

    public static void initialize() {
        FabricBrewingRecipeRegistryBuilder.BUILD.register(builder -> {
            builder.registerPotionRecipe(Potions.AWKWARD, Items.BLUE_ICE, Registries.POTION.getEntry(FREEZE));
            builder.registerPotionRecipe(Registries.POTION.getEntry(FREEZE), Items.REDSTONE, Registries.POTION.getEntry(LONG_FREEZE));
            builder.registerPotionRecipe(Registries.POTION.getEntry(FREEZE), Items.GLOWSTONE_DUST, Registries.POTION.getEntry(STRONG_FREEZE));;
        });
    }
}
