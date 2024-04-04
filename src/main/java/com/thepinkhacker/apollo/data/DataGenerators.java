package com.thepinkhacker.apollo.data;

import com.thepinkhacker.apollo.data.client.LanguageProvider;
import com.thepinkhacker.apollo.data.client.ModelProvider;
import com.thepinkhacker.apollo.data.server.ApolloRecipeProvider;
import com.thepinkhacker.apollo.data.server.WorldgenProvider;
import com.thepinkhacker.apollo.data.server.loottable.BlockLootTableProvider;
import com.thepinkhacker.apollo.data.server.tag.BlockTagProvider;
import com.thepinkhacker.apollo.data.server.tag.EntityTypeTagProvider;
import com.thepinkhacker.apollo.data.server.tag.FluidTagProvider;
import com.thepinkhacker.apollo.data.server.tag.ItemTagProvider;
import com.thepinkhacker.apollo.world.biome.ApolloBuiltinBiomes;
import com.thepinkhacker.apollo.world.dimension.ApolloDimensionTypes;
import com.thepinkhacker.apollo.world.feature.ApolloConfiguredFeatures;
import com.thepinkhacker.apollo.world.feature.ApolloPlacedFeatures;
import com.thepinkhacker.apollo.world.gen.noise.ApolloBuiltinNoiseParameters;
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
        registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, ApolloConfiguredFeatures::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, ApolloPlacedFeatures::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.DIMENSION_TYPE, ApolloDimensionTypes::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.NOISE_PARAMETERS, ApolloBuiltinNoiseParameters::bootstrap);
    }
}
