package com.ryangar46.apollo.data.tag;

import com.ryangar46.apollo.item.ApolloItems;
import com.ryangar46.apollo.registry.tag.ApolloItemTags;
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
        this.getOrCreateTagBuilder(ApolloItemTags.AIRTIGHT_BOOTS)
                .add(ApolloItems.SPACE_SUIT_BOOTS);
        this.getOrCreateTagBuilder(ApolloItemTags.AIRTIGHT_CHESTPLATES)
                .add(ApolloItems.SPACE_SUIT_CHESTPLATE);
        this.getOrCreateTagBuilder(ApolloItemTags.AIRTIGHT_HELMETS)
                .add(ApolloItems.SPACE_SUIT_HELMET);
        this.getOrCreateTagBuilder(ApolloItemTags.AIRTIGHT_LEGGINGS)
                .add(ApolloItems.SPACE_SUIT_LEGGINGS);
        this.getOrCreateTagBuilder(ApolloItemTags.LAUNCHPADS)
                .add(ApolloItems.LAUNCHPAD);
        this.getOrCreateTagBuilder(ApolloItemTags.MOON_ARMOR)
                .add(ApolloItems.SPACE_SUIT_BOOTS)
                .add(ApolloItems.SPACE_SUIT_CHESTPLATE)
                .add(ApolloItems.SPACE_SUIT_HELMET)
                .add(ApolloItems.SPACE_SUIT_LEGGINGS);
        this.getOrCreateTagBuilder(ApolloItemTags.NEGATIVE_GRAVITY_EQUIPABLES)
                .add(ApolloItems.NEGATIVE_GRAVITY_BOOTS);
        this.getOrCreateTagBuilder(ApolloItemTags.POSITIVE_GRAVITY_EQUIPABLES)
                .add(ApolloItems.POSITIVE_GRAVITY_BOOTS);
    }
}
