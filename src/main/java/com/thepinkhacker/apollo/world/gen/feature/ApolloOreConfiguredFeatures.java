package com.thepinkhacker.apollo.world.gen.feature;

import com.thepinkhacker.apollo.block.ApolloBlocks;
import com.thepinkhacker.apollo.registry.tag.ApolloBlockTags;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.structure.rule.TagMatchRuleTest;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;

public class ApolloOreConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?, ?>> LUNAR_ORE_IRON = ApolloConfiguredFeatures.of("lunar_ore_iron");
    public static final RegistryKey<ConfiguredFeature<?, ?>> ORE_CHEESE = ApolloConfiguredFeatures.of("ore_cheese");

    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> registerable) {
        RuleTest lunar_ore_rule = new TagMatchRuleTest(ApolloBlockTags.LUNAR_ORE_REPLACEABLES);

        ConfiguredFeatures.register(
                registerable,
                ORE_CHEESE,
                Feature.ORE,
                new OreFeatureConfig(
                        lunar_ore_rule,
                        ApolloBlocks.CHEESE_ORE.getDefaultState(),
                        17
                )
        );

        ConfiguredFeatures.register(
                registerable,
                LUNAR_ORE_IRON,
                Feature.ORE,
                new OreFeatureConfig(
                        lunar_ore_rule,
                        ApolloBlocks.LUNAR_IRON_ORE.getDefaultState(),
                        17
                )
        );
    }
}
