package com.thepinkhacker.apollo.entry;

import com.thepinkhacker.apollo.world.region.OverworldRegion;
import terrablender.api.Regions;
import terrablender.api.TerraBlenderApi;

public class ApolloTerraBlenderEntry implements TerraBlenderApi {
    @Override
    public void onTerraBlenderInitialized() {
        Regions.register(new OverworldRegion(10));
    }
}
