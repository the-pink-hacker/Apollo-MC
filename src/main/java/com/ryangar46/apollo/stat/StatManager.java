package com.ryangar46.apollo.stat;

import com.ryangar46.apollo.Apollo;
import net.minecraft.stat.StatFormatter;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static net.minecraft.stat.Stats.CUSTOM;

public class StatManager {
    public static final Identifier INTERACT_WITH_SHUTTLE_WORKBENCH = registerStat("interact_with_shuttle_workbench", StatFormatter.DEFAULT);

    public static void register() {
        // Java is weird and won't init the variables unless this runs
        // Otherwise it would try to init after the registry is frozen
        Apollo.LOGGER.info("Registering stats");
    }

    private static Identifier registerStat(String id, StatFormatter formatter) {
        Identifier identifier = new Identifier(Apollo.MOD_ID, id);
        Registry.register(Registry.CUSTOM_STAT, id, identifier);
        CUSTOM.getOrCreateStat(identifier, formatter);

        return identifier;
    }
}
