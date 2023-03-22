package com.ryangar46.apollo.registry.tag;

import com.ryangar46.apollo.Apollo;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ApolloItemTags {
    // TODO: Consolidate airtight tags
    public static final TagKey<Item> AIRTIGHT_BOOTS = register("airtight_boots");
    public static final TagKey<Item> AIRTIGHT_CHESTPLATES = register("airtight_chestplates");
    public static final TagKey<Item> AIRTIGHT_HELMETS = register("airtight_helmets");
    public static final TagKey<Item> AIRTIGHT_LEGGINGS = register("airtight_leggings");
    public static final TagKey<Item> LAUNCHPADS = register("launchpads");
    public static final TagKey<Item> MOON_ARMOR = register("moon_armor");
    public static final TagKey<Item> NEGATIVE_GRAVITY_EQUIPABLES = register("negative_gravity_equipables");
    public static final TagKey<Item> POSITIVE_GRAVITY_EQUIPABLES = register("positive_gravity_equipables");

    private static TagKey<Item> register(String id) {
        return TagKey.of(RegistryKeys.ITEM, new Identifier(Apollo.MOD_ID, id));
    }
}
