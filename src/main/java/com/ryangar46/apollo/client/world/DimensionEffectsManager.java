package com.ryangar46.apollo.client.world;

import com.ryangar46.apollo.Apollo;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.DimensionRenderingRegistry;
import net.minecraft.client.render.DimensionEffects;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

@Environment(EnvType.CLIENT)
public class DimensionEffectsManager {
    public static void register() {
        Apollo.LOGGER.info("Registering dimension effects");

        registerDimensionEffect("moon", new Moon());
    }

    public static void registerDimensionEffect(String id, DimensionEffects dimensionEffects) {
        DimensionRenderingRegistry.registerDimensionEffects(new Identifier(Apollo.MOD_ID, id), dimensionEffects);
    }

    @Environment(EnvType.CLIENT)
    public static class Moon extends DimensionEffects {
        public Moon() {
            super(Float.NaN, true, SkyType.NORMAL, false, false);
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
