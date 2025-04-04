package com.sarinsa.inundated.common.core;

import com.sarinsa.inundated.common.core.registry.InunChunkGens;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.chunk.ChunkGenerators;
import net.minecraft.world.level.levelgen.presets.WorldPresets;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Inundated.MODID)
public class Inundated {

    /** The mod's ID **/
    public static final String MODID = "inundated";

    /** The mod's display name */
    public static final String MOD_NAME = "Inundated";

    /** A logger instance using the modid as prefix/identifier **/
    public static final Logger LOGGER = LogManager.getLogger(MODID);



    public Inundated() {
        MinecraftForge.EVENT_BUS.register(new GameEventListener());

        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();

        InunChunkGens.CHUNK_GENS.register(modBus);
    }


    public void onCommonSetup(FMLCommonSetupEvent event) {
    }


    public static ResourceLocation resLoc(String path) {
        return new ResourceLocation(MODID, path);
    }
}
