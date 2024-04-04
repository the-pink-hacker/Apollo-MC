package com.thepinkhacker.apollo.resource;

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
}
