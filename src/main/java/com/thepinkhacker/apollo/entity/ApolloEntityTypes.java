package com.thepinkhacker.apollo.entity;

import com.thepinkhacker.apollo.Apollo;
import com.thepinkhacker.apollo.client.render.MeteoriteEntityRenderer;
import com.thepinkhacker.apollo.client.render.ShuttleEntityRenderer;
import com.thepinkhacker.apollo.entity.projectile.MeteoriteEntity;
import com.thepinkhacker.apollo.entity.vehicle.ShuttleEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ApolloEntityTypes {
    public static final EntityType<MeteoriteEntity> METEORITE = of(
            "meteorite",
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, MeteoriteEntity::new)
                    .dimensions(EntityDimensions.fixed(0.25F, 0.25F))
                    .trackRangeBlocks(4)
                    .trackedUpdateRate(10)
    );
    public static final EntityType<ShuttleEntity> SHUTTLE = of(
            "shuttle",
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, ShuttleEntity::new)
                    .dimensions(EntityDimensions.fixed(1.25f, 3.5f))
    );

    public static void register() {
        FabricDefaultAttributeRegistry.register(SHUTTLE, ShuttleEntity.createMobAttributes());
    }

    @Environment(EnvType.CLIENT)
    public static void registerClient() {
        EntityRendererRegistry.register(METEORITE, MeteoriteEntityRenderer::new);
        EntityRendererRegistry.register(SHUTTLE, ShuttleEntityRenderer::new);
    }

    private static <T extends Entity> EntityType<T> of(String id, FabricEntityTypeBuilder<T> builder) {
        return Registry.register(
                Registries.ENTITY_TYPE,
                Apollo.getIdentifier(id),
                builder.build()
        );
    }
}
