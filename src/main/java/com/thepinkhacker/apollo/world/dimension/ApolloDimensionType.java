package com.thepinkhacker.apollo.world.dimension;

import net.minecraft.block.Block;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.dimension.DimensionType;

import java.util.OptionalLong;

public class ApolloDimensionType extends DimensionType {
    private final SpaceBody spaceBody;

    public ApolloDimensionType(OptionalLong fixedTime, boolean hasSkylight, boolean bl, boolean ultrawarm, boolean bl2, double coordinateScale, boolean bl3, boolean piglinSafe, int i, int j, int k, TagKey<Block> tagKey, Identifier identifier, float f, MonsterSettings monsterSettings, SpaceBody spaceBody) {
        super(fixedTime, hasSkylight, bl, ultrawarm, bl2, coordinateScale, bl3, piglinSafe, i, j, k, tagKey, identifier, f, monsterSettings);
        this.spaceBody = spaceBody;
    }

    public SpaceBody getSpaceBody() {
        return this.spaceBody;
    }
}
