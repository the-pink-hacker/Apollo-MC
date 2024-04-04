package com.thepinkhacker.apollo.fluid;

import com.thepinkhacker.apollo.Apollo;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ApolloFluids {
    public static final FlowableFluid STILL_FUEL = register("fuel", new FuelFluid.Still());
    public static final FlowableFluid FLOWING_FUEL = register("flowing_fuel", new FuelFluid.Flowing());
    public static final FlowableFluid STILL_OIL = register("oil", new OilFluid.Still());
    public static final FlowableFluid FLOWING_OIL = register("flowing_oil", new OilFluid.Flowing());

    private static FlowableFluid register(String id, FlowableFluid fluid) {
        return Registry.register(Registries.FLUID, Apollo.getIdentifier(id), fluid);
    }
}
