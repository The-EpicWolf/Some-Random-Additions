package com.epicwolf.sra.client.render;

import com.epicwolf.sra.client.SraClient;
import com.epicwolf.sra.client.render.model.TechnoPigEntityModel;
import com.epicwolf.sra.client.render.state.TechnoPigEntityRenderState;
import com.google.common.collect.Maps;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.BabyModelPair;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.equipment.EquipmentModel;
import net.minecraft.client.render.entity.feature.SaddleFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.texture.MissingSprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.passive.PigVariant;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.EnumMap;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class TechnoPigEntityRenderer extends MobEntityRenderer<PigEntity, TechnoPigEntityRenderState, TechnoPigEntityModel> {

    private static final Identifier TECHNOBLADE_TEXTURE = Identifier.ofVanilla("textures/entity/pig/technoblade.png");
    private final EnumMap modelPairs;

    public TechnoPigEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new TechnoPigEntityModel(context.getPart(SraClient.TECHNOBLADE_PIG)), 0.7F);
        this.modelPairs = createModelPairs(context);
        this.addFeature(new SaddleFeatureRenderer<>(this, context.getEquipmentRenderer(), EquipmentModel.LayerType.PIG_SADDLE, (pigEntityRenderState) -> pigEntityRenderState.saddleStack, new TechnoPigEntityModel(context.getPart(EntityModelLayers.PIG_SADDLE)), new TechnoPigEntityModel(context.getPart(EntityModelLayers.PIG_BABY_SADDLE))));
    }

    private static EnumMap createModelPairs(EntityRendererFactory.Context context) {
        return Maps.newEnumMap(Map.of(PigVariant.Model.NORMAL, new BabyModelPair(new TechnoPigEntityModel(context.getPart(SraClient.TECHNOBLADE_PIG)), new TechnoPigEntityModel(context.getPart(SraClient.TECHNOBLADE_PIG_BABY))), PigVariant.Model.COLD, new BabyModelPair(new TechnoPigEntityModel(context.getPart(SraClient.TECHNOBLADE_COLD_PIG)), new TechnoPigEntityModel(context.getPart(SraClient.TECHNOBLADE_COLD_PIG_BABY)))));
    }

    public void render(TechnoPigEntityRenderState pigEntityRenderState, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        if (pigEntityRenderState.variant != null) {
            this.model = (TechnoPigEntityModel) ((BabyModelPair)this.modelPairs.get(pigEntityRenderState.variant.modelAndTexture().model())).get(pigEntityRenderState.baby);
            super.render(pigEntityRenderState, matrixStack, vertexConsumerProvider, i);
        }
    }

    @Override
    public TechnoPigEntityRenderState createRenderState() {return new TechnoPigEntityRenderState();}

    @Override
    public Identifier getTexture(TechnoPigEntityRenderState pigEntityRenderState) {
        if (pigEntityRenderState.isTechnoblade) {
            return TECHNOBLADE_TEXTURE;
        }
        return pigEntityRenderState.variant == null ? MissingSprite.getMissingSpriteId() : pigEntityRenderState.variant.modelAndTexture().asset().texturePath();
    }


    @Override
    public void updateRenderState(PigEntity pigEntity, TechnoPigEntityRenderState pigEntityRenderState, float f) {
        super.updateRenderState(pigEntity, pigEntityRenderState, f);
        pigEntityRenderState.saddleStack = pigEntity.getEquippedStack(EquipmentSlot.SADDLE).copy();
        pigEntityRenderState.variant = (PigVariant)pigEntity.getVariant().value();
        pigEntityRenderState.isTechnoblade = "Technoblade".equals(Formatting.strip(pigEntity.getName().getString()));
    }
}
