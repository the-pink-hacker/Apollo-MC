package com.thepinkhacker.apollo.sound;

import com.thepinkhacker.apollo.Apollo;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public abstract class ApolloSoundEvents {
    public static final RegistryEntry.Reference<SoundEvent> MUSIC_MOON = eventReference("music.moon");

    public static void register() {}

    private static RegistryEntry.Reference<SoundEvent> eventReference(String id) {
        Identifier apolloId = Apollo.getIdentifier(id);
        return Registry.registerReference(Registries.SOUND_EVENT, apolloId, SoundEvent.of(apolloId));
    }
}
