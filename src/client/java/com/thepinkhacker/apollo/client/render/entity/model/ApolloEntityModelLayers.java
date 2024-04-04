package com.thepinkhacker.apollo.client.render.entity.model;

import com.thepinkhacker.apollo.Apollo;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;

public abstract class ApolloEntityModelLayers {
    public static final EntityModelLayer METEORITE = createMain("meteorite");
    public static final EntityModelLayer SHUTTLE = createMain("shuttle");

    public static void register() {
        EntityModelLayerRegistry.registerModelLayer(METEORITE, MeteoriteEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(SHUTTLE, ShuttleEntityModel::getTexturedModelData);
    }

    private static EntityModelLayer createMain(String path) {
        return create(path, "main");
    }

    private static EntityModelLayer create(String path, String name) {
        return new EntityModelLayer(Apollo.getIdentifier(path), name);
    }
}
