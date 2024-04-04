package com.thepinkhacker.apollo.entry;

import com.thepinkhacker.apollo.Apollo;
import com.thepinkhacker.apollo.world.gen.ApolloOverworldSurfaceRules;
import com.thepinkhacker.apollo.world.gen.region.OverworldRegion;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;
import terrablender.api.TerraBlenderApi;

public class ApolloTerraBlenderEntry implements TerraBlenderApi {
    @Override
    public void onTerraBlenderInitialized() {
        Regions.register(new OverworldRegion(5));
        SurfaceRuleManager.addSurfaceRules(
                SurfaceRuleManager.RuleCategory.OVERWORLD,
                Apollo.MOD_ID,
                ApolloOverworldSurfaceRules.createRules()
        );
    }
}
