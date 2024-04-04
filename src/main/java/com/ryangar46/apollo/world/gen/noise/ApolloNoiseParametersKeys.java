package com.ryangar46.apollo.world.gen.noise;

import com.ryangar46.apollo.Apollo;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;

public class ApolloNoiseParametersKeys {
    public static final RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> MARIA = registry("maria");

    private static RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> registry(String id) {
        return RegistryKey.of(Registry.NOISE_KEY, new Identifier(Apollo.MOD_ID, id));
    }
}
