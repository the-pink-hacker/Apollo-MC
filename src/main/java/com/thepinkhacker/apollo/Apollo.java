package com.thepinkhacker.apollo;

import com.thepinkhacker.apollo.block.entity.ApolloBlockEntityTypes;
import com.thepinkhacker.apollo.entity.ApolloEntityTypes;
import com.thepinkhacker.apollo.item.ApolloItemGroups;
import com.thepinkhacker.apollo.item.ApolloItems;
import com.thepinkhacker.apollo.recipe.ApolloRecipeTypes;
import com.thepinkhacker.apollo.resource.ApolloResources;
import com.thepinkhacker.apollo.sound.ApolloSoundEvents;
import com.thepinkhacker.apollo.stat.ApolloStats;
import com.thepinkhacker.apollo.world.ApolloGameRules;
import com.thepinkhacker.apollo.world.biome.ApolloBiomeKeys;
import com.thepinkhacker.apollo.world.spawner.GenericSpawnerManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib.GeckoLib;

public class Apollo implements ModInitializer {
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
		ApolloRecipeTypes.register();
		ApolloStats.register();
		ApolloBlockEntityTypes.register();
		ApolloResources.register();
		ApolloSoundEvents.register();

		ServerLifecycleEvents.SERVER_STARTED.register(server -> GenericSpawnerManager.register());
	}

	public static Identifier getIdentifier(String path) {
		return new Identifier(MOD_ID, path);
	}
}
