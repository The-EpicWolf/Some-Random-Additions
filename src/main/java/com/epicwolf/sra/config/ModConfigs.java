package com.epicwolf.sra.config;

import com.epicwolf.sra.Sra;
import com.mojang.datafixers.util.Pair;

public class ModConfigs {
    public static SimpleConfig CONFIG;
    private static ModConfigProvider configs;

    public static boolean ENABLE_MACE_REBALANCE;

    public static boolean ENABLE_NEW_ENCHANTMENTS;

    public static boolean ENABLE_ENTITY_RENAMING;

    public static boolean ENABLE_NEW_POTIONS;

    public static boolean ENABLE_WITHER_BUFF;

    public static int ANVIL_MAX_LEVEL_COST;

    public static void registerConfigs() {
        configs = new ModConfigProvider();
        createConfigs();

        CONFIG = SimpleConfig.of(Sra.MOD_ID).provider(configs).request();

        assignConfigs();
    }

    private static void createConfigs() {
        configs.addKeyValuePair(new Pair<>("EnableMaceRebalance", true), "boolean (does`nt affect the breach enchantment)");
        configs.addKeyValuePair(new Pair<>("EnableNewEnchantments", true), "boolean (does`nt work)");
        configs.addKeyValuePair(new Pair<>("EnableEntityRenaming", true), "boolean (client side)");
        configs.addKeyValuePair(new Pair<>("EnableNewPotions", true), "boolean");
        configs.addKeyValuePair(new Pair<>("EnableWitherBuff", true), "boolean");
        configs.addKeyValuePair(new Pair<>("AnvilMaxLevelCost", 80), "int (minecraft default is 40)");
    }

    public static void assignConfigs() {
        ENABLE_MACE_REBALANCE = CONFIG.getOrDefault("EnableMaceRebalance", true);
        ENABLE_NEW_ENCHANTMENTS = CONFIG.getOrDefault("EnableNewEnchantments", true);
        ENABLE_ENTITY_RENAMING = CONFIG.getOrDefault("EnableEntityRenaming", true);
        ENABLE_NEW_POTIONS = CONFIG.getOrDefault("EnableNewPotions", true);
        ENABLE_WITHER_BUFF = CONFIG.getOrDefault("EnableNewPotions", true);
        ANVIL_MAX_LEVEL_COST = CONFIG.getOrDefault("AnvilMaxLevelCost", 40);

        System.out.println("All " + configs.getConfigsList().size() + " have been set properly");
    }
}