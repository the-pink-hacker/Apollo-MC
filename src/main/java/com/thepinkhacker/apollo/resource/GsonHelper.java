package com.thepinkhacker.apollo.resource;

import com.google.common.reflect.TypeToken;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.joml.Vector2f;
import org.joml.Vector2i;

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

    public Optional<Integer> getOptionalInt(String key) {
        return getOptional(key).map(JsonElement::getAsInt);
    }

    public Optional<Float> getOptionalFloat(String key) {
        return getOptional(key).map(JsonElement::getAsFloat);
    }

    public Optional<Double> getOptionalDouble(String key) {
        return getOptional(key).map(JsonElement::getAsDouble);
    }

    public Optional<JsonObject> getOptionalObject(String key) {
        return getOptional(key).map(JsonElement::getAsJsonObject);
    }

    public Optional<GsonHelper> getOptionalHelper(String key) {
        return getOptionalObject(key).map(GsonHelper::new);
    }

    public Vector2i getDefaultVector2I(String key, int x, int y) {
        return getOptionalHelper(key).map(vectorHelper -> new Vector2i(
                vectorHelper.getOptionalInt("x").orElse(x),
                vectorHelper.getOptionalInt("y").orElse(y)
        )).orElse(new Vector2i(x, y));
    }

    public Vector2f getDefaultVector2F(String key, float x, float y) {
        return getOptionalHelper(key).map(vectorHelper -> new Vector2f(
                vectorHelper.getOptionalFloat("x").orElse(x),
                vectorHelper.getOptionalFloat("y").orElse(y)
        )).orElse(new Vector2f(x, y));
    }

    public JsonObject getObject() {
        return object;
    }
}
