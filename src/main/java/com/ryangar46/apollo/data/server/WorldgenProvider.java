package com.ryangar46.apollo.data.server;

import com.ryangar46.apollo.Apollo;
import com.ryangar46.apollo.world.dimension.ApolloDimensionTypes;
import com.ryangar46.apollo.world.gen.noise.ApolloNoiseParametersKeys;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class WorldgenProvider extends FabricDynamicRegistryProvider {
    public WorldgenProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries, Entries entries) {
        addEntry(registries, entries, ApolloDimensionTypes.MOON, RegistryKeys.DIMENSION_TYPE);
        addEntry(registries, entries, ApolloNoiseParametersKeys.MARIA, RegistryKeys.NOISE_PARAMETERS);
    }

    private static <T> void addEntry(RegistryWrapper.WrapperLookup registries, Entries entries, RegistryKey<T> key, RegistryKey<Registry<T>> type) {
        entries.add(key, registries.getWrapperOrThrow(type).getOrThrow(key).value());
    }

    @Override
    public String getName() {
        return Apollo.MOD_ID;
    }
}
