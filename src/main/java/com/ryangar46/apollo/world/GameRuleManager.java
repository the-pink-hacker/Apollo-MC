package com.ryangar46.apollo.world;

import com.ryangar46.apollo.Apollo;
import net.fabricmc.fabric.api.gamerule.v1.CustomGameRuleCategory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.world.GameRules;

public class GameRuleManager {
    public static final CustomGameRuleCategory APOLLO = registerCategory("apollo");
    public static final GameRules.Key<GameRules.BooleanRule> DO_METEORITE_EXPLOSIONS = GameRuleRegistry.register("doMeteoriteExplosions", APOLLO, GameRuleFactory.createBooleanRule(true));
    public static final GameRules.Key<GameRules.BooleanRule> SUFFOCATE_IN_VACUUM = GameRuleRegistry.register("suffocateInVacuum", APOLLO, GameRuleFactory.createBooleanRule(true));

    public static void register() {}

    public static CustomGameRuleCategory registerCategory(String id) {
        return new CustomGameRuleCategory(new Identifier(Apollo.MOD_ID, id), new TranslatableText("gamerule.category." + id).formatted(Formatting.YELLOW, Formatting.BOLD));
    }
}
