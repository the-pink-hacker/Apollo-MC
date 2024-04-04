package com.ryangar46.apollo.data.server.loottable;

import com.ryangar46.apollo.item.ApolloItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.SurvivesExplosionLootCondition;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.util.Identifier;

import java.util.function.BiConsumer;

public class BlockLootTableProvider extends SimpleFabricLootTableProvider implements LootTableHelper {
    public BlockLootTableProvider(FabricDataOutput output) {
        super(output, LootContextTypes.BLOCK);
    }

    @Override
    public void accept(BiConsumer<Identifier, LootTable.Builder> biConsumer) {
        addBasicBlock(biConsumer, ApolloItems.LAUNCHPAD);
        addBasicBlock(biConsumer, ApolloItems.LUNAR_COBBLESTONE);
        addBasicBlock(biConsumer, ApolloItems.LUNAR_DUST);
        addBasicBlock(biConsumer, ApolloItems.LUNAR_SOIL);
        addBasicBlock(biConsumer, ApolloItems.METEORITE);
        addBasicBlock(biConsumer, ApolloItems.OIL_REFINERY);
        addBasicBlock(biConsumer, ApolloItems.REINFORCED_IRON_BLOCK);
        addBasicBlock(biConsumer, ApolloItems.SHUTTLE_WORKBENCH);
    }

    private void addBasicBlock(BiConsumer<Identifier, LootTable.Builder> biConsumer, Item item) {
        addLoot(
                biConsumer,
                item,
                LootTable.builder().pool(
                        LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1.0f))
                                .with(ItemEntry.builder(item))
                                .conditionally(SurvivesExplosionLootCondition.builder())
                )
        );
    }

    private void addBasicBlock(BiConsumer<Identifier, LootTable.Builder> biConsumer, Block block, Item drop) {
        addLoot(
                biConsumer,
                block,
                LootTable.builder().pool(
                        LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1.0f))
                                .with(ItemEntry.builder(drop))
                                .conditionally(SurvivesExplosionLootCondition.builder())
                )
        );
    }

    @Override
    public String getRoot() {
        return "blocks";
    }
}
