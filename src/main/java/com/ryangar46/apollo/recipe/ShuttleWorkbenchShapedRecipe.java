package com.ryangar46.apollo.recipe;

import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;

import java.util.Map;

public class ShuttleWorkbenchShapedRecipe extends ShapedRecipe implements ShuttleWorkbenchRecipe {
    public ShuttleWorkbenchShapedRecipe(Identifier id, String group, int width, int height, DefaultedList<Ingredient> input, ItemStack output) {
        super(id, group, CraftingRecipeCategory.MISC, width, height, input, output);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeManager.SHUTTLE_WORKBENCH_SHAPED_SERIALIZER;
    }

    public static class Serializer implements RecipeSerializer<ShuttleWorkbenchShapedRecipe> {
        @Override
        public ShuttleWorkbenchShapedRecipe read(Identifier id, JsonObject json) {
            String string = JsonHelper.getString(json, "group", "");
            Map<String, Ingredient> map = ShuttleWorkbenchShapedRecipe.readSymbols(JsonHelper.getObject(json, "key"));
            String[] strings = ShuttleWorkbenchShapedRecipe.removePadding(ShapedRecipe.getPattern(JsonHelper.getArray(json, "pattern")));

            int i = strings[0].length();
            int j = strings.length;

            DefaultedList<Ingredient> defaultedList = ShuttleWorkbenchShapedRecipe.createPatternMatrix(strings, map, i, j);
            ItemStack itemStack = ShuttleWorkbenchShapedRecipe.outputFromJson(JsonHelper.getObject(json, "result"));

            return new ShuttleWorkbenchShapedRecipe(id, string, i, j, defaultedList, itemStack);
        }

        @Override
        public ShuttleWorkbenchShapedRecipe read(Identifier id, PacketByteBuf buf) {
            int i = buf.readVarInt();
            int j = buf.readVarInt();

            String string = buf.readString();
            DefaultedList<Ingredient> defaultedList = DefaultedList.ofSize(i * j, Ingredient.EMPTY);
            defaultedList.replaceAll(ignored -> Ingredient.fromPacket(buf));
            ItemStack itemStack = buf.readItemStack();

            return new ShuttleWorkbenchShapedRecipe(id, string, i, j, defaultedList, itemStack);
        }

        @Override
        public void write(PacketByteBuf buf, ShuttleWorkbenchShapedRecipe recipe) {
            buf.writeVarInt(recipe.getWidth());
            buf.writeVarInt(recipe.getHeight());
            buf.writeString(recipe.getGroup());

            for (Ingredient ingredient : recipe.getIngredients()) {
                ingredient.write(buf);
            }

            buf.writeItemStack(recipe.getOutput());
        }
    }
}
