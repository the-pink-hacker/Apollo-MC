package com.ryangar46.apollo;

import com.ryangar46.apollo.entity.EntityManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class ApolloClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityManager.registerClient();
    }
}
