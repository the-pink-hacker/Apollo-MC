package com.thepinkhacker.apollo.world.dimension;

import net.minecraft.util.Identifier;

import java.util.Optional;

/**
 * A defaulted space body will only use some properties such as gravity. This is used to improve mod compatibility.
 */
public class DefaultedSpaceBody extends SpaceBody {
    private static final DefaultedSpaceBody DEFAULT = new DefaultedSpaceBody();

    public DefaultedSpaceBody() {
        super(
                GravityManager.DEFAULT,
                true,
                true,
                false,
                new Satellite[] {}
        );
    }

    public static SpaceBody elseDefault(Optional<SpaceBody> optional) {
        return optional.orElse(DEFAULT);
    }

    public static DefaultedSpaceBody getDefault() {
        return DEFAULT;
    }

    @Override
    public float skyAngle(float angle) {
        return angle;
    }

    @Override
    public Identifier getSecondaryOrbitingBody(Identifier texture) {
        return texture;
    }
}
