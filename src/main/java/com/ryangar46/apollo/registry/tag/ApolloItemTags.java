package com.ryangar46.apollo.registry.tag;

import com.ryangar46.apollo.Apollo;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ApolloItemTags {
    public static final TagKey<Item> AIRTIGHT_ARMOR = register("airtight_armor");
    public static final TagKey<Item> LAUNCHPADS = register("launchpads");
    public static final TagKey<Item> MOON_ARMOR = register("moon_armor");

    private static TagKey<Item> register(String id) {
        return TagKey.of(RegistryKeys.ITEM, new Identifier(Apollo.MOD_ID, id));
    }
}
