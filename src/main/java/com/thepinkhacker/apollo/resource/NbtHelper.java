package com.thepinkhacker.apollo.resource;

import net.minecraft.nbt.NbtByte;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;

import java.util.Optional;

public record NbtHelper(NbtCompound nbt) {
    public Optional<NbtElement> getOptionalElement(String key, int type) {
        if (!nbt.contains(key, type)) return Optional.empty();
        return Optional.ofNullable(nbt.get(key));
    }

    public Optional<String> getOptionalString(String key) {
        return getOptionalElement(key, NbtElement.STRING_TYPE).map(NbtElement::asString);
    }

    public Optional<Byte> getOptionalByte(String key) {
        return getOptionalElement(key, NbtElement.STRING_TYPE).map(nbt -> {
            if (nbt instanceof NbtByte nbtByte) {
                return nbtByte.byteValue();
            }
            return null;
        });
    }

    public Optional<Boolean> getOptionalBoolean(String key) {
        return getOptionalByte(key).map(value -> value == 1);
    }
}
