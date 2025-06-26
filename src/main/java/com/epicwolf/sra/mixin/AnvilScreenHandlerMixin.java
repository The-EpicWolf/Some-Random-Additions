package com.epicwolf.sra.mixin;

import com.epicwolf.sra.config.ModConfigs;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.block.BlockState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.screen.*;
import net.minecraft.screen.slot.ForgingSlotsManager;
import net.minecraft.text.Text;
import net.minecraft.util.StringHelper;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilScreenHandler.class)
public class AnvilScreenHandlerMixin extends ForgingScreenHandler {

    @Shadow
    private boolean keepSecondSlot;
    @Shadow
    private int repairItemUsage;
    @Nullable
    @Shadow
    private String newItemName;
    @Shadow
    private final Property levelCost = Property.create();

    public AnvilScreenHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context, ForgingSlotsManager forgingSlotsManager) {
        super(type, syncId, playerInventory, context, forgingSlotsManager);
    }

    @Inject(method = "updateResult",at = @At("HEAD"), cancellable = true)
    private void updateResult(CallbackInfo ci) {
        int k;
        ItemStack itemStack = this.input.getStack(0);
        this.keepSecondSlot = false;
        this.levelCost.set(1);
        int i = 0;
        long l = 0L;
        int j = 0;
        if (itemStack.isEmpty() || !EnchantmentHelper.canHaveEnchantments(itemStack)) {
            this.output.setStack(0, ItemStack.EMPTY);
            this.levelCost.set(0);
            return;
        }
        ItemStack itemStack2 = itemStack.copy();
        ItemStack itemStack3 = this.input.getStack(1);
        ItemEnchantmentsComponent.Builder builder = new ItemEnchantmentsComponent.Builder(EnchantmentHelper.getEnchantments(itemStack2));
        l += (long) itemStack.getOrDefault(DataComponentTypes.REPAIR_COST, 0) + (long) itemStack3.getOrDefault(DataComponentTypes.REPAIR_COST, 0);
        this.repairItemUsage = 0;
        if (!itemStack3.isEmpty()) {
            boolean bl = itemStack3.contains(DataComponentTypes.STORED_ENCHANTMENTS);
            if (itemStack2.isDamageable() && itemStack.canRepairWith(itemStack3)) {
                int m;
                k = Math.min(itemStack2.getDamage(), itemStack2.getMaxDamage() / 4);
                if (k <= 0) {
                    this.output.setStack(0, ItemStack.EMPTY);
                    this.levelCost.set(0);
                    return;
                }
                for (m = 0; k > 0 && m < itemStack3.getCount(); ++m) {
                    int n = itemStack2.getDamage() - k;
                    itemStack2.setDamage(n);
                    ++i;
                    k = Math.min(itemStack2.getDamage(), itemStack2.getMaxDamage() / 4);
                }
                this.repairItemUsage = m;
            } else {
                if (!(bl || itemStack2.isOf(itemStack3.getItem()) && itemStack2.isDamageable())) {
                    this.output.setStack(0, ItemStack.EMPTY);
                    this.levelCost.set(0);
                    return;
                }
                if (itemStack2.isDamageable() && !bl) {
                    int k2 = itemStack.getMaxDamage() - itemStack.getDamage();
                    int m = itemStack3.getMaxDamage() - itemStack3.getDamage();
                    int n = m + itemStack2.getMaxDamage() * 12 / 100;
                    int o = k2 + n;
                    int p = itemStack2.getMaxDamage() - o;
                    if (p < 0) {
                        p = 0;
                    }
                    if (p < itemStack2.getDamage()) {
                        itemStack2.setDamage(p);
                        i += 2;
                    }
                }
                ItemEnchantmentsComponent itemEnchantmentsComponent = EnchantmentHelper.getEnchantments(itemStack3);
                boolean bl2 = false;
                boolean bl3 = false;
                for (Object2IntMap.Entry<RegistryEntry<Enchantment>> entry : itemEnchantmentsComponent.getEnchantmentEntries()) {
                    int r;
                    RegistryEntry<Enchantment> registryEntry = entry.getKey();
                    int q = builder.getLevel(registryEntry);
                    r = q == (r = entry.getIntValue()) ? r + 1 : Math.max(r, q);
                    Enchantment enchantment = registryEntry.value();
                    boolean bl4 = enchantment.isAcceptableItem(itemStack);
                    if (this.player.isInCreativeMode() || itemStack.isOf(Items.ENCHANTED_BOOK)) {
                        bl4 = true;
                    }
                    for (RegistryEntry<Enchantment> registryEntry2 : builder.getEnchantments()) {
                        if (registryEntry2.equals(registryEntry) || Enchantment.canBeCombined(registryEntry, registryEntry2)) continue;
                        bl4 = false;
                        ++i;
                    }
                    if (!bl4) {
                        bl3 = true;
                        continue;
                    }
                    bl2 = true;
                    if (r > enchantment.getMaxLevel()) {
                        r = enchantment.getMaxLevel();
                    }
                    builder.set(registryEntry, r);
                    int s = enchantment.getAnvilCost();
                    if (bl) {
                        s = Math.max(1, s / 2);
                    }
                    i += s * r;
                    if (itemStack.getCount() <= 1) continue;
                    i = 40;
                }
                if (bl3 && !bl2) {
                    this.output.setStack(0, ItemStack.EMPTY);
                    this.levelCost.set(0);
                    return;
                }
            }
        }
        if (this.newItemName == null || StringHelper.isBlank(this.newItemName)) {
            if (itemStack.contains(DataComponentTypes.CUSTOM_NAME)) {
                j = 1;
                i += j;
                itemStack2.remove(DataComponentTypes.CUSTOM_NAME);
            }
        } else if (!this.newItemName.equals(itemStack.getName().getString())) {
            j = 1;
            i += j;
            itemStack2.set(DataComponentTypes.CUSTOM_NAME, Text.literal(this.newItemName));
        }
        int t = i <= 0 ? 0 : (int) MathHelper.clamp(l + (long)i, 0L, Integer.MAX_VALUE);
        this.levelCost.set(t);
        if (i <= 0) {
            itemStack2 = ItemStack.EMPTY;
        }
        if (j == i && j > 0) {
            if (this.levelCost.get() >= 40) {
                this.levelCost.set(39);
            }
            this.keepSecondSlot = true;
        }
        if (this.levelCost.get() >= ModConfigs.ANVIL_MAX_LEVEL_COST && !this.player.isInCreativeMode()) {
            itemStack2 = ItemStack.EMPTY;
        }
        if (!itemStack2.isEmpty()) {
            k = itemStack2.getOrDefault(DataComponentTypes.REPAIR_COST, 0);
            if (k < itemStack3.getOrDefault(DataComponentTypes.REPAIR_COST, 0)) {
                k = itemStack3.getOrDefault(DataComponentTypes.REPAIR_COST, 0);
            }
            if (j != i || j == 0) {
                k = AnvilScreenHandler.getNextCost(k);
            }
            itemStack2.set(DataComponentTypes.REPAIR_COST, k);
            EnchantmentHelper.set(itemStack2, builder.build());
        }
        this.output.setStack(0, itemStack2);
        this.sendContentUpdates();
        ci.cancel();
    }

    @Shadow
    protected void onTakeOutput(PlayerEntity player, ItemStack stack) {
    }

    @Shadow
    protected boolean canUse(BlockState state) {
        return false;
    }

    @Shadow
    public void updateResult() {}
}
