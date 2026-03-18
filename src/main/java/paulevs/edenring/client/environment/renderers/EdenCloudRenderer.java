package paulevs.edenring.client.environment.renderers;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import paulevs.edenring.EdenRing;
import paulevs.edenring.client.EdenRingClient;
import paulevs.edenring.client.environment.TransformHelper;
import paulevs.edenring.client.environment.animation.SpriteGrid;
import paulevs.edenring.client.environment.weather.CloudAnimation;

@OnlyIn(Dist.CLIENT)
public class EdenCloudRenderer {
	private static final ResourceLocation CLOUDS = EdenRing.makeID("textures/environment/clouds.png");
	private SpriteGrid grid = new SpriteGrid(CloudAnimation::new, (biome, random) -> {
		if (biome == null) {
			// Handle null biome case (e.g., return a default value)
			return 0; // Or another appropriate value based on your logic
		}
		float fog = biome.settings.getFogDensity();
		if (fog > 1 && random.nextInt(5) > 0) {
			return (int) (random.nextFloat() * fog * 2);
		}
		return random.nextInt(16) == 0 ? 1 : 0;
	});


	
	@SuppressWarnings("resource")
	public void render(ClientLevel level, PoseStack poseStack, float tickDelta) {
		Minecraft minecraft = Minecraft.getInstance();
		Camera camera = minecraft.gameRenderer.getMainCamera();
		if (poseStack == null || camera == null || camera.getEntity() == null || level == null) {
			return;
		}
		
		// Init
		
		RenderSystem.enableBlend();
		RenderSystem.enableDepthTest();
		RenderSystem.depthMask(true);
		RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.setShaderTexture(0, CLOUDS);
		RenderSystem.defaultBlendFunc();
		
		// Start
		
		poseStack.pushPose();

		TransformHelper.applyPerspective(poseStack, camera);
		if (minecraft.player != null && minecraft.options.bobView().get() && EdenRingClient.hasIris()) {
			TransformHelper.fixBobbing(poseStack, minecraft.player, tickDelta);
		}
		
		ChunkPos pos = camera.getEntity().chunkPosition();
		int distance = minecraft.options.renderDistance().get();
		grid.render(level, pos, distance << 1 | 1, poseStack, camera, tickDelta, null);
		
		poseStack.popPose();
		
		// Finalise
		
		RenderSystem.enableCull();
		RenderSystem.disableBlend();
	}
}
