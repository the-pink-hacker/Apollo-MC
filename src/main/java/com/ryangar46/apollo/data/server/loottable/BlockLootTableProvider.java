package com.ryangar46.apollo.data.server.loottable;

import com.ryangar46.apollo.item.ApolloItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.MatchToolLootCondition;
import net.minecraft.loot.condition.SurvivesExplosionLootCondition;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.entry.AlternativeEntry;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.ExplosionDecayLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.item.EnchantmentPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.util.Identifier;

import java.util.function.BiConsumer;

public class BlockLootTableProvider extends SimpleFabricLootTableProvider implements LootTableHelper {
    private static final LootCondition.Builder WITH_SILK_TOUCH = MatchToolLootCondition.builder(ItemPredicate.Builder.create().enchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, NumberRange.IntRange.atLeast(1))));

    public BlockLootTableProvider(FabricDataOutput output) {
        super(output, LootContextTypes.BLOCK);
    }

    @Override
    public void accept(BiConsumer<Identifier, LootTable.Builder> biConsumer) {
        /* === Basic Blocks === */
        addBasicBlock(biConsumer, ApolloItems.LAUNCHPAD);
        addBasicBlock(biConsumer, ApolloItems.LUNAR_COBBLESTONE);
        addBasicBlock(biConsumer, ApolloItems.LUNAR_DUST);
        addBasicBlock(biConsumer, ApolloItems.LUNAR_SOIL);
        addBasicBlock(biConsumer, ApolloItems.METEORITE);
        addBasicBlock(biConsumer, ApolloItems.OIL_REFINERY);
        addBasicBlock(biConsumer, ApolloItems.REINFORCED_IRON_BLOCK);
        addBasicBlock(biConsumer, ApolloItems.SHUTTLE_WORKBENCH);

        /* === Ores === */
        addOreBlock(biConsumer, ApolloItems.LUNAR_IRON_ORE, Items.RAW_IRON);
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

    private void addOreBlock(BiConsumer<Identifier, LootTable.Builder> biConsumer, Item ore, Item rawOre) {
        addLoot(
                biConsumer,
                ore,
                LootTable.builder().pool(
                        LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1.0f))
                                .with(AlternativeEntry.builder()
                                        .conditionally(WITH_SILK_TOUCH)
                                        .alternatively(ItemEntry.builder(ore))
                                )
                                .with(ItemEntry.builder(rawOre)
                                        .apply(ApplyBonusLootFunction.oreDrops(Enchantments.FORTUNE))
                                        .apply(ExplosionDecayLootFunction.builder())
                                )
                )
        );
    }

    @Override
    public String getRoot() {
        return "blocks";
    }
}
