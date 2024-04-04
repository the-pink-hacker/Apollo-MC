package com.ryangar46.apollo.item;

import com.ryangar46.apollo.Apollo;
import com.ryangar46.apollo.block.BlockManager;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class ItemGroupManager {
    public static final ItemGroup APOLLO = FabricItemGroupBuilder.create(new Identifier(Apollo.MOD_ID, "apollo"))
            .icon(() -> new ItemStack(ItemManager.SPACE_SUIT_HELMET))
            .build();

    public static final ItemGroup MOON = FabricItemGroupBuilder.create(new Identifier(Apollo.MOD_ID, "moon"))
            .icon(() -> new ItemStack(BlockManager.LUNAR_SOIL))
            .build();
}
