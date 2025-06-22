package com.epicwolf.sra;

import com.epicwolf.sra.config.ModConfigs;
import com.epicwolf.sra.effect.ModEffects;
import com.epicwolf.sra.enchantment.ModEnchantments;
import com.epicwolf.sra.potion.ModPotions;
import net.fabricmc.api.ModInitializer;

public class Sra implements ModInitializer {

    public static final String MOD_ID = "sra";

//    public static Logger LOGGER;

    @Override
    public void onInitialize() {
        ModConfigs.registerConfigs();
//        if (ModConfigs.ENABLE_NEW_ENCHANTMENTS) {
            ModEnchantments.bootstrap();
//        }
        if (ModConfigs.ENABLE_NEW_POTIONS) {
            ModEffects.initialize();
            ModPotions.initialize();
        }
    }
}
