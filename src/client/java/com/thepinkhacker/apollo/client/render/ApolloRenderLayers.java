package com.thepinkhacker.apollo.client.render;

import com.thepinkhacker.apollo.block.ApolloBlocks;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

public class ApolloRenderLayers {
    public static void register() {
        BlockRenderLayerMap.INSTANCE.putBlocks(
                RenderLayer.getCutout(),
                ApolloBlocks.OXYGEN_PLANT
        );
    }
}
