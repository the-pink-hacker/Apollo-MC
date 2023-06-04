package com.thepinkhacker.apollo.world.dimension;

import com.google.gson.*;
import com.thepinkhacker.apollo.resource.GsonHelper;
import net.minecraft.util.Identifier;
import org.joml.Vector2i;
import org.joml.Vector3f;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

public class SpaceBody {
    private static final float NIGHT_ANGLE = 0.5f;
    private final double gravity;
    private final boolean isAtmosphereVisible;
    private final boolean hasOxygen;
    private final boolean spawnsMeteorites;
    private final StarSettings starSettings;
    private final Satellite lightProvider;
    private final Satellite[] satellites;

    public SpaceBody(
            double gravity,
            boolean isAtmosphereVisible,
            boolean hasOxygen,
            boolean spawnsMeteorites,
            StarSettings starSettings,
            Satellite lightProvider,
            Satellite[] satellites
    ) {
        this.gravity = gravity;
        this.isAtmosphereVisible = isAtmosphereVisible;
        this.hasOxygen = hasOxygen;
        this.spawnsMeteorites = spawnsMeteorites;
        this.starSettings = starSettings;
        this.lightProvider = lightProvider;
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

    public StarSettings getStarSettings() {
        return starSettings;
    }

    public boolean spawnsMeteorites() {
        return spawnsMeteorites;
    }

    public Satellite[] getSatellites() {
        return satellites;
    }

    /**
     * @return All satellites (including light provider)
     */
    public Satellite[] getAllSatellites() {
        if (satellites == null) return new Satellite[0];
        if (lightProvider == null) return satellites;

        Satellite[] all = new Satellite[satellites.length + 1];
        all[0] = lightProvider;

        for (int i = 0; i < satellites.length; i++) {
            all[i + 1] = satellites[i];
        }

        return all;
    }

    public Satellite getLightProvider() {
        return lightProvider;
    }

    public boolean isDefault() {
        return true;
    }

    public static void registerGsonType(GsonBuilder gsonBuilder) {
        gsonBuilder.registerTypeAdapter(SpaceBody.class, new Serializer())
                .registerTypeAdapter(Satellite.class, new Satellite.Serializer())
                .registerTypeAdapter(Orbit.class, new Orbit.Serializer())
                .registerTypeAdapter(StarSettings.class, new StarSettings.Serializer())
                .registerTypeAdapter(Identifier.class, new Identifier.Serializer());
    }

    private static class Serializer implements JsonDeserializer<SpaceBody>, JsonSerializer<SpaceBody> {
        @Override
        public SpaceBody deserialize(
                JsonElement jsonElement,
                Type type,
                JsonDeserializationContext context
        ) throws JsonParseException {
            JsonObject object = jsonElement.getAsJsonObject();
            GsonHelper helper = new GsonHelper(object);

            double gravity = helper.getOptionalDouble("gravity").orElse(GravityManager.DEFAULT);
            boolean isAtmosphereVisible = object.get("is_atmosphere_visible").getAsBoolean();
            boolean hasOxygen = object.get("has_oxygen").getAsBoolean();
            boolean spawnsMeteorites = object.get("spawns_meteorites").getAsBoolean();

            Type satelliteType = GsonHelper.getType(Satellite.class);

            // Satellites
            Satellite lightProvider = context.deserialize(object.get("light_provider"), satelliteType);

            List<JsonElement> satellites = object.getAsJsonArray("satellites").asList();
            Satellite[] parsedSatellites = new Satellite[satellites.size()];

            StarSettings starSettings = helper
                    .getOptionalDeserialization(context, StarSettings.class, "star_settings")
                    .orElse(StarSettings.getDefault());

            for (int i = 0; i < satellites.size(); i++) {
                parsedSatellites[i] = context.deserialize(satellites.get(i), satelliteType);
            }

            return new SpaceBody(
                    gravity,
                    isAtmosphereVisible,
                    hasOxygen,
                    spawnsMeteorites,
                    starSettings,
                    lightProvider,
                    parsedSatellites
            );
        }

        @Override
        public JsonElement serialize(SpaceBody spaceBody, Type type, JsonSerializationContext context) {
            JsonObject object = new JsonObject();

            object.addProperty("gravity", spaceBody.gravity);
            object.addProperty("is_atmosphere_visible", spaceBody.isAtmosphereVisible);
            object.addProperty("has_oxygen", spaceBody.hasOxygen);
            object.addProperty("spawns_meteorites", spaceBody.spawnsMeteorites);
            object.add("light_provider", context.serialize(spaceBody.lightProvider));
            object.add("satellites", context.serialize(spaceBody.satellites));

            return object;
        }
    }

    public static class Satellite {
        private final Identifier texture;
        private final Identifier prefixedTexture;
        private final float scale;
        private final Vector2i phases;
        private final Orbit orbit;

        public Satellite(Identifier texture, float scale, Vector2i phases, Orbit orbit) {
            this.texture = texture;
            this.prefixedTexture = texture.withPrefixedPath("textures/");
            this.scale = scale;
            this.phases = phases;
            this.orbit = orbit;
        }

        public Identifier getPrefixedTexture() {
            return prefixedTexture;
        }

        public Identifier getTexture() {
            return texture;
        }

        public float getScale() {
            return scale;
        }
         public Vector2i getPhases() {
            return phases;
         }

         public Orbit getOrbit() {
             return orbit;
         }

        private static class Serializer implements JsonDeserializer<Satellite>, JsonSerializer<Satellite> {
            @Override
            public Satellite deserialize(
                    JsonElement jsonElement,
                    Type type,
                    JsonDeserializationContext context
            ) throws JsonParseException {
                JsonObject object = jsonElement.getAsJsonObject();
                GsonHelper helper = new GsonHelper(object);
                Identifier texture = context.deserialize(object.get("texture"), GsonHelper.getType(Identifier.class));
                float scale = helper.getOptionalFloat("scale").orElse(1.0f);
                Vector2i phases = helper.getDefaultVector2I("phases", 1, 1);

                Orbit orbit = helper.getOptionalDeserialization(context, Orbit.class, "orbit")
                        .orElse(new Orbit(false, 1.0f, new Vector3f(0.0f, 0.0f, 0.0f)));

                return new Satellite(texture, scale, phases, orbit);
            }

            @Override
            public JsonElement serialize(Satellite satellite, Type type, JsonSerializationContext context) {
                JsonObject object = new JsonObject();

                object.addProperty("texture", satellite.texture.toString());
                object.addProperty("scale", satellite.scale);
                object.add("phases", context.serialize(satellite.phases));
                object.add("orbit", context.serialize(satellite.orbit));

                return object;
            }
        }
    }

    // TODO: Complete speed
    // Speed affects:
    // [x] Satellite position
    // [ ] Time of day
    public record Orbit(boolean fixed, double speed, Vector3f offset) {
        public double getTimeMultiplier() {
            return fixed ? 0.0 : speed;
        }

        private static class Serializer implements JsonDeserializer<Orbit>, JsonSerializer<Orbit> {
            @Override
            public Orbit deserialize(
                    JsonElement jsonElement,
                    Type type,
                    JsonDeserializationContext context
            ) throws JsonParseException {
                JsonObject object = jsonElement.getAsJsonObject();
                GsonHelper helper = new GsonHelper(object);

                boolean fixed = helper.getOptionalBoolean("fixed").orElse(false);
                double speed = helper.getOptionalDouble("speed").orElse(1.0d);
                Vector3f offset = helper.getDefaultVector3F("offset", 0.0f, 0.0f, 0.0f);

                return new Orbit(fixed, speed, offset);
            }

            @Override
            public JsonElement serialize(Orbit orbit, Type type, JsonSerializationContext context) {
                JsonObject object = new JsonObject();
                object.addProperty("fixed", orbit.fixed);
                object.addProperty("speed", orbit.speed);
                object.add("offset", context.serialize(orbit.offset));
                return object;
            }
        }
    }

    public record StarSettings(
            boolean display,
            double startTime,
            double endTime,
            long seed,
            int amount
    ) {
        private static final double DEFAULT_START_TIME = 0.0d;
        private static final double DEFAULT_END_TIME = 1.0d;
        private static final long DEFAULT_STAR_SEED = 10_842;
        private static final int DEFAULT_STAR_AMOUNT = 1_500;

        public boolean alwaysDisplay() {
            return this.display && this.startTime == 0.0d && this.endTime == 1.0d;
        }

        public static StarSettings getDefault() {
            return new StarSettings(
                    false,
                    DEFAULT_START_TIME,
                    DEFAULT_END_TIME,
                    DEFAULT_STAR_SEED,
                    DEFAULT_STAR_AMOUNT
            );
        }

        private static class Serializer implements JsonDeserializer<StarSettings>, JsonSerializer<StarSettings> {
            @Override
            public StarSettings deserialize(
                    JsonElement jsonElement,
                    Type type,
                    JsonDeserializationContext context
            ) throws JsonParseException {
                JsonObject object = jsonElement.getAsJsonObject();
                GsonHelper helper = new GsonHelper(object);
                boolean display = helper.getOptionalBoolean("display").orElse(false);

                Optional<GsonHelper> time = helper.getOptionalHelper("time");

                // Defaults to show all day
                double startTime = time.map(timeHelper -> timeHelper
                        .getOptionalDouble("start")
                        .orElse(DEFAULT_START_TIME)
                ).orElse(DEFAULT_START_TIME);
                double endTime = time.map(timeHelper -> timeHelper
                        .getOptionalDouble("end")
                        .orElse(DEFAULT_END_TIME)
                ).orElse(DEFAULT_END_TIME);

                long seed = helper.getOptionalLong("seed").orElse(DEFAULT_STAR_SEED);
                int amount = helper.getOptionalInt("amount").orElse(DEFAULT_STAR_AMOUNT);
                return new StarSettings(display, startTime, endTime, seed, amount);
            }

            @Override
            public JsonElement serialize(StarSettings starSettings, Type type, JsonSerializationContext context) {
                JsonObject object = new JsonObject();
                object.addProperty("display", starSettings.display);
                object.addProperty("amount", starSettings.amount);

                JsonObject time = new JsonObject();
                time.addProperty("start", starSettings.startTime);
                time.addProperty("end", starSettings.endTime);
                object.add("time", time);

                return object;
            }
        }
    }
}
