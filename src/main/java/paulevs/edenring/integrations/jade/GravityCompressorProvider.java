package paulevs.edenring.integrations.jade;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import paulevs.edenring.EdenRing;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public enum GravityCompressorProvider implements IBlockComponentProvider {
	INSTANCE;

	public static final ResourceLocation ID = EdenRing.makeID("gravity_compressor");

	@Override
	public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
		BlockState state = accessor.getBlockState();
		int power = state.hasProperty(BlockStateProperties.POWER) ? state.getValue(BlockStateProperties.POWER) : 0;

		tooltip.add(Component.translatable("edenring.jade.gravity_compressor.power", power));
		if (power > 0) {
			tooltip.add(Component.translatable("edenring.jade.gravity_compressor.status.active"));
		}
		else {
			tooltip.add(Component.translatable("edenring.jade.gravity_compressor.status.inactive"));
		}
	}

	@Override
	public ResourceLocation getUid() {
		return ID;
	}
}
