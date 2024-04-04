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
    public static final Identifier APOLLO = Apollo.getIdentifier("apollo");

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
            return color.multiply(sunHeight * 0.94f + 0.06f, sunHeight * 0.94f + 0.06f, sunHeight * 0.91f + 0.09f);
        }

        @Override
        public boolean useThickFog(int camX, int camY) {
            return false;
        }
    }
}
