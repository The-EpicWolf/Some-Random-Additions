package com.epicwolf.sra.client;

import com.epicwolf.sra.Sra;
import com.epicwolf.sra.client.render.TechnoPigEntityRenderer;
import com.epicwolf.sra.client.render.model.TechnoPigEntityModel;
import com.epicwolf.sra.config.ModConfigs;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;

public class SraClient implements ClientModInitializer {

    public static final EntityModelLayer TECHNOBLADE_PIG = new EntityModelLayer(Identifier.of(Sra.MOD_ID, "pig"), "main");

    public static final EntityModelLayer TECHNOBLADE_PIG_BABY = new EntityModelLayer(Identifier.of(Sra.MOD_ID, "pig_baby"), "main");

    public static final EntityModelLayer TECHNOBLADE_COLD_PIG = new EntityModelLayer(Identifier.of(Sra.MOD_ID, "cold_pig"), "main");

    public static final EntityModelLayer TECHNOBLADE_COLD_PIG_BABY = new EntityModelLayer(Identifier.of(Sra.MOD_ID, "cold_pig_baby"), "main");

    @Override
    public void onInitializeClient() {

        if (ModConfigs.ENABLE_ENTITY_RENAMING) {
            EntityModelLayerRegistry.registerModelLayer(TECHNOBLADE_PIG, TechnoPigEntityModel::getTexturedModelData);
            EntityModelLayerRegistry.registerModelLayer(TECHNOBLADE_PIG_BABY, TechnoPigEntityModel::getBabyTexturedModelData);
            EntityModelLayerRegistry.registerModelLayer(TECHNOBLADE_COLD_PIG, TechnoPigEntityModel::getColdTexturedModelData);
            EntityModelLayerRegistry.registerModelLayer(TECHNOBLADE_COLD_PIG_BABY, TechnoPigEntityModel::getBabyColdTexturedModelData);
            EntityRendererRegistry.register(EntityType.PIG, TechnoPigEntityRenderer::new);
        }
    }
}
