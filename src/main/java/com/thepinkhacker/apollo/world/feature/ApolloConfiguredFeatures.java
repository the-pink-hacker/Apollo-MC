package com.thepinkhacker.apollo.world.feature;

import com.thepinkhacker.apollo.Apollo;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class ApolloConfiguredFeatures {
    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> registerable) {
        ApolloOreConfiguredFeatures.bootstrap(registerable);
        ApolloMiscConfiguredFeatures.bootstrap(registerable);
    }

    public static RegistryKey<ConfiguredFeature<?, ?>> of(String id) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Apollo.getIdentifier(id));
    }
}
