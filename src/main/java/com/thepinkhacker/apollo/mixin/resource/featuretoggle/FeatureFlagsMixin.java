package com.thepinkhacker.apollo.mixin.resource.featuretoggle;

import com.thepinkhacker.apollo.resource.featuretoggle.ApolloFeatureFlags;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.resource.featuretoggle.FeatureManager;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Debug(export = true)
@Mixin(FeatureFlags.class)
public abstract class FeatureFlagsMixin {
    @ModifyVariable(
            method = "<clinit>",
            at = @At("STORE"),
            ordinal = 0
    )
    private static FeatureManager.Builder builderInit(FeatureManager.Builder builder) {
        ApolloFeatureFlags.register(builder);
        return builder;
    }
}
