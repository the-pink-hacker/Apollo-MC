package com.ryangar46.apollo.data.server.loottable;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.loot.LootTable;
import net.minecraft.util.Identifier;

import java.util.function.BiConsumer;

public interface LootTableHelper {
    String getRoot();
    default void addLoot(BiConsumer<Identifier, LootTable.Builder> biConsumer, Block block, LootTable.Builder builder) {
        Identifier lootTableIdentifier = block.getRegistryEntry().registryKey().getValue();
        lootTableIdentifier = new Identifier(lootTableIdentifier.getNamespace(), this.getRoot() + "/" + lootTableIdentifier.getPath());
        biConsumer.accept(lootTableIdentifier, builder);
    }

    default void addLoot(BiConsumer<Identifier, LootTable.Builder> biConsumer, Item item, LootTable.Builder builder) {
        Identifier lootTableIdentifier = item.getRegistryEntry().registryKey().getValue();
        lootTableIdentifier = new Identifier(lootTableIdentifier.getNamespace(), this.getRoot() + "/" + lootTableIdentifier.getPath());
        biConsumer.accept(lootTableIdentifier, builder);
    }

    default void addLoot(BiConsumer<Identifier, LootTable.Builder> biConsumer, ItemConvertible item, LootTable.Builder builder) {
        Identifier lootTableIdentifier = item.asItem().getRegistryEntry().registryKey().getValue();
        lootTableIdentifier = new Identifier(lootTableIdentifier.getNamespace(), this.getRoot() + "/" + lootTableIdentifier.getPath());
        biConsumer.accept(lootTableIdentifier, builder);
    }
}
