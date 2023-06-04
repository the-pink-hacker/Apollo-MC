package com.thepinkhacker.apollo.world.dimension;

import com.thepinkhacker.apollo.resource.SpaceBodyManager;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

// TODO: Add improved time command
public abstract class DayCycleManager {
    private static final long TICKS_PER_SECOND = 20;
    private static final long MINUTES_PER_SECOND = 20;
    private static final long SECONDS_PER_DAY = 60 * MINUTES_PER_SECOND;
    private static final long TICKS_PER_DAY = TICKS_PER_SECOND * SECONDS_PER_DAY;
    private static final long TICKS_PER_QUARTER_DAY = TICKS_PER_DAY / 4;
    public static final long DAY_TICKS = 1_000L;
    public static final long NOON_TICKS = TICKS_PER_QUARTER_DAY;
    public static final long NIGHT_TICKS = 13_000L;
    public static final long MIDNIGHT_TICKS = TICKS_PER_QUARTER_DAY * 3L;

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

    public static double getSkyAngleDegrees(WorldTime time) {
        double d = MathHelper.fractionalPart((double)time.asTicks() / TICKS_PER_DAY - 0.25d);
        double e = 0.5d - Math.cos(d * Math.PI) / 2.0d;
        return (d * 2.0d + e) / 3.0d * 360.0d;
    }

    public static double getSkyAngleDegreesLightProvider(World world) {
        return getSkyAngleDegrees(getLightProviderTime(world.getLunarTime(), world));
    }

    public static class WorldTime {
        private final long overworldTicks;

        private WorldTime(long timeTicks) {
            this.overworldTicks = timeTicks;
        }

        public static WorldTime ofOverworldTicks(long ticks) {
            return new WorldTime(ticks);
        }

        public long asTicks() {
            return overworldTicks;
        }

        public static WorldTime ofOrbitTicks(long ticks, SpaceBody.Orbit orbit) {
            return ofOverworldTicks((long)(ticks * orbit.getTimeMultiplier()));
        }

        public long asOrbitTicks(SpaceBody.Orbit orbit) {
            return (long)(asTicks() / orbit.getTimeMultiplier());
        }
    }
}
