package com.epicwolf.sra.client.render.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.*;

import java.util.Set;

@Environment(EnvType.CLIENT)
public class TechnoPigEntityModel extends PigEntityModel {
    public static final ModelTransformer BABY_TRANSFORMER = new BabyModelTransformer(false, 4.0F, 4.0F, Set.of("head"));

    public TechnoPigEntityModel(ModelPart modelPart) {
        super(modelPart);
    }

    public static TexturedModelData getTexturedModelData() {
        Dilation dilation = new Dilation(0);
        return TexturedModelData.of(getModelData(dilation), 64, 64);
    }


    public static TexturedModelData getBabyTexturedModelData() {
        return getTexturedModelData().transform(BABY_TRANSFORMER);
    }

    public static TexturedModelData getColdTexturedModelData() {
        ModelData modelData = getModelData(new Dilation(0));
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().uv(28, 8).cuboid(-5.0f, -10.0f, -7.0f, 10.0f, 16.0f, 8.0f).uv(28, 32).cuboid(-5.0f, -10.0f, -7.0f, 10.0f, 16.0f, 8.0f, new Dilation(0.5f)), ModelTransform.of(0.0f, 11.0f, 2.0f, 1.5707964f, 0.0f, 0.0f));
        return TexturedModelData.of(modelData, 64, 64);
    }

    public static TexturedModelData getBabyColdTexturedModelData() {
        return getColdTexturedModelData().transform(BABY_TRANSFORMER);
    }


    protected static ModelData getModelData(Dilation dilation) {
        ModelData modelData = QuadrupedEntityModel.getModelData(6, true, false, dilation);
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -4.0F, -8.0F, 8.0F, 8.0F, 8.0F, dilation)
                .uv(0, 48).cuboid(-4.0F, -4.0F, -8.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.5F))
                .uv(16, 16).cuboid(-2.0F, 0.0F, -9.0F, 4.0F, 3.0F, 1.0F, dilation), ModelTransform.origin(0.0F, 12.0F, -6.0F));
        return modelData;
    }
}
