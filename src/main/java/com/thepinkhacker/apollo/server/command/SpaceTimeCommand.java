package com.thepinkhacker.apollo.server.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.thepinkhacker.apollo.resource.SpaceBodyManager;
import com.thepinkhacker.apollo.world.dimension.DayCycleManager;
import net.minecraft.command.argument.TimeArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.world.World;

// TODO: Provide feedback in something other than ticks
public abstract class SpaceTimeCommand {
    private static final long TICKS_PER_DAY = 24_000L;

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("spacetime")
                .requires(source -> source.hasPermissionLevel(2))
                .then(CommandManager.literal("add")
                        .then(CommandManager.argument("time", TimeArgumentType.time()))
                        .executes(context -> 0)
                )
                .then(CommandManager.literal("query")
                        .then(CommandManager.literal(QueryTime.DAY.toString())
                                .executes(context -> executeQuery(context.getSource(), QueryTime.DAY))
                        )
                        .then(CommandManager.literal(QueryTime.DAYTIME.toString())
                                .executes(context -> executeQuery(context.getSource(), QueryTime.DAYTIME))
                        )
                        .then(CommandManager.literal(QueryTime.GAMETIME.toString())
                                .executes(context -> executeQuery(context.getSource(), QueryTime.GAMETIME))
                        )
                )
                .then(CommandManager.literal("set")
                        .then(CommandManager.literal(BuiltinTimes.DAY.toString())
                                .executes(context -> executeSet(context.getSource(), BuiltinTimes.DAY))
                        )
                        .then(CommandManager.literal(BuiltinTimes.NOON.toString())
                                .executes(context -> executeSet(context.getSource(), BuiltinTimes.NOON))
                        )
                        .then(CommandManager.literal(BuiltinTimes.NIGHT.toString())
                                .executes(context -> executeSet(context.getSource(), BuiltinTimes.NIGHT))
                        )
                        .then(CommandManager.literal(BuiltinTimes.MIDNIGHT.toString())
                                .executes(context -> executeSet(context.getSource(), BuiltinTimes.MIDNIGHT))
                        )
                        .then(CommandManager.argument("time", TimeArgumentType.time())
                                .executes(context -> executeSet(
                                        context.getSource(),
                                        IntegerArgumentType.getInteger(context, "time")
                                ))
                        )
                )
        );
    }

    private static int executeQuery(ServerCommandSource source, QueryTime type) {
        return executeQuery(source, source.getWorld(), type);
    }

    private static int executeQuery(ServerCommandSource source, ServerWorld world, QueryTime type) {
        DayCycleManager.WorldTime time = DayCycleManager.getLightProviderTime(world.getTime(), world);

        int parsedTime = (int)(type.getTime(time));
        source.sendFeedback(() -> Text.translatable("commands.time.query", parsedTime), true);

        return parsedTime;
    }

    private static int executeSet(ServerCommandSource source, int time) {
        return executeSet(source, DayCycleManager.WorldTime.ofOverworldTicks(time));
    }

    private static int executeSet(ServerCommandSource source, BuiltinTimes time) {
        return executeSet(source, time.getTime(source.getWorld()));
    }

    private static int executeSet(ServerCommandSource source, DayCycleManager.WorldTime time) {
        return executeSet(source, time, source.getWorld());
    }

    private static int executeSet(ServerCommandSource source, DayCycleManager.WorldTime time, World world) {
        long timeTicks = SpaceBodyManager
                .getInstance()
                .getSpaceBody(world)
                .map(spaceBody -> time.asOrbitTicks(spaceBody.getLightProvider().getOrbit()))
                .orElse(time.asTicks());

        return executeSet(source, timeTicks);
    }

    private static int executeSet(ServerCommandSource source, long ticks) {
        for (ServerWorld serverWorld : source.getServer().getWorlds()) {
            serverWorld.setTimeOfDay(ticks);
        }

        source.sendFeedback(() -> Text.translatable("commands.time.set", ticks), true);

        return (int)(ticks % TICKS_PER_DAY);
    }

    private enum BuiltinTimes {
        DAY("day", DayCycleManager.DAY_TICKS),
        NOON("noon", DayCycleManager.NOON_TICKS),
        NIGHT("night", DayCycleManager.NIGHT_TICKS),
        MIDNIGHT("midnight", DayCycleManager.MIDNIGHT_TICKS);

        private final String id;
        private final long overworldTicks;

        BuiltinTimes(String id, long overworldTicks) {
            this.id = id;
            this.overworldTicks = overworldTicks;
        }

        public DayCycleManager.WorldTime getTime(World world) {
            return DayCycleManager.getLightProviderTime(this.overworldTicks, world);
        }

        @Override
        public String toString() {
            return this.id;
        }
    }

    private enum QueryTime {
        DAY("day"),
        DAYTIME("daytime"),
        GAMETIME("gametime");

        private final String id;

        QueryTime(String id) {
            this.id = id;
        }

        public long getTime(DayCycleManager.WorldTime time) {
            return switch (this) {
                case DAY -> time.asTicks() / TICKS_PER_DAY % 2_147_483_647L;
                case DAYTIME -> time.asTicks();
                case GAMETIME -> time.asTicks() % 2_147_483_647L;
            };
        }

        @Override
        public String toString() {
            return this.id;
        }
    }
}
