package dev.neddslayer.chickencoop.fabric;

import dev.neddslayer.chickencoop.ChickenCoop;
import net.fabricmc.api.ModInitializer;

public class FabricCommonInitializer implements ModInitializer {
	@Override
	public void onInitialize() {
		ChickenCoop.register();
	}
}
