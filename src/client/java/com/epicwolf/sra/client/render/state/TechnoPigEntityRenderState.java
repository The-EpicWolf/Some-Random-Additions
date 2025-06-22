package com.epicwolf.sra.client.render.state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.state.PigEntityRenderState;
import net.minecraft.item.ItemStack;

@Environment(EnvType.CLIENT)
public class TechnoPigEntityRenderState extends PigEntityRenderState {

    public boolean isTechnoblade;

    public TechnoPigEntityRenderState() {
        this.saddleStack = ItemStack.EMPTY;
    }
}
