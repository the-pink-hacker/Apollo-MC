package com.thepinkhacker.apollo.resource;

public class SpaceBody {
    private final double gravity;
    private final boolean isAtmosphereVisible;
    private final boolean hasOxygen;

    public SpaceBody(double gravity, boolean isAtmosphereVisible, boolean hasOxygen) {
        this.gravity = gravity;
        this.isAtmosphereVisible = isAtmosphereVisible;
        this.hasOxygen = hasOxygen;
    }

    public double getGravity() {
        return gravity;
    }

    public boolean getIsAtmosphereVisible() {
        return isAtmosphereVisible;
    }

    public boolean getHasOxygen() {
        return hasOxygen;
    }

    @Override
    public String toString() {
        return "gravity: " + gravity +
                "\nisAtmosphereVisible: " + isAtmosphereVisible +
                "\nhasOxygen: " + hasOxygen;
    }
}
