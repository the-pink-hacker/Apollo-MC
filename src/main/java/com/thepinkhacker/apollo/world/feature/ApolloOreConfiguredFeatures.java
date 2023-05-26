package com.thepinkhacker.apollo.world.feature;

import com.thepinkhacker.apollo.Apollo;
import com.thepinkhacker.apollo.block.ApolloBlocks;
import com.thepinkhacker.apollo.registry.tag.ApolloBlockTags;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.structure.rule.TagMatchRuleTest;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;

public class ApolloOreConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?, ?>> LUNAR_ORE_IRON = of("lunar_ore_iron");

    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> registerable) {
        RuleTest lunar_ore_rule = new TagMatchRuleTest(ApolloBlockTags.LUNAR_ORE_REPLACEABLES);
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

    private static RegistryKey<ConfiguredFeature<?, ?>> of(String id) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Apollo.getIdentifier(id));
    }
}
