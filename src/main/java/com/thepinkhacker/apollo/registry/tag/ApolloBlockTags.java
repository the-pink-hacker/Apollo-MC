package com.thepinkhacker.apollo.registry.tag;

import com.thepinkhacker.apollo.Apollo;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public class ApolloBlockTags {
    public static final TagKey<Block> FLUID_PIPE_CONNECTABLE_BLOCKS = of("fluid_pipe_connectable_blocks");
    public static final TagKey<Block> LAUNCHPADS = of("launchpads");
    public static final TagKey<Block> LUNAR_ORE_REPLACEABLES = of("lunar_ore_replaceables");
    public static final TagKey<Block> INFINIBURN_MOON = of("infiniburn_moon");
    
    private static TagKey<Block> of(String id) {
        return TagKey.of(RegistryKeys.BLOCK, Apollo.getIdentifier(id));
    }
}
