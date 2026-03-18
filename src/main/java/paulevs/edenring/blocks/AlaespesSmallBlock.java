package paulevs.edenring.blocks;

import com.google.common.collect.Maps;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.betterx.bclib.client.models.ModelsHelper;
import org.betterx.bclib.client.models.PatternsHelper;

import java.util.Map;
import java.util.Optional;

public class AlaespesSmallBlock extends OverlayPlantBlock {
	public AlaespesSmallBlock() {
		super(true);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public BlockModel getBlockModel(ResourceLocation blockId, BlockState blockState) {
		String modId = blockId.getNamespace();
		Map<String, String> textures = Maps.newHashMap();
		textures.put("%texture%", modId + ":block/alaespes_color");
		textures.put("%overlay%", modId + ":block/alaespes_overlay");
		Optional<String> pattern = PatternsHelper.createJson(EdenPatterns.BLOCK_TINTED_CROSS_OVERLAY, textures);
		return ModelsHelper.fromPattern(pattern);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public BlockModel getItemModel(ResourceLocation itemID) {
		String modId = itemID.getNamespace();
		Map<String, String> textures = Maps.newHashMap();
		textures.put("%texture%", modId + ":block/alaespes_color");
		textures.put("%overlay%", modId + ":block/alaespes_overlay");
		Optional<String> pattern = PatternsHelper.createJson(EdenPatterns.ITEM_TINTED_OVERLAY, textures);
		return ModelsHelper.fromPattern(pattern);
	}
}
