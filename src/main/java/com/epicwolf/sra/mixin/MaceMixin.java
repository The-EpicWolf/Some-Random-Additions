package com.epicwolf.sra.mixin;

import com.epicwolf.sra.config.ModConfigs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.MaceItem;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.minecraft.item.MaceItem.shouldDealAdditionalDamage;

@Mixin(MaceItem.class)
public class MaceMixin {

    @Inject(method = "getBonusAttackDamage",at = @At("HEAD"), cancellable = true)
    public void getBonusAttackDamage(Entity target, float baseAttackDamage, DamageSource damageSource, CallbackInfoReturnable<Float> cir) {
        if (ModConfigs.ENABLE_MACE_REBALANCE) {
            Entity d = damageSource.getSource();
            if (d instanceof LivingEntity livingEntity) {
                if (!shouldDealAdditionalDamage(livingEntity)) {
                    cir.setReturnValue(0.0F);
                } else {
                    double f = livingEntity.fallDistance;
                    double g;
                    if (f <= (double) 8.0F) {
                        g = (double) 1.5F * f;
                    } else {
                        g = (double) 12.0F + f - (double) 8.0F;
                    }

                    World var14 = livingEntity.getWorld();
                    if (var14 instanceof ServerWorld) {
                        ServerWorld serverWorld = (ServerWorld) var14;
                        cir.setReturnValue((float) (g + (double) EnchantmentHelper.getSmashDamagePerFallenBlock(serverWorld, livingEntity.getWeaponStack(), target, damageSource, 0.0F) * f));
                    } else {
                        cir.setReturnValue((float) g);
                    }
                }
            } else {
                cir.setReturnValue(0.0F);
            }
            cir.cancel();
        }
    }
}
