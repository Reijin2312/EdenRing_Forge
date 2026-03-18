package paulevs.edenring.world.biomes;

import org.betterx.bclib.api.v2.levelgen.biomes.BiomeAPI;
import org.betterx.bclib.api.v2.levelgen.biomes.BiomeAPI.BiomeType;
import paulevs.edenring.registries.EdenBiomes;
import paulevs.edenring.world.biomes.air.AirOceanBiome;
import paulevs.edenring.world.biomes.air.GraviliteDebrisFieldBiome;
import paulevs.edenring.world.biomes.air.SkyColonyBiome;
import paulevs.edenring.world.biomes.cave.EmptyCaveBiome;
import paulevs.edenring.world.biomes.cave.ErodedCaveBiome;
import paulevs.edenring.world.biomes.land.BrainStormBiome;
import paulevs.edenring.world.biomes.land.GoldenForestBiome;
import paulevs.edenring.world.biomes.land.LakesideDesertBiome;
import paulevs.edenring.world.biomes.land.MycoticForestBiome;
import paulevs.edenring.world.biomes.land.OldMycoticForestBiome;
import paulevs.edenring.world.biomes.land.PulseForestBiome;
import paulevs.edenring.world.biomes.land.StoneGardenBiome;
import paulevs.edenring.world.biomes.land.WindValleyBiome;

import java.util.ArrayList;
import java.util.List;

public final class EdenBiomeLists {
    public static final List<EdenRingBiome> BIOMES_LAND = new ArrayList<>();
    public static final List<EdenRingBiome> BIOMES_AIR = new ArrayList<>();
    public static final List<EdenRingBiome> BIOMES_CAVE = new ArrayList<>();

    // LAND //
    public static final EdenRingBiome STONE_GARDEN = registerLand(new StoneGardenBiome());
    public static final EdenRingBiome GOLDEN_FOREST = registerLand(new GoldenForestBiome());
    public static final EdenRingBiome MYCOTIC_FOREST = registerLand(new MycoticForestBiome());
    public static final EdenRingBiome PULSE_FOREST = registerLand(new PulseForestBiome());
    public static final EdenRingBiome BRAINSTORM = registerLand(new BrainStormBiome());
    public static final EdenRingBiome LAKESIDE_DESERT = registerLand(new LakesideDesertBiome());
    public static final EdenRingBiome WIND_VALLEY = registerLand(new WindValleyBiome());

    // AIR //
    public static final EdenRingBiome AIR_OCEAN = registerVoid(new AirOceanBiome());
    public static final EdenRingBiome GRAVILITE_DEBRIS_FIELD = registerVoid(new GraviliteDebrisFieldBiome());
    public static final EdenRingBiome SKY_COLONY = registerVoid(new SkyColonyBiome());

    // CAVES //
    public static final EdenRingBiome EMPTY_CAVE = registerCave(new EmptyCaveBiome());
    public static final EdenRingBiome ERODED_CAVE = registerCave(new ErodedCaveBiome());

    // SUBBIOMES //
    public static final EdenRingBiome OLD_MYCOTIC_FOREST = registerSubBiome(new OldMycoticForestBiome(), MYCOTIC_FOREST);

    private EdenBiomeLists() {
    }

    public static void ensureLoaded() {
    }

    private static EdenRingBiome registerBiome(EdenRingBiome.Config biomeConfig, BiomeAPI.BiomeType type) {
        return EdenRingBiome.create(biomeConfig, type);
    }

    private static EdenRingBiome registerLand(EdenRingBiome.Config biomeConfig) {
        EdenRingBiome biome = registerBiome(biomeConfig, EdenBiomes.EDEN_LAND);
        BIOMES_LAND.add(biome);
        return biome;
    }

    private static EdenRingBiome registerVoid(EdenRingBiome.Config biomeConfig) {
        EdenRingBiome biome = registerBiome(biomeConfig, EdenBiomes.EDEN_LAND);
        BIOMES_AIR.add(biome);
        return biome;
    }

    private static EdenRingBiome registerCave(EdenRingBiome.Config biomeConfig) {
        EdenRingBiome biome = registerBiome(biomeConfig, EdenBiomes.EDEN_LAND);
        BIOMES_CAVE.add(biome);
        return biome;
    }

    private static EdenRingBiome registerSubBiome(EdenRingBiome.Config biomeConfig, EdenRingBiome parent) {
        EdenRingBiome biome = EdenRingBiome.createSubBiome(biomeConfig, parent);
        BiomeType type = parent.getIntendedType();
        if (type == EdenBiomes.EDEN_LAND) {
            BIOMES_LAND.add(biome);
        } else if (type == EdenBiomes.EDEN_VOID) {
            BIOMES_AIR.add(biome);
        } else if (type == EdenBiomes.EDEN_CAVE) {
            BIOMES_CAVE.add(biome);
        }
        return biome;
    }
}
