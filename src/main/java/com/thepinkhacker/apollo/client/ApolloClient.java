package com.thepinkhacker.apollo.client;

import com.thepinkhacker.apollo.client.render.ApolloRenderLayers;
import com.thepinkhacker.apollo.client.world.DimensionEffectsManager;
import com.thepinkhacker.apollo.entity.ApolloEntityTypes;
import com.thepinkhacker.apollo.fluid.ApolloFluids;
import com.thepinkhacker.apollo.network.ApolloPackets;
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
        ApolloPackets.registerClient();
    }
}
