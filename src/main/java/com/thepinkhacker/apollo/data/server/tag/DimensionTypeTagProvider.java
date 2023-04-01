package com.thepinkhacker.apollo.data.server.tag;

import com.thepinkhacker.apollo.registry.tag.ApolloDimensionTypeTags;
import com.thepinkhacker.apollo.world.dimension.ApolloDimensionTypes;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.dimension.DimensionType;

import java.util.concurrent.CompletableFuture;

public class DimensionTypeTagProvider extends FabricTagProvider<DimensionType> {
    public DimensionTypeTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, RegistryKeys.DIMENSION_TYPE, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        this.getOrCreateTagBuilder(ApolloDimensionTypeTags.EARTH_VISIBLE_WORLDS)
                .add(ApolloDimensionTypes.MOON);
        this.getOrCreateTagBuilder(ApolloDimensionTypeTags.METEORITE_SPAWNING_WORLDS)
                .add(ApolloDimensionTypes.MOON);
        this.getOrCreateTagBuilder(ApolloDimensionTypeTags.ATMOSPHERE_NOT_VISIBLE_WORLDS)
                .add(ApolloDimensionTypes.MOON);
    }
}
