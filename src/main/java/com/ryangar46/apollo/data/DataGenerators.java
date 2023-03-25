package com.ryangar46.apollo.data;

import com.ryangar46.apollo.data.client.LanguageProvider;
import com.ryangar46.apollo.data.client.ModelProvider;
import com.ryangar46.apollo.data.server.BlockTagProvider;
import com.ryangar46.apollo.data.server.WorldgenProvider;
import com.ryangar46.apollo.data.server.loottable.BlockLootTableProvider;
import com.ryangar46.apollo.data.server.tag.*;
import com.ryangar46.apollo.world.dimension.ApolloDimensionTypes;
import com.ryangar46.apollo.world.gen.noise.ApolloBuiltinNoiseParameters;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;

public class DataGenerators implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(BlockLootTableProvider::new);
        pack.addProvider(BlockTagProvider::new);
        pack.addProvider(DimensionTypeTagProvider::new);
        pack.addProvider(EntityTypeTagProvider::new);
        pack.addProvider(FluidTagProvider::new);
        pack.addProvider(ItemTagProvider::new);
        pack.addProvider(LanguageProvider::new);
        pack.addProvider(ModelProvider::new);
        pack.addProvider(WorldgenProvider::new);
    }

    @Override
    public void buildRegistry(RegistryBuilder registryBuilder) {
        registryBuilder.addRegistry(RegistryKeys.DIMENSION_TYPE, ApolloDimensionTypes::bootstrapType);
        registryBuilder.addRegistry(RegistryKeys.NOISE_PARAMETERS, ApolloBuiltinNoiseParameters::bootstrap);
    }
}
