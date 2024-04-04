package com.ryangar46.apollo.world.dimension;

import com.ryangar46.apollo.Apollo;
import net.minecraft.block.Block;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;

import java.util.OptionalLong;

public class DimensionManager {
    private static final RegistryKey<DimensionOptions> MOON_OPTIONS = RegistryKey.of(Registry.DIMENSION_KEY, new Identifier(Apollo.MOD_ID, "moon"));
    public static RegistryKey<World> MOON = RegistryKey.of(Registry.WORLD_KEY, MOON_OPTIONS.getValue());
    private static final RegistryKey<DimensionType> MOON_TYPE = RegistryKey.of(Registry.DIMENSION_TYPE_KEY, new Identifier(Apollo.MOD_ID, "moon"));

    public static void register() {
        BuiltinRegistries.add(BuiltinRegistries.DIMENSION_TYPE, MOON_TYPE, createType(
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

    private static DimensionType createType(OptionalLong fixedTime, boolean hasSkylight, boolean hasCeiling, boolean ultrawarm, boolean natural, double coordinateScale, boolean bedWorks, boolean piglinSafe, int minY, int height, int logicalHeight, TagKey<Block> infiniburn, Identifier effects, float ambientLight, DimensionType.MonsterSettings monsterSettings) {
        return new DimensionType(fixedTime, hasSkylight, hasCeiling, ultrawarm, natural, coordinateScale, bedWorks, piglinSafe, minY, height, logicalHeight, infiniburn, effects, ambientLight, monsterSettings);
    }

    private static DimensionType.MonsterSettings createMonsterSetting(boolean piglinSafe, boolean hasRaids, IntProvider monsterSpawnLightTest, int monsterSpawnBlockLightLimit) {
        return new DimensionType.MonsterSettings(piglinSafe, hasRaids, monsterSpawnLightTest, monsterSpawnBlockLightLimit);
    }
}