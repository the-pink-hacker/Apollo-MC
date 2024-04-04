package com.thepinkhacker.apollo.item;

import com.thepinkhacker.apollo.Apollo;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;

public abstract class ApolloItemGroups {
    public static final RegistryKey<ItemGroup> APOLLO = of(
            "apollo",
            builder -> builder
                    .icon(() -> new ItemStack(ApolloItems.SPACE_SUIT_HELMET))
                    .entries(((context, entries) -> {
                        entries.add(ApolloItems.FUEL_BUCKET);
                        entries.add(ApolloItems.FUEL_BOTTLE);
                        entries.add(ApolloItems.OIL_BUCKET);
                        entries.add(ApolloItems.OIL_BOTTLE);
                        entries.add(ApolloItems.FLUID_HOPPER);
                        entries.add(ApolloItems.FLUID_PIPE);
                        entries.add(ApolloItems.FLUID_VALVE_PIPE);
                        entries.add(ApolloItems.OIL_REFINERY);
                        entries.add(ApolloItems.STORAGE_TANK);
                        entries.add(ApolloItems.OILED_SAND);
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
                        entries.add(ApolloItems.LAUNCHPAD);
                        entries.add(ApolloItems.SHUTTLE_WORKBENCH);
                    }))
    );

    public static final RegistryKey<ItemGroup> MOON = of(
            "moon",
            builder -> builder
                    .icon(() -> new ItemStack(ApolloItems.LUNAR_SOIL))
                    .entries(((context, entries) -> {
                        entries.add(ApolloItems.LUNAR_DUST);
                        entries.add(ApolloItems.LUNAR_SOIL);
                        entries.add(ApolloItems.LUNAR_STONE);
                        entries.add(ApolloItems.LUNAR_COBBLESTONE);
                        entries.add(ApolloItems.LUNAR_IRON_ORE);
                    }))
    );

    public static void register() {}

    private static RegistryKey<ItemGroup> of(String id, IdItemGroupBuilder builderCallback) {
        RegistryKey<ItemGroup> key = RegistryKey.of(RegistryKeys.ITEM_GROUP, Apollo.getIdentifier(id));

        ItemGroup itemGroup = builderCallback
                .getBuilder(createBuilder(id))
                .displayName(Text.translatable("itemGroup." + Apollo.MOD_ID + "." + id))
                .build();

        Registry.register(Registries.ITEM_GROUP, key, itemGroup);

        return key;
    }

    private static ItemGroup.Builder createBuilder(String id) {
        return FabricItemGroup.builder().displayName(Text.translatable("itemGroup." + Apollo.MOD_ID + "." + id));
    }

    @FunctionalInterface
    private interface IdItemGroupBuilder {
        ItemGroup.Builder getBuilder(ItemGroup.Builder builder);
    }
}
