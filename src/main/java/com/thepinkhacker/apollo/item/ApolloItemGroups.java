package com.thepinkhacker.apollo.item;

import com.thepinkhacker.apollo.Apollo;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ApolloItemGroups {
    public static final ItemGroup APOLLO = createBuilder("apollo")
            .icon(() -> new ItemStack(ApolloItems.SPACE_SUIT_HELMET))
            .entries(((context, entries) -> {
                entries.add(ApolloItems.FUEL_BUCKET);
                entries.add(ApolloItems.OIL_BUCKET);
                entries.add(ApolloItems.FLUID_PIPE);
                entries.add(ApolloItems.OIL_REFINERY);
                entries.add(ApolloItems.STORAGE_TANK);
                entries.add(ApolloItems.METEORITE);
                entries.add(ApolloItems.METEORITE_SCRAP);
                entries.add(ApolloItems.REINFORCED_IRON_BLOCK);
                entries.add(ApolloItems.NEGATIVE_GRAVITY_BOOTS);
                entries.add(ApolloItems.POSITIVE_GRAVITY_BOOTS);
                entries.add(ApolloItems.REINFORCED_IRON_INGOT);
                entries.add(ApolloItems.SPACE_SUIT_HELMET);
                entries.add(ApolloItems.SPACE_SUIT_CHESTPLATE);
                entries.add(ApolloItems.SPACE_SUIT_LEGGINGS);
                entries.add(ApolloItems.SPACE_SUIT_BOOTS);
                entries.add(ApolloItems.AIRLOCK_CONTROLLER);
                entries.add(ApolloItems.AIRLOCK_FRAME);
                entries.add(ApolloItems.LAUNCHPAD);
                entries.add(ApolloItems.SHUTTLE_WORKBENCH);
                entries.add(ApolloItems.OXYGEN_PLANT);
                entries.add(ApolloItems.OXYGEN_PLANT_SEEDS);
            }))
            .build();

    public static final ItemGroup MOON = createBuilder("moon")
            .icon(() -> new ItemStack(ApolloItems.LUNAR_SOIL))
            .entries(((context, entries) -> {
                entries.add(ApolloItems.LUNAR_DUST);
                entries.add(ApolloItems.LUNAR_SOIL);
                entries.add(ApolloItems.LUNAR_STONE);
                entries.add(ApolloItems.LUNAR_COBBLESTONE);
                entries.add(ApolloItems.LUNAR_IRON_ORE);
            }))
            .build();

    public static void register() {}

    public static ItemGroup.Builder createBuilder(String id) {
        return FabricItemGroup.builder(new Identifier(Apollo.MOD_ID, id)).displayName(Text.translatable("itemGroup." + Apollo.MOD_ID + "." + id));
    }
}
