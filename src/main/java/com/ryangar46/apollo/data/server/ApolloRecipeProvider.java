package com.ryangar46.apollo.data.server;

import com.ryangar46.apollo.item.ApolloItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;

import java.util.List;
import java.util.function.Consumer;

public class ApolloRecipeProvider extends FabricRecipeProvider {
    public ApolloRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
        // TODO: Add Shuttle Workbench recipes
        /* === Crafting Table === */
        // Reinforced Iron Ingot
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ApolloItems.REINFORCED_IRON_INGOT)
                .input(ApolloItems.METEORITE_SCRAP)
                .input(Items.IRON_INGOT, 4)
                .group("reinforced_iron_ingot")
                .criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(Items.IRON_INGOT))
                .criterion(hasItem(ApolloItems.METEORITE_SCRAP), conditionsFromItem(ApolloItems.METEORITE_SCRAP))
                .offerTo(exporter);

        // Reinforced Iron Ingot <-> Reinforced Iron Block
        RecipeProvider.offerReversibleCompactingRecipes(
                exporter,
                RecipeCategory.MISC,
                ApolloItems.REINFORCED_IRON_INGOT,
                RecipeCategory.BUILDING_BLOCKS,
                ApolloItems.REINFORCED_IRON_BLOCK,
                "reinforced_iron_block_from_reinforced_iron_ingot",
                "reinforced_iron_block",
                "reinforced_iron_ingot_from_reinforced_iron_block",
                "reinforced_iron_ingot"
        );

        /* === Smelting === */
        // Lunar Iron Ore -> Iron Ingot
        offerSmeltingBlasting(
                exporter,
                ApolloItems.LUNAR_IRON_ORE,
                RecipeCategory.MISC,
                Items.IRON_INGOT,
                0.7f,
                "iron_ingot"
        );

        // Meteorite -> Meteorite Scrap
        offerSmeltingBlasting(
                exporter,
                ApolloItems.METEORITE,
                RecipeCategory.MISC,
                ApolloItems.METEORITE_SCRAP,
                0.7f,
                "meteorite_scrap"
        );

        // Lunar Cobblestone -> Lunar Stone
        offerSmeltingBlasting(
                exporter,
                ApolloItems.LUNAR_COBBLESTONE,
                RecipeCategory.BUILDING_BLOCKS,
                ApolloItems.LUNAR_STONE,
                0.7f,
                "lunar_stone"
        );
    }

    private static void offerSmeltingBlasting(Consumer<RecipeJsonProvider> exporter, List<ItemConvertible> ingredients, RecipeCategory category, ItemConvertible result, float experience, int cookingTime, String group) {
        RecipeProvider.offerSmelting(exporter, ingredients, category, result, experience, cookingTime, group);
        RecipeProvider.offerBlasting(exporter, ingredients, category, result, experience, cookingTime / 2, group);
    }

    private static void offerSmeltingBlasting(Consumer<RecipeJsonProvider> exporter, List<ItemConvertible> ingredients, RecipeCategory category, ItemConvertible result, float experience, String group) {
        RecipeProvider.offerSmelting(exporter, ingredients, category, result, experience, 200, group);
        RecipeProvider.offerBlasting(exporter, ingredients, category, result, experience, 100, group);
    }

    private static void offerSmeltingBlasting(Consumer<RecipeJsonProvider> exporter, ItemConvertible ingredient, RecipeCategory category, ItemConvertible result, float experience, int cookingTime, String group) {
        offerSmeltingBlasting(exporter, List.of(ingredient), category, result, experience, cookingTime, group);
    }

    private static void offerSmeltingBlasting(Consumer<RecipeJsonProvider> exporter, ItemConvertible ingredient, RecipeCategory category, ItemConvertible result, float experience, String group) {
        offerSmeltingBlasting(exporter, List.of(ingredient), category, result, experience, group);
    }
}
