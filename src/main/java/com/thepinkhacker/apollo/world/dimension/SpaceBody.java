package com.thepinkhacker.apollo.world.dimension;

import com.google.gson.*;
import com.thepinkhacker.apollo.resource.GsonHelper;
import net.minecraft.util.Identifier;

import java.lang.reflect.Type;
import java.util.List;

public class SpaceBody {
    private static final float NIGHT_ANGLE = 0.5f;
    private final double gravity;
    private final boolean isAtmosphereVisible;
    private final boolean hasOxygen;
    private final boolean spawnsMeteorites;
    private final Satellite[] satellites;

    public SpaceBody(
            double gravity,
            boolean isAtmosphereVisible,
            boolean hasOxygen,
            boolean spawnsMeteorites,
            Satellite[] satellites
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

    public Satellite[] getSatellites() {
        return satellites;
    }

    public boolean isDefault() {
        return true;
    }

    public float skyAngle(float angle) {
        return isAtmosphereVisible ? angle : NIGHT_ANGLE;
    }

    public Identifier getSecondaryOrbitingBody(Identifier texture) {
        return isAtmosphereVisible ? texture : satellites[1].texture;
    }

    public static void registerGsonType(GsonBuilder gsonBuilder) {
        gsonBuilder.registerTypeAdapter(SpaceBody.class, new Deserializer());
        gsonBuilder.registerTypeAdapter(Satellite.class, new Satellite.Deserializer());
        gsonBuilder.registerTypeAdapter(Identifier.class, new Identifier.Serializer());
    }

    private static class Deserializer implements JsonDeserializer<SpaceBody> {
        @Override
        public SpaceBody deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
            JsonObject object = jsonElement.getAsJsonObject();
            double gravity = object.get("gravity").getAsDouble();
            boolean isAtmosphereVisible = object.get("is_atmosphere_visible").getAsBoolean();
            boolean hasOxygen = object.get("has_oxygen").getAsBoolean();
            boolean spawnsMeteorites = object.get("spawns_meteorites").getAsBoolean();
            List<JsonElement> satellites = object.getAsJsonArray("satellites").asList();
            Satellite[] parsedSatellites = new Satellite[satellites.size()];
            for (int i = 0; i < satellites.size(); i++) {
                parsedSatellites[i] = context.deserialize(satellites.get(i), GsonHelper.getType(Satellite.class));
            }
            return new SpaceBody(
                    gravity,
                    isAtmosphereVisible,
                    hasOxygen,
                    spawnsMeteorites,
                    parsedSatellites
            );
        }
    }

    public record Satellite(Identifier texture, boolean fixedOrbit) {
        public static Satellite fromShortTexture(Identifier texture, boolean fixedOrbit) {
            return new Satellite(texture.withPrefixedPath("textures/"), fixedOrbit);
        }

        private static class Deserializer implements JsonDeserializer<Satellite> {
            @Override
            public Satellite deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
                JsonObject object = jsonElement.getAsJsonObject();
                Identifier texture = context.deserialize(object.get("texture"), GsonHelper.getType(Identifier.class));
                boolean fixedOrbit = object.get("fixedOrbit").getAsBoolean();
                return Satellite.fromShortTexture(texture, fixedOrbit);
            }
        }
    }
}
