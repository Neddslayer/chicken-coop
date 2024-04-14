package dev.neddslayer.chickencoop.forge;

import dev.architectury.platform.forge.EventBuses;
import dev.neddslayer.chickencoop.ChickenCoop;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ChickenCoop.MOD_ID)
@SuppressWarnings("unused")
public class ForgeCommonInitializer {
    public ForgeCommonInitializer() {
        EventBuses.registerModEventBus(ChickenCoop.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        ChickenCoop.register();
    }
}
