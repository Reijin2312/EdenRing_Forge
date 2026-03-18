package paulevs.edenring.client.entities.renderers;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import paulevs.edenring.EdenRing;
import paulevs.edenring.client.entities.models.LilWormEntityModel;
import paulevs.edenring.entities.LilWormEntity;
import paulevs.edenring.registries.EdenEntitiesRenderers;

public class LilWormEntityRenderer extends MobRenderer<LilWormEntity, LilWormEntityModel> {
	private static final ResourceLocation TEXTURE = EdenRing.makeID("textures/entity/lil_worm.png");

	public LilWormEntityRenderer(Context context) {
		super(context, new LilWormEntityModel(context.bakeLayer(EdenEntitiesRenderers.LIL_WORM_MODEL)), 0.2F);
	}

	@Override
	public ResourceLocation getTextureLocation(LilWormEntity entity) {
		return TEXTURE;
	}
}
