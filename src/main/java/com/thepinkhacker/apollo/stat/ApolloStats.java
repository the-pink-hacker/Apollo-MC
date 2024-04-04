package com.thepinkhacker.apollo.stat;

import com.thepinkhacker.apollo.Apollo;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.stat.StatFormatter;
import net.minecraft.util.Identifier;

import static net.minecraft.stat.Stats.CUSTOM;

public class ApolloStats {
    public static final Identifier INTERACT_WITH_SHUTTLE_WORKBENCH = registerStat("interact_with_shuttle_workbench", StatFormatter.DEFAULT);

    // Java is weird and won't init the variables unless this runs
    // Otherwise it would try to init after the registry is frozen
    public static void register() {}

    private static Identifier registerStat(String id, StatFormatter formatter) {
        Identifier identifier = Apollo.getIdentifier(id);
        Registry.register(Registries.CUSTOM_STAT, id, identifier);
        CUSTOM.getOrCreateStat(identifier, formatter);

        return identifier;
    }
}
