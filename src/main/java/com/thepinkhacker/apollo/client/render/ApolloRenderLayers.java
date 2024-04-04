package com.thepinkhacker.apollo.client.render;

import com.thepinkhacker.apollo.block.ApolloBlocks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

@Environment(EnvType.CLIENT)
public class ApolloRenderLayers {
    public static void register() {
        BlockRenderLayerMap.INSTANCE.putBlocks(
                RenderLayer.getCutout(),
                ApolloBlocks.OXYGEN_PLANT
        );
    }
}
