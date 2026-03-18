package paulevs.edenring;

import org.betterx.bclib.registry.RegistryBootstrap;

import net.minecraft.core.registries.Registries;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.registries.RegisterEvent;

import org.betterx.bclib.registry.BaseRegistry;
import paulevs.edenring.client.EdenRingForgeClientEvents;
import paulevs.edenring.paintings.EdenPaintings;
import paulevs.edenring.registries.EdenBlockEntities;
import paulevs.edenring.registries.EdenEntities;
import paulevs.edenring.registries.EdenParticles;
import paulevs.edenring.registries.EdenSounds;
import paulevs.edenring.world.generator.EdenBiomeSource;

@Mod(EdenRing.MOD_ID)
public class EdenRingForge {
    private final EdenRing edenRing = new EdenRing();

    public EdenRingForge() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        modBus.addListener(EventPriority.HIGHEST, this::ensureBlocksLoaded);
        modBus.addListener(EdenEntities::onRegister);
        modBus.addListener(EdenEntities::onRegisterAttributes);
        modBus.addListener(EdenBlockEntities::onRegister);
        modBus.addListener(EdenSounds::onRegister);
        modBus.addListener(EdenParticles::onRegister);
        modBus.addListener(EventPriority.HIGHEST, this::registerFeatures);
        modBus.addListener(EventPriority.HIGHEST, this::ensureItemsLoaded);
        modBus.addListener(EventPriority.LOWEST, RegistryBootstrap::register);
        modBus.addListener(this::registerPaintings);
        modBus.addListener(this::registerBiomeSourceCodec);
        modBus.addListener(this::onCommonSetup);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            modBus.addListener(EdenRingForgeClientEvents::onClientSetup);
            modBus.addListener(EdenRingForgeClientEvents::registerRenderers);
            modBus.addListener(EdenRingForgeClientEvents::registerLayerDefinitions);
            modBus.addListener(EdenRingForgeClientEvents::registerParticleProviders);
            modBus.addListener(EdenRingForgeClientEvents::registerDimensionEffects);
        });
        MinecraftForge.EVENT_BUS.addListener(EdenRing::onLootTableLoad);
    }

    private void onCommonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(edenRing::onInitialize);
    }

    private void ensureBlocksLoaded(RegisterEvent event) {
        if (!event.getRegistryKey().equals(Registries.BLOCK)) {
            return;
        }
        forceLoad("paulevs.edenring.registries.EdenBlocks");
        // EdenFeatures references EdenBlocks statics; loading here keeps block construction legal
        // and prepares BCLFeature entries before FEATURE registration occurs.
        forceLoad("paulevs.edenring.registries.EdenFeatures");
    }

    private void ensureItemsLoaded(RegisterEvent event) {
        if (!event.getRegistryKey().equals(Registries.ITEM)) {
            return;
        }
        forceLoad("paulevs.edenring.registries.EdenItems");
        EdenEntities.registerSpawnEggs();
        EdenRing.registerCreativeTabs();
        EdenRing.LOGGER.info(
                "Creative tab source sizes: blockItems="
                        + BaseRegistry.getModBlockItems(EdenRing.MOD_ID).size()
                        + ", items="
                        + BaseRegistry.getModItems(EdenRing.MOD_ID).size()
        );
    }

    private void registerFeatures(RegisterEvent event) {
        if (!event.getRegistryKey().equals(Registries.FEATURE)) {
            return;
        }
        forceLoad("paulevs.edenring.registries.EdenFeatures");
    }

    private void registerPaintings(RegisterEvent event) {
        if (!event.getRegistryKey().equals(Registries.PAINTING_VARIANT)) {
            return;
        }
        EdenPaintings.init();
    }

    private void registerBiomeSourceCodec(RegisterEvent event) {
        if (!event.getRegistryKey().equals(Registries.BIOME_SOURCE)) {
            return;
        }
        event.register(Registries.BIOME_SOURCE, helper -> helper.register(EdenRing.makeID("biome_source"), EdenBiomeSource.CODEC));
    }

    private static void forceLoad(String className) {
        try {
            Class.forName(className);
        } catch (ClassNotFoundException ignored) {
        }
    }
}
