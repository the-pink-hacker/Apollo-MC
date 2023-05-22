package com.thepinkhacker.apollo.resource;

public record SpaceBody(double gravity, boolean isAtmosphereVisible, boolean hasOxygen) {
    @Override
    public String toString() {
        return "gravity: " + gravity +
                "\nisAtmosphereVisible: " + isAtmosphereVisible +
                "\nhasOxygen: " + hasOxygen;
    }
}
