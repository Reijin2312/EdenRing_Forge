package paulevs.edenring.client;

import net.minecraftforge.client.event.RegisterDimensionSpecialEffectsEvent;
import net.minecraftforge.fml.ModList;
import paulevs.edenring.EdenRing;
import paulevs.edenring.client.environment.renderers.EdenCloudRenderer;
import paulevs.edenring.client.environment.renderers.EdenDimensionEffects;
import paulevs.edenring.client.environment.renderers.EdenSkyRenderer;
import paulevs.edenring.client.environment.renderers.EdenWeatherRenderer;
import paulevs.edenring.config.Configs;

public final class EdenRingClient {
	public static final EdenClientConfig CLIENT_CONFIG = new EdenClientConfig();
	private static final EdenSkyRenderer SKY_RENDERER = new EdenSkyRenderer();
	private static final EdenCloudRenderer CLOUD_RENDERER = new EdenCloudRenderer();
	private static final EdenWeatherRenderer WEATHER_RENDERER = new EdenWeatherRenderer();
	private static boolean hasIris;

	private EdenRingClient() {
	}

	public static void onInitializeClient() {
		Configs.CLIENT_CONFIG.saveChanges();
		hasIris = ModList.get().isLoaded("iris") || ModList.get().isLoaded("oculus");
	}

	public static void registerDimensionEffects(RegisterDimensionSpecialEffectsEvent event) {
		event.register(EdenRing.EDEN_RING_TYPE_KEY.location(), new EdenDimensionEffects(SKY_RENDERER, CLOUD_RENDERER, WEATHER_RENDERER));
	}
	
	public static boolean hasIris() {
		return hasIris;
	}
}
