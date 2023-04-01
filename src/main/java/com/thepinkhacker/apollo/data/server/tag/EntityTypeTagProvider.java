package com.thepinkhacker.apollo.data.server.tag;

import com.thepinkhacker.apollo.entity.ApolloEntityTypes;
import com.thepinkhacker.apollo.registry.tag.ApolloEntityTypeTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.EntityTypeTags;

import java.util.concurrent.CompletableFuture;

public class EntityTypeTagProvider extends FabricTagProvider.EntityTypeTagProvider {
    public EntityTypeTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        this.getOrCreateTagBuilder(ApolloEntityTypeTags.SHUTTLES)
                .add(ApolloEntityTypes.SHUTTLE);
        this.getOrCreateTagBuilder(ApolloEntityTypeTags.VACUUM_IMMUNE_CREATURES)
                .forceAddTag(EntityTypeTags.SKELETONS)
                .addTag(ApolloEntityTypeTags.SHUTTLES);
    }
}
