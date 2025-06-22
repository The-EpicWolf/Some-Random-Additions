package com.epicwolf.sra.client;

import com.epicwolf.sra.config.ModConfigs;
import com.epicwolf.sra.enchantment.EnchantmentGenerator;
import com.epicwolf.sra.tag.ModTagProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class SraDataGenerator implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(EnchantmentGenerator::new);
//        if (ModConfigs.ENABLE_NEW_ENCHANTMENTS) {
            pack.addProvider(ModTagProvider::new);
//        }
    }
}
