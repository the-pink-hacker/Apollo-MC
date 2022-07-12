package com.ryangar46.apollo.fluid;

import net.minecraft.util.StringIdentifiable;

public enum PipeStorableFluid implements StringIdentifiable {
    EMPTY("empty"),
    WATER("water"),
    OIL("oil"),
    FUEL("fuel");

    final String NAME;

    PipeStorableFluid(String name) {
        NAME = name;
    }

    @Override
    public String asString() {
        return this.NAME;
    }

    public boolean isEmpty() {
        return this == EMPTY;
    }

    public boolean canMerge(PipeStorableFluid fluidType) {
        if (isEmpty() || fluidType.isEmpty()) return true;
        return this == fluidType;
    }
}
