package com.ryangar46.apollo.data;

import com.ryangar46.apollo.data.tag.*;
import com.ryangar46.apollo.world.dimension.ApolloDimensionTypes;
import com.ryangar46.apollo.world.gen.noise.ApolloBuiltinNoiseParameters;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.data.report.BlockListProvider;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;

public class DataGenerators implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(BlockTagProvider::new);
        pack.addProvider(DimensionTypeTagProvider::new);
        pack.addProvider(EntityTypeTagProvider::new);
        pack.addProvider(FluidTagProvider::new);
        pack.addProvider(ItemTagProvider::new);
        pack.addProvider(LanguageProvider::new);
        pack.addProvider(WorldgenProvider::new);
    }

    @Override
    public void buildRegistry(RegistryBuilder registryBuilder) {
        registryBuilder.addRegistry(RegistryKeys.DIMENSION_TYPE, ApolloDimensionTypes::bootstrapType);
        registryBuilder.addRegistry(RegistryKeys.NOISE_PARAMETERS, ApolloBuiltinNoiseParameters::bootstrap);
    }
}
