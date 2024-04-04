package com.ryangar46.apollo.world;

import com.ryangar46.apollo.Apollo;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class ApolloWorlds {
    public static RegistryKey<World> MOON = RegistryKey.of(RegistryKeys.WORLD, new Identifier(Apollo.MOD_ID, "moon"));
}
