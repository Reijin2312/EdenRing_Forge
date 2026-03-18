package paulevs.edenring.client.environment.renderers;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import paulevs.edenring.EdenRing;
import paulevs.edenring.client.EdenRingClient;
import paulevs.edenring.client.environment.TransformHelper;
import paulevs.edenring.client.environment.animation.SpriteGrid;
import paulevs.edenring.client.environment.weather.LightningAnimation;
import paulevs.edenring.registries.EdenBiomes;

public class EdenWeatherRenderer {
	private static final ResourceLocation LIGHTNING = EdenRing.makeID("textures/environment/lightning.png");
	private SpriteGrid grid = new SpriteGrid(LightningAnimation::new, (biome, random) -> {
		// Check for null biome before accessing getBiomeKey()
		return biome != null && biome.getBiomeKey() == EdenBiomes.BRAINSTORM ? random.nextInt(3) : 0;
	});

	@SuppressWarnings("resource")
	public void render(ClientLevel level, float tickDelta, LightTexture lightTexture) {
		PoseStack poseStack = new PoseStack();
		Minecraft minecraft = Minecraft.getInstance();
		Camera camera = minecraft.gameRenderer.getMainCamera();
		if (camera == null || camera.getEntity() == null || level == null) {
			return;
		}

		// Init

		RenderSystem.enableBlend();
		RenderSystem.enableDepthTest();
		RenderSystem.depthMask(true);
		RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.setShaderTexture(0, LIGHTNING);
		lightTexture.turnOnLightLayer();

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
		lightTexture.turnOffLightLayer();
	}
}
