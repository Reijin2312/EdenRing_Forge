package paulevs.edenring.world.generator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.RegistryOps;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate.Sampler;
import org.betterx.bclib.api.v2.generator.BiomePicker;
import org.betterx.bclib.api.v2.generator.map.hex.HexBiomeMap;
import org.betterx.bclib.interfaces.BiomeMap;
import paulevs.edenring.EdenRing;
import paulevs.edenring.world.biomes.EdenBiomeLists;
import paulevs.edenring.noise.InterpolationCell;
import paulevs.edenring.world.biomes.EdenRingBiome;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EdenBiomeSource extends BiomeSource {
	public static final Codec<EdenBiomeSource> CODEC = RecordCodecBuilder.create(
		(instance) -> instance.group(
			RegistryOps.retrieveGetter(Registries.BIOME)
		).apply(instance, instance.stable(EdenBiomeSource::new))
	);
	
	private static final int SAMPLE_DXZ = 8;
	private static final int SAMPLE_DY = 8;
	private static final int LAND_SCAN_STEP = 4;
	private static final int EDEN_MIN_Y = 0;
	private static final int EDEN_MAX_Y = 320;
	private static final int SAMPLE_WIDTH = 3;
	private static final int SAMPLE_HEIGHT = (EDEN_MAX_Y - EDEN_MIN_Y) / SAMPLE_DY + 1;
	private static final int SMOOTH_WIDTH = 4;
	private static final int SMOOTH_HEIGHT = SAMPLE_HEIGHT + 1;
	private final Map<ChunkPos, TerrainCells> terrainCache = new ConcurrentHashMap<>();
	private BiomePicker pickerLand;
	private BiomePicker pickerVoid;
	private BiomePicker pickerCave;
	private BiomeMap mapLand;
	private BiomeMap mapVoid;
	private BiomeMap mapCave;

	protected List<Holder<Biome>> biomes;
	
	public EdenBiomeSource(HolderGetter<Biome> biomeRegistry) {
		super();

		biomes = ((HolderLookup<Biome>) biomeRegistry).listElementIds()
			.filter(key -> key.location().getNamespace().equals(EdenRing.MOD_ID))
			.map(biomeRegistry::getOrThrow)
			.collect(Collectors.toList());

		if (pickerLand == null) {
			pickerLand = new BiomePicker(biomeRegistry);
			Iterator<EdenRingBiome> biomeLand = EdenBiomeLists.BIOMES_LAND.iterator();
			while (biomeLand.hasNext()) {
				pickerLand.addBiome(biomeLand.next());
			}
			pickerLand.rebuild();
			
			pickerVoid = new BiomePicker(biomeRegistry);
			Iterator<EdenRingBiome> biomeAir = EdenBiomeLists.BIOMES_AIR.iterator();
			while (biomeAir.hasNext()) {
				pickerVoid.addBiome(biomeAir.next());
			}
			pickerVoid.rebuild();
			
			pickerCave = new BiomePicker(biomeRegistry);
			Iterator<EdenRingBiome> biomeCave = EdenBiomeLists.BIOMES_CAVE.iterator();
			while (biomeCave.hasNext()) {
				pickerCave.addBiome(biomeCave.next());
			}
			pickerCave.rebuild();
		}

		mapLand = new HexBiomeMap(0, GeneratorOptions.biomeSizeLand, pickerLand);
		mapVoid = new HexBiomeMap(0, GeneratorOptions.biomeSizeVoid, pickerVoid);
		mapCave = new HexBiomeMap(0, GeneratorOptions.biomeSizeCave, pickerCave);
	}
	
	@Override
	protected Codec<? extends BiomeSource> codec() {
		return CODEC;
	}
	
	@Override
	public Holder<Biome> getNoiseBiome(int x, int y, int z, Sampler sampler) {
		cleanCache(x, z);
		
		int px = (x << 2) | 2;
		int py = (y << 2) | 2;
		int pz = (z << 2) | 2;
		
		ChunkPos chunkPos = new ChunkPos(px >> 4, pz >> 4);
		TerrainCells cells = terrainCache.computeIfAbsent(chunkPos, this::createTerrainCells);
		MutableBlockPos pos = new MutableBlockPos(px, 0, pz);
		
		if (isLand(cells, pos)) {
			if (isCave(cells, pos.setY(py))) {
				return mapCave.getBiome(px, 0, pz).biome;
			}
			return mapLand.getBiome(px, 0, pz).biome;
		}
		return mapVoid.getBiome(px, 0, pz).biome;
	}
	
	public void setSeed(long seed) {
		mapLand = new HexBiomeMap(seed, GeneratorOptions.biomeSizeLand, pickerLand);
		mapVoid = new HexBiomeMap(seed, GeneratorOptions.biomeSizeVoid, pickerVoid);
		mapCave = new HexBiomeMap(seed, GeneratorOptions.biomeSizeCave, pickerCave);
	}
	
	private void cleanCache(int x, int z) {
		if ((x & 63) == 0 && (z & 63) == 0) {
			terrainCache.clear();
			mapLand.clearCache();
			mapVoid.clearCache();
			mapCave.clearCache();
		}
	}
	
	private boolean isLand(TerrainCells cells, MutableBlockPos pos) {
		for (int py = EDEN_MIN_Y; py < EDEN_MAX_Y; py += LAND_SCAN_STEP) {
			if (sampleTerrainDensity(cells, pos.setY(py)) > 0.0F) {
				return true;
			}
		}
		return false;
	}
	
	private boolean isCave(TerrainCells cells, MutableBlockPos pos) {
		if (pos.getY() < 8 || pos.getY() > 240) return false;
		int baseY = pos.getY();
		boolean v1 = sampleTerrainDensity(cells, pos.setY(baseY)) > 0.0F;
		boolean v2 = sampleTerrainDensity(cells, pos.setY(baseY + 12)) > 0.0F;
		boolean v3 = sampleTerrainDensity(cells, pos.setY(baseY - 16)) > 0.0F;
		return v1 && v2 && v3;
	}

	private TerrainCells createTerrainCells(ChunkPos chunkPos) {
		TerrainGenerator generator = MultiThreadGenerator.getTerrainGenerator();
		BlockPos origin = new BlockPos(chunkPos.getMinBlockX(), 0, chunkPos.getMinBlockZ());
		InterpolationCell terrain = new InterpolationCell(
				generator,
				SAMPLE_WIDTH,
				SAMPLE_HEIGHT,
				SAMPLE_DXZ,
				SAMPLE_DY,
				origin
		);
		InterpolationCell smooth = new InterpolationCell(
				generator,
				SMOOTH_WIDTH,
				SMOOTH_HEIGHT,
				SAMPLE_DXZ,
				SAMPLE_DY,
				origin.offset(-4, -4, -4)
		);
		return new TerrainCells(terrain, smooth);
	}

	private float sampleTerrainDensity(TerrainCells cells, MutableBlockPos pos) {
		float densityA = cells.terrain.get(pos, false);
		float densityB = cells.smooth.get(pos, false);
		return (densityA + densityB) * 0.5F;
	}

	private static class TerrainCells {
		private final InterpolationCell terrain;
		private final InterpolationCell smooth;

		private TerrainCells(InterpolationCell terrain, InterpolationCell smooth) {
			this.terrain = terrain;
			this.smooth = smooth;
		}
	}

	@Override
	protected Stream<Holder<Biome>> collectPossibleBiomes() {
		return biomes.stream();
	}
}
