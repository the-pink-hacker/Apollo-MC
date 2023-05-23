package com.thepinkhacker.apollo.world.dimension;

public class SpaceBody {
    private final double gravity;
    private final boolean isAtmosphereVisible;
    private final boolean hasOxygen;

    public SpaceBody(double gravity, boolean isAtmosphereVisible, boolean hasOxygen) {
        this.gravity = gravity;
        this.isAtmosphereVisible = isAtmosphereVisible;
        this.hasOxygen = hasOxygen;
    }

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

    public double getGravity() {
        return gravity;
    }

    public boolean isAtmosphereVisible() {
        return isAtmosphereVisible;
    }

    public boolean hasOxygen() {
        return hasOxygen;
    }

    public boolean isDefault() {
        return true;
    }

}
