package com.ryangar46.apollo.entity;

import com.ryangar46.apollo.Apollo;
import com.ryangar46.apollo.block.BlockManager;
import com.ryangar46.apollo.block.entity.FluidPipeBlockEntity;
import com.ryangar46.apollo.client.render.MeteoriteEntityRenderer;
import com.ryangar46.apollo.client.render.ShuttleEntityRenderer;
import com.ryangar46.apollo.entity.projectile.MeteoriteEntity;
import com.ryangar46.apollo.entity.vehicle.ShuttleEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class EntityManager {
    public static final EntityType<MeteoriteEntity> METEORITE = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(Apollo.MOD_ID, "meteorite"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, MeteoriteEntity::new)
                    .dimensions(EntityDimensions.fixed(0.25F, 0.25F))
                    .trackRangeBlocks(4).trackedUpdateRate(10)
                    .build()
    );
    public static final EntityType<ShuttleEntity> SHUTTLE = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(Apollo.MOD_ID, "shuttle"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, ShuttleEntity::new)
                    .dimensions(EntityDimensions.fixed(1.25f, 3.5f))
                    .build()
    );
    public static final BlockEntityType<FluidPipeBlockEntity> FLUID_PIPE_BLOCK_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            new Identifier(Apollo.MOD_ID, "shuttle"),
            FabricBlockEntityTypeBuilder.create(FluidPipeBlockEntity::new, BlockManager.FLUID_PIPE)
                    .build()
    );

    public static void register() {
        FabricDefaultAttributeRegistry.register(SHUTTLE, ShuttleEntity.createMobAttributes());
    }

    @Environment(EnvType.CLIENT)
    public static void registerClient() {
        EntityRendererRegistry.register(METEORITE, MeteoriteEntityRenderer::new);
        EntityRendererRegistry.register(SHUTTLE, ShuttleEntityRenderer::new);
    }
}
