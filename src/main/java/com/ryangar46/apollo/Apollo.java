package com.ryangar46.apollo;

import com.ryangar46.apollo.block.BlockManager;
import com.ryangar46.apollo.entity.EntityManager;
import com.ryangar46.apollo.fluid.FluidManager;
import com.ryangar46.apollo.item.ItemManager;
import com.ryangar46.apollo.recipe.RecipeManager;
import com.ryangar46.apollo.world.GameRuleManager;
import com.ryangar46.apollo.world.biome.ApolloOverworldRegion;
import com.ryangar46.apollo.world.biome.BiomeManager;
import com.ryangar46.apollo.world.surfacerule.ApolloSurfaceRules;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.GeckoLib;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;
import terrablender.api.TerraBlenderApi;

public class Apollo implements ModInitializer, TerraBlenderApi {
	public static final String MOD_ID = "apollo";
	public static final Logger LOGGER = LogManager.getLogger("apollo");

	@Override
	public void onInitialize() {
		GeckoLib.initialize();
		GameRuleManager.register();
		ItemManager.register();
		BlockManager.register();
		FluidManager.register();
		EntityManager.register();
		BiomeManager.register();
		RecipeManager.register();
	}

	@Override
	public void onTerraBlenderInitialized() {
		Regions.register(new ApolloOverworldRegion(new Identifier(MOD_ID, "overworld"), 2));

		SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, MOD_ID, ApolloSurfaceRules.makeRules());
	}
}
