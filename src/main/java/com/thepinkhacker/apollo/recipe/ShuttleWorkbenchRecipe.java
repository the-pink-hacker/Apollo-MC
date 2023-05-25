package com.thepinkhacker.apollo.recipe;

import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.RecipeType;

public interface ShuttleWorkbenchRecipe extends CraftingRecipe {
    @Override
    default RecipeType<?> getType() {
        return ApolloRecipeTypes.SHUTTLE_WORKBENCH_RECIPE_TYPE;
    }
}
