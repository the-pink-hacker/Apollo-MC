package com.thepinkhacker.apollo.resource;

import com.google.common.reflect.TypeToken;

import java.lang.reflect.Type;

public abstract class GsonHelper {
    public static Type getType(Class<?> classOf) {
        return TypeToken.of(classOf).getType();
    }
}
