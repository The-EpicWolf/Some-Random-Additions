package com.epicwolf.sra;

import com.epicwolf.sra.config.ModConfigs;
import com.epicwolf.sra.effect.ModEffects;
import com.epicwolf.sra.enchantment.ModEnchantments;
import com.epicwolf.sra.potion.ModPotions;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;

public class Sra implements ModInitializer, PreLaunchEntrypoint {

    public static final String MOD_ID = "sra";

//    public static Logger LOGGER;

    @Override
    public void onInitialize() {
//        if (ModConfigs.ENABLE_NEW_ENCHANTMENTS) {
            ModEnchantments.bootstrap();
//        }
        if (ModConfigs.ENABLE_NEW_POTIONS) {
            ModEffects.initialize();
            ModPotions.initialize();
        }
    }

    @Override
    public void onPreLaunch() {
        ModConfigs.registerConfigs();
    }
}
