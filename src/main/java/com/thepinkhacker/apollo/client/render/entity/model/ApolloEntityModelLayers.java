package com.thepinkhacker.apollo.client.render.entity.model;

import com.thepinkhacker.apollo.Apollo;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;

@Environment(EnvType.CLIENT)
public abstract class ApolloEntityModelLayers {
    public static final EntityModelLayer METEORITE = createMain("meteorite");

    public static void register() {
        EntityModelLayerRegistry.registerModelLayer(METEORITE, MeteoriteEntityModel::getTexturedModelData);
    }

    private static EntityModelLayer createMain(String path) {
        return create(path, "main");
    }

    private static EntityModelLayer create(String path, String name) {
        return new EntityModelLayer(Apollo.getIdentifier(path), name);
    }
}
