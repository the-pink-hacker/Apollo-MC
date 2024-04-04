package com.ryangar46.apollo.fluid;

import com.ryangar46.apollo.Apollo;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class FluidManager {
    public static FlowableFluid STILL_FUEL = Registry.register(Registry.FLUID, new Identifier(Apollo.MOD_ID, "fuel"), new FuelFluid.Still());
    public static FlowableFluid FLOWING_FUEL = Registry.register(Registry.FLUID, new Identifier(Apollo.MOD_ID, "flowing_fuel"), new FuelFluid.Flowing());
    public static FlowableFluid STILL_OIL = Registry.register(Registry.FLUID, new Identifier(Apollo.MOD_ID, "oil"), new OilFluid.Still());
    public static FlowableFluid FLOWING_OIL = Registry.register(Registry.FLUID, new Identifier(Apollo.MOD_ID, "flowing_oil"), new OilFluid.Flowing());

    public static void register() {
        Apollo.LOGGER.info("Registering fluids");
        registerFluid(STILL_FUEL, FLOWING_FUEL, "block/fuel_still", "block/fuel_flow", false);
        registerFluid(STILL_OIL, FLOWING_OIL, "block/oil_still", "block/oil_flow", true);
    }

    public static void registerFluid(FlowableFluid still, FlowableFluid flowable, String still_id, String flowable_id, boolean solid) {
        FluidRenderHandlerRegistry.INSTANCE.register(still, flowable, new SimpleFluidRenderHandler(
                new Identifier(Apollo.MOD_ID, still_id),
                new Identifier(Apollo.MOD_ID, flowable_id)
        ));

        if (solid) {
            BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getSolid(), still, flowable);
        } else {
            BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), still, flowable);
        }

        ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register((atlasTexture, registry) -> {
            registry.register(new Identifier(Apollo.MOD_ID, still_id));
            registry.register(new Identifier(Apollo.MOD_ID, flowable_id));
        });
    }
}
