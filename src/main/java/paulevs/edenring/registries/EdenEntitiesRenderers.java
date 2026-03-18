package paulevs.edenring.registries;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.client.event.EntityRenderersEvent;
import paulevs.edenring.EdenRing;
import paulevs.edenring.client.entities.models.DiskwingEntityModel;
import paulevs.edenring.client.entities.models.LilWormEntityModel;
import paulevs.edenring.client.entities.renderers.DiskwingEntityRenderer;
import paulevs.edenring.client.entities.renderers.EdenPaintingRenderer;
import paulevs.edenring.client.entities.renderers.LightningRayRenderer;
import paulevs.edenring.client.entities.renderers.LilWormEntityRenderer;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("all")
public class EdenEntitiesRenderers {
	public static final ModelLayerLocation LIGHTNING_RAY_MODEL = registerMain("lightning_ray");
	public static final ModelLayerLocation DISKWING_MODEL = registerMain("diskwing");
	public static final ModelLayerLocation LIL_WORM_MODEL = registerMain("lil_worm");
	
	public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(EdenEntities.LIGHTNING_RAY, LightningRayRenderer::new);
		event.registerEntityRenderer(EdenEntities.LIMPHIUM_PAINTING, EdenPaintingRenderer::new);
		event.registerEntityRenderer(EdenEntities.DISKWING, DiskwingEntityRenderer::new);
		event.registerEntityRenderer(EdenEntities.LIL_WORM, LilWormEntityRenderer::new);
	}

	public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(DISKWING_MODEL, DiskwingEntityModel::getTexturedModelData);
		event.registerLayerDefinition(LIL_WORM_MODEL, LilWormEntityModel::getTexturedModelData);
	}
	
	private static ModelLayerLocation registerMain(String id) {
		return new ModelLayerLocation(EdenRing.makeID(id), "main");
	}
}
