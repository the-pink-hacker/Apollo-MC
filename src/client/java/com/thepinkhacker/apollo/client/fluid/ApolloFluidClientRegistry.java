package com.thepinkhacker.apollo.client.fluid;

import com.thepinkhacker.apollo.Apollo;
import com.thepinkhacker.apollo.fluid.ApolloFluids;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.fluid.FlowableFluid;

public class ApolloFluidClientRegistry {
    public static void register() {
        registerFluid(
                ApolloFluids.STILL_FUEL,
                ApolloFluids.FLOWING_FUEL,
                "block/fuel_still",
                "block/fuel_flow",
                false
        );
        registerFluid(
                ApolloFluids.STILL_OIL,
                ApolloFluids.FLOWING_OIL,
                "block/oil_still",
                "block/oil_flow",
                true
        );
    }

    private static void registerFluid(FlowableFluid still, FlowableFluid flowable, String still_id, String flowable_id, boolean solid) {
        FluidRenderHandlerRegistry.INSTANCE.register(still, flowable, new SimpleFluidRenderHandler(
                Apollo.getIdentifier(still_id),
                Apollo.getIdentifier(flowable_id)
        ));

        BlockRenderLayerMap.INSTANCE.putFluids(
                solid ? RenderLayer.getSolid() : RenderLayer.getTranslucent(),
                still,
                flowable
        );
    }
}
