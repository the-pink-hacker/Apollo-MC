package com.thepinkhacker.apollo;

import com.thepinkhacker.apollo.block.entity.ApolloBlockEntityTypes;
import com.thepinkhacker.apollo.entity.ApolloEntityTypes;
import com.thepinkhacker.apollo.item.ApolloItemGroups;
import com.thepinkhacker.apollo.item.ApolloItems;
import com.thepinkhacker.apollo.recipe.RecipeManager;
import com.thepinkhacker.apollo.stat.ApolloStats;
import com.thepinkhacker.apollo.world.ApolloGameRules;
import com.thepinkhacker.apollo.world.biome.ApolloBiomeKeys;
import com.thepinkhacker.apollo.world.spawner.GenericSpawnerManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib.GeckoLib;
import terrablender.api.TerraBlenderApi;

public class Apollo implements ModInitializer, TerraBlenderApi {
	public static final String MOD_ID = "apollo";
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		GeckoLib.initialize();
		ApolloBiomeKeys.register();
		ApolloGameRules.register();
		ApolloItemGroups.register();
		ApolloItems.register();
		ApolloEntityTypes.register();
		RecipeManager.register();
		ApolloStats.register();
		ApolloBlockEntityTypes.register();

		ServerLifecycleEvents.SERVER_STARTED.register(server -> GenericSpawnerManager.register());
	}
}