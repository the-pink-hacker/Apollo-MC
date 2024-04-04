package com.ryangar46.apollo.data;

import com.ryangar46.apollo.world.dimension.ApolloDimensionTypes;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.dimension.DimensionType;

import java.util.concurrent.CompletableFuture;

public class WorldgenProvider extends FabricDynamicRegistryProvider {
    public WorldgenProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries, Entries entries) {
        final RegistryWrapper.Impl<DimensionType> dimensionTypeRegistry = registries.getWrapperOrThrow(RegistryKeys.DIMENSION_TYPE);
        entries.add(ApolloDimensionTypes.MOON, dimensionTypeRegistry.getOrThrow(ApolloDimensionTypes.MOON).value());
    }

    @Override
    public String getName() {
        return "temp-name-apollo";
    }
}
