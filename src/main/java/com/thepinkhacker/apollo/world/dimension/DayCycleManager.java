package com.thepinkhacker.apollo.world.dimension;

import com.thepinkhacker.apollo.resource.SpaceBodyManager;
import net.minecraft.world.World;

// TODO: Add improved time command
public abstract class DayCycleManager {
    public static WorldTime getLightProviderTime(long ticks, SpaceBody spaceBody) {
        return WorldTime.ofOrbitTicks(ticks, spaceBody.getLightProvider().getOrbit());
    }

    public static WorldTime getLightProviderTime(long ticks, World world) {
        return SpaceBodyManager
                .getInstance()
                .getSpaceBody(world)
                .map(spaceBody -> getLightProviderTime(ticks, spaceBody))
                .orElse(WorldTime.ofOverworldTicks(ticks));
    }

    public static WorldTime getLightProviderTime(World world) {
        return getLightProviderTime(world.getLunarTime(), world);
    }

    public static class WorldTime {
        private static final long TICKS_PER_SECOND = 20;
        private static final long MINUTES_PER_SECOND = 20;
        private static final long SECONDS_PER_DAY = 60 * MINUTES_PER_SECOND;
        private static final long TICKS_PER_DAY = TICKS_PER_SECOND * SECONDS_PER_DAY;
        private static final long TICKS_PER_QUARTER_DAY = TICKS_PER_DAY / 4;
        public static final WorldTime DAY = WorldTime.ofOverworldTicks(0);
        public static final WorldTime NOON = WorldTime.ofOverworldTicks(TICKS_PER_QUARTER_DAY);
        public static final WorldTime NIGHT = WorldTime.ofOverworldTicks(TICKS_PER_QUARTER_DAY * 2);
        public static final WorldTime MIDNIGHT = WorldTime.ofOverworldTicks(TICKS_PER_QUARTER_DAY * 3);
        private final long overworldTicks;

        private WorldTime(long timeTicks) {
            this.overworldTicks = timeTicks;
        }

        public static WorldTime ofOverworldTicks(long ticks) {
            return new WorldTime(ticks);
        }

        public long inTicks() {
            return overworldTicks;
        }

        public static WorldTime ofOrbitTicks(long ticks, SpaceBody.Orbit orbit) {
            return ofOverworldTicks((long)(ticks / orbit.getTimeMultiplier()));
        }

        public long inOrbitTicks(SpaceBody.Orbit orbit) {
            return (long)(inTicks() * orbit.getTimeMultiplier());
        }
    }
}
