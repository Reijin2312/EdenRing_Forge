package paulevs.edenring.registries;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import paulevs.edenring.particles.LeafParticle;
import paulevs.edenring.particles.OscillatingParticle;
import paulevs.edenring.particles.WindParticle;

@OnlyIn(Dist.CLIENT)
public class EdenParticleFactories {
	public static void register(RegisterParticleProvidersEvent event) {
		event.registerSpriteSet(EdenParticles.AURITIS_LEAF_PARTICLE, LeafParticle.ParticleFactory::new);
		event.registerSpriteSet(EdenParticles.WIND_PARTICLE, WindParticle.ParticleFactory::new);
		event.registerSpriteSet(EdenParticles.YOUNG_VOLVOX, OscillatingParticle.ParticleFactory::new);
	}
}
