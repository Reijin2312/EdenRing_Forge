package paulevs.edenring.client.environment.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

public class EdenDimensionEffects extends DimensionSpecialEffects {
	private final EdenSkyRenderer skyRenderer;
	private final EdenCloudRenderer cloudRenderer;
	private final EdenWeatherRenderer weatherRenderer;

	public EdenDimensionEffects(EdenSkyRenderer skyRenderer, EdenCloudRenderer cloudRenderer, EdenWeatherRenderer weatherRenderer) {
		super(Float.NaN, false, SkyType.END, true, false);
		this.skyRenderer = skyRenderer;
		this.cloudRenderer = cloudRenderer;
		this.weatherRenderer = weatherRenderer;
	}

	@Override
	public Vec3 getBrightnessDependentFogColor(Vec3 color, float sunHeight) {
		return color.scale(0.15F);
	}

	@Override
	public float[] getSunriseColor(float timeOfDay, float partialTicks) {
		return null;
	}

	@Override
	public boolean isFoggyAt(int x, int z) {
		return false;
	}

	@Override
	public boolean renderSky(
			ClientLevel level,
			int ticks,
			float partialTick,
			PoseStack poseStack,
			Camera camera,
			Matrix4f projectionMatrix,
			boolean isFoggy,
			Runnable setupFog
	) {
		skyRenderer.render(level, poseStack, projectionMatrix, partialTick);
		return true;
	}

	@Override
	public boolean renderClouds(
			ClientLevel level,
			int ticks,
			float partialTick,
			PoseStack poseStack,
			double camX,
			double camY,
			double camZ,
			Matrix4f projectionMatrix
	) {
		cloudRenderer.render(level, poseStack, partialTick);
		return true;
	}

	@Override
	public boolean renderSnowAndRain(
			ClientLevel level,
			int ticks,
			float partialTick,
			LightTexture lightTexture,
			double camX,
			double camY,
			double camZ
	) {
		weatherRenderer.render(level, partialTick, lightTexture);
		return true;
	}
}
