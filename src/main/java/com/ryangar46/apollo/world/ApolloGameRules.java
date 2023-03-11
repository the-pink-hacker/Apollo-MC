package com.ryangar46.apollo.world;

import com.ryangar46.apollo.Apollo;
import net.fabricmc.fabric.api.gamerule.v1.CustomGameRuleCategory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.world.GameRules;

public class ApolloGameRules {
    public static final CustomGameRuleCategory APOLLO = registerCategory("apollo");
    public static final GameRules.Key<GameRules.BooleanRule> DO_METEORITE_IMPACTS = GameRuleRegistry.register("doMeteoriteImpacts", APOLLO, GameRuleFactory.createBooleanRule(true));
    public static final GameRules.Key<GameRules.BooleanRule> DO_METEORITE_LANDINGS = GameRuleRegistry.register("doMeteoriteLandings", APOLLO, GameRuleFactory.createBooleanRule(true));
    public static final GameRules.Key<GameRules.IntRule> SHUTTLE_ESCAPE_HEIGHT = GameRuleRegistry.register("shuttleEscapeHeight", APOLLO, GameRuleFactory.createIntRule(128));
    public static final GameRules.Key<GameRules.BooleanRule> SUFFOCATE_IN_VACUUM = GameRuleRegistry.register("suffocateInVacuum", APOLLO, GameRuleFactory.createBooleanRule(true));

    // Java is weird and won't init the variables unless this runs
    // Otherwise it would try to init after the registry is frozen
    public static void register() {}

    public static CustomGameRuleCategory registerCategory(String id) {
        return new CustomGameRuleCategory(new Identifier(Apollo.MOD_ID, id), Text.translatable("gamerule.category." + id).formatted(Formatting.YELLOW, Formatting.BOLD));
    }
}
