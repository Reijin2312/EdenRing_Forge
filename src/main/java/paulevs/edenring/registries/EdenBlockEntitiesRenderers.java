package paulevs.edenring.registries;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import paulevs.edenring.blocks.entities.renderers.EdenPortalBlockEntityRenderer;

@OnlyIn(Dist.CLIENT)
public class EdenBlockEntitiesRenderers {
	public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerBlockEntityRenderer(EdenBlockEntities.EDEN_PORTAL, EdenPortalBlockEntityRenderer::new);
	}
}
