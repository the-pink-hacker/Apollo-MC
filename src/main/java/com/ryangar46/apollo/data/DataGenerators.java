package com.ryangar46.apollo.data;

import com.ryangar46.apollo.data.client.LanguageProvider;
import com.ryangar46.apollo.data.client.ModelProvider;
import com.ryangar46.apollo.data.server.ApolloRecipeProvider;
import com.ryangar46.apollo.data.server.WorldgenProvider;
import com.ryangar46.apollo.data.server.loottable.BlockLootTableProvider;
import com.ryangar46.apollo.data.server.tag.*;
import com.ryangar46.apollo.world.biome.ApolloBuiltinBiomes;
import com.ryangar46.apollo.world.dimension.ApolloDimensionTypes;
import com.ryangar46.apollo.world.feature.ApolloOreConfiguredFeatures;
import com.ryangar46.apollo.world.feature.ApolloOrePlacedFeatures;
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
        pack.addProvider(ApolloRecipeProvider::new);
        pack.addProvider(WorldgenProvider::new);
    }

    @Override
    public void buildRegistry(RegistryBuilder registryBuilder) {
        registryBuilder.addRegistry(RegistryKeys.BIOME, ApolloBuiltinBiomes::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, ApolloOreConfiguredFeatures::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, ApolloOrePlacedFeatures::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.DIMENSION_TYPE, ApolloDimensionTypes::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.NOISE_PARAMETERS, ApolloBuiltinNoiseParameters::bootstrap);
    }
}
