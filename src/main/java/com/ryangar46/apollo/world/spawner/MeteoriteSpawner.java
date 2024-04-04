package com.ryangar46.apollo.world.spawner;

import com.ryangar46.apollo.entity.ApolloEntityTypes;
import com.ryangar46.apollo.entity.projectile.MeteoriteEntity;
import com.ryangar46.apollo.tag.TagManager;
import com.ryangar46.apollo.world.ApolloGameRules;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.SpawnHelper;

public class MeteoriteSpawner implements GenericSpawner {
    private int cooldown;

    @Override
    public int spawn(ServerWorld world, boolean spawnMonsters, boolean spawnAnimals) {
        if (world.getGameRules().getBoolean(ApolloGameRules.DO_METEORITE_LANDINGS))  {
            if (world.getDimensionEntry().isIn(TagManager.METEORITE_SPAWNING_WORLDS)) {
                cooldown--;

                if (this.cooldown > 0) {
                    return 0;
                }

                Random random = world.random;

                this.cooldown = random.nextBetween(24_000, 36_000);

                ServerPlayerEntity player = world.getRandomAlivePlayer();

                if (player != null) {
                    BlockPos blockPos = player.getBlockPos();
                    BlockPos offsetPos =  new BlockPos(blockPos.getX() + random.nextBetween(-10, 10), world.getHeight() + 16, blockPos.getZ() + random.nextBetween(-10, 10));

                    if (SpawnHelper.isClearForSpawn(world, offsetPos, world.getBlockState(offsetPos), world.getFluidState(offsetPos), ApolloEntityTypes.METEORITE)) {
                        MeteoriteEntity entity = ApolloEntityTypes.METEORITE.create(world);

                        if (entity != null) {
                            entity.refreshPositionAndAngles(offsetPos, 0.0f, 0.0f);
                            entity.setVelocity(0.0f, -1.0f, 0.0f, 30.0f, 30.0f);
                            world.spawnEntityAndPassengers(entity);
                            return 1;
                        }
                    }
                }
            }
        }

        return 0;
    }
}
