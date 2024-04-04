package com.thepinkhacker.apollo.client.world;

import com.thepinkhacker.apollo.Apollo;
import com.thepinkhacker.apollo.world.dimension.ApolloDimensionEffects;
import net.fabricmc.fabric.api.client.rendering.v1.DimensionRenderingRegistry;
import net.minecraft.client.render.DimensionEffects;
import net.minecraft.util.math.Vec3d;

public class DimensionEffectsManager {

    public static void register() {
        Apollo.LOGGER.info("Registering dimension effects");

        DimensionRenderingRegistry.registerDimensionEffects(ApolloDimensionEffects.APOLLO, new ApolloCustomDimensionEffects());
    }

    public static class ApolloCustomDimensionEffects extends DimensionEffects {
        public ApolloCustomDimensionEffects() {
            super(
                    Float.NaN,
                    false,
                    SkyType.NONE,
                    false,
                    false
            );
        }

        @Override
        public Vec3d adjustFogColor(Vec3d color, float sunHeight) {
            return color;
        }

        @Override
        public boolean useThickFog(int camX, int camY) {
            return false;
        }
    }
}
