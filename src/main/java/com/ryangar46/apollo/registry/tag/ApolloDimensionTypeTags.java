package com.ryangar46.apollo.registry.tag;

import com.ryangar46.apollo.Apollo;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.dimension.DimensionType;

public class ApolloDimensionTypeTags {
    public static final TagKey<DimensionType> ATMOSPHERE_NOT_VISIBLE_WORLDS = register("atmosphere_not_visible_worlds");
    public static final TagKey<DimensionType> EARTH_VISIBLE_WORLDS = register("earth_visible_worlds");
    public static final TagKey<DimensionType> METEORITE_SPAWNING_WORLDS = register("meteorite_spawning_worlds");

    private static TagKey<DimensionType> register(String id) {
        return TagKey.of(RegistryKeys.DIMENSION_TYPE, new Identifier(Apollo.MOD_ID, id));
    }
}
