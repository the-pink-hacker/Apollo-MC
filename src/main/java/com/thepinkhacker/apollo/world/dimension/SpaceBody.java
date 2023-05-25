package com.thepinkhacker.apollo.world.dimension;

import net.minecraft.util.Identifier;

public class SpaceBody {
    private static final float NIGHT_ANGLE = 0.5f;
    private final double gravity;
    private final boolean isAtmosphereVisible;
    private final boolean hasOxygen;
    private final boolean spawnsMeteorites;
    private final String[] satellites;

    public SpaceBody(
            double gravity,
            boolean isAtmosphereVisible,
            boolean hasOxygen,
            boolean spawnsMeteorites,
            String[] satellites
    ) {
        this.gravity = gravity;
        this.isAtmosphereVisible = isAtmosphereVisible;
        this.hasOxygen = hasOxygen;
        this.spawnsMeteorites = spawnsMeteorites;
        this.satellites = satellites;
    }

    @Override
    public String toString() {
        return "gravity: " + gravity +
                "\nisAtmosphereVisible: " + isAtmosphereVisible +
                "\nhasOxygen: " + hasOxygen +
                "\nspawnsMeteorites: " + spawnsMeteorites;
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

    public boolean spawnsMeteorites() {
        return spawnsMeteorites;
    }

    public String[] getSatellites() {
        return satellites;
    }

    public boolean isDefault() {
        return true;
    }

    public float skyAngle(float angle) {
        return isAtmosphereVisible ? angle : NIGHT_ANGLE;
    }

    public Identifier getSecondaryOrbitingBody(Identifier texture) {
        return isAtmosphereVisible ? texture : new Identifier(satellites[1]);
    }
}
