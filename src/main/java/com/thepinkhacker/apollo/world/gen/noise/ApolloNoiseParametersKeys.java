package com.thepinkhacker.apollo.world.gen.noise;

import com.thepinkhacker.apollo.Apollo;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;

public class ApolloNoiseParametersKeys {
    public static final RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> MARIA = registry("maria");

    private static RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> registry(String id) {
        return RegistryKey.of(RegistryKeys.NOISE_PARAMETERS, new Identifier(Apollo.MOD_ID, id));
    }
}
