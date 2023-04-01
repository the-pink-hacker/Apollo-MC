package com.thepinkhacker.apollo.recipe;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.recipe.ShapelessRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;

public class ShuttleWorkbenchShapelessRecipe extends ShapelessRecipe implements ShuttleWorkbenchRecipe {
    public ShuttleWorkbenchShapelessRecipe(Identifier id, String group, ItemStack output, DefaultedList<Ingredient> input) {
        super(id, group, CraftingRecipeCategory.MISC, output, input);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeManager.SHUTTLE_WORKBENCH_SHAPELESS_SERIALIZER;
    }

    public static class Serializer implements RecipeSerializer<ShuttleWorkbenchShapelessRecipe> {
        @Override
        public ShuttleWorkbenchShapelessRecipe read(Identifier id, JsonObject json) {
            String string = JsonHelper.getString(json, "group", "");
            DefaultedList<Ingredient> defaultedList = ShapelessRecipe.Serializer.getIngredients(JsonHelper.getArray(json, "ingredients"));

            if (defaultedList.isEmpty()) {
                throw new JsonParseException("No ingredients for shapeless recipe");
            }

            if (defaultedList.size() > 9) {
                throw new JsonParseException("Too many ingredients for shapeless recipe");
            }

            ItemStack itemStack = ShapedRecipe.outputFromJson(JsonHelper.getObject(json, "result"));

            return new ShuttleWorkbenchShapelessRecipe(id, string, itemStack, defaultedList);
        }

        @Override
        public ShuttleWorkbenchShapelessRecipe read(Identifier id, PacketByteBuf buf) {
            String string = buf.readString();
            int i = buf.readVarInt();
            DefaultedList<Ingredient> defaultedList = DefaultedList.ofSize(i, Ingredient.EMPTY);
            defaultedList.replaceAll(ignored -> Ingredient.fromPacket(buf));
            ItemStack itemStack = buf.readItemStack();

            return new ShuttleWorkbenchShapelessRecipe(id, string, itemStack, defaultedList);
        }

        @Override
        public void write(PacketByteBuf buf, ShuttleWorkbenchShapelessRecipe recipe) {
            buf.writeString(recipe.getGroup());
            buf.writeVarInt(recipe.getIngredients().size());

            for (Ingredient ingredient : recipe.getIngredients()) {
                ingredient.write(buf);
            }

            // TODO: Call "getOutput" instead of getting "output"
            buf.writeItemStack(recipe.output);
        }
    }
}
