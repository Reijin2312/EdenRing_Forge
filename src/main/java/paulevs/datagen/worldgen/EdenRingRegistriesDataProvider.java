package paulevs.datagen.worldgen;

import java.util.concurrent.CompletableFuture;

import org.betterx.bclib.api.v3.datagen.RegistriesDataProvider;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import paulevs.edenring.EdenRing;
import paulevs.datagen.EdenRingRegistrySupplier;

public class EdenRingRegistriesDataProvider extends RegistriesDataProvider {
    public EdenRingRegistriesDataProvider(
            PackOutput output,
            CompletableFuture<HolderLookup.Provider> registriesFuture
    ) {
        super(EdenRing.LOGGER, EdenRingRegistrySupplier.INSTANCE, output, registriesFuture);
    }
}
