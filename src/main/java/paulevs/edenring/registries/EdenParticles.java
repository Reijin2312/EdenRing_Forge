package paulevs.edenring.registries;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;

import net.minecraftforge.registries.RegisterEvent;

import paulevs.edenring.EdenRing;

public class EdenParticles {
	public static SimpleParticleType AURITIS_LEAF_PARTICLE;
	public static SimpleParticleType WIND_PARTICLE;
	public static SimpleParticleType YOUNG_VOLVOX;

	private static final ResourceKey<ParticleType<?>> KEY_AURITIS_LEAF_PARTICLE = key("auritis_leaf_particle");
	private static final ResourceKey<ParticleType<?>> KEY_WIND_PARTICLE = key("wind_particle");
	private static final ResourceKey<ParticleType<?>> KEY_YOUNG_VOLVOX = key("young_volvox");

	public static void onRegister(RegisterEvent event) {
		if (!event.getRegistryKey().equals(Registries.PARTICLE_TYPE)) {
			return;
		}

		event.register(Registries.PARTICLE_TYPE, helper -> {
			AURITIS_LEAF_PARTICLE = register(helper, KEY_AURITIS_LEAF_PARTICLE);
			WIND_PARTICLE = register(helper, KEY_WIND_PARTICLE);
			YOUNG_VOLVOX = register(helper, KEY_YOUNG_VOLVOX);
		});
	}

	private static SimpleParticleType register(
			RegisterEvent.RegisterHelper<ParticleType<?>> helper,
			ResourceKey<ParticleType<?>> key
	) {
		SimpleParticleType type = new SimpleParticleType(false);
		helper.register(key.location(), type);
		return type;
	}

	private static ResourceKey<ParticleType<?>> key(String id) {
		return ResourceKey.create(Registries.PARTICLE_TYPE, EdenRing.makeID(id));
	}

	public static void ensureStaticallyLoadedServerside() {
		// Particles are registered in onRegister via Forge RegisterEvent.
	}
}
