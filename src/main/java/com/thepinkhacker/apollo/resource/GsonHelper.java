package com.thepinkhacker.apollo.resource;

import com.google.common.reflect.TypeToken;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.lang.reflect.Type;
import java.util.Optional;

public class GsonHelper {
    private final JsonObject object;

    public GsonHelper(JsonObject object) {
        this.object = object;
    }

    public static Type getType(Class<?> classOf) {
        return TypeToken.of(classOf).getType();
    }

    public Optional<JsonElement> getOptional(String key) {
        return Optional.ofNullable(object.get(key));
    }


    public Optional<Boolean> getOptionalBoolean(String key) {
        return getOptional(key).map(JsonElement::getAsBoolean);
    }

    public Optional<Float> getOptionalFloat(String key) {
        return getOptional(key).map(JsonElement::getAsFloat);
    }

    public Optional<Double> getOptionalDouble(String key) {
        return getOptional(key).map(JsonElement::getAsDouble);
    }

    public JsonObject getObject() {
        return object;
    }
}
