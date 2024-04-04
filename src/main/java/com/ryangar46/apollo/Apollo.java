package com.ryangar46.apollo;

import com.ryangar46.apollo.block.BlockManager;
import com.ryangar46.apollo.item.ItemGroupManager;
import com.ryangar46.apollo.item.ItemManager;
import com.ryangar46.apollo.world.biome.BiomeManager;
import com.ryangar46.apollo.world.dimension.DimensionManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Apollo implements ModInitializer {
	public static final String MOD_ID = "apollo";
	public static final Logger LOGGER = LogManager.getLogger("apollo");

	@Override
	public void onInitialize() {
		ItemGroupManager.register();
		ItemManager.register();
		BlockManager.register();

		BiomeManager.register();
		DimensionManager.register();
	}
}
