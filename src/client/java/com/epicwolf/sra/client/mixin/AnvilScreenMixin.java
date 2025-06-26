package com.epicwolf.sra.client.mixin;

import com.epicwolf.sra.config.ModConfigs;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.AnvilScreen;
import net.minecraft.client.gui.screen.ingame.ForgingScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(AnvilScreen.class)
public class AnvilScreenMixin extends ForgingScreen<AnvilScreenHandler> {
    @Shadow
    private static final Text TOO_EXPENSIVE_TEXT = Text.translatable("container.repair.expensive");
    @Final
    @Shadow
    private PlayerEntity player;

    public AnvilScreenMixin(AnvilScreenHandler handler, PlayerInventory playerInventory, Text title, Identifier texture) {
        super(handler, playerInventory, title, texture);
    }

    @Inject(method = "drawForeground", at = @At("HEAD"), cancellable = true)
    protected void drawForeground(DrawContext context, int mouseX, int mouseY, CallbackInfo ci) {
        super.drawForeground(context, mouseX, mouseY);
        int i = this.handler.getLevelCost();
        if (i > 0) {
            Text text;
            int j = -8323296;
            if (i >= ModConfigs.ANVIL_MAX_LEVEL_COST && !client.player.isInCreativeMode()) {
                text = TOO_EXPENSIVE_TEXT;
                j = -40864;
            } else if (!this.handler.getSlot(2).hasStack()) {
                text = null;
            } else {
                text = Text.translatable("container.repair.cost", i);
                if (!this.handler.getSlot(2).canTakeItems(this.player)) {
                    j = -40864;
                }
            }
            if (text != null) {
                int k = this.backgroundWidth - 8 - this.textRenderer.getWidth(text) - 2;
                context.fill(k - 2, 67, this.backgroundWidth - 8, 79, 0x4F000000);
                context.drawTextWithShadow(this.textRenderer, text, k, 69, j);
            }
        }
        ci.cancel();
    }

    @Shadow
    protected void drawInvalidRecipeArrow(DrawContext context, int x, int y) {

    }
}
