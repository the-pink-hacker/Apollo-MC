package com.ryangar46.apollo.data;

import com.ryangar46.apollo.Apollo;
import com.ryangar46.apollo.block.ApolloBlocks;
import com.ryangar46.apollo.entity.ApolloEntityTypes;
import com.ryangar46.apollo.item.ApolloItemGroups;
import com.ryangar46.apollo.item.ApolloItems;
import com.ryangar46.apollo.stat.ApolloStats;
import com.ryangar46.apollo.world.ApolloGameRules;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.fabricmc.fabric.api.gamerule.v1.CustomGameRuleCategory;
import net.minecraft.util.Identifier;
import net.minecraft.world.GameRules;

public class LanguageProvider extends FabricLanguageProvider {
    protected LanguageProvider(FabricDataOutput dataOutput) {
        super(dataOutput, "en_us");
    }

    @Override
    public void generateTranslations(TranslationBuilder builder) {
        /* === Blocks === */
        builder.add(ApolloBlocks.AIRLOCK_FRAME, "Airlock Frame");
        builder.add(ApolloBlocks.FUEL, "Fuel");
        builder.add(ApolloBlocks.FLUID_PIPE, "Fluid Pipe");
        builder.add(ApolloBlocks.LAUNCHPAD, "Launchpad");
        builder.add(ApolloBlocks.LUNAR_COBBLESTONE, "Lunar Cobblestone");
        builder.add(ApolloBlocks.LUNAR_DUST, "Lunar Dust");
        builder.add(ApolloBlocks.LUNAR_IRON_ORE, "Lunar Iron Ore");
        builder.add(ApolloBlocks.LUNAR_SOIL, "Lunar Soil");
        builder.add(ApolloBlocks.LUNAR_STONE, "Lunar Stone");
        builder.add(ApolloBlocks.METEORITE, "Meteorite");
        builder.add(ApolloBlocks.OIL, "Oil");
        builder.add(ApolloBlocks.OIL_REFINERY, "Oil Refinery");
        builder.add(ApolloBlocks.REINFORCED_IRON_BLOCK, "Reinforced Iron Block");
        builder.add(ApolloBlocks.SHUTTLE_WORKBENCH, "Shuttle Workbench");
        builder.add(ApolloBlocks.STORAGE_TANK, "Storage Tank");

        /* === Containers === */
        addContainer(builder,"shuttle_workbench", "Shuttle Workbench");

        /* === Entities === */
        builder.add(ApolloEntityTypes.METEORITE, "Meteorite");
        builder.add(ApolloEntityTypes.SHUTTLE, "Shuttle");

        /* === Gamerules === */
        addGamerule(builder,
                ApolloGameRules.DO_METEORITE_IMPACTS,
                "Do Meteorite Explosions",
                "Controls meteorites impact explosion"
        );
        addGamerule(builder,
                ApolloGameRules.DO_METEORITE_LANDINGS,
                "Do Meteorite Landings",
                "Controls meteorites landing on certain planets"
        );
        addGamerule(builder,
                ApolloGameRules.SHUTTLE_ESCAPE_HEIGHT,
                "Shuttle Escape Height",
                "At what y level does a shuttle leave the current world"
        );
        addGamerule(builder,
                ApolloGameRules.SUFFOCATE_IN_VACUUM,
                "Suffocate in vacuum",
                "Whether mobs and players suffocate in a vacuum"
        );

        /* === Gamerule Categories === */
        addGameruleCategory(builder, ApolloGameRules.APOLLO, "Apollo");

        /* === Items === */
        builder.add(ApolloItems.FUEL_BUCKET, "Fuel Bucket");
        builder.add(ApolloItems.METEORITE_SCRAP, "Meteorite Scrap");
        builder.add(ApolloItems.NEGATIVE_GRAVITY_BOOTS, "Negative Gravity Boots");
        builder.add(ApolloItems.OIL_BUCKET, "Oil Bucket");
        builder.add(ApolloItems.POSITIVE_GRAVITY_BOOTS, "Positive Gravity Boots");
        builder.add(ApolloItems.REINFORCED_IRON_INGOT, "Reinforced Iron Ingot");
        builder.add(ApolloItems.SPACE_SUIT_BOOTS, "Space Suit Boots");
        builder.add(ApolloItems.SPACE_SUIT_CHESTPLATE, "Space Suit Chestplate");
        builder.add(ApolloItems.SPACE_SUIT_HELMET, "Space Suit Helmet");
        builder.add(ApolloItems.SPACE_SUIT_LEGGINGS, "Space Suit Leggings");

        /* === Item Groups === */
        builder.add(ApolloItemGroups.APOLLO, "Apollo");
        builder.add(ApolloItemGroups.MOON, "Moon");

        /* === Statistics === */
        addStatistic(builder, ApolloStats.INTERACT_WITH_SHUTTLE_WORKBENCH, "Interactions with Shuttle Workbench");
    }

    private static void addContainer(TranslationBuilder builder, String container, String value) {
        builder.add("container." + Apollo.MOD_ID + "." + container, value);
    }

    private static void addGamerule(TranslationBuilder builder, GameRules.Key<?> gamerule, String title, String description) {
        addGamerule(builder, gamerule, title);
        builder.add(gamerule.getTranslationKey() + ".description", description);
    }

    private static void addGamerule(TranslationBuilder builder, GameRules.Key<?> gamerule, String title) {
        builder.add(gamerule.getTranslationKey(), title);
    }

    private static void addGameruleCategory(TranslationBuilder builder, CustomGameRuleCategory gameruleCategory, String value) {
        builder.add(gameruleCategory.getName().getString(), value);
    }

    private static void addStatistic(TranslationBuilder builder, Identifier statistic, String value) {
        builder.add("stat." + statistic.getNamespace() + "." + statistic.getPath(), value);
    }
}
