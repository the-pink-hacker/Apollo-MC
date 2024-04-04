package com.thepinkhacker.apollo.resource.featuretoggle;

import com.thepinkhacker.apollo.Apollo;
import net.minecraft.resource.featuretoggle.FeatureFlag;
import net.minecraft.resource.featuretoggle.FeatureManager;
import net.minecraft.util.Identifier;

public abstract class ApolloFeatureFlags {
    public static final Identifier PRESSURIZATION_ID = Apollo.getIdentifier("pressurization");
    public static FeatureFlag PRESSURIZATION;

    public static void register(FeatureManager.Builder builder) {
        PRESSURIZATION = builder.addFlag(PRESSURIZATION_ID);
    }
}
