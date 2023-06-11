package com.thepinkhacker.apollo.client.world;

import com.thepinkhacker.apollo.Apollo;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.DimensionRenderingRegistry;
import net.minecraft.client.render.DimensionEffects;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

@Environment(EnvType.CLIENT)
public class DimensionEffectsManager {
    public static final Identifier APOLLO = Apollo.getIdentifier(Apollo.MOD_ID);

    public static void registerClient() {
        Apollo.LOGGER.info("Registering dimension effects");

        DimensionRenderingRegistry.registerDimensionEffects(APOLLO, new ApolloDimensionEffects());
    }

    @Environment(EnvType.CLIENT)
    public static class ApolloDimensionEffects extends DimensionEffects {
        public ApolloDimensionEffects() {
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
