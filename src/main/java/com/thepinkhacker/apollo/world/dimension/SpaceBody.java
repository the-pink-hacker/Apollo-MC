package com.thepinkhacker.apollo.world.dimension;

import com.google.gson.*;
import com.thepinkhacker.apollo.resource.GsonHelper;
import net.minecraft.util.Identifier;
import org.joml.Vector2f;
import org.joml.Vector2i;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

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

    public static void registerGsonType(GsonBuilder gsonBuilder) {
        gsonBuilder.registerTypeAdapter(SpaceBody.class, new Deserializer());
        gsonBuilder.registerTypeAdapter(Satellite.class, new Satellite.Deserializer());
        gsonBuilder.registerTypeAdapter(Identifier.class, new Identifier.Serializer());
    }

    private static class Deserializer implements JsonDeserializer<SpaceBody> {
        @Override
        public SpaceBody deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
            JsonObject object = jsonElement.getAsJsonObject();
            GsonHelper helper = new GsonHelper(object);
            double gravity = helper.getOptionalDouble("gravity").orElse(GravityManager.DEFAULT);
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

    public record Satellite(Identifier texture, float scale, Vector2i phases, Orbit orbit) {
        public static Satellite fromShortTexture(
                Identifier texture,
                float scale,
                Vector2i phases,
                Orbit orbit
        ) {
            return new Satellite(
                    texture.withPrefixedPath("textures/"),
                    scale,
                    phases,
                    orbit
            );
        }

        private static class Deserializer implements JsonDeserializer<Satellite> {
            @Override
            public Satellite deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
                JsonObject object = jsonElement.getAsJsonObject();
                GsonHelper helper = new GsonHelper(object);
                Identifier texture = context.deserialize(object.get("texture"), GsonHelper.getType(Identifier.class));
                boolean fixedOrbit = helper.getOptionalBoolean("fixedOrbit").orElse(false);
                float scale = helper.getOptionalFloat("scale").orElse(1.0f);
                Vector2i phases = helper.getDefaultVector2I("phases", 1, 1);

                Orbit orbit = helper.getOptionalHelper("orbit").map(orbitHelper -> new Orbit(
                        orbitHelper.getOptionalBoolean("fixed").orElse(false),
                        orbitHelper.getDefaultVector2F("offset", 0.0f, 0.0f)
                )).orElse(new Orbit(false, new Vector2f(0.0f, 0.0f)));

                return Satellite.fromShortTexture(texture, scale, phases, orbit);
            }
        }
    }

    public record Orbit(boolean fixed, Vector2f offset) {}
}
