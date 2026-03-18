package paulevs.edenring.integrations.jade;

import paulevs.edenring.EdenRing;
import paulevs.edenring.blocks.GravityCompressorBlock;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class JadePlugin implements IWailaPlugin {
	@Override
	public void registerClient(IWailaClientRegistration registration) {
		EdenRing.LOGGER.info("Registering Jade integration for EdenRing.");
		registration.registerBlockComponent(GravityCompressorProvider.INSTANCE, GravityCompressorBlock.class);
	}
}
