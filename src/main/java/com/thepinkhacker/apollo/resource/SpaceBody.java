package com.thepinkhacker.apollo.resource;

import com.thepinkhacker.apollo.world.dimension.GravityManager;

public record SpaceBody(double gravity, boolean isAtmosphereVisible, boolean hasOxygen){
    public static SpaceBody defaultSpaceBody() {
        return new SpaceBody(
                GravityManager.DEFAULT,
                true,
                true
        );
    }

    @Override
    public String toString() {
        return "gravity: " + gravity +
                "\nisAtmosphereVisible: " + isAtmosphereVisible +
                "\nhasOxygen: " + hasOxygen;
    }
}
