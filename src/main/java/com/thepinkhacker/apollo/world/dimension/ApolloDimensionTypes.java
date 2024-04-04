package com.thepinkhacker.apollo.world.dimension;

import com.thepinkhacker.apollo.Apollo;
import com.thepinkhacker.apollo.registry.tag.ApolloBlockTags;
import net.minecraft.block.Block;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.dimension.DimensionType;

import java.util.OptionalLong;

public class ApolloDimensionTypes {
    public static final Identifier MOON_ID  = Apollo.getIdentifier("moon");
    public static final RegistryKey<DimensionType> MOON = of(MOON_ID);

    public static void bootstrap(Registerable<DimensionType> registerable) {
        registerable.register(MOON, createType(
                OptionalLong.empty(),
                true,
                false,
                false,
                false,
                1.0d,
                true,
                false,
                0,
                256,
                256,
                ApolloBlockTags.INFINIBURN_MOON,
                0.0f,
                createMonsterSetting(
                        false,
                        false,
                        UniformIntProvider.create(0, 7),
                        0
                )
        ));
    }

    private static RegistryKey<DimensionType> of(Identifier id) {
        return RegistryKey.of(RegistryKeys.DIMENSION_TYPE, id);
    }

    public static DimensionType createType(
            OptionalLong fixedTime,
            boolean hasSkylight,
            boolean hasCeiling,
            boolean ultrawarm,
            boolean natural,
            double coordinateScale,
            boolean bedWorks,
            boolean piglinSafe,
            int minY,
            int height,
            int logicalHeight,
            TagKey<Block> infiniburn,
            float ambientLight,
            DimensionType.MonsterSettings monsterSettings
    ) {
        return new DimensionType(
                fixedTime,
                hasSkylight,
                hasCeiling,
                ultrawarm,
                natural,
                coordinateScale,
                bedWorks,
                piglinSafe,
                minY,
                height,
                logicalHeight,
                infiniburn,
                ApolloDimensionEffects.APOLLO,
                ambientLight,
                monsterSettings
        );
    }

    // Only to have argument names show up
    private static DimensionType.MonsterSettings createMonsterSetting(boolean piglinSafe, boolean hasRaids, IntProvider monsterSpawnLightTest, int monsterSpawnBlockLightLimit) {
        return new DimensionType.MonsterSettings(piglinSafe, hasRaids, monsterSpawnLightTest, monsterSpawnBlockLightLimit);
    }
}
