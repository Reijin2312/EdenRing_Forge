package paulevs.edenring.client;

import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterDimensionSpecialEffectsEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import paulevs.edenring.registries.EdenBlockEntitiesRenderers;
import paulevs.edenring.registries.EdenEntitiesRenderers;
import paulevs.edenring.registries.EdenParticleFactories;

public final class EdenRingForgeClientEvents {
    private EdenRingForgeClientEvents() {
    }

    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(EdenRingClient::onInitializeClient);
    }

    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        EdenEntitiesRenderers.registerRenderers(event);
        EdenBlockEntitiesRenderers.registerRenderers(event);
    }

    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        EdenEntitiesRenderers.registerLayerDefinitions(event);
    }

    public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
        EdenParticleFactories.register(event);
    }

    public static void registerDimensionEffects(RegisterDimensionSpecialEffectsEvent event) {
        EdenRingClient.registerDimensionEffects(event);
    }
}
