package com.epicwolf.sra.enchantment;

import com.epicwolf.sra.config.ModConfigs;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.minecraft.component.EnchantmentEffectComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.effect.EnchantmentEffectTarget;
import net.minecraft.enchantment.effect.value.AddEnchantmentEffect;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.EnchantmentTags;
import net.minecraft.registry.tag.ItemTags;

import java.util.concurrent.CompletableFuture;

public class EnchantmentGenerator extends FabricDynamicRegistryProvider {
    public EnchantmentGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
        System.out.println("REGISTERING ENCHANTS");
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries, Entries entries) {
//        if (ModConfigs.ENABLE_NEW_ENCHANTMENTS) {
            register(entries, ModEnchantments.FROST_BITE, Enchantment.builder(
                            Enchantment.definition(
                                    registries.getOrThrow(RegistryKeys.ITEM).getOrThrow(ItemTags.SWORD_ENCHANTABLE),
                                    2,
                                    2,
                                    Enchantment.leveledCost(10, 20),
                                    Enchantment.leveledCost(60, 20),
                                    4,
                                    AttributeModifierSlot.HAND)).exclusiveSet(registries.getOrThrow(RegistryKeys.ENCHANTMENT).getOrThrow(ModEnchantments.ICE_EXCLUSIVE_SET))
                    .addEffect(
                            EnchantmentEffectComponentTypes.POST_ATTACK,
                            EnchantmentEffectTarget.ATTACKER,
                            EnchantmentEffectTarget.VICTIM,
                            new FrostBiteEnchantmentEffect())
            );
            register(entries, ModEnchantments.FROZEN, Enchantment.builder(
                            Enchantment.definition(
                                    registries.getOrThrow(RegistryKeys.ITEM).getOrThrow(ItemTags.BOW_ENCHANTABLE),
                                    2,
                                    1,
                                    Enchantment.constantCost(20),
                                    Enchantment.constantCost(50),
                                    4,
                                    AttributeModifierSlot.HAND)).exclusiveSet(registries.getOrThrow(RegistryKeys.ENCHANTMENT).getOrThrow(ModEnchantments.ICE_EXCLUSIVE_SET))
                    .addEffect(
                            EnchantmentEffectComponentTypes.POST_ATTACK,
                            EnchantmentEffectTarget.ATTACKER,
                            EnchantmentEffectTarget.VICTIM,
                            new FrostBiteEnchantmentEffect())
            );
//        }
//        if (ModConfigs.ENABLE_MACE_REBALANCE) {
            register(entries, Enchantments.BREACH, Enchantment.builder(
                            Enchantment.definition(
                                    registries.getOrThrow(RegistryKeys.ITEM).getOrThrow(ItemTags.MACE_ENCHANTABLE),
                                    2,
                                    4,
                                    Enchantment.leveledCost(15, 9),
                                    Enchantment.leveledCost(65, 9),
                                    4,
                                    new AttributeModifierSlot[]{AttributeModifierSlot.MAINHAND})).exclusiveSet(registries.getOrThrow(RegistryKeys.ENCHANTMENT).getOrThrow(EnchantmentTags.DAMAGE_EXCLUSIVE_SET))
                    .addEffect(
                            EnchantmentEffectComponentTypes.ARMOR_EFFECTIVENESS,
                            new AddEnchantmentEffect(EnchantmentLevelBasedValue.linear(-0.09F))));
//        }
    }

    private void register(Entries entries, RegistryKey<Enchantment> key, Enchantment.Builder builder, ResourceCondition... resourceConditions) {
        entries.add(key, builder.build(key.getValue()), resourceConditions);
    }

    @Override
    public String getName() {
        return "Enchantments";
    }
}
