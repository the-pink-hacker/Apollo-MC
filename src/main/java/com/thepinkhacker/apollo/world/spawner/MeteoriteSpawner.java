package com.thepinkhacker.apollo.world.spawner;

import com.thepinkhacker.apollo.entity.ApolloEntityTypes;
import com.thepinkhacker.apollo.entity.projectile.MeteoriteEntity;
import com.thepinkhacker.apollo.resource.SpaceBodyManager;
import com.thepinkhacker.apollo.world.ApolloGameRules;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.World;

public class MeteoriteSpawner implements GenericSpawner {
    // Minutes * ticks/min
    private static final int COOLDOWN_MIN = 20 * 1200;
    private static final int COOLDOWN_MAX = 30 * 1200;
    private static final float SPEED = 30.0f;
    private int cooldown;

    @Override
    public int spawn(ServerWorld world, boolean spawnMonsters, boolean spawnAnimals) {
        if (!canSpawn(world)) return 0;

        cooldown--;

        if (this.cooldown > 0) return 0;

        Random random = world.random;

        this.cooldown = random.nextBetween(COOLDOWN_MIN, COOLDOWN_MAX);

        ServerPlayerEntity player = world.getRandomAlivePlayer();

        if (player == null) return 0;

        BlockPos blockPos = player.getBlockPos();
        BlockPos offsetPos =  new BlockPos(blockPos.getX() + random.nextBetween(-10, 10), world.getHeight() + 16, blockPos.getZ() + random.nextBetween(-10, 10));

        if (!SpawnHelper.isClearForSpawn(world, offsetPos, world.getBlockState(offsetPos), world.getFluidState(offsetPos), ApolloEntityTypes.METEORITE)) return 0;

        MeteoriteEntity entity = ApolloEntityTypes.METEORITE.create(world);

        if (entity == null) return 0;

        // TODO: Randomize yaw and pitch
        entity.refreshPositionAndAngles(offsetPos, 0.0f, 0.0f);
        entity.setVelocity(0.0f, -1.0f, 0.0f, SPEED, 30.0f);
        world.spawnEntityAndPassengers(entity);
        return 1;
    }

    private boolean canSpawn(World world) {
        return world.getGameRules().getBoolean(ApolloGameRules.DO_METEORITE_LANDINGS) &&
                SpaceBodyManager.getInstance().getSpaceBodyOrDefault(world).spawnsMeteorites();
    }
}
