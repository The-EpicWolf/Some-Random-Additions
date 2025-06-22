package com.epicwolf.sra.tag;


import com.epicwolf.sra.enchantment.ModEnchantments;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModTagProvider extends FabricTagProvider.FabricValueLookupTagProvider<Enchantment> {
    public ModTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, RegistryKeys.ENCHANTMENT, registriesFuture, (enchantment) -> Enchantments.FIRE_ASPECT);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        this.builder(ModEnchantments.ICE_EXCLUSIVE_SET)
                .add(Enchantments.FIRE_ASPECT)
                .add(Enchantments.FLAME);
    }
}
