package com.thepinkhacker.apollo.world.dimension;

import java.util.Optional;

/**
 * A defaulted space body will only use some properties such as gravity. This is used to improve mod compatibility.
 */
public class DefaultedSpaceBody extends SpaceBody {
    private static final DefaultedSpaceBody INSTANCE = new DefaultedSpaceBody();

    public DefaultedSpaceBody() {
        super(
                GravityManager.DEFAULT,
                true,
                true,
                false,
                null,
                null,
                null
        );
    }

    public static DefaultedSpaceBody getInstance() {
        return INSTANCE;
    }
}
