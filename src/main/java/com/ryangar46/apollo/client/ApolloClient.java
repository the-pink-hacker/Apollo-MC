package com.ryangar46.apollo.client;

import com.ryangar46.apollo.client.render.ApolloRenderLayers;
import com.ryangar46.apollo.client.world.DimensionEffectsManager;
import com.ryangar46.apollo.entity.ApolloEntityTypes;
import com.ryangar46.apollo.fluid.ApolloFluids;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class ApolloClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ApolloEntityTypes.registerClient();
        DimensionEffectsManager.registerClient();
        ApolloFluids.registerClient();
        ApolloRenderLayers.register();
    }
}
