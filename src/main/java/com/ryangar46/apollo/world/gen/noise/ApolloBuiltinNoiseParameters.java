package com.ryangar46.apollo.world.gen.noise;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;

public class ApolloBuiltinNoiseParameters {
    public static void bootstrap(Registerable<DoublePerlinNoiseSampler.NoiseParameters> registerable) {
        register(registerable, ApolloNoiseParametersKeys.MARIA, -7, 1.0d, 1.0d);
    }

    private static void register(Registerable<DoublePerlinNoiseSampler.NoiseParameters> registerable, RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> key, int firstOctave, double firstAmplitude, double... amplitudes) {
        registerable.register(key, new DoublePerlinNoiseSampler.NoiseParameters(firstOctave, firstAmplitude, amplitudes));
    }
}
