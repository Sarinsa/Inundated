package com.sarinsa.inundated.common.worldgen.chunkgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.material.Fluids;

public class InundatedChunkGenerator extends NoiseBasedChunkGenerator {


    public static final Codec<InundatedChunkGenerator> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(BiomeSource.CODEC.fieldOf("biome_source").forGetter((chunkGen) -> {
            return chunkGen.biomeSource;
        }), NoiseGeneratorSettings.CODEC.fieldOf("settings").forGetter((chunkGen) -> {
            return chunkGen.settings;
        })).apply(instance, instance.stable(InundatedChunkGenerator::new));
    });



    public InundatedChunkGenerator(BiomeSource biomeSource, Holder<NoiseGeneratorSettings> settingsHolder) {
        super(biomeSource, settingsHolder);
    }


    @Override
    public void applyBiomeDecoration(WorldGenLevel level, ChunkAccess chunkAccess, StructureManager structureManager) {
        ChunkPos pos = chunkAccess.getPos();
        final int minX = pos.getMinBlockX();
        final int minY = getMinY();
        final int minZ = pos.getMinBlockZ();

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                waterifyColumn(level, chunkAccess, pos, minX, minY, minZ, x, z);
            }
        }

        super.applyBiomeDecoration(level, chunkAccess, structureManager);
    }

    private void waterifyColumn(WorldGenLevel level, ChunkAccess chunkAccess, ChunkPos pos, int minX, int minY, int minZ, int xOffset, int zOffset) {
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos().setWithOffset(new BlockPos(minX, minY, minZ), xOffset, getMinY(), zOffset);

        for (int y = getMinY(); y < getSeaLevel(); y++) {
            mutablePos.setY(y);
            BlockState state = chunkAccess.getBlockState(mutablePos);

            if (!state.getFluidState().isEmpty()) continue;

            if (state.hasProperty(BlockStateProperties.WATERLOGGED)) {
                state = state.trySetValue(BlockStateProperties.WATERLOGGED, true);
                chunkAccess.setBlockState(mutablePos, state, false);
            }
            else if (state.isAir()) {
                chunkAccess.setBlockState(mutablePos, Blocks.WATER.defaultBlockState(), false);
            }
        }
    }

    @Override
    public int getSeaLevel() {
        return 110;
    }
}
