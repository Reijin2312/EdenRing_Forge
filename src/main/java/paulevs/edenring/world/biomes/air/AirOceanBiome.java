package paulevs.edenring.world.biomes.air;

import org.betterx.bclib.api.v2.levelgen.biomes.BCLBiomeBuilder;

import paulevs.edenring.registries.EdenBiomes;
import paulevs.edenring.registries.EdenEntities;
import paulevs.edenring.world.biomes.BiomesCommonMethods;
import paulevs.edenring.world.biomes.EdenRingBiome;

public class AirOceanBiome extends EdenRingBiome.Config {
    public AirOceanBiome() {
        super(EdenBiomes.AIR_OCEAN.location());
    }

    @Override
    protected void addCustomBuildData(BCLBiomeBuilder builder) {
        BiomesCommonMethods.addDefaultVoidFeatures(builder);
        builder.spawn(EdenEntities.DISKWING, 20, 3, 10);
    }
}
