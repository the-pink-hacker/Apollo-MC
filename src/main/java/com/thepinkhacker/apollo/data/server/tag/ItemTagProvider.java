package com.thepinkhacker.apollo.data.server.tag;

import com.thepinkhacker.apollo.item.ApolloItems;
import com.thepinkhacker.apollo.registry.tag.ApolloItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public ItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        this.getOrCreateTagBuilder(ApolloItemTags.AIRTIGHT_ARMOR)
                .add(ApolloItems.SPACE_SUIT_BOOTS)
                .add(ApolloItems.SPACE_SUIT_CHESTPLATE)
                .add(ApolloItems.SPACE_SUIT_HELMET)
                .add(ApolloItems.SPACE_SUIT_LEGGINGS);
        this.getOrCreateTagBuilder(ApolloItemTags.LAUNCHPADS)
                .add(ApolloItems.LAUNCHPAD);
        this.getOrCreateTagBuilder(ApolloItemTags.MOON_ARMOR)
                .add(ApolloItems.SPACE_SUIT_BOOTS)
                .add(ApolloItems.SPACE_SUIT_CHESTPLATE)
                .add(ApolloItems.SPACE_SUIT_HELMET)
                .add(ApolloItems.SPACE_SUIT_LEGGINGS);
    }
}
