package com.thepinkhacker.apollo.entity;

import com.thepinkhacker.apollo.Apollo;
import com.thepinkhacker.apollo.block.ApolloBlocks;
import com.thepinkhacker.apollo.block.entity.FluidPipeBlockEntity;
import com.thepinkhacker.apollo.client.render.MeteoriteEntityRenderer;
import com.thepinkhacker.apollo.client.render.ShuttleEntityRenderer;
import com.thepinkhacker.apollo.entity.projectile.MeteoriteEntity;
import com.thepinkhacker.apollo.entity.vehicle.ShuttleEntity;
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

public class ApolloEntityTypes {
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
            FabricBlockEntityTypeBuilder.create(FluidPipeBlockEntity::new, ApolloBlocks.FLUID_PIPE)
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
