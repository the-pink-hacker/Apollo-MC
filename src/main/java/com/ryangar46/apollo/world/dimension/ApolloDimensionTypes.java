package com.ryangar46.apollo.world.dimension;

import com.ryangar46.apollo.Apollo;
import net.minecraft.block.Block;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.dimension.DimensionType;

import java.util.OptionalLong;

public class ApolloDimensionTypes {
    public static final RegistryKey<DimensionType> MOON = RegistryKey.of(RegistryKeys.DIMENSION_TYPE, new Identifier(Apollo.MOD_ID, "moon"));

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
                BlockTags.INFINIBURN_OVERWORLD,
                new Identifier(Apollo.MOD_ID, "moon"),
                0.0f,
                createMonsterSetting(
                        false,
                        false,
                        UniformIntProvider.create(0, 7),
                        0
                )
        ));
    }

    // Only to have argument names show up
    private static DimensionType createType(OptionalLong fixedTime, boolean hasSkylight, boolean hasCeiling, boolean ultrawarm, boolean natural, double coordinateScale, boolean bedWorks, boolean piglinSafe, int minY, int height, int logicalHeight, TagKey<Block> infiniburn, Identifier effects, float ambientLight, DimensionType.MonsterSettings monsterSettings) {
        return new DimensionType(fixedTime, hasSkylight, hasCeiling, ultrawarm, natural, coordinateScale, bedWorks, piglinSafe, minY, height, logicalHeight, infiniburn, effects, ambientLight, monsterSettings);
    }

    // Only to have argument names show up
    private static DimensionType.MonsterSettings createMonsterSetting(boolean piglinSafe, boolean hasRaids, IntProvider monsterSpawnLightTest, int monsterSpawnBlockLightLimit) {
        return new DimensionType.MonsterSettings(piglinSafe, hasRaids, monsterSpawnLightTest, monsterSpawnBlockLightLimit);
    }
}
