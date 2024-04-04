package com.ryangar46.apollo.world.biome;

import net.minecraft.util.Identifier;
import terrablender.api.Region;
import terrablender.api.RegionType;

public class ApolloOverworldRegion extends Region {
    public ApolloOverworldRegion(Identifier name, int weight) {
        super(name, RegionType.OVERWORLD, weight);
    }

    /*@Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> mapper) {
        addBiomeSimilar(mapper, BiomeKeys.DESERT, BiomeManager.OIL_DESERT);
    }*/
}
