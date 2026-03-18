package paulevs.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.betterx.worlds.together.tag.v3.TagManager;
import paulevs.datagen.recipes.EdenRingBlockLootTableProvider;
import paulevs.datagen.recipes.EdenRingRecipeDataProvider;
import paulevs.datagen.worldgen.EdenRingBiomesDataProvider;
import paulevs.edenring.EdenRing;
import paulevs.edenring.registries.EdenParticles;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = EdenRing.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EdenRingDatagen {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        if (!event.includeServer()) {
            return;
        }

        TagManager.ensureStaticallyLoaded();
        EdenRingBiomesDataProvider.ensureStaticallyLoaded();
        EdenRingRecipeDataProvider.buildRecipes();
        EdenParticles.ensureStaticallyLoadedServerside();

        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> registriesFuture = event.getLookupProvider();

        RegistrySetBuilder registryBuilder = new RegistrySetBuilder();
        EdenRingRegistrySupplier.INSTANCE.bootstrapRegistries(registryBuilder);

        DatapackBuiltinEntriesProvider entriesProvider = new DatapackBuiltinEntriesProvider(
                output,
                registriesFuture,
                registryBuilder,
                Set.of(EdenRing.MOD_ID)
        );
        generator.addProvider(true, entriesProvider);
        CompletableFuture<HolderLookup.Provider> registryProvider = entriesProvider.getRegistryProvider();

        generator.addProvider(true, new EdenRingBiomesDataProvider(output, registryProvider, existingFileHelper));
        generator.addProvider(true, new EdenRingRecipeDataProvider(output));
        generator.addProvider(true, new EdenRingBlockLootTableProvider(output));
        generator.addProvider(true, new EdenRingBlockTagDataProvider(output, registryProvider, existingFileHelper));
        generator.addProvider(true, new EdenRingItemTagDataProvider(output, registryProvider, existingFileHelper));
    }
}
