package com.thepinkhacker.apollo.world;

import com.thepinkhacker.apollo.Apollo;
import com.thepinkhacker.apollo.world.dimension.ApolloDimensionTypes;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class ApolloWorlds {
    public static final RegistryKey<World> MOON = of(ApolloDimensionTypes.MOON_ID);

    private static RegistryKey<World> of(Identifier id) {
        return RegistryKey.of(RegistryKeys.WORLD, id);
    }
}
