package paulevs.edenring.blocks;

import com.google.common.collect.Maps;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockState;
import org.betterx.bclib.client.models.BasePatterns;
import org.betterx.bclib.client.models.ModelsHelper;
import org.betterx.bclib.client.models.PatternsHelper;
import org.betterx.bclib.interfaces.CustomItemProvider;
import paulevs.edenring.EdenRing;
import paulevs.edenring.items.AlaespesBlockItem;

import java.util.Map;
import java.util.Optional;

public class AlaespesBlock extends OverlayDoublePlantBlock implements CustomItemProvider {
	@Override
	public BlockItem getCustomItem(ResourceLocation resourceLocation, Item.Properties itemProperties) {
		return new AlaespesBlockItem(this, itemProperties);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public UnbakedModel getModelVariant(ResourceLocation stateId, BlockState blockState, Map<ResourceLocation, UnbakedModel> modelCache) {
		Optional<String> pattern;
		if (blockState.getValue(TOP)) {
			pattern = PatternsHelper.createJson(BasePatterns.BLOCK_EMPTY, EdenRing.makeID("alaespes_color"));
		}
		else {
			pattern = PatternsHelper.createJson(EdenRing.makeID("models/block/alaespes.json"), Maps.newHashMap());
		}
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
