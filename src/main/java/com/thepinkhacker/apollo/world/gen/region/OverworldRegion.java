package com.thepinkhacker.apollo.world.gen.region;

import com.mojang.datafixers.util.Pair;
import com.thepinkhacker.apollo.Apollo;
import com.thepinkhacker.apollo.world.biome.ApolloBiomeKeys;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import terrablender.api.Region;
import terrablender.api.RegionType;

import java.util.function.Consumer;

public class OverworldRegion extends Region {
    private static final Identifier NAME = Apollo.getIdentifier("overworld");

    public OverworldRegion(int weight) {
        super(NAME, RegionType.OVERWORLD, weight);
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> mapper) {
        addBiomeSimilar(mapper, BiomeKeys.DESERT, ApolloBiomeKeys.OIL_DESERT);
    }
}
