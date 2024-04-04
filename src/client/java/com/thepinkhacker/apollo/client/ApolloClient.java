package com.thepinkhacker.apollo.client;

import com.thepinkhacker.apollo.client.fluid.ApolloFluidClientRegistry;
import com.thepinkhacker.apollo.client.gui.screen.ApolloClientScreenRegistry;
import com.thepinkhacker.apollo.client.network.ApolloPacketClientRegistry;
import com.thepinkhacker.apollo.client.render.ApolloRenderLayers;
import com.thepinkhacker.apollo.client.render.entity.ApolloEntityRenderers;
import com.thepinkhacker.apollo.client.render.entity.model.ApolloEntityModelLayers;
import com.thepinkhacker.apollo.client.world.DimensionEffectsManager;
import com.thepinkhacker.apollo.entity.ApolloEntityTypes;
import net.fabricmc.api.ClientModInitializer;

public class ApolloClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ApolloEntityTypes.registerClient();
        DimensionEffectsManager.register();
        ApolloFluidClientRegistry.register();
        ApolloRenderLayers.register();
        ApolloEntityModelLayers.register();
        ApolloPacketClientRegistry.register();
        ApolloEntityRenderers.register();
        ApolloClientScreenRegistry.register();
    }
}
