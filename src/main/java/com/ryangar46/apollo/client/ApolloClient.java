package com.ryangar46.apollo.client;

import com.ryangar46.apollo.client.world.DimensionEffectsManager;
import com.ryangar46.apollo.entity.EntityManager;
import com.ryangar46.apollo.gui.ScreenManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class ApolloClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityManager.registerClient();
        ScreenManager.registerClient();
        DimensionEffectsManager.register();
    }
}
