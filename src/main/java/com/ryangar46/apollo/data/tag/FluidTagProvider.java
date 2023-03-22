package com.ryangar46.apollo.data.tag;

import com.ryangar46.apollo.fluid.ApolloFluids;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.FluidTags;

import java.util.concurrent.CompletableFuture;

public class FluidTagProvider extends FabricTagProvider.FluidTagProvider {
    public FluidTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        this.getOrCreateTagBuilder(FluidTags.WATER)
                .add(ApolloFluids.FLOWING_FUEL)
                .add(ApolloFluids.STILL_FUEL)
                .add(ApolloFluids.FLOWING_OIL)
                .add(ApolloFluids.STILL_OIL);
    }
}
