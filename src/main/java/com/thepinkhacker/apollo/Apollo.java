package com.thepinkhacker.apollo;

import com.thepinkhacker.apollo.block.entity.ApolloBlockEntityTypes;
import com.thepinkhacker.apollo.entity.ApolloEntityTypes;
import com.thepinkhacker.apollo.item.ApolloItemGroups;
import com.thepinkhacker.apollo.item.ApolloItems;
import com.thepinkhacker.apollo.recipe.ApolloRecipeTypes;
import com.thepinkhacker.apollo.resource.ApolloResources;
import com.thepinkhacker.apollo.server.ApolloServerEvents;
import com.thepinkhacker.apollo.server.command.ApolloCommands;
import com.thepinkhacker.apollo.sound.ApolloSoundEvents;
import com.thepinkhacker.apollo.stat.ApolloStats;
import com.thepinkhacker.apollo.world.ApolloGameRules;
import com.thepinkhacker.apollo.world.biome.ApolloBiomeKeys;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Apollo implements ModInitializer {
	public static final String MOD_ID = "apollo";
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ApolloBiomeKeys.register();
		ApolloGameRules.register();
		ApolloItemGroups.register();
		ApolloItems.register();
		ApolloEntityTypes.register();
		ApolloRecipeTypes.register();
		ApolloStats.register();
		ApolloBlockEntityTypes.register();
		ApolloResources.register();
		ApolloSoundEvents.register();
		ApolloServerEvents.register();
		ApolloCommands.register();
	}

	public static Identifier getIdentifier(String path) {
		return new Identifier(MOD_ID, path);
	}
}
