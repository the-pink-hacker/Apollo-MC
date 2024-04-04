package com.ryangar46.apollo;

import com.ryangar46.apollo.block.ApolloBlocks;
import com.ryangar46.apollo.entity.EntityManager;
import com.ryangar46.apollo.item.ItemManager;
import com.ryangar46.apollo.recipe.RecipeManager;
import com.ryangar46.apollo.stat.StatManager;
import com.ryangar46.apollo.world.GameRuleManager;
import com.ryangar46.apollo.world.biome.ApolloOverworldRegion;
import com.ryangar46.apollo.world.spawner.GenericSpawnerManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib.GeckoLib;
import terrablender.api.Regions;
import terrablender.api.TerraBlenderApi;

public class Apollo implements ModInitializer, TerraBlenderApi {
	public static final String MOD_ID = "apollo";
	public static final Logger LOGGER = LogManager.getLogger("apollo");

	@Override
	public void onInitialize() {
		GeckoLib.initialize();
		GameRuleManager.register();
		ItemManager.register();
		ApolloBlocks.register();
		EntityManager.register();
		//BiomeManager.register();
		RecipeManager.register();
		StatManager.register();
		//DimensionManager.register();
		//ApolloBuiltinNoiseParameters.registry();

		ServerLifecycleEvents.SERVER_STARTED.register(server -> GenericSpawnerManager.register());
	}

	@Override
	public void onTerraBlenderInitialized() {
		Regions.register(new ApolloOverworldRegion(new Identifier(MOD_ID, "overworld"), 2));

		//SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, MOD_ID, ApolloSurfaceRules.makeRules());
	}
}
