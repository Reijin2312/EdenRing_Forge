package paulevs.edenring.blocks;

import com.google.common.collect.Maps;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.betterx.bclib.blocks.BaseRotatedPillarBlock;
import org.betterx.bclib.client.models.ModelsHelper;
import org.betterx.bclib.client.models.PatternsHelper;

import java.util.Map;
import java.util.Optional;

public class GraviliteBlock extends BaseRotatedPillarBlock {
	public GraviliteBlock() {
		super(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK).lightLevel(state -> 15));
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public UnbakedModel getModelVariant(ResourceLocation stateId, BlockState blockState, Map<ResourceLocation, UnbakedModel> modelCache) {
		ResourceLocation pillarUp = new ResourceLocation(
			stateId.getNamespace(),
			"block/" + stateId.getPath() + "_pillar_up"
		);
		
		if (!modelCache.containsKey(pillarUp)) {
			Map<String, String> textures = Maps.newHashMap();
			String modId = stateId.getNamespace();
			String name = stateId.getPath();
			textures.put("%side%", modId + ":block/" + name + "_side");
			textures.put("%end%", modId + ":block/" + name + "_top");
			Optional<String> pattern = PatternsHelper.createJson(EdenPatterns.BLOCK_PILLAR_NO_SHADE, textures);
			modelCache.put(pillarUp, ModelsHelper.fromPattern(pattern));
		}
		
		return ModelsHelper.createRotatedModel(pillarUp, blockState.getValue(AXIS));
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public BlockModel getItemModel(ResourceLocation itemID) {
		Map<String, String> textures = Maps.newHashMap();
		String modId = itemID.getNamespace();
		String name = itemID.getPath();
		textures.put("%side%", modId + ":block/" + name + "_side");
		textures.put("%end%", modId + ":block/" + name + "_top");
		Optional<String> pattern = PatternsHelper.createJson(EdenPatterns.BLOCK_PILLAR_NO_SHADE, textures);
		return ModelsHelper.fromPattern(pattern);
	}
}
