package com.sarinsa.inundated.common.core.registry;

import com.mojang.serialization.Codec;
import com.sarinsa.inundated.common.core.Inundated;
import com.sarinsa.inundated.common.worldgen.chunkgen.InundatedChunkGenerator;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class InunChunkGens {

    public static final DeferredRegister<Codec<? extends ChunkGenerator>> CHUNK_GENS = DeferredRegister.create(Registries.CHUNK_GENERATOR, Inundated.MODID);


    public static final RegistryObject<Codec<InundatedChunkGenerator>> INUNDATED_GENERATOR = CHUNK_GENS.register("inundated", () -> InundatedChunkGenerator.CODEC);
}
