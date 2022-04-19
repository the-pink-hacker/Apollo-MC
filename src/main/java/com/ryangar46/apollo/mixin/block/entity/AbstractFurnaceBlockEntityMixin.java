package com.ryangar46.apollo.mixin.block.entity;

import com.ryangar46.apollo.item.ItemManager;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.Map;

@Mixin(AbstractFurnaceBlockEntity.class)
public abstract class AbstractFurnaceBlockEntityMixin {
    @Shadow
    private static void addFuel(Map<Item, Integer> fuelTimes, ItemConvertible item, int fuelTime) {}

    @ModifyVariable(
            method = "Lnet/minecraft/block/entity/AbstractFurnaceBlockEntity;createFuelTimeMap()Ljava/util/Map;",
            at = @At("STORE"),
            ordinal = 0
    )
    private static Map<Item, Integer> fuel(Map<Item, Integer> map) {
        addFuel(map, ItemManager.FUEL_BUCKET, 20000);
        return map;
    }
}
