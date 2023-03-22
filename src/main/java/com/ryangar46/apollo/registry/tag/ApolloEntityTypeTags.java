package com.ryangar46.apollo.registry.tag;

import com.ryangar46.apollo.Apollo;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ApolloEntityTypeTags {
    public static final TagKey<EntityType<?>> SHUTTLES = register("shuttles");
    public static final TagKey<EntityType<?>> VACUUM_IMMUNE_CREATURES = register("vacuum_immune_creatures");

    private static TagKey<EntityType<?>> register(String id) {
        return TagKey.of(RegistryKeys.ENTITY_TYPE, new Identifier(Apollo.MOD_ID, id));
    }
}
