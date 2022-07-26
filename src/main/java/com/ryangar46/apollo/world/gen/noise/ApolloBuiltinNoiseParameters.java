package com.ryangar46.apollo.world.gen.noise;

import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;

public class ApolloBuiltinNoiseParameters {
    public static void registry() {
        register(ApolloNoiseParametersKeys.MARIA, -7, 1.0d, 1.0d);
    }

    private static RegistryEntry<DoublePerlinNoiseSampler.NoiseParameters> register(RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> registryKey, int firstOctave, double amplitude, double... amplitudes) {
        return BuiltinRegistries.add(BuiltinRegistries.NOISE_PARAMETERS, registryKey, new DoublePerlinNoiseSampler.NoiseParameters(firstOctave, amplitude, amplitudes));
    }
}
