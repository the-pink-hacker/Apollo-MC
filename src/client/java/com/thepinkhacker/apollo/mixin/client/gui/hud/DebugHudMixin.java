package com.thepinkhacker.apollo.mixin.client.gui.hud;

import com.thepinkhacker.apollo.fluid.FluidCarrier;
import com.thepinkhacker.apollo.fluid.FluidCarrierStorage;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.DebugHud;
import net.minecraft.fluid.Fluid;
import net.minecraft.registry.Registries;
import net.minecraft.util.Formatting;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Debug(export = true)
@Mixin(DebugHud.class)
public abstract class DebugHudMixin {

    @Shadow private HitResult blockHit;

    @Shadow @Final private MinecraftClient client;

    @Inject(
            method = "getRightText",
            at = @At("TAIL")
    )
    private void getRightText(CallbackInfoReturnable<List<String>> info) {
        if (this.client.world == null) return;

        if (this.blockHit instanceof BlockHitResult blockHitResult) {
            BlockPos lookingPos = blockHitResult.getBlockPos();

            if (this.client.world.getBlockEntity(lookingPos) instanceof FluidCarrier<?> fluidCarrier) {
                List<String> text = info.getReturnValue();
                // TODO: Display all fluid storages in carrier
                // TODO: Sync fluid carriers to client
                FluidCarrierStorage<?> fluidStorage = fluidCarrier.getMainCarrierStorage();
                Fluid fluid = fluidStorage.variant.getFluid();
                long amount = fluidStorage.getAmount();

                text.add("");
                text.add(Formatting.UNDERLINE + "Target Fluid Carrier: " + lookingPos.getX() + ", " + lookingPos.getY() + ", " + lookingPos.getZ());
                text.add("Fluid: " + Registries.FLUID.getId(fluid));
                text.add("Amount: " + amount);
            }
        }
    }
}
