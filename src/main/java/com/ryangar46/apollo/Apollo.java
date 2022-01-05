package com.ryangar46.apollo;

import com.ryangar46.apollo.world.biome.BiomeRegisterer;
import com.ryangar46.apollo.world.dimension.DimensionRegisterer;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Apollo implements ModInitializer {
	public static final String MOD_ID = "apollo";
	public static final Logger LOGGER = LogManager.getLogger("apollo");

	@Override
	public void onInitialize() {
		BiomeRegisterer.register();
		DimensionRegisterer.register();
	}
}
